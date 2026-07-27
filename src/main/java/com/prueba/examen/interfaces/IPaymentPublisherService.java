package com.prueba.examen.interfaces;

import com.prueba.examen.dto.PaymentChangeEventDTO;

public interface IPaymentPublisherService {
	public void publishStatusChange(PaymentChangeEventDTO paymentChangeEventDTO);
}
