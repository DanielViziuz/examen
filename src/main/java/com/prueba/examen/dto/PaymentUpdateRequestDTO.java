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
public class PaymentUpdateRequestDTO {
	private PaymentStatus status;
}
