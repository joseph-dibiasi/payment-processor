package com.payment.processor.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.payment.processor.models.BillingDetails;
import com.payment.processor.models.PaymentAuthorizationDTO;
import com.payment.processor.models.ShippingDetails;
import com.payment.processor.repositories.BillingDetailsRepository;
import com.payment.processor.repositories.ShippingDetailsRepository;
import com.payment.processor.services.PaymentProcessorService;

/**
 * Service layer, implementation of PaymentProcessor service logic.
 *
 * @author Joseph DiBiasi
 * @version 1.0
 */
@Service
public class PaymentProcessorServiceImpl implements PaymentProcessorService {
	
    private final BillingDetailsRepository billingDetailsRespository;
    private final ShippingDetailsRepository shippingDetailsRespository;


    public PaymentProcessorServiceImpl(BillingDetailsRepository billingDetailsRespository, ShippingDetailsRepository shippingDetailsRespository) {
        this.billingDetailsRespository = billingDetailsRespository;
        this.shippingDetailsRespository = shippingDetailsRespository;
    }

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

	private List<String> verifyShippingDetails(ShippingDetails shippingDetails) {
		/*
		 * Basic validation of shipping details.
		 */
		List<String> errors = new ArrayList<>();
		if (shippingDetails == null) {
			errors.add("Shipping details are required.");
			return errors;
		}
		if (isNullOrEmpty(shippingDetails.getFirstName())) {
			errors.add("Shipping first name is required.");
		}
		if (isNullOrEmpty(shippingDetails.getLastName())) {
			errors.add("Shipping last name is required.");
		}
		if (isNullOrEmpty(shippingDetails.getEmail())) {
			errors.add("Shipping email is required.");
		}
		if (isNullOrEmpty(shippingDetails.getPhone())) {
			errors.add("Shipping phone is required.");
		}
		if (isNullOrEmpty(shippingDetails.getAddress())) {
			errors.add("Shipping address is required.");
		}
		if (isNullOrEmpty(shippingDetails.getCity())) {
			errors.add("Shipping city is required.");
		}
		if (isNullOrEmpty(shippingDetails.getState())) {
			errors.add("Shipping state is required.");
		}
		if (isNullOrEmpty(shippingDetails.getZip())) {
			errors.add("Shipping zip is required.");
		}
		if (isNullOrEmpty(shippingDetails.getCountry())) {
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
		if (isNullOrEmpty(paymentDetails.getFirstName())) {
			errors.add("Billing first name is required.");
		}
		if (isNullOrEmpty(paymentDetails.getLastName())) {
			errors.add("Billing last name is required.");
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
		if (paymentDetails.getAmountRequested() == null || paymentDetails.getAmountRequested() <= 0) {
			errors.add("No payment amount requested.");
		}
		return errors;
	}

	private boolean isNullOrEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}

	private void storePaymentAuthorizationResults(PaymentAuthorizationDTO paymentAuthorization) {
		// Database storage logic here.
		try {
			billingDetailsRespository.save(paymentAuthorization.getBillingDetails());			
		} catch (Exception e) {
			System.err.println("Error saving billing details: " + e.getMessage());
			throw new RuntimeException("Failed to Store billing details.", e);
		}
//		try {
//			shippingDetailsRespository.save(paymentAuthorization.getShippingDetails());
//		} catch (Exception e) {
//			System.err.println("Error saving shipping details: " + e.getMessage());
//			throw new RuntimeException("Failed to Store shipping details.", e);
//		}

	}

	private void connectToPaymentGateway(PaymentAuthorizationDTO paymentAuthorization) {
		// Third-party credit card network integration logic here.
		System.out.println("Successfully connected to payment gateway.");
	}

}
