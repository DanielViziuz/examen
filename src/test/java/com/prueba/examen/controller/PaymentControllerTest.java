package com.prueba.examen.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.examen.dto.PaymentRequestDTO;
import com.prueba.examen.dto.PaymentResponseDTO;
import com.prueba.examen.dto.PaymentUpdateRequestDTO;
import com.prueba.examen.enumerable.PaymentStatus;
import com.prueba.examen.service.PaymentService;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {
	private MockMvc mockMvc;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }
    
    @Test
    void setPayment_shouldCreatePayment() throws Exception {
        PaymentRequestDTO request = new PaymentRequestDTO();
        request.setConcept("Buy puzzle");
        request.setProductQuantity(2);
        request.setOrigin("Emisor1");
        request.setDestiny("Store");
        request.setTotalAmount(10.50);
        request.setStatus(PaymentStatus.NEW);

        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setId("6a68b47378e5cb02071a9de2");
        response.setConcept("Buy puzzle");
        response.setProductQuantity(2);
        response.setOrigin("Emisor1");
        response.setDestiny("Store");
        response.setTotalAmount(10.50);
        response.setStatus(PaymentStatus.NEW);

        when(paymentService.setPayment(any(PaymentRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/payment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("6a68b47378e5cb02071a9de2"));
    }
    
    @Test
    void getAllPayments_shouldReturnAllPayments() throws Exception {
    	PaymentResponseDTO pay1 = new PaymentResponseDTO();
    	pay1.setId("6a68b47378e5cb02071a9de2");
    	pay1.setConcept("Buy puzzle");
    	pay1.setProductQuantity(2);
    	pay1.setOrigin("Emisor1");
    	pay1.setDestiny("Store");
    	pay1.setTotalAmount(10.50);
    	pay1.setStatus(PaymentStatus.NEW);
    	PaymentResponseDTO pay2 = new PaymentResponseDTO();
    	pay2.setId("6a68A66379y8cb02071a9af3");
    	pay2.setConcept("Buy puzzle");
    	pay2.setProductQuantity(2);
    	pay2.setOrigin("Emisor1");
    	pay2.setDestiny("Store");
    	pay2.setTotalAmount(10.50);
    	pay2.setStatus(PaymentStatus.NEW);

        when(paymentService.getAllPayments()).thenReturn(List.of(pay1, pay2));

        mockMvc.perform(get("/payment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("6a68b47378e5cb02071a9de2"))
                .andExpect(jsonPath("$[1].id").value("6a68A66379y8cb02071a9af3"));
    }
    
    @Test
    void getPaymentBy_shouldReturnPaymentById() throws Exception {
    	PaymentResponseDTO pay1 = new PaymentResponseDTO();
    	pay1.setId("6a68b47378e5cb02071a9de2");
    	pay1.setConcept("Buy puzzle");
    	pay1.setProductQuantity(2);
    	pay1.setOrigin("Emisor1");
    	pay1.setDestiny("Store");
    	pay1.setTotalAmount(10.50);
    	pay1.setStatus(PaymentStatus.NEW);

        when(paymentService.getPaymentBy("6a68b47378e5cb02071a9de2")).thenReturn(pay1);

        mockMvc.perform(get("/payment/by").param("id", "6a68b47378e5cb02071a9de2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("6a68b47378e5cb02071a9de2"));
    }
    
    @Test
    void updateStatusPayment_shouldUpdatePaymentStatus() throws Exception {
        PaymentUpdateRequestDTO updateRequest = new PaymentUpdateRequestDTO();
        updateRequest.setStatus(PaymentStatus.SUCCESSFUL);

        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setId("6a68b47378e5cb02071a9de2");
        response.setStatus(PaymentStatus.SUCCESSFUL);

        when(paymentService.updateStatusPayment("6a68b47378e5cb02071a9de2", PaymentStatus.SUCCESSFUL)).thenReturn(response);

        mockMvc.perform(patch("/payment/{id}", "6a68b47378e5cb02071a9de2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESSFUL"));
    }
}
