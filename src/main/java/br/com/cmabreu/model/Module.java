package br.com.cmabreu.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.cmabreu.misc.JSONHelper;
import br.com.cmabreu.rti1516e.Alternative;
import br.com.cmabreu.rti1516e.ArrayData;
import br.com.cmabreu.rti1516e.BasicData;
import br.com.cmabreu.rti1516e.DataTypes;
import br.com.cmabreu.rti1516e.EnumeratedData;
import br.com.cmabreu.rti1516e.Enumerator;
import br.com.cmabreu.rti1516e.Field;
import br.com.cmabreu.rti1516e.FixedRecordData;
import br.com.cmabreu.rti1516e.ObjectClass;
import br.com.cmabreu.rti1516e.SimpleData;
import br.com.cmabreu.rti1516e.VariantRecordData;

public class Module implements Serializable {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger( Module.class );
	
	private File file;
	private String name;
	private String purpose;
	private String description;
	private String glyph;
	private Integer interactionsCount;
	private Integer dimensionsCount;
	private Integer transportationsCount;
	private Integer dataTypesCount;
	private DataTypes dataTypes;
	private List<ObjectClass> classes;
	
	public Module(File file) {
		this.file = file;
		this.dataTypes = new DataTypes();
		this.interactionsCount = 0;
		this.dimensionsCount = 0;
		this.transportationsCount = 0;
		this.classes = new ArrayList<ObjectClass>();
	}

	public List<ObjectClass> getClasses() {
		return classes;
	}
	
	public DataTypes getDataTypes() {
		return dataTypes;
	}
	
	public File getFile() {
		return file;
	}

	public String getFileName() {
		return this.file.getName();
	}
	
	public Integer getInteractionsCount() {
		return interactionsCount;
	}

	public Integer getDimensionsCount() {
		return dimensionsCount;
	}

	public Integer getTransportationsCount() {
		return transportationsCount;
	}

	public Integer getDataTypesCount() {
		return dataTypesCount;
	}

