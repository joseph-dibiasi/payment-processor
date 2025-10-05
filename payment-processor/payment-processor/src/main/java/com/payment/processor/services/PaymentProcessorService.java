package com.payment.processor.services;

import com.payment.processor.models.PaymentAuthorizationDTO;



public interface PaymentProcessorService {

	void authorizePayment(PaymentAuthorizationDTO paymentAuthorization);

}
