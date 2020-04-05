package br.com.cmabreu.datatypes;

import hla.rti1516e.RtiFactory;
import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.encoding.EncoderFactory;
import hla.rti1516e.encoding.HLAfixedRecord;
import hla.rti1516e.encoding.HLAinteger16BE;

public class EntityIdentifierStructEncoder {
	
	private EncoderFactory encoderFactory;
	
	{
		try {
			RtiFactory rtiFactory = RtiFactoryFactory.getRtiFactory();
			encoderFactory = rtiFactory.getEncoderFactory(); 
		}
		catch (Exception RTIinternalError) { 		
		}
	}		
   private HLAfixedRecord entityIdentifierEncoder = encoderFactory.createHLAfixedRecord();
   private HLAinteger16BE siteId = encoderFactory.createHLAinteger16BE();
   private HLAinteger16BE applicationId = encoderFactory.createHLAinteger16BE();
   private HLAinteger16BE entityNumber = encoderFactory.createHLAinteger16BE();
   
   public EntityIdentifierStructEncoder(EntityIdentifierStruct entityIdentifierStruct) {
       
	   siteId.setValue((short) 3001);
       applicationId.setValue((short) 101);
       entityNumber.setValue((short) 102);
       entityIdentifierEncoder.add(siteId);
       entityIdentifierEncoder.add(applicationId);
       entityIdentifierEncoder.add(entityNumber);
   }
   public byte[] toByteArray() {    	
   	return entityIdentifierEncoder.toByteArray();
   }
   public HLAfixedRecord getentityIdentifierEncoder() {
   	return entityIdentifierEncoder;
   }
	
}
