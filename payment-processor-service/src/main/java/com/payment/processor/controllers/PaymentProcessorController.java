package com.payment.processor.controllers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

	public PaymentProcessorController(PaymentProcessorService birdInTheHandService) {
		this.paymentProcessorService = birdInTheHandService;

	}

	private static final String BEARER_PREFIX = "Bearer ";
	private static final String EXPECTED_TOKEN = "authToken123";

	@PostMapping(BASE_URL + "/authorization")
	public ResponseEntity<ResponseObject> paymentAuthorization(HttpServletRequest request,
			@RequestBody PaymentAuthorizationDTO paymentAuthorization) {

		/*
		 * Payment authorization endpoint. Accepts a PaymentAuthorizationDTO that
		 * contains shipping and billing details needed to process a payment and
		 * finalize a purchase. Secured by confirming a valid bearer token is present in
		 * the Authorization header, invalid tokens provide a 401 response. Failure to
		 * validate payment details provides a 422 response. Successful payment
		 * authorization returns a 200 response.
		 */
		try {
			String authHeader = request.getHeader("Authorization");

			if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
				return ResponseEntity.status(HttpStatusCode.valueOf(401))
						.body(new ResponseObject("Unauthorized", "Missing or invalid Authorization header."));
			}

			String token = authHeader.substring(BEARER_PREFIX.length());

			if (!EXPECTED_TOKEN.equals(token)) {
				return ResponseEntity.status(HttpStatusCode.valueOf(401))
						.body(new ResponseObject("Unauthorized", "Invalid token."));
			}

			paymentProcessorService.authorizePayment(paymentAuthorization);

			return ResponseEntity.ok(new ResponseObject("Payment Authorization Successful."));

		} catch (Exception e) {
			System.out.println("Payment Authorization Failed: " + e.getMessage());

			return ResponseEntity.status(HttpStatusCode.valueOf(422))
					.body(new ResponseObject("Payment Authorization Failed.", e.getMessage()));
		}
	}
}
