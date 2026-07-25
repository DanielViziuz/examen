package com.prueba.examen.vo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.prueba.examen.enumerable.PaymentStatus;

@Document(collection = "Payment")
public class Payment {

    @Id
    private String id;

    private String concept;

    private Integer productQuantity;

    private String origin;

    private String destiny;

    private double totalAmount;

    private PaymentStatus status;
    
    public String getId() {
    	return this.id;
    }
    
    public void setId(String id) {
    	this.id = id;
    }
    
    public String getConcept() {
    	return this.concept;
    }
    
    public void setConcept(String concept) {
    	this.concept = concept;
    }

	public Integer getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestiny() {
		return destiny;
	}

	public void setDestiny(String destiny) {
		this.destiny = destiny;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}
    
    
}
