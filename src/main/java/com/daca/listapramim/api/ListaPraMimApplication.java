package com.daca.listapramim.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ListaPraMimApplication {

	public static void main(String[] args) {
		SpringApplication.run(ListaPraMimApplication.class, args);
	}

}
