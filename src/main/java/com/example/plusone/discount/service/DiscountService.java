package com.example.plusone.discount.service;

import com.example.plusone.discount.dto.Gs25Dto;
import com.example.plusone.discount.dto.Gs25SearchDto;
import com.example.plusone.discount.dto.ProductDto;
import com.example.plusone.discount.dto.SearchDto;
import com.example.plusone.discount.entity.Product;
import com.example.plusone.discount.gs25.dto.Gs25PreDto;
import com.example.plusone.discount.mapper.DiscountMapper;
import com.example.plusone.discount.openfeign.OpenFeign;
import com.example.plusone.discount.repository.ProductRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscountService {

    private final ProductRepository productRepository;
    private final OpenFeign openFeign;

    public List<ProductDto> productFilter(String filterDiscountType) {
//        log.info(productRepository.findAllByDiscountType(filterDiscountType).toString());
        return DiscountMapper.INSTANCE.toDTOs(productRepository.findAllByDiscountType(filterDiscountType));
    }


    public void putProduct(ProductDto productDto){
//        productDto.setId(UUID.randomUUID().toString());
        log.info(productDto.toString());
        productRepository.save(DiscountMapper.INSTANCE.toEntity(productDto));

    }

    public void putProducts(List<ProductDto> productDtoList) {
        List<Product> productList =  DiscountMapper.INSTANCE.toEntities(productDtoList);
        for (Product product : productList) {
            productRepository.save(product);
        }
    }

    public ProductDto getProduct(String id) {
        Product product = productRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        return DiscountMapper.INSTANCE.toDTO(product);

    }

    public List<ProductDto> searchProduct(SearchDto searchDto) {

        List<Product> productList = productRepository.findAllByNameContainsAndDiscountType(searchDto.getQuery(),searchDto.getDiscount_type());

        return DiscountMapper.INSTANCE.toDTOs(productList);
    }

    public Map<String, Object> insertGs25(Gs25SearchDto gs25SearchDto) {

        log.info(gs25SearchDto.toString());
        String result = openFeign.feignGetGs25_2(gs25SearchDto.getPageNum(),gs25SearchDto.getPageSize(),gs25SearchDto.getSearchType());
        log.info(result);



        return null;
    }


}
