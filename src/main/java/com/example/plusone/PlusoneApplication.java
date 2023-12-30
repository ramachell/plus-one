package com.example.plusone;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
//@EnableFeignClients
public class PlusoneApplication {
	public static void main(String[] args) {
		SpringApplication.run(PlusoneApplication.class, args);
	}

}
