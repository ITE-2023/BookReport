package com.project.bookreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookreportApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookreportApplication.class, args);
	}

}
