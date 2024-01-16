package com.example.plusone.kakaochat.dto;

import com.example.plusone.discount.dto.ProductDto;
import lombok.*;

import java.util.List;

// KakaoTemplate.java
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KakaoTemplate {


    @Setter
    @Getter
    private List<ProductDto> outputs;

    // getters and setters
}
