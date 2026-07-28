package com.prueba.examen.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.prueba.examen.interfaces.IPaymentService;
import com.prueba.examen.repository.PaymentRepository;
import com.prueba.examen.vo.Payment;
import com.prueba.examen.dto.PaymentChangeEventDTO;
import com.prueba.examen.dto.PaymentRequestDTO;
import com.prueba.examen.dto.PaymentResponseDTO;
import com.prueba.examen.enumerable.PaymentStatus;
import com.prueba.examen.mapper.PaymentMapper;

@Service
public class PaymentService implements IPaymentService{

	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	PaymentPublisherService paymentPublisherService;
	
	@Override
	public PaymentResponseDTO setPayment(PaymentRequestDTO paymentToSave) {
		Payment paymentToSaveEntity = PaymentMapper.INSTANCE.toEntity(paymentToSave);
		return PaymentMapper.INSTANCE.toDTO(this.paymentRepository.save(paymentToSaveEntity));
	}

	@Override
	public List<PaymentResponseDTO> getAllPayments() {
		return this.paymentRepository.findAll().stream().map((paymentItem) -> {
			return PaymentMapper.INSTANCE.toDTO(paymentItem);
		}).toList();
	}

	@Override
	public PaymentResponseDTO getPaymentBy(String id) {
		Optional<Payment> paymentOptional = this.paymentRepository.findById(id);
		if(paymentOptional.isPresent()) {
			return PaymentMapper.INSTANCE.toDTO(paymentOptional.get());
		}else {
			return null;
		}
	}

	@Override
	public PaymentResponseDTO updateStatusPayment(String id, PaymentStatus status) {
		if(id!=null) {
			Optional<Payment> paymentOptional = this.paymentRepository.findById(id);
			if(paymentOptional.isPresent()) {			
				Payment paymentFound = paymentOptional.get();
				PaymentChangeEventDTO rabbitMQMessage = this.createMessageToRabbitMQ(id, paymentFound.getStatus().getDescription(), status.getDescription());
				paymentFound.setStatus(status);
				this.paymentRepository.save(paymentFound);
				this.paymentPublisherService.publishStatusChange(rabbitMQMessage);//Publicando mensaje de cambio de status
				return PaymentMapper.INSTANCE.toDTO(this.paymentRepository.findById(id).get());
			}else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pago no encontrado");
							
			}
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El id del pago es obligatorio");
		}
	}
	
	private PaymentChangeEventDTO createMessageToRabbitMQ(String paymentId, String previousStatus, String newStatus) {
		PaymentChangeEventDTO rabbitMQMessage = new PaymentChangeEventDTO();
		rabbitMQMessage.setPaymentId(paymentId);
		rabbitMQMessage.setPreviousStatus(previousStatus);
		rabbitMQMessage.setNewStatus(newStatus);
		rabbitMQMessage.setCreatedAt(LocalDateTime.now());
		
		return rabbitMQMessage;
	}


}
