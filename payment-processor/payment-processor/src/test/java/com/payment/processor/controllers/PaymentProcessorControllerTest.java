package com.payment.processor.controllers;

import com.payment.processor.models.PaymentAuthorizationDTO;
import com.payment.processor.models.ResponseObject;
import com.payment.processor.services.PaymentProcessorService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void testReport_Success() {
        PaymentAuthorizationDTO dto = new PaymentAuthorizationDTO();

        doNothing().when(paymentProcessorService).authorizePayment(dto);

        ResponseObject response = controller.report(request, dto);

        assertEquals("Payment Authorization Successful.", response.getResponseMessage());
        assertNull(response.getErrorMessage());
        verify(paymentProcessorService, times(1)).authorizePayment(dto);
    }

    @Test
    void testReport_Failure() {
        PaymentAuthorizationDTO dto = new PaymentAuthorizationDTO();

        doThrow(new IllegalArgumentException("Invalid payment")).when(paymentProcessorService).authorizePayment(dto);

        ResponseObject response = controller.report(request, dto);

        assertEquals("Payment Authorization Failed.", response.getResponseMessage());
        assertEquals("Invalid payment", response.getErrorMessage());
        verify(paymentProcessorService, times(1)).authorizePayment(dto);
    }
}
