package com.example.plusone.discount.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DiscountDto {

    private Long id;
    private String name;
    private String type;

    private String start_date;

    private String end_date;

}
