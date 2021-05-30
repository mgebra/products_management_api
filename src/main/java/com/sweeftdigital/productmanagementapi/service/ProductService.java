package com.sweeftdigital.productmanagementapi.service;

import com.sweeftdigital.productmanagementapi.entity.Product;
import com.sweeftdigital.productmanagementapi.model.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto);

    void deleteProduct(Long id);

    Page<ProductDto> getProducts(String name, Pageable pageable);
}
