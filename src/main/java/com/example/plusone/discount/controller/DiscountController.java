package com.example.plusone.discount.controller;


import com.example.plusone.discount.dto.*;
import com.example.plusone.discount.gs25.dto.Gs25PreDto;
import com.example.plusone.discount.gs25.dto.Gs25Product;
import com.example.plusone.discount.service.DiscountService;
import com.example.plusone.discount.utils.RequestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * @param id 상품 id
     * @return 상품 1개의 정보
     */
    @GetMapping("/api/v1/convenience-stores/discounts/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String id){
        ProductDto productDto = discountService.getProduct(id);
        return ResponseEntity.status(HttpStatus.OK.value()).body(productDto);

    }

    // discount type에 따라서 결과 return
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



    @GetMapping("/api/v1/convenience-stores/discounts/insert/gs25")
    public int insertProductGs25(@RequestBody Gs25SearchDto gs25SearchDto){
        log.info("insert Gs25 API");
        List<ProductDto> result = discountService.GetProductGs25(gs25SearchDto);
        discountService.putProducts(result);
        return result.size();
    }

    @GetMapping("/api/v1/convenience-stores/discounts/insert/cu")
    public int insertProductCu(@RequestParam int pageIndex){
        log.info("insert Cu API");

        List<ProductDto> result = discountService.getProductCu(pageIndex);
        discountService.putProducts(result);
        return result.size();
    }

    @GetMapping("/api/v1/convenience-stores/discounts/insert/sevenEleven")
    public int insertProductSevenEleven(@RequestParam int pTab, @RequestParam int intPageSize){
        log.info("insert SevenEleven Api");
        List<ProductDto> result = new ArrayList<>();
        // intCurrPage가 0,1일때는 intPageSize 가 제대로 작동을 안해서 따로 실행해야함
        result.addAll(discountService.getProductSevenEleven(pTab,10,0));
        result.addAll(discountService.getProductSevenEleven(pTab,10,1));
        result.addAll(discountService.getProductSevenEleven(pTab,intPageSize,2));
        discountService.putProducts(result);
        return result.size();
    }
}
