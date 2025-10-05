package com.payment.processor.models;

/**
 * Top-level DTO matching the frontend payload which contains shippingDetails and billingDetails.
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
