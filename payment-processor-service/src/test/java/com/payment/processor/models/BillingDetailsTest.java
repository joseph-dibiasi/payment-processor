package com.payment.processor.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for BillingDetails model.
 *
 * @author Joseph DiBiasi
 * @version 1.0
 */
class BillingDetailsTest {

    @Test
    void testDefaultConstructorAndGettersSetters() {
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

        assertEquals("John", billing.getFirstName());
        assertEquals("Doe", billing.getLastName());
        assertEquals("123 Main St", billing.getAddress());
        assertEquals("New York", billing.getCity());
        assertEquals("NY", billing.getState());
        assertEquals(10001, billing.getZip());
        assertEquals("USA", billing.getCountry());
        assertEquals("4111111111111111", billing.getCardNumber());
        assertEquals("John Doe", billing.getCardName());
        assertEquals("12/25", billing.getExpirationDate());
        assertEquals(123, billing.getCvv());
        assertEquals(100, billing.getAmountRequested());
    }

    @Test
    void testNullValues() {
        BillingDetails billing = new BillingDetails();
        assertNull(billing.getFirstName());
        assertNull(billing.getLastName());   
        assertNull(billing.getAddress());
        assertNull(billing.getCity());
        assertNull(billing.getState());
        assertNull(billing.getZip());
        assertNull(billing.getCountry());
        assertNull(billing.getCardNumber());
        assertNull(billing.getCardName());
        assertNull(billing.getExpirationDate());
        assertNull(billing.getCvv());
        assertNull(billing.getAmountRequested());
    }
}
