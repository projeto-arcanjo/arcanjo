package br.com.cmabreu.rti1516e;

import java.io.Serializable;

/*
    <alternative>
        <enumerator>Trainee</enumerator>
        <name>CoursePassed</name>
        <dataType>HLAboolean</dataType>
        <semantics>Ratings scale for employees under training</semantics>
    </alternative>
*/

public class Alternative implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String enumerator;
	private String name;
	private String dataType;
	private String semantics;
	
	
	public Alternative(String enumerator, String name, String dataType, String semantics) {
		this.enumerator = enumerator;
		this.name = name;
		this.dataType = dataType;
		this.semantics = semantics;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getEnumerator() {
		return enumerator;
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
