package com.prueba.examen.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.prueba.examen.config.RabbitMQConfig;
import com.prueba.examen.dto.PaymentChangeEventDTO;
import com.prueba.examen.interfaces.IPaymentPublisherService;

@Service
public class PaymentPublisherService implements IPaymentPublisherService{
	
	private final RabbitTemplate rabbitTemplate;
	
	public PaymentPublisherService(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void publishStatusChange(PaymentChangeEventDTO paymentChangeEventDTO) {		
		rabbitTemplate.convertAndSend(
	            RabbitMQConfig.EXCHANGE,
	            RabbitMQConfig.ROUTING_KEY,
	            paymentChangeEventDTO
	        );
	}
	
}
