package com.example.plusone.discount.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product_discountDto {
    private String id;

    private String name;

    private String image_url;

    private int price;

    private String description;

    private String type;

    private String categories;

    private DiscountDto discountType;
}
