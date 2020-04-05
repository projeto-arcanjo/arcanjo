package br.com.cmabreu.rti1516e;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
     <fixedRecordData>
        <name>HLAinteractionSubscription</name>
        <encoding>HLAfixedRecord</encoding>
        <semantics>Interaction subscription information</semantics>
        <field>
           <name>HLAinteractionClass</name>
           <dataType>HLAhandle</dataType>
           <semantics>Encoded interaction class handle</semantics>
        </field>
        <field>
           <name>HLAactive</name>
           <dataType>HLAboolean</dataType>
           <semantics>Whether subscription is active (HLAtrue=active)</semantics>
        </field>
     </fixedRecordData>
*/

public class FixedRecordData implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String encoding;
	private String semantics;
	private List<Field> fields;
	
	public FixedRecordData(String name, String encoding, String semantics) {
		this.name = name;
		this.encoding = encoding;
		this.semantics = semantics;
		this.fields = new ArrayList<Field>();
	}

	public String getName() {
		return name;
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	public String getSemantics() {
		return semantics;
	}
	
	public List<Field> getFields() {
		return fields;
	}
	
}
