package com.example.plusone.discount.controller;


import com.example.plusone.discount.dto.*;
import com.example.plusone.discount.gs25.dto.Gs25PreDto;
import com.example.plusone.discount.service.DiscountService;
import com.example.plusone.discount.utils.RequestUtils;
import com.example.plusone.kakaochat.dto.*;
import com.example.plusone.kakaochat.service.DiscountKakaoService;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DiscountController {

    private final DiscountService discountService;

    private final DiscountKakaoService discountKakaoService;



    @PutMapping("/api/v1/convenience-stores/discounts/putdata")
    public ResponseEntity putData(@RequestBody Gs25PreDto gs25PreDto){
        return null;
    }

    @PostMapping("/chatBot")
    public KakaoResponseDto getProductList (@RequestBody KakaoRequestDto kakaoRequestDto){
        return discountKakaoService.getKaKaoChatBotMessage(kakaoRequestDto);
    }

    @PostMapping("/chatBot2")
    public KakaoResponseDto getProductListTest (@RequestBody KakaoRequestDto kakaoRequestDto){
        log.info("chatBot2 api");
        return discountKakaoService.getKaKaoChatBotMessage2(kakaoRequestDto);
    }

    @PostMapping("/random")
    public String ramdomTest(@RequestBody KakaoRequestDto kakaoRequestDto){
        log.info("random api");
        JSONObject response = new JSONObject();
        response.put("version","2.0");

        JSONObject template = new JSONObject();
        response.put("template",template);

        JSONArray outputs = new JSONArray();
        template.put("outputs",outputs);


        JSONObject simpleText = new JSONObject();

        JSONObject noNamed = new JSONObject();

        noNamed.put("simpleText", simpleText);
        outputs.add(noNamed);

        Random ran = new Random();
        Set<Integer> lotto = new HashSet<>();
        while(lotto.size()<6) {
            lotto.add(ran.nextInt(1, 45));
        }
        ArrayList<Integer> al = new ArrayList<>(lotto);

        Collections.sort(al);

        simpleText.put("text",al.toString());

        return response.toJSONString().replace("\\/","/");
    }


    @RequestMapping("/test2con")
    public String confirm(@RequestBody KakaoTimeDto dto){
        log.info("confirm?");
        log.info(dto.toString());
        /*
        {
             "status": "SUCCESS",
                 "value": "a",
                 "data": {
             "extracted": "a"
            }
        }
        */
        String time = dto.getValue().getOrigin();
        time = time.replaceAll(" ","");

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy년M월d일")
                .optionalStart()
                .appendPattern("yyyy-M-d")
                .optionalEnd()
                .toFormatter(Locale.KOREA);

        // 문자열을 LocalDate로 변환
        LocalDate localDate = LocalDate.parse(time, formatter);


        JsonObject noNamed = new JsonObject();

        noNamed.addProperty("status", "SUCCESS");

        noNamed.addProperty("value",localDate.toString());
        JsonObject data = new JsonObject();
        data.addProperty("extracted",localDate.toString());
        noNamed.add("data",data);

        log.info(noNamed.toString());

        return noNamed.toString().replace("\\/","/");
    }

    @PostMapping("/test2")
    public String ChatBotTest(@RequestBody KakaoRequestDto kakaoRequestDto){
        // 카카오챗봇에서 어떻게 해야 보낼까

        log.info("date api");

        log.info(kakaoRequestDto.toString());

        LocalDate date = kakaoRequestDto.getAction().getParams().getSys_date_params();


        // 해당 날짜의 요일 얻기

        String[] days = {"월","화","수","목","금","토","일"};
        String result = days[date.getDayOfWeek().getValue()-1];

        // 결과 출력
        System.out.println("입력한 날짜: " + date);
        System.out.println("해당 날짜의 요일: " + result);

        JSONObject response = new JSONObject();
        response.put("version","2.0");

        JSONObject template = new JSONObject();
        response.put("template",template);

        JSONArray outputs = new JSONArray();
        template.put("outputs",outputs);

        JSONObject simpleText = new JSONObject();

        JSONObject noNamed = new JSONObject();

        noNamed.put("simpleText", simpleText);
        outputs.add(noNamed);

        simpleText.put("text",result+"요일 입니다");

        return response.toJSONString().replace("\\/","/");
    }

    @PostMapping("/api/v1/convenience-stores/discounts/insert/gs25")
    public ResponseEntity insertGs25(Gs25Dto gs25Dto){

        ProductDto productDto = ProductDto.builder()
                .name(gs25Dto.getName())
                .imageUrl(gs25Dto.getImg())
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
