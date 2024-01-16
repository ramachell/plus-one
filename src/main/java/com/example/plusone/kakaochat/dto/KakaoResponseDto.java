package com.example.plusone.kakaochat.dto;

import com.example.plusone.discount.dto.ProductDto;
import lombok.*;
import org.hibernate.result.Outputs;

import java.util.List;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
@ToString
public class KakaoResponseDto {
        private Template template;
        private String version;


    @Getter
    @Setter
    @ToString
    public static class Template {
        private List<Outputs> outputs;
    }

    @Getter
    @Setter
    @ToString
    public static class Outputs{
        private ListCard listCard;

    }

    @Getter
    @Setter
    @ToString
    public static class ListCard{
        private Header header;
        private List<Items> items;
    }

    @Getter
    @Setter
    @ToString
    public static class Header{
        protected String title;
    }

    @Getter
    @Setter
    @ToString
    public static class Items {
        private String title;
        private String description;
        private String imageUrl;
        private String description2;

    }

}
