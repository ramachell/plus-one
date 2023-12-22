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
import java.util.UUID;

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


            Product product = Product.
                    builder().
                    name("김치").build();
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
            String id = UUID.randomUUID().toString();

            Optional<Product> product = productRepository.findById(id);
            if (product.isEmpty()) {
                throw new Exception("product is empty");
            } else {
            }
        } catch(Exception e) {
            log.error("error", e);
        }
    }

    @Test
    void testCreateAndRead(){
        log.info("test3");


        try {

            String id = UUID.randomUUID().toString();
            log.info(id);

            Product product = Product.
                    builder().

                    name("김치")
                    .price(1000)
                    .description("맛김치")
                    .build();
            productRepository.save(product);
            log.info("product id : {}", product.getId());
            log.info("take 1 : {}" , id);
            log.info("take 2 : {}" , id);
            log.info("take 3 : {}" , id);
            log.info("take 4 : {}" , id);

            Optional<Product> product2 = productRepository.findById(id);
            log.info(product2.toString());
            if (product2.isEmpty()) {
                throw new Exception("product is empty");
            } else {
                log.info("product2 is : {}", product2.get().toString());
            }
        } catch(Exception e) {
            log.error("error", e);
        }

        try {

        } catch(Exception e) {
            log.error("error", e);
        }
    }
}
