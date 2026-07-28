package com.prueba.examen.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.prueba.examen.dto.PaymentChangeEventDTO;
import com.prueba.examen.dto.PaymentRequestDTO;
import com.prueba.examen.dto.PaymentResponseDTO;
import com.prueba.examen.enumerable.PaymentStatus;
import com.prueba.examen.repository.PaymentRepository;
import com.prueba.examen.vo.Payment;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
	
	@Mock
    private PaymentRepository paymentRepository;
	
	@Mock
    private PaymentPublisherService paymentPublisherService;
	
	@InjectMocks
    private PaymentService paymentService;
	
	private Payment payment;
	
	private List<Payment> payments;
	
	@BeforeEach
    void setUp() {
        payment = new Payment();
        payment.setId("6a68f2a76c3e18c5dca3e9f1");
        payment.setStatus(PaymentStatus.NEW); 
        
        Payment payment2 = new Payment();
        payment2.setId("6a68g5b76c3e80c9qca3e0a3");
        payment2.setStatus(PaymentStatus.NEW); 
        
        payments = new ArrayList<Payment>();
        
        payments.add(payment);
        payments.add(payment2);
    }
	
	@Test
    void setPayment_shouldSaveAndReturnPayment() {
		PaymentRequestDTO request = new PaymentRequestDTO();
        request.setConcept("Buy puzzle");
        request.setProductQuantity(2);
        request.setOrigin("Emisor1");
        request.setDestiny("Store");
        request.setTotalAmount(10.50);
        request.setStatus(PaymentStatus.NEW);

        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        PaymentResponseDTO response = paymentService.setPayment(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo("6a68f2a76c3e18c5dca3e9f1");
    }
	
	@Test
    void getAllPayments_shouldReturnPaymentList() {

        when(paymentRepository.findAll()).thenReturn(payments);

        List<PaymentResponseDTO> response = paymentService.getAllPayments();

        assertThat(response).hasSize(2);        
        assertThat(response.get(0).getId()).isEqualTo("6a68f2a76c3e18c5dca3e9f1");
        assertThat(response.get(1).getId()).isEqualTo("6a68g5b76c3e80c9qca3e0a3");
    }
	
	@Test
    void getPaymentBy_shouldReturnPaymentById() {
        when(paymentRepository.findById("6a68f2a76c3e18c5dca3e9f1")).thenReturn(Optional.of(payment));

        PaymentResponseDTO response = paymentService.getPaymentBy("6a68f2a76c3e18c5dca3e9f1");

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo("6a68f2a76c3e18c5dca3e9f1");
    }

	@Test
    void getPaymentBy_shouldReturnNullIfNotExistPayment() {
        when(paymentRepository.findById("6a68g5b76c3e80c9qca31111")).thenReturn(Optional.empty());

        PaymentResponseDTO response = paymentService.getPaymentBy("6a68g5b76c3e80c9qca31111");

        assertThat(response).isNull();
    }
	
	@Test
    void updateStatusPayment_shouldUpdatePaymentStatus() {
        when(paymentRepository.findById("6a68f2a76c3e18c5dca3e9f1"))
            .thenReturn(Optional.of(payment))              
            .thenReturn(Optional.of(payment));              

        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        PaymentResponseDTO response = paymentService.updateStatusPayment("6a68f2a76c3e18c5dca3e9f1", PaymentStatus.SUCCESSFUL);

        assertThat(response).isNotNull();

        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(paymentCaptor.capture());
        assertThat(paymentCaptor.getValue().getStatus()).isEqualTo(PaymentStatus.SUCCESSFUL);

        ArgumentCaptor<PaymentChangeEventDTO> eventCaptor = ArgumentCaptor.forClass(PaymentChangeEventDTO.class);
        verify(paymentPublisherService, times(1)).publishStatusChange(eventCaptor.capture());
        assertThat(eventCaptor.getValue().getPaymentId()).isEqualTo("6a68f2a76c3e18c5dca3e9f1");
    }
	
	@Test
    void updateStatusPayment_thrownBadRequestIfNotExistPayment() {
        when(paymentRepository.findById("6a68g5b76c3e80c9qca31111")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> paymentService.updateStatusPayment("6a68g5b76c3e80c9qca31111", PaymentStatus.SUCCESSFUL))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Pago no encontrado");

        verify(paymentRepository, never()).save(any(Payment.class));
        verify(paymentPublisherService, never()).publishStatusChange(any());
    }
}
