package com.example.plusone.discount.openfeign;

import com.example.plusone.config.OpenFeignConfig;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;


@FeignClient(name = "testfeign",url = "gs25.gsretail.com/gscvs/ko/products/event-goods-search?CSRFToken=60960598-3d2d-437c-bea2-58486021c6f3")
public interface OpenFeign {

    @GetMapping
    String feignGetGs25();
}