	public void processDataTypes( JSONObject jobj ) {
		logger.info("Lendo tipos de dados...");
		try {
			if( jobj.has("simpleDataTypes") ) {
				Object obj = jobj.get("simpleDataTypes");
				if ( obj instanceof JSONObject ) processSimpleDataTypes( (JSONObject)obj );
			}
			if( jobj.has("fixedRecordDataTypes") ) {
				Object obj = jobj.get("fixedRecordDataTypes");
				if ( obj instanceof JSONObject ) processFixedRecordDataTypes( (JSONObject)obj );
			}
			if( jobj.has("basicDataRepresentations") ) {
				Object obj = jobj.get("basicDataRepresentations");
				if ( obj instanceof JSONObject ) processBasicDataRepresentations( (JSONObject)obj );
			}
			if( jobj.has("arrayDataTypes") ) {
				Object obj = jobj.get("arrayDataTypes");
				if ( obj instanceof JSONObject ) processArrayDataTypes( (JSONObject)obj );
			}
			if( jobj.has("enumeratedDataTypes") ) {
				Object obj = jobj.get("enumeratedDataTypes");
				if ( obj instanceof JSONObject ) processEnumeratedDataTypes( (JSONObject)obj );
			}
			
			if( jobj.has("variantRecordDataTypes") ) {
				Object obj = jobj.get("variantRecordDataTypes");
				if ( obj instanceof JSONObject ) processVariantRecordDataTypes( (JSONObject)obj );
			}
			
			logger.info("Tipos de dados carregados.");
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	private void processBasicDataRepresentations(JSONObject basicDataRepresentations) throws Exception {
		JSONArray basicDatas = JSONHelper.getFlexible( basicDataRepresentations, "basicData" ); // basicDataRepresentations.getJSONArray("basicData");
		for ( int x=0; x < basicDatas.length(); x++ ) {
			JSONObject basicData = basicDatas.getJSONObject(x);
			String name = JSONHelper.getString(basicData,"name");
			Integer size = JSONHelper.getInt(basicData,"size");
			String interpretation = JSONHelper.getString(basicData,"interpretation");
			String endian = JSONHelper.getString(basicData,"endian");
			String encoding = JSONHelper.getString(basicData,"encoding");
			this.dataTypes.getBasicDataRepresentations().add( new BasicData(name, size, interpretation, endian, encoding) );
		}
	}

	private void processSimpleDataTypes(JSONObject simpleDataTypes) throws Exception {
		JSONArray simpleDatas = JSONHelper.getFlexible( simpleDataTypes, "simpleData" ); // simpleDataTypes.getJSONArray("simpleData");
		for ( int x=0; x < simpleDatas.length(); x++ ) {
			JSONObject simpleData = simpleDatas.getJSONObject(x);
			String name = JSONHelper.getString(simpleData,"name");
			String accuracy = JSONHelper.getString(simpleData,"accuracy");
			String units = JSONHelper.getString(simpleData,"units");
			String semantics = JSONHelper.getString(simpleData,"semantics");
			String representation = JSONHelper.getString(simpleData,"representation");
			String resolution = String.valueOf( simpleData.get("resolution") );
			this.dataTypes.getSimpleDataTypes().add( new SimpleData(name, representation, units, resolution, accuracy, semantics) );
		}
	}

	private void processVariantRecordDataTypes(JSONObject variantRecordDataTypes) throws Exception {
		JSONArray variantRecordDatas = JSONHelper.getFlexible( variantRecordDataTypes, "variantRecordData" ); // arrayDataTypes.getJSONArray("arrayData");
		for ( int x=0; x < variantRecordDatas.length(); x++ ) {
			JSONObject variantRecordData = variantRecordDatas.getJSONObject(x);
			String name = JSONHelper.getString(variantRecordData,"name");
			String dataType = JSONHelper.getString(variantRecordData,"dataType");
			String discriminant = JSONHelper.getString(variantRecordData,"discriminant");
			String encoding = JSONHelper.getString(variantRecordData,"encoding");
			String semantics = JSONHelper.getString(variantRecordData,"semantics");
			JSONArray alternatives = JSONHelper.getFlexible( variantRecordData, "alternative");
			VariantRecordData vrd = new VariantRecordData( name, discriminant, dataType, encoding, semantics ); 
			for( int y=0; y < alternatives.length(); y++  ) {
				JSONObject alternative = alternatives.getJSONObject(y);
				String aenumerator = alternative.getString("enumerator");
				String aname = alternative.getString("name");
				String adataType = alternative.getString("dataType");
				String asemantics = alternative.getString("semantics");
				vrd.getAlternatives().add( new Alternative( aenumerator,  aname,  adataType,  asemantics )  );
			}
			this.dataTypes.getVariantRecordDataTypes().add( vrd );
		}
	}
	
	
	private void processFixedRecordDataTypes(JSONObject fixedRecordDataTypes) throws Exception {
		JSONArray fixedRecordDatas = JSONHelper.getFlexible( fixedRecordDataTypes, "fixedRecordData" ); 
		for ( int x=0; x < fixedRecordDatas.length(); x++ ) {
			JSONObject fixedRecordData = fixedRecordDatas.getJSONObject(x);
			String name = JSONHelper.getString(fixedRecordData,"name");
			String encoding = JSONHelper.getString(fixedRecordData,"encoding");
			String semantics = JSONHelper.getString(fixedRecordData,"semantics");
			JSONArray fields = JSONHelper.getFlexible(fixedRecordData,"field");
			FixedRecordData frd = new FixedRecordData(name, encoding, semantics); 
			for( int y=0; y < fields.length(); y++  ) {
				JSONObject field = fields.getJSONObject(y);
				String fName = JSONHelper.getString(field,"name");
				String fDataType = JSONHelper.getString(field,"dataType");
				String fSemantics = JSONHelper.getString(field,"semantics");
				frd.getFields().add( new Field( fName, fDataType, fSemantics)  );
			}
			this.dataTypes.getFixedRecordDataTypes().add( frd );
		}
	}

	private void processArrayDataTypes(JSONObject arrayDataTypes) throws Exception {
		JSONArray arrayDatas = JSONHelper.getFlexible( arrayDataTypes, "arrayData" );// arrayDataTypes.getJSONArray("arrayData");
		for ( int x=0; x < arrayDatas.length(); x++ ) {
			JSONObject arrayData = arrayDatas.getJSONObject(x);
			String name = JSONHelper.getString(arrayData,"name");
			String dataType = JSONHelper.getString(arrayData,"dataType");
			String cardinality = String.valueOf( arrayData.get("cardinality") );
			String encoding = JSONHelper.getString(arrayData,"encoding");
			String semantics = JSONHelper.getString(arrayData,"semantics");
			this.dataTypes.getArrayDataTypes().add( new ArrayData(name, dataType, cardinality, encoding, semantics)  );
		}
	}

	private void processEnumeratedDataTypes(JSONObject enumeratedDataTypes)  {
		JSONArray enumeratedDatas = JSONHelper.getFlexible( enumeratedDataTypes, "enumeratedData" );
		for ( int x=0; x < enumeratedDatas.length(); x++ ) {
			JSONObject enumeratedData = enumeratedDatas.getJSONObject(x);
			
			try {
				String name = JSONHelper.getString(enumeratedData,"name");
				String representation = JSONHelper.getString(enumeratedData,"representation");
				String semantics = JSONHelper.getString(enumeratedData,"semantics");
				JSONArray enumerators = JSONHelper.getFlexible(enumeratedData,"enumerator");
				EnumeratedData ed = new EnumeratedData(name, representation, semantics); 
				
				for( int y=0; y < enumerators.length(); y++  ) {
					JSONObject enumerator = enumerators.getJSONObject(y);
					
					String fName = JSONHelper.getString(enumerator, "name");
					Integer fValue = JSONHelper.getInt( enumerator, "value");
					
					ed.getEnumerators().add( new Enumerator( fName, fValue)  );
				}
				
				this.dataTypes.getEnumeratedDataTypes().add( ed );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setIdentification(JSONObject identification) throws Exception {
		this.name = JSONHelper.getString( identification, "name" );
		this.purpose = JSONHelper.getString( identification, "purpose" );
		this.description = JSONHelper.getString( identification, "description");
		try {
			this.glyph = JSONHelper.getString( identification.getJSONObject("glyph"), "content");
		} catch ( Exception e ) {
			
		}
	}

	public String getName() {
		return name;
	}

	public String getPurpose() {
		return purpose;
	}

	public String getDescription() {
		return description;
	}

	public String getGlyph() {
		return glyph;
	}

	public void setTransportations( Object transps ) {
		//
	}

	public void setDimensions( Object dims ) {
		
		if ( dims instanceof JSONObject ) {
			//System.out.println( ((JSONObject)dims).toString() );
		}
		if ( dims instanceof JSONArray ) {
			//System.out.println( ((JSONArray)dims).toString() );
		}		
		
	}

	public void setInteracions( Object inercs ) {
		//
	}


}
