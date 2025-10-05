package com.payment.processor.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.payment.processor.models.PaymentAuthorizationDTO;
import com.payment.processor.models.ResponseObject;
import com.payment.processor.services.PaymentProcessorService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class PaymentProcessorController {
	private static final String BASE_URL = "/payment-processor";
	private final PaymentProcessorService paymentProcessorService;

	public PaymentProcessorController(
			PaymentProcessorService birdInTheHandService
			) {
		this.paymentProcessorService = birdInTheHandService;

	}
	
	@PostMapping(BASE_URL + "/authorization")
	public ResponseObject paymentAuthorization(HttpServletRequest request, @RequestBody PaymentAuthorizationDTO paymentAuthorization) {

		try {
			paymentProcessorService.authorizePayment(paymentAuthorization);
			return new ResponseObject("Payment Authorization Successful.");
		} catch (Exception e) {
			System.out.println("Payment Authorization Failed: " + e.getMessage());
			// Make this return a 422 error.
			return new ResponseObject("Payment Authorization Failed.", e.getMessage());
		}
	}
	
	
}
