package br.com.cmabreu.rti1516e;

import java.io.Serializable;

/*
     <simpleData>
        <name>HLAfloat64Time</name>
        <representation>HLAfloat64BE</representation>
        <units>NA</units>
        <resolution>4.9E-308</resolution>
        <accuracy>NA</accuracy>
        <semantics>Standardized 64 bit float time</semantics>
     </simpleData>		
*/

public class SimpleData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String representation;
	private String units;
	private String resolution;
	private String accuracy;
	private String semantics;
	
	public SimpleData(String name, String representation, String units, String resolution, String accuracy, String semantics) {
		this.name = name;
		this.representation = representation;
		this.units = units;
		this.resolution = resolution;
		this.accuracy = accuracy;
		this.semantics = semantics;
	}

	public String getName() {
		return name;
	}
	
	public String getRepresentation() {
		return representation;
	}
	
	public String getUnits() {
		return units;
	}
	
	public String getResolution() {
		return resolution;
	}
	
	public String getAccuracy() {
		return accuracy;
	}
	
	public String getSemantics() {
		return semantics;
	}

}


