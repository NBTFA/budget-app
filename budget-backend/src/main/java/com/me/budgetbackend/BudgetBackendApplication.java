package com.me.budgetbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableOpenApi
@MapperScan("com.me.budgetbackend.mapper")
public class BudgetBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetBackendApplication.class, args);
	}

}
