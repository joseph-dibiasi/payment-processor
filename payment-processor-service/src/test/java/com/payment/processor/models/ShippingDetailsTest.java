package com.payment.processor.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ShippingDetails model.
 *
 * @author Joseph DiBiasi
 * @version 1.0
 */
class ShippingDetailsTest {

    @Test
    void testDefaultConstructorAndGettersSetters() {
        ShippingDetails shipping = new ShippingDetails();

        shipping.setFirstName("Jane");
        shipping.setLastName("Doe");
        shipping.setEmail("jane@example.com");
        shipping.setPhone("555-1234");
        shipping.setAddress("456 Elm St");
        shipping.setCity("Los Angeles");
        shipping.setState("CA");
        shipping.setZip("90001");
        shipping.setCountry("USA");

        assertEquals("Jane", shipping.getFirstName());
        assertEquals("Doe", shipping.getLastName());
        assertEquals("jane@example.com", shipping.getEmail());
        assertEquals("555-1234", shipping.getPhone());
        assertEquals("456 Elm St", shipping.getAddress());
        assertEquals("Los Angeles", shipping.getCity());
        assertEquals("CA", shipping.getState());
        assertEquals("90001", shipping.getZip());
        assertEquals("USA", shipping.getCountry());
    }

    @Test
    void testNullValues() {
        ShippingDetails shipping = new ShippingDetails();
        assertNull(shipping.getFirstName());
        assertNull(shipping.getLastName()); 
        assertNull(shipping.getEmail());
        assertNull(shipping.getPhone());
        assertNull(shipping.getAddress());
        assertNull(shipping.getCity());
        assertNull(shipping.getState());
        assertNull(shipping.getZip());
        assertNull(shipping.getCountry());
    }
}
