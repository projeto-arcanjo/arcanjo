package br.com.cmabreu.rti1516e;

import java.io.Serializable;

public class ObjectInstance implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private ObjectClass objectClass;
	private String objectName;
	private Integer objectInstanceHandle;

	public ObjectInstance(Integer objectInstanceHandle, ObjectClass objectClass, String objectName) {
		this.objectClass = objectClass;
		this.objectName = objectName;
		this.objectInstanceHandle = objectInstanceHandle;
	}

	public ObjectClass getObjectClass() {
		return objectClass;
	}


	public String getObjectName() {
		return objectName;
	}
	
	public Integer getObjectInstanceHandle() {
		return objectInstanceHandle;
	}
	
}
