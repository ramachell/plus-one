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
	public static void main(String[] args) {
		SpringApplication.run(PlusoneApplication.class, args);
	}

}
