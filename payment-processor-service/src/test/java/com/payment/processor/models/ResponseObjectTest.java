package com.payment.processor.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class ResponseObjectTest {

    @Test
    void testSingleArgConstructor() {
        ResponseObject response = new ResponseObject("Success");
        assertEquals("Success", response.getResponseMessage());
        assertNull(response.getErrorMessage());
    }

    @Test
    void testTwoArgConstructor() {
        ResponseObject response = new ResponseObject("Failed", "Invalid card");
        assertEquals("Failed", response.getResponseMessage());
        assertEquals("Invalid card", response.getErrorMessage());
    }

    @Test
    void testSettersAndGetters() {
        ResponseObject response = new ResponseObject("Test");
        response.setResponseMessage("Updated");
        response.setErrorMessage("Error");
        assertEquals("Updated", response.getResponseMessage());
        assertEquals("Error", response.getErrorMessage());
    }
}
