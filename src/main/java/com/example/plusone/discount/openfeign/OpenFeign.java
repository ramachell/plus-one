package com.example.plusone.discount.openfeign;

import com.example.plusone.config.OpenFeignConfig;
import com.example.plusone.discount.dto.Gs25Dto;
import com.example.plusone.discount.dto.Gs25SearchDto;
import com.example.plusone.discount.gs25.dto.Gs25PreDto;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;


@FeignClient(name = "testfeign",url = "http://gs25.gsretail.com/gscvs/ko/products/event-goods-search")
public interface OpenFeign {

    @GetMapping
    String feignGetGs25();

    @GetMapping(value = "?pageNum={pageNum}&pageSize={pageSize}&parameterList={parameterList}")
    String feignGetGs25_2(@PathVariable int pageNum,
                              @PathVariable int pageSize,
                              @PathVariable String parameterList);

}
