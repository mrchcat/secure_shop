package com.github.mrchcat.intershop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IntershopApplication {

	public static void main(String[] args) {
		var context=SpringApplication.run(IntershopApplication.class, args);
		Tester tester=context.getBean(Tester.class);
		tester.run();
	}

}
