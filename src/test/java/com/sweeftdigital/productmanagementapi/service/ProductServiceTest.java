package com.sweeftdigital.productmanagementapi.service;

import com.sweeftdigital.productmanagementapi.entity.Product;
import com.sweeftdigital.productmanagementapi.model.ProductDto;
import com.sweeftdigital.productmanagementapi.model.mapper.ProductMapper;
import com.sweeftdigital.productmanagementapi.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTest {

    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Before
    public void setUp() {
        Product product = Product.builder()
                .id(1L)
                .name("test")
                .description("test desc")
                .weight(BigDecimal.ONE)
                .price(BigDecimal.TEN)
                .country("Georgia")
                .createDate(LocalDateTime.now())
                .build();

        Product product2 = Product.builder()
                .id(2L)
                .name("test 2")
                .description("test desc 2")
                .weight(BigDecimal.ONE)
                .price(BigDecimal.TEN)
                .country("USA")
                .createDate(LocalDateTime.now())
                .build();

        PageImpl<Product> products = new PageImpl<>(Arrays.asList(product, product2), PageRequest.of(0, 2), 2);

        Mockito.when(productRepository.save(any(Product.class)))
                .thenReturn(product);

        Mockito.when(productRepository.findById(any(Long.class)))
                .thenReturn(Optional.ofNullable(product));

        Mockito.when(productRepository.findAllByName(any(String.class), any(Pageable.class)))
                .thenReturn(products);

        productService = new ProductServiceImpl(productRepository, new ProductMapper());
    }

    @Test
    public void whenCreatedProduct_mustCreate() {
        ProductDto dto = productService.createProduct(new ProductDto());

        assertEquals("Name should be test", dto.getName(), "test");
        assertEquals("Country should be Georgia", dto.getCountry(), "Georgia");
    }

    @Test
    public void whenGetProducts_mustBeTwoProducts() {
        Page<ProductDto> products = productService.getProducts("test", PageRequest.of(0, 2));

        assertEquals("Total products should be two", products.getTotalElements(), 2L);
    }

}
