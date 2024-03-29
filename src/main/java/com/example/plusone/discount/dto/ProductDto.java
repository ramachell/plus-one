package com.example.plusone.discount.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductDto {

    private String id;

    private String name;

    private String imageUrl;

    private int price;

    private int discountType;

    private int convenienceStore;

}
