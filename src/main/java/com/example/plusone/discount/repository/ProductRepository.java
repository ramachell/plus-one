package com.example.plusone.discount.repository;

import com.example.plusone.discount.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String > {
    List<Product> findAllByDiscountType(String filterDiscountType);

    List<Product> findAllByNameContainsAndDiscountType(String query, String discountType);
}
