package com.sweeftdigital.productmanagementapi.controller;

import com.sweeftdigital.productmanagementapi.model.ProductDto;
import com.sweeftdigital.productmanagementapi.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api")
@Slf4j
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(path = "/product")
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductDto productDto) {

        ProductDto product = productService.createProduct(productDto);

        log.info("Created product with name {}", productDto.getName());

        return new ResponseEntity(product, HttpStatus.CREATED);
    }

    @PutMapping(path = "/product")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody @Valid ProductDto productDto) {

        ProductDto product = productService.updateProduct(productDto);

        log.info("Updated product with id = {} and name = {}", product.getId(), productDto.getName());

        return new ResponseEntity(product, HttpStatus.OK);
    }

    @DeleteMapping(path = "/product/{id}")
    public ResponseEntity deleteProduct(@PathVariable @NotNull Long id) {

        productService.deleteProduct(id);

        log.info("Deleted product with id {}", id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/products")
    public ResponseEntity<Page<ProductDto>> getProducts(String name, Pageable pageable) {

        log.info("Get products with name - {}, page number - {}, page size - {}, is sorted - {}",
                name, pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().isSorted());

        Page<ProductDto> products = productService.getProducts(name, pageable);

        return new ResponseEntity(products, HttpStatus.OK);
    }
}
