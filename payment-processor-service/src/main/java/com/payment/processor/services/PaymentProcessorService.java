package com.payment.processor.services;

import com.payment.processor.models.PaymentAuthorizationDTO;


/**
 * Service layer, defines contract for PaymentProcessorPaymentImpl.
 *
 * @author Joseph DiBiasi
 * @version 1.0
 */
public interface PaymentProcessorService {

	void authorizePayment(PaymentAuthorizationDTO paymentAuthorization);

}
