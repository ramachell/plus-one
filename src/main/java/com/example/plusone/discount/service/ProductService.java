package com.example.plusone.discount.service;

import com.example.plusone.discount.entity.Product;
import com.example.plusone.discount.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void putProduct(Product product){
        productRepository.save(product);
    };
}
