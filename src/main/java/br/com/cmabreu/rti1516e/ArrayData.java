package br.com.cmabreu.rti1516e;

import java.io.Serializable;

/*
<arrayData>
   <name>HLAhandleList</name>
   <dataType>HLAhandle</dataType>
   <cardinality>Dynamic</cardinality>
   <encoding>HLAvariableArray</encoding>
   <semantics>List of encoded handles</semantics>
</arrayData>		
*/

public class ArrayData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String dataType;
	private String cardinality;
	private String encoding;
	private String semantics;
	
	public ArrayData(String name, String dataType, String cardinality, String encoding, String semantics) {
		this.name = name;
		this.dataType = dataType;
		this.cardinality = cardinality;
		this.encoding = encoding;
		this.semantics = semantics;
	}

	public String getName() {
		return name;
	}

	public String getDataType() {
		return dataType;
	}

	public String getCardinality() {
		return cardinality;
	}

	public String getEncoding() {
		return encoding;
	}

	public String getSemantics() {
		return semantics;
	}

}
