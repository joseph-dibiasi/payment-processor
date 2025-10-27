package com.payment.processor.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for PaymentAuthorization model.
 *
 * @author Joseph DiBiasi
 * @version 1.0
 */
class PaymentAuthorizationDTOTest {

    @Test
    void testGettersAndSetters() {
        PaymentAuthorizationDTO dto = new PaymentAuthorizationDTO();

        ShippingDetails shipping = new ShippingDetails();
        BillingDetails billing = new BillingDetails();

        dto.setShippingDetails(shipping);
        dto.setBillingDetails(billing);

        assertSame(shipping, dto.getShippingDetails());
        assertSame(billing, dto.getBillingDetails());
    }

    @Test
    void testDefaultConstructor() {
        PaymentAuthorizationDTO dto = new PaymentAuthorizationDTO();
        assertNull(dto.getShippingDetails());
        assertNull(dto.getBillingDetails());
    }
}
