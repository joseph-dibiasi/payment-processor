package com.payment.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The main application class for the PaymentProcessor.
 * @author Joseph DiBiasi
 * @version 1.0
 */
@SpringBootApplication
@EntityScan("com.payment.processor.models")
@EnableJpaRepositories("com.payment.processor.repositories")
public class PaymentProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentProcessorApplication.class, args);
	}

}
