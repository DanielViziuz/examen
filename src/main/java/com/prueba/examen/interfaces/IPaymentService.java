package com.prueba.examen.interfaces;

import java.util.List;

import com.prueba.examen.dto.PaymentRequestDTO;
import com.prueba.examen.dto.PaymentResponseDTO;
import com.prueba.examen.enumerable.PaymentStatus;

public interface IPaymentService {
    PaymentResponseDTO setPayment (PaymentRequestDTO paymentToSave);

    List<PaymentResponseDTO> getAllPayments ();

    PaymentResponseDTO getPaymentBy(String id);

    PaymentResponseDTO updateStatusPayment(String id, PaymentStatus status);

}
