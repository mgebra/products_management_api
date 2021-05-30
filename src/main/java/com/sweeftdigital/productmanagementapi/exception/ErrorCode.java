package com.sweeftdigital.productmanagementapi.exception;

public enum ErrorCode {

    BAD_REQUEST("Bad request", 400),
    NOT_FOUND("Not found", 404),
    SYSTEM_ERROR("System error", 500);

    private final String message;
    private final Integer status;

    ErrorCode(String message, Integer status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public Integer getStatus() {
        return status;
    }
}