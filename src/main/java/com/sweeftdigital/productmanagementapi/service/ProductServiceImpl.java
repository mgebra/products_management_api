package com.sweeftdigital.productmanagementapi.service;

import com.sweeftdigital.productmanagementapi.entity.Product;
import com.sweeftdigital.productmanagementapi.exception.AppException;
import com.sweeftdigital.productmanagementapi.exception.ErrorCode;
import com.sweeftdigital.productmanagementapi.model.ProductDto;
import com.sweeftdigital.productmanagementapi.model.mapper.ProductMapper;
import com.sweeftdigital.productmanagementapi.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        product.setCreateDate(LocalDateTime.now());

        product = productRepository.save(product);

        return productMapper.toDto(product);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        Product product = getProductOrThrow(productDto.getId());

        product = productMapper.toEntity(productDto, product);
        productRepository.save(product);

        return productMapper.toDto(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = getProductOrThrow(id);

        productRepository.delete(product);
    }

    @Override
    public Page<ProductDto> getProducts(String name, Pageable pageable) {
        Page<Product> page = (name == null || name.isEmpty()) ?
                productRepository.findAll(pageable) :
                productRepository.findAllByName(name, pageable);

        List<ProductDto> response = page.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());

        return new PageImpl<>(response, pageable, page.getTotalElements());
    }

    private Product getProductOrThrow(Long id) {
        Optional<Product> byId = productRepository.findById(id);

        return byId.orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
    }
}
