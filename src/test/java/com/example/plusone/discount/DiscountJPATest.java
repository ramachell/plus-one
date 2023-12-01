package com.example.plusone.discount;


import com.example.plusone.config.JpaConfig;
import com.example.plusone.discount.entity.Product;
import com.example.plusone.discount.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

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
            Product product = new Product(11L, "김치");
            productRepository.save(product);
            log.info("product id : {}", product.getId());
        } catch(Exception e) {
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
            Optional<Product> product = productRepository.findById(11L);
            if (product.isEmpty()) {
                throw new Exception("product is empty");
            } else {
            }
        } catch(Exception e) {
            log.error("error", e);
        }
    }
}
