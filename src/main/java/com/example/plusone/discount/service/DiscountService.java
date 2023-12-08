package com.example.plusone.discount.service;

import com.example.plusone.discount.dto.ProductDto;
import com.example.plusone.discount.entity.Discount;
import com.example.plusone.discount.entity.Product;
import com.example.plusone.discount.mapper.DiscountMapper;
import com.example.plusone.discount.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscountService {

    private final ProductRepository productRepository;


    public void putProduct(ProductDto productDto){
        productDto.setId(UUID.randomUUID().toString());
        log.info(productDto.toString());
        productRepository.save(DiscountMapper.INSTANCE.toEntity(productDto));

    }
}
