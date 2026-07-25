package com.prueba.examen.enumerable;

public enum PaymentStatus {
	NEW(1),	IN_PROGRESS(2),	SUCCESSFUL(3), ERROR(4);
	
	
	private int id;
	
	private String description;
	
	PaymentStatus(int id) {
		this.id = id;
		switch(id) {
			case 1:
				this.description = "NEW";
				break;
			case 2:
				this.description = "IN_PROGRESS";
				break;
			case 3:
				this.description = "SUCCESSFUL";
				break;
			case 4:
				this.description = "ERROR";
				break;
			default:
				break;
		}
	}
	public int getId(){ return this.id; }
	
	public String getDescription() {return this.description;}
}
