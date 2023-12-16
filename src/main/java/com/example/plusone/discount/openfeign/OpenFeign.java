package com.example.plusone.discount.openfeign;

import com.example.plusone.config.openFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "testfeign", url = "localhost:9000/apitest",configuration = openFeignConfig.class)
public interface OpenFeign {

    //https://api.github.com/repos/octocat/hello-world/contributors
    @GetMapping
    String feignCall(String url);

    @PostMapping
    String feignPost(String url);
}
