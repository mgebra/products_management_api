package com.sweeftdigital.productmanagementapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweeftdigital.productmanagementapi.model.ProductDto;
import com.sweeftdigital.productmanagementapi.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    @Test
    public void createProductTest()
            throws Exception {

        ProductDto product = ProductDto.builder()
                .id(1L)
                .name("test")
                .description("test desc")
                .weight(BigDecimal.ONE)
                .price(BigDecimal.TEN)
                .country("Georgia")
                .build();

        given(productService.createProduct(any())).willReturn(product);

        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult result = this.mvc.perform(post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        ProductDto productDto = objectMapper.readValue(contentAsString, ProductDto.class);

        assertThat(productDto.getName().equals(product.getName())).isEqualTo(true);
    }
}
