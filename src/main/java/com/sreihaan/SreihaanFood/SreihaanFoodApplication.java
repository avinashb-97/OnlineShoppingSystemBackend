package com.sreihaan.SreihaanFood;

import com.sreihaan.SreihaanFood.model.persistence.repository.UserDataRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import java.beans.PropertyVetoException;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserDataRepository.class)
@EnableAutoConfiguration
//@EntityScan({"com.sreihaan.SreihaanFood.model.persistence.User","com.sreihaan.SreihaanFood.model.persistence.repository.SubCategory"})
public class SreihaanFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(SreihaanFoodApplication.class, args);
	}
}