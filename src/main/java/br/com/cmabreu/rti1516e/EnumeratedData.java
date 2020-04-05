package br.com.cmabreu.rti1516e;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
     <enumeratedData>
        <name>HLAownership</name>
        <representation>HLAinteger32BE</representation>
        <semantics>NA</semantics>
        <enumerator>
           <name>Unowned</name>
           <value>0</value>
        </enumerator>
        <enumerator>
           <name>Owned</name>
           <value>1</value>
        </enumerator>
     </enumeratedData>
*/

public class EnumeratedData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String representation;
	private String semantics;
	private List<Enumerator> enumerators;
	
	public EnumeratedData(String name, String representation, String semantics) {
		this.name = name;
		this.representation = representation;
		this.semantics = semantics;
		this.enumerators = new ArrayList<Enumerator>();
	}

	public String getName() {
		return name;
	}
	
	public String getRepresentation() {
		return representation;
	}
	
	public String getSemantics() {
		return semantics;
	}
	
	public List<Enumerator> getEnumerators() {
		return enumerators;
	}
	
}
