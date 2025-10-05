package com.payment.processor.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.payment.processor.models.BillingDetails;
import com.payment.processor.models.PaymentAuthorizationDTO;
import com.payment.processor.models.ShippingDetails;
import com.payment.processor.services.PaymentProcessorService;

@Service
public class PaymentProcessorServiceImpl implements PaymentProcessorService {

	@Override
	public void authorizePayment(PaymentAuthorizationDTO paymentAuthorization) {
		/*
		 * Shipping and Billing details are all validated here. An error in any field
		 * results in an IllegalArgumentException, but all fields are still checked to
		 * collect all validation errors.
		 */
		List<String> errors = new ArrayList<>();
		errors.addAll(verifyShippingDetails(paymentAuthorization.getShippingDetails()));
		errors.addAll(verifyBillingDetails(paymentAuthorization.getBillingDetails()));
		if (!errors.isEmpty()) {
			throw new IllegalArgumentException("Validation failed: " + String.join(" ", errors));
		}
		/*
		 * Integrate with third-party credit card network here.
		 */
		connectToPaymentGateway(paymentAuthorization);
		/*
		 * Store payment authorization results in database here. Batch processing of
		 * successful payment authorizations handled in different service.
		 */
		storePaymentAuthorizationResults(paymentAuthorization);
	}

	private List<String> verifyShippingDetails(ShippingDetails userDetails) {
		/*
		 * Basic validation of shipping details.
		 */
		List<String> errors = new ArrayList<>();
		if (userDetails == null) {
			errors.add("Shipping details are required.");
			return errors;
		}
		if (isNullOrEmpty(userDetails.getFullName())) {
			errors.add("Shipping full name is required.");
		}
		if (isNullOrEmpty(userDetails.getEmail())) {
			errors.add("Shipping email is required.");
		}
		if (isNullOrEmpty(userDetails.getPhone())) {
			errors.add("Shipping phone is required.");
		}
		if (isNullOrEmpty(userDetails.getAddress())) {
			errors.add("Shipping address is required.");
		}
		if (isNullOrEmpty(userDetails.getCity())) {
			errors.add("Shipping city is required.");
		}
		if (isNullOrEmpty(userDetails.getState())) {
			errors.add("Shipping state is required.");
		}
		if (isNullOrEmpty(userDetails.getZip())) {
			errors.add("Shipping zip is required.");
		}
		if (isNullOrEmpty(userDetails.getCountry())) {
			errors.add("Shipping country is required.");
		}
		return errors;
	}

	private List<String> verifyBillingDetails(BillingDetails paymentDetails) {
		/*
		 * Basic validation of billing details.
		 */
		List<String> errors = new ArrayList<>();
		if (paymentDetails == null) {
			errors.add("Billing details are required.");
			return errors;
		}
		if (isNullOrEmpty(paymentDetails.getFullName())) {
			errors.add("Billing full name is required.");
		}
		if (isNullOrEmpty(paymentDetails.getAddress())) {
			errors.add("Billing address is required.");
		}
		if (isNullOrEmpty(paymentDetails.getCity())) {
			errors.add("Billing city is required.");
		}
		if (isNullOrEmpty(paymentDetails.getState())) {
			errors.add("Billing state is required.");
		}
		if (isNullOrEmpty(paymentDetails.getZip())) {
			errors.add("Billing zip is required.");
		}
		if (isNullOrEmpty(paymentDetails.getCountry())) {
			errors.add("Billing country is required.");
		}
		if (isNullOrEmpty(paymentDetails.getCardNumber())) {
			errors.add("Card number is required.");
		}
		if (isNullOrEmpty(paymentDetails.getCardName())) {
			errors.add("Card name is required.");
		}
		if (isNullOrEmpty(paymentDetails.getExpiryDate())) {
			errors.add("Card expiration date is required.");
		}
		if (isNullOrEmpty(paymentDetails.getCvv())) {
			errors.add("Card CVV is required.");
		}
		if (paymentDetails.getPaymentRequested() == null || paymentDetails.getPaymentRequested() <= 0) {
			errors.add("No payment amount requested.");
		}
		return errors;
	}

	private boolean isNullOrEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}

	private void storePaymentAuthorizationResults(PaymentAuthorizationDTO paymentAuthorization) {
		// Database storage logic here.
		System.out.println("Payment authorization results stored successfully.");
	}

	private void connectToPaymentGateway(PaymentAuthorizationDTO paymentAuthorization) {
		// Third-party credit card network integration logic here.
		System.out.println("Successfully connected to payment gateway.");
	}

}
