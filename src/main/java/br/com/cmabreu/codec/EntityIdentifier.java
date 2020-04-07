package br.com.cmabreu.codec;

public class EntityIdentifier {
	private short sideId;
	private short applicationId;
	private short entityNumber;
	
	public EntityIdentifier(short a, short b, short c) {
		sideId = a;
		applicationId = b;
		entityNumber = c;	
	}
	public EntityIdentifier(int a, int b, int c) {
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
