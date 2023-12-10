package com.example.plusone.discount.dto;


import lombok.*;

@Builder
@Data
public class ResponseDto<T> {
    private String error ;
    private int status;
    private String message;
    private String timestamp ;
    private String path ;
    private String code ;
    private T data ;

}
