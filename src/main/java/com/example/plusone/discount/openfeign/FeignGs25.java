package com.example.plusone.discount.openfeign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "gs25",url = "http://")
public interface FeignGs25 {

}
