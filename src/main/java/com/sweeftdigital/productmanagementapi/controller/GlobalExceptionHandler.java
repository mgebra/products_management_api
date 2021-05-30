package com.sweeftdigital.productmanagementapi.controller;

import com.sweeftdigital.productmanagementapi.exception.AppException;
import com.sweeftdigital.productmanagementapi.exception.ErrorCode;
import com.sweeftdigital.productmanagementapi.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final HttpServletResponse httpResponse;

    @Autowired
    public GlobalExceptionHandler(HttpServletResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(e.getMessage(), e);
        Map<String, Object> errors = new HashMap<>();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors().stream()
                .map(a -> new FieldError(a.getObjectName(), a.getField(), a.getRejectedValue(), a.isBindingFailure(), null, null, a.getDefaultMessage())).collect(Collectors.toList());
        errors.put("fieldErrors", fieldErrors);

        List<ObjectError> globalErrors = e.getBindingResult().getGlobalErrors().stream().map(a -> new ObjectError(a.getObjectName(), a.getDefaultMessage())).collect(Collectors.toList());
        errors.put("globalErrors", globalErrors);

        ErrorResponse errorResponse = buildResponse(400, ErrorCode.BAD_REQUEST, errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(AppException.class)
    public ErrorResponse handleError(AppException e) {
        log.error(e.getMessage(), e);

        return buildResponse(e.getErrorCode().getStatus(), e.getErrorCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleError(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        Map<String, Object> errors = new HashMap<>();
        Set<String> violations =
                e.getConstraintViolations().stream().map(a -> a.getMessage()).collect(Collectors.toSet());
        errors.put("violations", violations);

        ErrorResponse errorResponse = buildResponse(400, ErrorCode.BAD_REQUEST, errors);
        return errorResponse;
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        log.error(e.getMessage(), e);

        return new ResponseEntity<>(buildResponse(HttpStatus.BAD_REQUEST.value(), ErrorCode.BAD_REQUEST, error), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleError(Exception e) {
        log.error(e.getMessage(), e);

        return buildResponse(HttpStatus.BAD_REQUEST.value(), ErrorCode.SYSTEM_ERROR);
    }

    private ErrorResponse buildResponse(int httpStatus, ErrorCode errorCode) {
        httpResponse.setStatus(httpStatus);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(errorCode.getMessage());
        errorResponse.setCode(errorCode);

        return errorResponse;
    }

    private ErrorResponse buildResponse(int httpStatus, ErrorCode errorCode, String message) {
        httpResponse.setStatus(httpStatus);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(message);
        errorResponse.setCode(errorCode);

        return errorResponse;
    }

    private ErrorResponse buildResponse(int statusCode, ErrorCode errorCode, Map<String, Object> errors) {
        ErrorResponse errorResponse = buildResponse(statusCode, errorCode);
        errorResponse.setData(errors);

        return errorResponse;
    }
}
