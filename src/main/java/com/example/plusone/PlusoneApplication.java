package com.example.plusone;

import com.example.plusone.gs25.entity.gs25Entity;
import com.example.plusone.gs25.repository.gs25Repository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@RequiredArgsConstructor
public class PlusoneApplication {


	private final gs25Repository gs25Repository;

	    @PostConstruct
	    public void initMembers() {
	        //DB 에 Sample 데이터를 저장하기

	        gs25Repository.save(
					gs25Entity.builder()
							.num(12)
							.price(50000)
							.name("코카콜라_500mL")
							.type("two_plus_one").build());
	    }

	public static void main(String[] args) {
		SpringApplication.run(PlusoneApplication.class, args);
	}

}
