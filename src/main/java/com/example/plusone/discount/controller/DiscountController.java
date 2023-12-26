package com.example.plusone.discount.controller;


import com.example.plusone.discount.dto.*;
import com.example.plusone.discount.gs25.dto.Gs25PreDto;
import com.example.plusone.discount.gs25.dto.Gs25Product;
import com.example.plusone.discount.service.DiscountService;
import com.example.plusone.discount.utils.RequestUtils;
import jakarta.websocket.server.PathParam;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.RequestUtil;
import org.bouncycastle.asn1.x509.Time;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DiscountController {

    private final DiscountService discountService;

    @PutMapping("/api/v1/convenience-stores/discounts/putdata")
    public ResponseEntity putdata(@RequestBody Gs25PreDto gs25PreDto){
        return null;
    }

    @PostMapping("/api/v1/convenience-stores/discounts/insert/gs25")
    public ResponseEntity insertGs25(Gs25Dto gs25Dto){

        ProductDto productDto = ProductDto.builder()
                .name(gs25Dto.getName())
                .image_url(gs25Dto.getImg())
                .price(gs25Dto.getPrice())
                .discountType(gs25Dto.getType().charAt(0))
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
    public ResponseEntity productFilter(@RequestParam int discountType){
//        log.info(filter_discount_type);
        List<ProductDto> productDtoList = discountService.productFilter(discountType);
//        log.info(productDtoList.toString());
        return ResponseEntity.status(HttpStatus.OK).body(productDtoList);
    }


    @PostMapping("/api/v1/convenience-stores/discounts/search")
    public ResponseEntity searchProduct(@RequestBody SearchDto searchDto){
        List<ProductDto> productDtoList = discountService.searchProduct(searchDto);
//        log.info(productDtoList.toString());

        ResponseDto responseDto = ResponseDto.builder()
                .code(String.valueOf(HttpStatus.OK.value()))
                .timestamp(String.valueOf(new Timestamp(System.currentTimeMillis())))
                .path(RequestUtils.getHttpServletRequest().getRequestURL().toString())
                .message("")
                .error("")
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
                .build();

        discountService.putProduct(productDto);

        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }

    @GetMapping("/api/v1/convenience-stores/discounts/insert/gs25")
    public void saveGs25(@RequestBody Gs25SearchDto gs25SearchDto){
        log.info("insert Gs25 API");
        discountService.insertGs25(gs25SearchDto);

    }

    @GetMapping("/api/v1/convenience-stores/discounts/insert/cu")
    public void getCuData(){
        log.info("insert Cu API");
        int pageIndex = 1;
        List<ProductDto> result = new ArrayList<>();
        for(int i = 1 ; i < 3 ; i ++) {

            try {
                Thread.sleep(1000);
                result.addAll(discountService.getProductCu(i));
            } catch (Exception e){
                e.printStackTrace();
            }
            log.info(result.toString());
        }
        discountService.putProducts(result);
    }

    @GetMapping("/api/v1/convenience-stores/discounts/get/SevenEleven/{intPageSize}&{intCurrPage}")
    public void get7Eleven(@PathVariable int intPageSize, @PathVariable int intCurrPage){
        discountService.getProductSevenEleven();
    }



}
