package com.prueba.examen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springdoc.core.annotations.ParameterObject;

import com.prueba.examen.dto.PaymentFilterRequestDTO;
import com.prueba.examen.dto.PaymentRequestDTO;
import com.prueba.examen.dto.PaymentResponseDTO;
import com.prueba.examen.dto.PaymentUpdateRequestDTO;
import com.prueba.examen.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	@Autowired
	PaymentService paymentService;
	
	@Operation(summary = "Dar de alta un pago", description = "Crea un nuevo registro de pago")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PaymentResponseDTO> setPayment(@Valid @RequestBody  PaymentRequestDTO request){
		return ResponseEntity.status(HttpStatus.OK).body(this.paymentService.setPayment(request));
	}
	
	@Operation(summary = "Enlista pagos", description = "Obtiene todo los pagos generados en la base")
	@GetMapping()
	public ResponseEntity<List<PaymentResponseDTO>> getAllPayments(){
		return ResponseEntity.status(HttpStatus.OK).body(this.paymentService.getAllPayments());
	}
	
	@Operation(summary = "Buscar pagos por Id", description = "Obtiene el pago relacionado a su ID")
	@GetMapping("/by")
	public ResponseEntity<PaymentResponseDTO> getPaymentBy(@Valid @ParameterObject PaymentFilterRequestDTO paymentFilterRequestDTO){
		return ResponseEntity.status(HttpStatus.OK).body(this.paymentService.getPaymentBy(paymentFilterRequestDTO.getId()));
	}
	
	@Operation(summary = "Actualiza status", description = "Actualiza el estado de un pago y es notificado a RabbitMQ")
	@PatchMapping("{id}")
	public ResponseEntity<PaymentResponseDTO> updateStatusPayment(@PathVariable String id, @Valid @RequestBody PaymentUpdateRequestDTO paymentUpdateRequestDTO){
		return ResponseEntity.status(HttpStatus.OK).body(this.paymentService.updateStatusPayment(id, paymentUpdateRequestDTO.getStatus()));
	}
}
