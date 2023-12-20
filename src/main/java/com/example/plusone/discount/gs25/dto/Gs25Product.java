package com.example.plusone.discount.gs25.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Gs25Product {
    private String attFileId;
    private GoodsStat goodsStat;

    private int prmtPriority;
    private String imageFileAppDt;
    private GoodsStat goodsStatOld;

    private String eventTypeNm;
    private String goodsNm;
    private String goodsStatNm;
    private String goodsStatAppDt;
    private String prmtCd;
    private EventTypeSp eventTypeSp;

    private double price;
    private int prmtBuyQty;
    private double priceOld;
    private String attFileNmOld;
    private String abrGoodsNm;
    private String priceApplyDate;
    private String prmtYear;
    private String attFileIdOld;
    private String goodsStatNmOld;
    private String attFileNm;
}
