package com.venkatesh.PaymentService.service;

import com.venkatesh.PaymentService.model.PaymentRequest;
import com.venkatesh.PaymentService.model.PaymentResponse;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(String orderId);
}
