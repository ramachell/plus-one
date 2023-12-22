package com.example.plusone.discount.controller;


import com.example.plusone.discount.dto.*;
import com.example.plusone.discount.gs25.dto.Gs25PreDto;
import com.example.plusone.discount.gs25.dto.Gs25Product;
import com.example.plusone.discount.service.DiscountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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
    public ResponseEntity putdata(@RequestBody Gs25PreDto gs25PreDtos){
        return null;
    }

    @PostMapping("/api/v1/convenience-stores/discounts/insert/gs25")
    public ResponseEntity insertGs25(Gs25Dto gs25Dto){

        ProductDto productDto = ProductDto.builder()
                .name(gs25Dto.getName())
                .image_url(gs25Dto.getImg())
                .price(gs25Dto.getPrice())
                .discountType(gs25Dto.getType())
                .build();

        discountService.putProduct(productDto);

        return null;
    }

    @PostMapping("/api/v1/convenience-stores/discounts/insert/7_eleven")
    public ResponseEntity insert7_eleven(){
        // 비상... gs25랑은 너무나 다른방식....

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


//    @GetMapping("/api/v1/convenience-stores/discounts/insert/gs25")
//    public ResponseEntity insertGs25(){
//        Map<String,Object> map = discountService.insertGs25();
//
//        ResponseDto<Object> responseDto = ResponseDto.builder()
//                .code("SUCCESS")
//                .timestamp(String.valueOf(new Timestamp(System.currentTimeMillis())))
//                .path("여기 어케하지... 비워야하나")
//                .message("성공")
//                .error("ERROR")
//                .status(HttpStatus.OK.value())
//                .build();
//
//        return ResponseEntity.status(HttpStatus.OK.value()).body(map);
//    }

    @PutMapping("/api/v1/convenience-stores/discounts/insert/gs25")
    public ResponseEntity test(@RequestBody Gs25PreDto gs25PreDto){
        Gs25Product gs25Product = gs25PreDto.getResults().get(0);
        log.info(gs25Product.toString());
        ProductDto productDto = ProductDto.builder()
                .name(gs25Product.getGoodsNm())
                .price((int) gs25Product.getPrice())
                .image_url(gs25Product.getAttFileId())
                .description("상품1")
                .build();

        discountService.putProduct(productDto);

        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }

    @GetMapping("/api/v1/convenience-stores/discounts/insert/gs25_2")
    public ResponseEntity saveGs25(@RequestBody Gs25SearchDto gs25SearchDto){
        discountService.insertGs25(gs25SearchDto);
        return null;

    }











}
