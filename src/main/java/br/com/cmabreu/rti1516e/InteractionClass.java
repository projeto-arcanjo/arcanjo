package br.com.cmabreu.rti1516e;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import br.com.cmabreu.misc.JSONHelper;

/*
    <name>OrderTaken</name>
    <sharing>Publish</sharing>
    <dimensions/>
    <transportation>HLAreliable</transportation>
    <order>TimeStamp</order>
    <semantics>Waiter has taken customers order</semantics>
    <dimensions>
    	<dimension>HLAfederate</dimension>
    </dimensions>
    <parameter>
		<name>HLAtransportation</name>
		<dataType>HLAtransportationName</dataType>
		<semantics>Transportation type used in receiving reflections</semantics>
    </parameter>        
*/

public class InteractionClass implements Serializable {
	private static final long serialVersionUID = 1L;
	private String fullName;
	private String name;
	private String sharing;
	private String transportation;
	private String order;
	private String semantics;
	private String moduleName;
	private List<Parameter> parameters;
	private Integer handle;
	
	public InteractionClass( String fullName, JSONObject interactionClass, String moduleName ) throws Exception {
		this.parameters = new ArrayList<Parameter>();
		this.fullName = fullName;
		this.name = JSONHelper.getString(interactionClass,"name");
		this.sharing = JSONHelper.getString(interactionClass,"sharing");
		this.transportation = JSONHelper.getString(interactionClass,"transportation");
		this.order = JSONHelper.getString(interactionClass,"order");
		this.semantics = JSONHelper.getString(interactionClass,"semantics");
		this.moduleName = moduleName;
		
		if( interactionClass.has("parameter") ) {
			JSONArray parameters = JSONHelper.getFlexible(interactionClass, "parameter");
			for( int x = 0; x < parameters.length(); x++ ) {
				JSONObject parameter = parameters.getJSONObject( x );
				String pName = JSONHelper.getString( parameter,"name");
				String pDataType = JSONHelper.getString( parameter,"dataType");
				String pSemantics = JSONHelper.getString( parameter,"semantics");
				this.parameters.add( new Parameter( pName, pDataType, pSemantics ) );
			}
		}
		
	}
	
	public Integer getHandle() {
		return handle;
	}

	public void setHandle(Integer handle) {
		this.handle = handle;
	}

	public String getModuleName() {
		return moduleName;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Parameter> getParameters() {
		return parameters;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public String getSharing() {
		return sharing;
	}
	
	public String getTransportation() {
		return transportation;
	}
	
	public String getOrder() {
		return order;
	}
	
	public String getSemantics() {
		return semantics;
	}
	
	
}
