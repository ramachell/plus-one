package com.example.plusone.discount.controller;


import com.example.plusone.discount.dto.*;
import com.example.plusone.discount.entity.Discount;
import com.example.plusone.discount.entity.Product;
import com.example.plusone.discount.service.DiscountService;
import com.example.plusone.discount.service.ProductService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.crypto.KeyAgreementSpi;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DiscountController {

    private final DiscountService discountService;


    @RequestMapping("aaaa")
    public void saveA(){

    }

    @PutMapping("/api/v1/convenience-stores/discounts/putdata")
    public ResponseEntity putdata(@RequestBody Gs25Dto gs25Dto){
        return null;
    }

    @PostMapping("/gs25/save")
    public ResponseEntity saveDB(Gs25_2Dto gs25_2Dto){

        ProductDto productDto = ProductDto.builder()
                .name(gs25_2Dto.getName())
                .image_url(gs25_2Dto.getImg())
                .price(gs25_2Dto.getPrice())
                .discountType(gs25_2Dto.getType())
                .description("test")
                .build();

        discountService.putProduct(productDto);

        return null;
    }

    @PutMapping("/api/v1/convenience-stores/discounts")
    public ResponseEntity saveProduct(@RequestBody ProductDto productDto){
//        log.info(productDto.toString());
        discountService.putProduct(productDto);

        // 나중에 저장 되고 안되고에 따라 바꿀예정이지만 일단...
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @PutMapping("/api/v1/convenience-stores/discounts/bulk")
    public ResponseEntity saveProductList(@RequestBody List<ProductDto> productDtos){
        discountService.putProducts(productDtos);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/convenience-stores/discounts/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String id){
        ProductDto productDto = discountService.getProduct(id);
        return ResponseEntity.status(HttpStatus.OK.value()).body(productDto);

    }

    @GetMapping("/api/v1/convenience-stores/discounts")
    public ResponseEntity productFilter(@RequestParam String filter_discount_type){
//        log.info(filter_discount_type);
        List<ProductDto> productDtoList = discountService.productFilter(filter_discount_type);
//        log.info(productDtoList.toString());
        return ResponseEntity.status(HttpStatus.OK).body(productDtoList);
    }


    @PostMapping("/api/v1/convenience-stores/discounts/search")
    public ResponseEntity searchProduct(@RequestBody SearchDto searchDto){
        List<ProductDto> productDtoList = discountService.searchProduct(searchDto);
//        log.info(productDtoList.toString());

        ResponseDto responseDto = ResponseDto.builder()
                .code("SUCCESS")
                .timestamp(String.valueOf(new Timestamp(System.currentTimeMillis())))
                .path("여기 어케하지... 비워야하나")
                .message("성공")
                .error("ERROR")
                .status(HttpStatus.OK.value())
                .data(productDtoList)
                .build();

        return ResponseEntity.status(HttpStatus.OK.value()).body(responseDto);
    }





}
