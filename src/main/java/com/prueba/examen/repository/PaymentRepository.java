package com.prueba.examen.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.prueba.examen.vo.Payment;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, String>{

}
