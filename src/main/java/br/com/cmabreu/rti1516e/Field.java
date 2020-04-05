package br.com.cmabreu.rti1516e;

import java.io.Serializable;

/*
    <field>
       <name>HLAinteractionClass</name>
       <dataType>HLAhandle</dataType>
       <semantics>Encoded interaction class handle</semantics>
    </field>
*/

public class Field implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String dataType;
	private String semantics;
	
	public Field(String name, String dataType, String semantics) {
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
