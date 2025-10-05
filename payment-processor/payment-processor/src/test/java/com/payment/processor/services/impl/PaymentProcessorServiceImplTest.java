package com.payment.processor.services.impl;

import com.payment.processor.models.BillingDetails;
import com.payment.processor.models.PaymentAuthorizationDTO;
import com.payment.processor.models.ShippingDetails;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentProcessorServiceImplTest {

    private final PaymentProcessorServiceImpl service = new PaymentProcessorServiceImpl();

    @Test
    void testAuthorizePayment_Success() {
        PaymentAuthorizationDTO dto = new PaymentAuthorizationDTO();

        ShippingDetails shipping = new ShippingDetails();
        shipping.setFullName("John Doe");
        shipping.setEmail("john@example.com");
        shipping.setPhone("1234567890");
        shipping.setAddress("123 Main St");
        shipping.setCity("New York");
        shipping.setState("NY");
        shipping.setZip("10001");
        shipping.setCountry("USA");

        BillingDetails billing = new BillingDetails();
        billing.setFullName("John Doe");
        billing.setAddress("123 Main St");
        billing.setCity("New York");
        billing.setState("NY");
        billing.setZip("10001");
        billing.setCountry("USA");
        billing.setCardNumber("4111111111111111");
        billing.setCardName("John Doe");
        billing.setExpiryDate("12/25");
        billing.setCvv("123");
        billing.setPaymentRequested(100);

        dto.setShippingDetails(shipping);
        dto.setBillingDetails(billing);

        assertDoesNotThrow(() -> service.authorizePayment(dto));
    }

    @Test
    void testAuthorizePayment_FailsOnMissingShipping() {
        PaymentAuthorizationDTO dto = new PaymentAuthorizationDTO();
        dto.setShippingDetails(null);
        dto.setBillingDetails(new BillingDetails());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.authorizePayment(dto));
        assertTrue(ex.getMessage().contains("Shipping details are required."));
    }

    @Test
    void testAuthorizePayment_FailsOnMissingBilling() {
        PaymentAuthorizationDTO dto = new PaymentAuthorizationDTO();
        dto.setShippingDetails(new ShippingDetails());
        dto.setBillingDetails(null);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.authorizePayment(dto));
        assertTrue(ex.getMessage().contains("Billing details are required."));
    }

    @Test
    void testAuthorizePayment_FailsOnInvalidFields() {
        PaymentAuthorizationDTO dto = new PaymentAuthorizationDTO();
        ShippingDetails shipping = new ShippingDetails();
        shipping.setFullName(""); // Invalid
        shipping.setEmail(null);  // Invalid
        shipping.setPhone("");    // Invalid
        shipping.setAddress("");  // Invalid
        shipping.setCity("");     // Invalid
        shipping.setState("");    // Invalid
        shipping.setZip("");      // Invalid
        shipping.setCountry("");  // Invalid

        BillingDetails billing = new BillingDetails();
        billing.setFullName(""); // Invalid
        billing.setAddress("");  // Invalid
        billing.setCity("");     // Invalid
        billing.setState("");    // Invalid
        billing.setZip("");      // Invalid
        billing.setCountry("");  // Invalid
        billing.setCardNumber(""); // Invalid
        billing.setCardName("");   // Invalid
        billing.setExpiryDate(""); // Invalid
        billing.setCvv("");        // Invalid
        billing.setPaymentRequested(0); // Invalid

        dto.setShippingDetails(shipping);
        dto.setBillingDetails(billing);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.authorizePayment(dto));
        assertTrue(ex.getMessage().contains("Validation failed:"));
        assertTrue(ex.getMessage().contains("Shipping full name is required."));
        assertTrue(ex.getMessage().contains("Billing full name is required."));
        assertTrue(ex.getMessage().contains("No payment amount requested."));
    }
}
