package com.example.plusone.discount;


import com.example.plusone.config.JpaConfig;
import com.example.plusone.discount.entity.Product;
import com.example.plusone.discount.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.*;

@Slf4j
@Import({JpaConfig.class})
@DataJpaTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DiscountJPATest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void testCreate() {
        log.info("test");
        // TODO
        // 모델을 하나 만들고
        // 데이터를 저장을 한다

        try {


            Product product = Product.builder().name("김치").build();
            productRepository.save(product);
            log.info("product id : {}", product.getId());

        } catch (Exception e) {
            log.error("error", e);
        }
    }

    @Test
    void testRead() {
        log.info("test");
        // TODO
        // 모델을 하나 만들고
        // 데이터를 저장을 한다

        try {
            String id = UUID.randomUUID().toString();

            Optional<Product> product = productRepository.findById(id);
            if (product.isEmpty()) {
                throw new Exception("product is empty");
            } else {
            }
        } catch (Exception e) {
            log.error("error", e);
        }
    }

    @Test
    void testCreateAndRead() {
        log.info("test3");


        try {

            String id = UUID.randomUUID().toString();
            log.info(id);

            Product product = Product.builder().

                    name("김치").price(1000).build();
            productRepository.save(product);
            log.info("product id : {}", product.getId());
            log.info("take 1 : {}", id);
            log.info("take 2 : {}", id);
            log.info("take 3 : {}", id);
            log.info("take 4 : {}", id);

            Optional<Product> product2 = productRepository.findById(id);
            log.info(product2.toString());
            if (product2.isEmpty()) {
                throw new Exception("product is empty");
            } else {
                log.info("product2 is : {}", product2.get().toString());
            }
        } catch (Exception e) {
            log.error("error", e);
        }

        try {

        } catch (Exception e) {
            log.error("error", e);
        }
    }

    @Test
    String getCuData() {
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

    @Test
    void test() {
        List<String> list = new ArrayList<>();
        list.add("//aaaa/bbbb");
        list.add("aaaa/cccc");
        for (String src : list) {
            if (src.startsWith("//")) {
                log.info(src.substring(2));
            } else {
                log.info(src);
            }

        }
    }

}