package com.example.plusone.kakaochat.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class DefaultMessageService {

    /**
     * msg를 입력받아서
     * 카카오 챗봇 형식의 json 문자열 만들기
     *
     * @ param String msg : 내가 보내고싶은 msg
     * @ return String : msg를 포함한 카카오챗봇 형식의 json 문자열
     */
    public String makeJson(String msg) {
        JSONObject json = new JSONObject();
        json.put("version", "2.0");

        JSONObject template = new JSONObject();
        json.put("template", template);

        JSONArray outputs = new JSONArray();
        template.put("outputs", outputs);

        JSONObject noNamed = new JSONObject();
        outputs.add(noNamed);

        JSONObject simpleText = new JSONObject();
        noNamed.put("simpleText", simpleText);

        simpleText.put("text", msg);

        return json.toJSONString();
    }
}