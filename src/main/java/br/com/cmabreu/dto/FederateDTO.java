package br.com.cmabreu.dto;

import java.io.Serializable;

import br.com.cmabreu.entities.Federate;

public class FederateDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String classeTipo;
	private String hlaObjetName;
	private String federateName;
	private String federateType;
	private String federateHost;
	private String rtiVersion;
	private int reflectionsReceived;
	private int updatesSent;
	private int interactionsReceived;
	private int interactionsSent;
	private int objectInstancesUpdated;
	

	public FederateDTO( Federate federate ) {
		this.hlaObjetName = federate.getObjectName();
		this.classeTipo = federate.getClasseTipo();
		this.federateName = federate.gethLAfederateName();
		this.federateType = federate.gethLAfederateType();
		this.federateHost = federate.gethLAfederateHost();
		this.rtiVersion = federate.gethLARTIversion();
		this.reflectionsReceived = federate.gethLAreflectionsReceived();
		this.updatesSent = federate.gethLAupdatesSent();
		this.interactionsSent = federate.gethLAinteractionsSent();
		this.objectInstancesUpdated = federate.gethLAobjectInstancesUpdated();
	}

	public String getClasseTipo() {
		return classeTipo;
	}

	public String getHlaObjetName() {
		return hlaObjetName;
	}

	public String getFederateName() {
		return federateName;
	}
	
	public String getFederateType() {
		return federateType;
	}
	
	public String getFederateHost() {
		return federateHost;
	}
	
	public String getRtiVersion() {
		return rtiVersion;
	}
	
	public int getReflectionsReceived() {
		return reflectionsReceived;
	}
	
	public int getUpdatesSent() {
		return updatesSent;
	}
	
	public int getInteractionsReceived() {
		return interactionsReceived;
	}
	
	public int getInteractionsSent() {
		return interactionsSent;
	}
	
	public int getObjectInstancesUpdated() {
		return objectInstancesUpdated;
	}
	
	
}
