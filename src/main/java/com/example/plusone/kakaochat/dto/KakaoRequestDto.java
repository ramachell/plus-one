package com.example.plusone.kakaochat.dto;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KakaoRequestDto {
    private Intent intent;
    private UserRequestData userRequest;
    private Bot bot;
    private Action action;

    // 생성자, getter 및 setter

    @Getter
    public static class Intent {
        private String id;
        private String name;

        // 생성자, getter 및 setter
    }

    @Getter
    public static class UserRequestData {
        private String timezone;
        private Map<String, String> params;
        private Block block;
        private String utterance;
        private String lang;
        private User user;

        // 생성자, getter 및 setter
    }

    @Getter
    public static class Block {
        private String id;
        private String name;

        // 생성자, getter 및 setter
    }
    @Getter
    public static class User {
        private String id;
        private String type;
        private Map<String, String> properties;

        // 생성자, getter 및 setter
    }

    @Getter
    public static class Bot {
        private String id;
        private String name;

        // 생성자, getter 및 setter
    }

    @Getter
    public static class Action {
        private String name;
        private Object clientExtra;
        private Param params;
        private String id;
        private Map<String, DetailParams> detailParams;

        // 생성자, getter 및 setter
    }

    @Getter
    public static class DetailParams {
        private String origin;
        private String value;
        private String groupName;

        // 생성자, getter 및 setter
    }

    @Getter
    public static class Param{
        private String query;
        private int discount_type;
        private LocalDate sys_date_params;
    }
}