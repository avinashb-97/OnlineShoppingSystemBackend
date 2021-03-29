package com.sreihaan.SreihaanFood;

import com.sreihaan.SreihaanFood.model.persistence.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@EnableAutoConfiguration
@EnableAsync
public class SreihaanFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(SreihaanFoodApplication.class, args);
	}
}