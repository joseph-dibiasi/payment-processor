package com.payment.processor.models;

/**
 * DTO class matching the front end payload containing ShippingDetails and BillingDetails.
 *
 * @author Joseph DiBiasi
 * @version 1.0
 */
public class PaymentAuthorizationDTO {

	private ShippingDetails shippingDetails;
	private BillingDetails billingDetails;

	public PaymentAuthorizationDTO() {
	}

	public ShippingDetails getShippingDetails() {
		return shippingDetails;
	}

	public void setShippingDetails(ShippingDetails shippingDetails) {
		this.shippingDetails = shippingDetails;
	}

	public BillingDetails getBillingDetails() {
		return billingDetails;
	}

	public void setBillingDetails(BillingDetails billingDetails) {
		this.billingDetails = billingDetails;
	}

}
