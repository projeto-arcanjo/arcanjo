package br.com.cmabreu.rti1516e;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
    <variantRecordData>
        <name>WaiterValue</name>
        <discriminant>ValIndex</discriminant>
        <dataType>ExperienceLevel</dataType>
        <alternative>
            <enumerator>Trainee</enumerator>
            <name>CoursePassed</name>
            <dataType>HLAboolean</dataType>
            <semantics>Ratings scale for employees under training</semantics>
        </alternative>
        <alternative>
            <enumerator>[Apprentice .. Senior], Master</enumerator>
            <name>Rating</name>
            <dataType>RateScale</dataType>
            <semantics>Ratings scale for permanent employees</semantics>
        </alternative>
        <alternative>
            <enumerator>HLAother</enumerator>
            <name>Other</name>
            <dataType>HLAtoken</dataType>
            <semantics>All others</semantics>
        </alternative>
        <encoding>HLAvariantRecord</encoding>
        <semantics>Datatype for waiter performance rating value</semantics>
    </variantRecordData>
*/

public class VariantRecordData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String discriminant;
	private String dataType;
	private String encoding;
	private String semantics;
	private List<Alternative> alternatives;
	
	public VariantRecordData(String name, String discriminant, String dataType, String encoding, String semantics) {
		this.name = name;
		this.discriminant = discriminant;
		this.dataType = dataType;
		this.encoding = encoding;
		this.semantics = semantics;
		this.alternatives = new ArrayList<Alternative>();
	}

	public String getName() {
		return name;
	}

	public String getDiscriminant() {
		return discriminant;
	}

	public String getDataType() {
		return dataType;
	}

	public String getEncoding() {
		return encoding;
	}

	public String getSemantics() {
		return semantics;
	}
	
	public List<Alternative> getAlternatives() {
		return alternatives;
	}

}
