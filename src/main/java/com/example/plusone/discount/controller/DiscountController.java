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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
        System.out.println("put API");
        return ResponseEntity.ok().build();
    }



}
