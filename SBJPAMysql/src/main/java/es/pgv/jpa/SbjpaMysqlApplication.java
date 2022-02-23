package es.pgv.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SbjpaMysqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbjpaMysqlApplication.class, args);
	}

}