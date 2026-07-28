package com.prueba.examen.dto;

import com.prueba.examen.enumerable.PaymentStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDTO {
	
	@NotBlank(message = "El concepto es obligatorio")
    private String concept;

	@NotNull(message = "La cantidad de productos es obligatoria")
    @Positive(message = "La cantidad de productos debe ser mayor a 0")
    private Integer productQuantity;

	@NotBlank(message = "Debe indicar quién realiza el pago")
    private String origin;

	@NotBlank(message = "Debe indicar a quién se le paga")
    private String destiny;

	@NotNull(message = "El monto total es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto total debe ser mayor a 0")
    private double totalAmount;

    private PaymentStatus status;
}
