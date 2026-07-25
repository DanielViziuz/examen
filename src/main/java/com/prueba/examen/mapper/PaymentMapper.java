package com.prueba.examen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.prueba.examen.dto.PaymentRequestDTO;
import com.prueba.examen.dto.PaymentResponseDTO;
import com.prueba.examen.vo.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
	PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

	@Mapping(target = "concept", source = "concept")
	@Mapping(target = "productQuantity", source = "productQuantity")
	@Mapping(target = "origin", source = "origin")
	@Mapping(target = "destiny", source = "destiny")
	@Mapping(target = "totalAmount", source = "totalAmount")
	@Mapping(target = "status", source = "status")
	Payment toEntity(PaymentRequestDTO paymentRequestDTO);
	
	@Mapping(target = "concept", source = "concept")
	@Mapping(target = "productQuantity", source = "productQuantity")
	@Mapping(target = "origin", source = "origin")
	@Mapping(target = "destiny", source = "destiny")
	@Mapping(target = "totalAmount", source = "totalAmount")
	@Mapping(target = "status", source = "status")
	@Mapping(target = "id", source = "id")
	PaymentResponseDTO toDTO(Payment payment);
}
