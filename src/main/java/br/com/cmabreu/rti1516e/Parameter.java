package br.com.cmabreu.rti1516e;

/*
    <parameter>
		<name>HLAtransportation</name>
		<dataType>HLAtransportationName</dataType>
		<semantics>Transportation type used in receiving reflections</semantics>
    </parameter>     
*/

public class Parameter {
	private String name;
	private String dataType;
	private String semantics;
	
	public Parameter(String name, String dataType, String semantics) {
		this.name = name;
		this.dataType = dataType;
		this.semantics = semantics;
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

}
