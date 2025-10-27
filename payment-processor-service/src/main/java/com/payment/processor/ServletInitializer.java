package com.payment.processor;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
/**
 * A class for the SpringBoot Servlet Initializer.
 *
 * @author Joseph DiBiasi
 * @version 1.0
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PaymentProcessorApplication.class);
	}

}
