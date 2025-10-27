package com.payment.processor.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.payment.processor.models.PaymentAuthorizationDTO;
import com.payment.processor.models.ResponseObject;
import com.payment.processor.services.PaymentProcessorService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Test class for PaymentProcessorController methods.
 *
 * @author Joseph DiBiasi
 * @version 1.0
 */
class PaymentProcessorControllerTest {

    private PaymentProcessorService paymentProcessorService;
    private PaymentProcessorController controller;
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        paymentProcessorService = mock(PaymentProcessorService.class);
        controller = new PaymentProcessorController(paymentProcessorService);
        request = mock(HttpServletRequest.class);
    }

    @Test
    void testAuthorizationHeaderMissing_returns401() {
        when(request.getHeader("Authorization")).thenReturn(null);
        PaymentAuthorizationDTO dto = new PaymentAuthorizationDTO();

        ResponseEntity<ResponseObject> response = controller.paymentAuthorization(request, dto);

        assertEquals(401, response.getStatusCode().value());
        assertEquals("Unauthorized", response.getBody().getResponseMessage());
        assertEquals("Missing or invalid Authorization header.", response.getBody().getErrorMessage());
    }

    @Test
    void testAuthorizationHeaderInvalidPrefix_returns401() {
        when(request.getHeader("Authorization")).thenReturn("InvalidPrefix token");
        PaymentAuthorizationDTO dto = new PaymentAuthorizationDTO();

        ResponseEntity<ResponseObject> response = controller.paymentAuthorization(request, dto);

        assertEquals(401, response.getStatusCode().value());
        assertEquals("Unauthorized", response.getBody().getResponseMessage());
        assertEquals("Missing or invalid Authorization header.", response.getBody().getErrorMessage());
    }

    @Test
    void testAuthorizationHeaderInvalidToken_returns401() {
        when(request.getHeader("Authorization")).thenReturn("Bearer wrongToken");
        PaymentAuthorizationDTO dto = new PaymentAuthorizationDTO();

        ResponseEntity<ResponseObject> response = controller.paymentAuthorization(request, dto);

        assertEquals(401, response.getStatusCode().value());
        assertEquals("Unauthorized", response.getBody().getResponseMessage());
        assertEquals("Invalid token.", response.getBody().getErrorMessage());
    }

    @Test
    void testValidToken_callsServiceAndReturns200() {
        when(request.getHeader("Authorization")).thenReturn("Bearer authToken123");
        PaymentAuthorizationDTO dto = new PaymentAuthorizationDTO();

        ResponseEntity<ResponseObject> response = controller.paymentAuthorization(request, dto);

        verify(paymentProcessorService, times(1)).authorizePayment(dto);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Payment Authorization Successful.", response.getBody().getResponseMessage());
        assertNull(response.getBody().getErrorMessage());
    }

    @Test
    void testServiceThrowsException_returns422() {
        when(request.getHeader("Authorization")).thenReturn("Bearer authToken123");
        PaymentAuthorizationDTO dto = new PaymentAuthorizationDTO();
        doThrow(new RuntimeException("Test Exception")).when(paymentProcessorService).authorizePayment(dto);

        ResponseEntity<ResponseObject> response = controller.paymentAuthorization(request, dto);

        verify(paymentProcessorService, times(1)).authorizePayment(dto);
        assertEquals(422, response.getStatusCode().value());
        assertEquals("Payment Authorization Failed.", response.getBody().getResponseMessage());
        assertEquals("Test Exception", response.getBody().getErrorMessage());
    }
}
