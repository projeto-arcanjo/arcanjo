package br.com.cmabreu.rti1516e;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataTypes implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<SimpleData> simpleDataTypes;
	private List<BasicData> basicDataRepresentations;
	private List<FixedRecordData> fixedRecordDataTypes;
	private List<ArrayData> arrayDataTypes;
	private List<EnumeratedData> enumeratedDataTypes;
	private List<VariantRecordData> variantRecordDataTypes;
	
	

	public DataTypes() {
		simpleDataTypes = new ArrayList<SimpleData>(); 
		basicDataRepresentations = new ArrayList<BasicData>(); 
		fixedRecordDataTypes = new ArrayList<FixedRecordData>(); 
		arrayDataTypes = new ArrayList<ArrayData>(); 
		enumeratedDataTypes = new ArrayList<EnumeratedData>(); 
		variantRecordDataTypes = new ArrayList<VariantRecordData>();
	}

	public List<SimpleData> getSimpleDataTypes() {
		return simpleDataTypes;
	}
	
	public List<BasicData> getBasicDataRepresentations() {
		return basicDataRepresentations;
	}
	
	public List<FixedRecordData> getFixedRecordDataTypes() {
		return fixedRecordDataTypes;
	}

	public List<ArrayData> getArrayDataTypes() {
		return arrayDataTypes;
	}
	
	public List<EnumeratedData> getEnumeratedDataTypes() {
		return enumeratedDataTypes;
	}
	
	public List<VariantRecordData> getVariantRecordDataTypes() {
		return variantRecordDataTypes;
	}
	
}
