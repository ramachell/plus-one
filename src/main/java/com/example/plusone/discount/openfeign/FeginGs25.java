package com.example.plusone.discount.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "gs25",url = "http://")
public interface FeginGs25 {

}
