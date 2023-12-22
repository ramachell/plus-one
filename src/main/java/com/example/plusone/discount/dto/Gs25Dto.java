package com.example.plusone.discount.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Gs25Dto {
    private String name;
    private String type;
    private String img;
    private int price;
}
