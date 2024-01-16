package com.example.plusone.kakaochat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoTimeDto {
    private Bot bot;
    private String utterance;
    private Params params;
    private boolean isInSlotFilling;
    private User user;
    private Value value;
    private String timezone;
    private String lang;


    // Bot 클래스
    @Getter
    public static class Bot {
        private String id;
        private String name;

    }

    // Params 클래스
    @Getter
    public static class Params {
        @JsonProperty("ignoreMe")
        private boolean ignoreMe;
        private String surface;

    }

    // User 클래스
    @Getter
    public static class User {
        private String id;
        private String type;
        private Properties properties;

    }

    // Properties 클래스
    public static class Properties {
        @JsonProperty("botUserKey")
        private String botUserKey;
        @JsonProperty("bot_user_key")
        private String botUserKeyAlias;

    }

    // Value 클래스
    @Getter
    public static class Value {
        private String origin;
        private String resolved;

    }
}