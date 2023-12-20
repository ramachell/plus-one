package com.example.plusone.discount.controller;


import com.example.plusone.discount.dto.*;
import com.example.plusone.discount.entity.Product;
import com.example.plusone.discount.gs25.dto.Gs25Dto;
import com.example.plusone.discount.gs25.dto.Gs25Product;
import com.example.plusone.discount.openfeign.OpenFeign;
import com.example.plusone.discount.service.DiscountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    @GetMapping("/api/v1/convenience-stores/discounts/insert/gs25")
    public ResponseEntity insertGs25(){
        Map<String,Object> map = discountService.insertGs25();

        ResponseDto responseDto = ResponseDto.builder()
                .code("SUCCESS")
                .timestamp(String.valueOf(new Timestamp(System.currentTimeMillis())))
                .path("여기 어케하지... 비워야하나")
                .message("성공")
                .error("ERROR")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.status(HttpStatus.OK.value()).body(map);
    }

    @PutMapping("/api/v1/convenience-stores/discounts/insert/gs25")
    public ResponseEntity test(@RequestBody Gs25Dto gs25Dto){
        Gs25Product gs25Product = gs25Dto.getResults().get(0);
        log.info(gs25Product.toString());
        ProductDto productDto = ProductDto.builder()
                .name(gs25Product.getGoodsNm())
                .price((int) gs25Product.getPrice())
                .image_url(gs25Product.getAttFileId())
                .description("상품1")
                .build();
        discountService.putProduct(productDto);


        return null;
    }

    // TODO
    // 1. 외부 gs25에 접속해서 상품 리스트를 받아오는 api
    // 2. 받아온 리스트를 DB에 저장할수 있게 바꾸는 api
    // 3. 그 리스트를 저장하는 것









}
