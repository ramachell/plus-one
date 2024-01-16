package com.example.plusone.kakaochat.dto;

import com.example.plusone.discount.dto.ProductDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KakaoResponse {

    private String version;
    private KakaoTemplate template;

    // getters and setters
}

