package com.sweeftdigital.productmanagementapi.model.mapper;

import com.sweeftdigital.productmanagementapi.entity.Product;
import com.sweeftdigital.productmanagementapi.model.ProductDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductMapper {

    public ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .weight(product.getWeight())
                .price(product.getPrice())
                .country(product.getCountry())
                .build();
    }

    public Product toEntity(ProductDto dto) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .weight(dto.getWeight())
                .price(dto.getPrice())
                .country(dto.getCountry())
                .build();
    }

    public Product toEntity(ProductDto dto, Product product) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setWeight(dto.getWeight());
        product.setPrice(dto.getPrice());
        product.setCountry(dto.getCountry());
        product.setUpdateDate(LocalDateTime.now());

        return product;
    }
}
