package com.sweeftdigital.productmanagementapi.repository;

import com.sweeftdigital.productmanagementapi.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    Page<Product> findAllByName(String name, Pageable pageable);
}
