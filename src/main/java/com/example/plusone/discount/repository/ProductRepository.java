package com.example.plusone.discount.repository;

import com.example.plusone.discount.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String > {
}
