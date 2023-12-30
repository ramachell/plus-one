package com.example.plusone.kakaochat.service;


import com.example.plusone.discount.dto.ProductDto;
import com.example.plusone.discount.dto.SearchDto;
import com.example.plusone.discount.entity.Discount;
import com.example.plusone.discount.mapper.DiscountMapper;
import com.example.plusone.discount.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscountProductService {

    private final ProductRepository productRepository;


    /**
     * 요청한 편의점 혹은 상품의 정보를 카카오 챗봇 메시지 형식의 json 문자열로 바꿔서 보내준다
     * @ param : 없음
     * @ return String : 카카오 챗봇 메시지 형식 json 문자열
     */
    public String makeProductJson(SearchDto searchDto) {

        List<ProductDto> productDtoList = getProductList(searchDto);

        JSONObject json = new JSONObject();
        json.put("version", "2.0");

        JSONObject template = new JSONObject();
        json.put("template", template);

        JSONArray outputs = new JSONArray();
        template.put("outputs", outputs);

        JSONObject noNamed = new JSONObject();
        outputs.add(noNamed);

        JSONObject itemCard = new JSONObject();
        noNamed.put("itemCard", itemCard);

        itemCard.put("title", "titletest"+getProductDtoStr(productDtoList));
        itemCard.put("description", "");
//        itemCard.put("head", "오늘(" + getDateTime.getDate() + " " + getDateTime.getDayOfWeek() + ") 메뉴");

        JSONArray itemList = new JSONArray();
        itemCard.put("itemList", itemList);

//        JSONObject breakFastPrice = new JSONObject();
//        breakFastPrice.put("title", "셀프라면 가격");
//        breakFastPrice.put("description", menu.getRestaurantProperty().getBreakFastPrice());

//        JSONObject lunchDinnerPrice = new JSONObject();
//        lunchDinnerPrice.put("title", "중식/석식 가격");
//        lunchDinnerPrice.put("description", menu.getRestaurantProperty().getCommonPrice());

//        itemList.add(breakFastTime);
//        itemList.add(lunchTime);
//        itemList.add(dinnerTime);
//        itemList.add(breakFastPrice);
//        itemList.add(lunchDinnerPrice);

        return json.toJSONString().replace("\\/", "/") ;
    }

    /**
     * 상품 검색 요청을 받으면 그에 맞는 ProductDto 제공
     *
     * @ param : 없음
     * @ return RestaurantMenu : 메뉴 정보 객체
     */
    @Transactional(readOnly = true)
    public List<ProductDto> getProductList(SearchDto searchDto) {
        log.info(searchDto.toString());

        List<ProductDto> productDto;

        try {
            productDto = DiscountMapper.INSTANCE.toDTOs(
                    productRepository.findAllByNameContainsAndDiscountType(searchDto.getQuery(),searchDto.getDiscount_type()));
            log.info(productDto.toString());
        } catch (Exception e) {
            throw new RuntimeException("서버의 응답이 없습니다. 개발자에게 문의해주세요! code:1");
        }

        return productDto;
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