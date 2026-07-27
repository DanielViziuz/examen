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

@RestController
@RequestMapping("/payment")
public class PaymentController {
	@Autowired
	PaymentService paymentService;
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PaymentResponseDTO> setPayment(@RequestBody  PaymentRequestDTO request){
		return ResponseEntity.status(HttpStatus.OK).body(this.paymentService.setPayment(request));
	}
	
	@GetMapping()
	public ResponseEntity<List<PaymentResponseDTO>> getAllPayments(){
		return ResponseEntity.status(HttpStatus.OK).body(this.paymentService.getAllPayments());
	}
	
	@GetMapping("/by")
	public ResponseEntity<PaymentResponseDTO> getPaymentBy(@ParameterObject PaymentFilterRequestDTO paymentFilterRequestDTO){
		return ResponseEntity.status(HttpStatus.OK).body(this.paymentService.getPaymentBy(paymentFilterRequestDTO.getId()));
	}
	
	@PatchMapping("{id}")
	public ResponseEntity<PaymentResponseDTO> updateStatusPayment(@PathVariable String id, @RequestBody PaymentUpdateRequestDTO paymentUpdateRequestDTO){
		return ResponseEntity.status(HttpStatus.OK).body(this.paymentService.updateStatusPayment(id, paymentUpdateRequestDTO.getStatus()));
	}
}
