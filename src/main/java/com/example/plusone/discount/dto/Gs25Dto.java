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
    private int pageNum;
    private int pageSize;
    private String searchType;
    private String searchWord;
    private String parameterList;

}
