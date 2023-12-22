package com.example.plusone.discount.gs25.dto;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Gs25PreDto {
    private List<Gs25Product> results;
    private Pagination pagination;

}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class GoodsStat {
    private String code;
    private String codeLowerCase;
}


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class EventTypeSp {
    private String code;
    private String codeLowerCase;

}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Pagination {
    private int totalNumberOfResults;
    private int numberOfPages;
    private int pageSize;
    private int currentPage;
}
