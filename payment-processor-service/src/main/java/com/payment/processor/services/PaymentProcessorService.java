package com.payment.processor.services;

import java.util.List;

import com.payment.processor.models.BillingDetails;
import com.payment.processor.models.PaymentAuthorizationDTO;


/**
 * Service layer, defines contract for PaymentProcessorPaymentImpl.
 *
 * @author Joseph DiBiasi
 * @version 1.0
 */
public interface PaymentProcessorService {

	void authorizePayment(PaymentAuthorizationDTO paymentAuthorization);

	List<BillingDetails> collectAndSettleAuthorizedPayments();

}
