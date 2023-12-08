package com.example.plusone.discount.controller;


import com.example.plusone.discount.dto.ProductDto;
import com.example.plusone.discount.entity.Discount;
import com.example.plusone.discount.entity.Product;
import com.example.plusone.discount.service.DiscountService;
import com.example.plusone.discount.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DiscountController {

    private final DiscountService discountService;


    @RequestMapping("aaaa")
    public void saveA(){

    }

    @PutMapping("/api/v1/convenience-stores/discounts")
    public ResponseEntity saveProduct(@RequestBody ProductDto productDto){
        log.info(productDto.toString());
        discountService.putProduct(productDto);

        // 나중에 저장 되고 안되고에 따라 바꿀예정이지만 일단...
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/v1/convenience-stores/discounts/bulk")
    public ResponseEntity saveProductList(@RequestBody List<ProductDto> productDtos){
        discountService.putProducts(productDtos);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/convenience-stores/discounts/{id}")
    public ResponseEntity getProduct(@PathVariable String id){

        Product product = discountService.getProduct(id);
        return ResponseEntity.status(HttpStatus.OK.value()).body(product);

    }



}
