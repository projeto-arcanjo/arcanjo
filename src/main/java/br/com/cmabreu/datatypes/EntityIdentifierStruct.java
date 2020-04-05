package br.com.cmabreu.datatypes;

public class EntityIdentifierStruct {
	private short sideId;
	private short applicationId;
	private short entityNumber;
	
	public EntityIdentifierStruct(short a, short b, short c) {
		sideId = a;
		applicationId = b;
		entityNumber = c;	
	}
	public EntityIdentifierStruct(int a, int b, int c) {
		sideId = (short) a;
		applicationId = (short) b;
		entityNumber = (short) c;	
	}
	public short getSideId() {
		return sideId;
	}

	public void setSideId(short sideId) {
		this.sideId = sideId;
	}

	public short getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(short applicationId) {
		this.applicationId = applicationId;
	}

	public short getEntityNumber() {
		return entityNumber;
	}

	public void setEntityNumber(short entityNumber) {
		this.entityNumber = entityNumber;
	}
	
	
}
