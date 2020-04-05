package br.com.cmabreu.rti1516e;

import java.io.Serializable;

import org.json.JSONObject;

import br.com.cmabreu.misc.JSONHelper;

/*
    <attribute notes="RPRnoteBase2">
       <name>EntityType</name>
       <dataType>EntityTypeStruct</dataType>
       <updateType>Static</updateType>
       <updateCondition>NA</updateCondition>
       <ownership>DivestAcquire</ownership>
       <sharing>PublishSubscribe</sharing>
       <transportation>HLAbestEffort</transportation>
       <order>Receive</order>
       <semantics>The category of the entity.</semantics>
    </attribute>
*/

public class Attribute implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String dataType;
	private String semantics;
	private String sharing;
	private String updateType;
	private String order;
	private String updateCondition;
	private String transportation;
	private Integer handle;
	
	public Attribute( JSONObject attribute ) throws Exception {
		this.name = JSONHelper.getString(attribute,"name");
		this.dataType = JSONHelper.getString(attribute,"dataType");
		this.semantics = JSONHelper.getString(attribute,"semantics");
		this.sharing = JSONHelper.getString(attribute,"sharing");
		this.updateType = JSONHelper.getString(attribute,"updateType");
		this.updateCondition = JSONHelper.getString(attribute,"updateCondition");
		this.transportation = JSONHelper.getString(attribute,"transportation");
		this.order = JSONHelper.getString(attribute,"order");
	}

	public void setHandle(Integer handle) {
		this.handle = handle;
	}
	
	public Integer getHandle() {
		return handle;
	}
	
	public String getName() {
		return name;
	}

	public String getDataType() {
		return dataType;
	}

	public String getSemantics() {
		return semantics;
	}

	public String getSharing() {
		return sharing;
	}

	public String getUpdateType() {
		return updateType;
	}

	public String getUpdateCondition() {
		return updateCondition;
	}
	
	public String getTransportation() {
		return transportation;
	}
	
	public String getOrder() {
		return order;
	}
	
}
