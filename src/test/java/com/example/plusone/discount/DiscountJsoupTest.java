package com.example.plusone.discount;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest()
@RunWith(SpringRunner.class)
public class DiscountJsoupTest {

    @Test
    public String  getCuData() {
        try {
            String url = "https://cu.bgfretail.com/event/plusAjax.do?pageIndex=2&searchCondition=23";

            // Jsoup을 사용하여 웹 페이지에 연결합니다.
            Document doc = Jsoup.connect(url).get();

            // 원하는 정보를 선택합니다.
            Elements productInfo = doc.select("div.name");

            // 가져온 정보를 처리합니다.
            StringBuilder result = new StringBuilder();
            for (Element info : productInfo) {
                result.append(info.text()).append("\n"); // 필요한 정보를 가져와서 결과에 추가합니다.
            }

            return result.toString(); // 결과 반환
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred during crawling.";
        }
    }
}
