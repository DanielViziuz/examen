package com.prueba.examen.dto;

import com.prueba.examen.enumerable.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDTO {
    private String id;

    private String concept;

    private Integer productQuantity;

    private String origin;

    private String destiny;

    private double totalAmount;

    private PaymentStatus status;
}
