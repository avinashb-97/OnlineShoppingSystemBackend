package com.sreihaan.SreihaanFood;

import io.github.kaiso.relmongo.config.EnableRelMongo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableRelMongo
public class SreihaanFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(SreihaanFoodApplication.class, args);
	}

}