package com.sweeftdigital.productmanagementapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;

    @NotEmpty
    private String name;

    private String description;

    private BigDecimal weight;

    @Positive
    private BigDecimal price;

    @NotEmpty
    private String country;
}
