package com.example.plusone.kakaochat.service;


import com.example.plusone.discount.dto.ProductDto;
import com.example.plusone.discount.dto.SearchDto;
import com.example.plusone.discount.entity.Discount;
import com.example.plusone.discount.mapper.DiscountMapper;
import com.example.plusone.discount.repository.ProductRepository;
import com.example.plusone.discount.service.DiscountService;
import com.example.plusone.discount.service.ProductService;
import com.example.plusone.kakaochat.dto.KakaoRequestDto;
import com.example.plusone.kakaochat.dto.KakaoResponseDto;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscountKakaoService {

    private final ProductRepository productRepository;

    private final DiscountService discountService;


    /**
     * 요청한 편의점 혹은 상품의 정보를 카카오 챗봇 메시지 형식의 json 문자열로 바꿔서 보내준다
     * @ param : kakaoRequestDto
     * @ return KakaoResponseDto : 카카오 챗봇 메시지 형식 json 문자열
     */
    public KakaoResponseDto getKaKaoChatBotMessage(@RequestBody KakaoRequestDto kakaoRequestDto){
        log.info("kakaoRequest : " + kakaoRequestDto.toString());

        SearchDto searchDto = SearchDto.builder()
                .query(kakaoRequestDto.getAction().getParams().getQuery())
                .discount_type(kakaoRequestDto.getAction().getParams().getDiscount_type())
                .build();
        log.info(searchDto.toString());

        List<ProductDto> productDtoList = discountService.searchProduct(searchDto);

        KakaoResponseDto kakaoResponseDto = new KakaoResponseDto();

        kakaoResponseDto.setVersion("2.0");

        KakaoResponseDto.Template template = new KakaoResponseDto.Template();


        List<KakaoResponseDto.Outputs> outputsList = new ArrayList<>();


        KakaoResponseDto.Outputs outputs = new KakaoResponseDto.Outputs();
        KakaoResponseDto.ListCard listCard = new KakaoResponseDto.ListCard();


        KakaoResponseDto.Header header = new KakaoResponseDto.Header();

        List<KakaoResponseDto.Items> itemList = new ArrayList<>();
        for (ProductDto productDto : productDtoList) {
            KakaoResponseDto.Items items = new KakaoResponseDto.Items();

            items.setTitle(productDto.getName());
            items.setDescription(searchDto.getDiscount_type()+"+1, 가격 : " + productDto.getPrice());
            items.setDescription2("편의점정보");
            items.setImageUrl(productDto.getImageUrl());
            itemList.add(items);
        }
        listCard.setItems(itemList);
        listCard.setHeader(header);
        template.setOutputs(outputsList);

        outputsList.add(outputs);

        outputs.setListCard(listCard);
        header.setTitle("할인정보입니다");


        kakaoResponseDto.setTemplate(template);
        log.info("list : " + productDtoList);
        log.info("template : " + template.toString());
        log.info("outputs : " + outputs.toString());
        log.info("itemList : " + itemList.toString());

        return kakaoResponseDto;
    }


    public KakaoResponseDto getKaKaoChatBotMessage2(@RequestBody KakaoRequestDto kakaoRequestDto){
        log.info("kakaoRequest : " + kakaoRequestDto.toString());

        SearchDto searchDto = SearchDto.builder()
                .query("콜라")
                .discount_type(2)
                .build();
        log.info(searchDto.toString());

        List<ProductDto> productDtoList = discountService.searchProduct(searchDto);

        KakaoResponseDto kakaoResponseDto = new KakaoResponseDto();

        kakaoResponseDto.setVersion("2.0");

        KakaoResponseDto.Template template = new KakaoResponseDto.Template();


        List<KakaoResponseDto.Outputs> outputsList = new ArrayList<>();


        KakaoResponseDto.Outputs outputs = new KakaoResponseDto.Outputs();
        KakaoResponseDto.ListCard listCard = new KakaoResponseDto.ListCard();


        KakaoResponseDto.Header header = new KakaoResponseDto.Header();

        List<KakaoResponseDto.Items> itemList = new ArrayList<>();
        for (ProductDto productDto : productDtoList) {
            KakaoResponseDto.Items items = new KakaoResponseDto.Items();

            items.setTitle(productDto.getName());
            items.setDescription(searchDto.getDiscount_type()+"+1, 가격 : " + productDto.getPrice());
            items.setDescription2("편의점정보");
            items.setImageUrl(productDto.getImageUrl());
            itemList.add(items);
        }
        listCard.setItems(itemList);
        listCard.setHeader(header);
        template.setOutputs(outputsList);

        outputsList.add(outputs);

        outputs.setListCard(listCard);
        header.setTitle("할인정보입니다");


        kakaoResponseDto.setTemplate(template);
        log.info("list : " + productDtoList);
        log.info("template : " + template.toString());
        log.info("outputs : " + outputs.toString());
        log.info("itemList : " + itemList.toString());

        return kakaoResponseDto;
    }
    /**
     * 상품 검색 요청을 받으면 그에 맞는 ProductDto 제공
     * @ param : 없음
     * @ return RestaurantMenu : 메뉴 정보 객체
     */
    @Transactional(readOnly = true)
    public List<ProductDto> getProductList(SearchDto searchDto) {
        log.info(searchDto.toString());

        List<ProductDto> productDtoList;

        try {
            productDtoList = DiscountMapper.INSTANCE.toDTOs(
                    productRepository.findAllByNameContainsAndDiscountType(searchDto.getQuery(),searchDto.getDiscount_type()));
            log.info(productDtoList.toString());
        } catch (Exception e) {
            throw new RuntimeException("서버의 응답이 없습니다. 개발자에게 문의해주세요! code:1");
        }

        return productDtoList;
    }

    /**
     * 상품 리스트 에서 제목만 우선 따와서 전송
     * 추후 내용도 좀 들어가던지 일단... 동작만 시험해보기
     *
     * @ param : ProductDtoList : DB 에서 가져온 productList
     * @ return String : 상품 이름만 쭉 나열한것
     */
    public String getProductDtoStr(List<ProductDto> productDtoList) {
        log.info(productDtoList.toString());
        StringBuilder stringBuilder = new StringBuilder();
        for (ProductDto productDto : productDtoList) {
            stringBuilder.append("상품 : ").append(productDto.getName()).append("\n\n");
        }
        log.info(stringBuilder.toString());

        return stringBuilder.toString();
//                "이름 ▼\n\n" + productDto.getName() +
//                "\n\n가격 ▼\n\n" + productDto.getPrice() +
//                "\n\n종류 ▼\n\n" + productDto.getDiscountType() +
//                "\n\n사진 ▼\n\n" + productDto.getImage_url() +
//                "\n\n편의점 ▼\n\n" + productDto.getConvenienceStore();
    }
}