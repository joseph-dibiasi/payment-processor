package com.payment.processor.services.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.payment.processor.models.BillingDetails;
import com.payment.processor.models.PaymentAuthorizationDTO;
import com.payment.processor.repositories.BillingDetailsRepository;
import com.payment.processor.repositories.ShippingDetailsRepository;

class PaymentProcessorServiceImplTest {

	private BillingDetailsRepository billingRepo;
	private ShippingDetailsRepository shippingRepo;
	private PaymentProcessorServiceImpl service;

	@BeforeEach
	void setUp() {
		billingRepo = mock(BillingDetailsRepository.class);
		shippingRepo = mock(ShippingDetailsRepository.class);
		service = new PaymentProcessorServiceImpl(billingRepo, shippingRepo);
	}

	@Test
	void authorizePayment_success_savesBothEntitiesAndSetsAuthorizationFields() {
		PaymentAuthorizationDTO dto = new PaymentAuthorizationDTO();

		com.payment.processor.models.ShippingDetails shipping = new com.payment.processor.models.ShippingDetails();
		shipping.setFirstName("John");
		shipping.setLastName("Doe");
		shipping.setEmail("john@example.com");
		shipping.setPhone("1234567890");
		shipping.setAddress("123 Main St");
		shipping.setCity("New York");
		shipping.setState("NY");
		shipping.setZip("10001");
		shipping.setCountry("USA");

		BillingDetails billing = new BillingDetails();
		billing.setFirstName("John");
		billing.setLastName("Doe");
		billing.setAddress("123 Main St");
		billing.setCity("New York");
		billing.setState("NY");
		billing.setZip(10001);
		billing.setCountry("USA");
		billing.setCardNumber("4111111111111111");
		billing.setCardName("John Doe");
		billing.setExpirationDate("12/25");
		billing.setCvv(123);
		billing.setAmountRequested(100);

		dto.setShippingDetails(shipping);
		dto.setBillingDetails(billing);

		// No exceptions from repos
		when(billingRepo.save(any(BillingDetails.class))).thenReturn(billing);
		when(shippingRepo.save(any(com.payment.processor.models.ShippingDetails.class))).thenReturn(shipping);

		assertDoesNotThrow(() -> service.authorizePayment(dto));

		// Verify saves invoked
		verify(billingRepo, times(1)).save(billing);
		verify(shippingRepo, times(1)).save(shipping);

		// Billing fields updated by connectToPaymentGateway
		assertTrue(Boolean.TRUE.equals(billing.getAuthorized()));
		assertFalse(Boolean.TRUE.equals(billing.getSettled()));
		assertNotNull(billing.getBillingDate());
	}

	@Test
	void authorizePayment_saveThrows_runtimeExceptionPropagated() {
		PaymentAuthorizationDTO dto = new PaymentAuthorizationDTO();

		com.payment.processor.models.ShippingDetails shipping = new com.payment.processor.models.ShippingDetails();
		shipping.setFirstName("John");
		shipping.setLastName("Doe");
		shipping.setEmail("john@example.com");
		shipping.setPhone("1234567890");
		shipping.setAddress("123 Main St");
		shipping.setCity("New York");
		shipping.setState("NY");
		shipping.setZip("10001");
		shipping.setCountry("USA");

		BillingDetails billing = new BillingDetails();
		billing.setFirstName("John");
		billing.setLastName("Doe");
		billing.setAddress("123 Main St");
		billing.setCity("New York");
		billing.setState("NY");
		billing.setZip(10001);
		billing.setCountry("USA");
		billing.setCardNumber("4111111111111111");
		billing.setCardName("John Doe");
		billing.setExpirationDate("12/25");
		billing.setCvv(123);
		billing.setAmountRequested(100);

		dto.setShippingDetails(shipping);
		dto.setBillingDetails(billing);

		when(billingRepo.save(any())).thenThrow(new RuntimeException("DB error"));

		RuntimeException ex = assertThrows(RuntimeException.class, () -> service.authorizePayment(dto));
		assertTrue(ex.getMessage().contains("Error Saving Payment Details"));
	}

	@Test
	void authorizePayment_missingShipping_throwsIllegalArgumentException() {
		PaymentAuthorizationDTO dto = new PaymentAuthorizationDTO();
		dto.setShippingDetails(null);
		dto.setBillingDetails(new BillingDetails());

		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.authorizePayment(dto));
		assertTrue(ex.getMessage().contains("Shipping details are required."));
	}

	@Test
	void authorizePayment_missingBilling_throwsIllegalArgumentException() {
		PaymentAuthorizationDTO dto = new PaymentAuthorizationDTO();
		dto.setShippingDetails(new com.payment.processor.models.ShippingDetails());
		dto.setBillingDetails(null);

		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.authorizePayment(dto));
		assertTrue(ex.getMessage().contains("Billing details are required."));
	}

	@Test
	void collectAndSettleAuthorizedPayments_setsSettledAndSavesAll() {
		BillingDetails b1 = new BillingDetails();
		b1.setAuthorized(Boolean.TRUE);
		b1.setSettled(Boolean.FALSE);
		b1.setBillingDate(LocalDateTime.now());

		List<BillingDetails> found = Collections.singletonList(b1);

		when(billingRepo.findByAuthorizedAndSettledAndBillingDate(eq(Boolean.TRUE), eq(Boolean.FALSE),
				any(LocalDateTime.class))).thenReturn(found);

		service.collectAndSettleAuthorizedPayments();

		// after collectAndSettleAuthorizedPayments the repo.saveAll should be called
		// with the same list
		ArgumentCaptor<List<BillingDetails>> captor = ArgumentCaptor.forClass((Class) List.class);
		verify(billingRepo, times(1)).saveAll(captor.capture());

		List<BillingDetails> saved = captor.getValue();
		assertEquals(1, saved.size());
		assertTrue(Boolean.TRUE.equals(saved.get(0).getSettled()));
	}

}