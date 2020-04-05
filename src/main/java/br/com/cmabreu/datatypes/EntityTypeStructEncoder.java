package br.com.cmabreu.datatypes;

import br.com.cmabreu.datatypes.EntityTypeStruct;

import hla.rti1516e.*;
import hla.rti1516e.encoding.EncoderFactory;
import hla.rti1516e.encoding.HLAASCIIchar;
import hla.rti1516e.encoding.HLAfixedRecord;
import hla.rti1516e.encoding.HLAinteger16BE;
import hla.rti1516e.RtiFactoryFactory;

public class EntityTypeStructEncoder {
	private EncoderFactory encoderFactory;
	
	{
		try {
			RtiFactory rtiFactory = RtiFactoryFactory.getRtiFactory();
			encoderFactory = rtiFactory.getEncoderFactory(); 
		}
		catch (Exception RTIinternalError) { 		
		}
	}	
	
	private HLAfixedRecord entityTypeEncoder = encoderFactory.createHLAfixedRecord();
	
	private HLAASCIIchar _entityKind = encoderFactory.createHLAASCIIchar();
	private HLAASCIIchar _domain = encoderFactory.createHLAASCIIchar();
	private HLAinteger16BE _countryCode = encoderFactory.createHLAinteger16BE();
	private HLAASCIIchar _category = encoderFactory.createHLAASCIIchar();
	private HLAASCIIchar _subCategory = encoderFactory.createHLAASCIIchar();
	private HLAASCIIchar _specific = encoderFactory.createHLAASCIIchar();
	private HLAASCIIchar _extra = encoderFactory.createHLAASCIIchar();
    	
		
    public EntityTypeStructEncoder(EntityTypeStruct entityTypeStruct) {    
  	
    	_entityKind.setValue(entityTypeStruct.getentityKind());
	    _domain.setValue(entityTypeStruct.getdomain());
	    _countryCode.setValue(entityTypeStruct.getcountryCode());
	    _category.setValue(entityTypeStruct.getcategory());
	    _subCategory.setValue(entityTypeStruct.getsubCategory());
	    _specific.setValue(entityTypeStruct.getspecific());
	    _extra.setValue(entityTypeStruct.getextra());
	    
        entityTypeEncoder.add(_entityKind);
        entityTypeEncoder.add(_domain);
        entityTypeEncoder.add(_countryCode);
        entityTypeEncoder.add(_category);
        entityTypeEncoder.add(_subCategory);
        entityTypeEncoder.add(_specific);
        entityTypeEncoder.add(_extra);    
        
    }
    
    
    public byte[] toByteArray() {
    	
    	return entityTypeEncoder.toByteArray();
    }
    
	
}
