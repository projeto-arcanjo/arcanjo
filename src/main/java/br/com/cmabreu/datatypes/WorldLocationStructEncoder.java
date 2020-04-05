package br.com.cmabreu.datatypes;

import hla.rti1516e.RtiFactory;
import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.encoding.EncoderFactory;
import hla.rti1516e.encoding.HLAfixedRecord;
import hla.rti1516e.encoding.HLAfloat64BE;

public class WorldLocationStructEncoder {
		
	private EncoderFactory encoderFactory;
	
	{
		try {
			RtiFactory rtiFactory = RtiFactoryFactory.getRtiFactory();
			encoderFactory = rtiFactory.getEncoderFactory(); 
		}
		catch (Exception RTIinternalError) { 		
		}
	}	
		
    private HLAfixedRecord posEncoder = encoderFactory.createHLAfixedRecord(); 
    private HLAfloat64BE cabecalhoEncoder = encoderFactory.createHLAfloat64BE();    
    private HLAfloat64BE xEncoder = encoderFactory.createHLAfloat64BE();
    private HLAfloat64BE yEncoder = encoderFactory.createHLAfloat64BE();
    private HLAfloat64BE zEncoder = encoderFactory.createHLAfloat64BE();
 	
    public WorldLocationStructEncoder(WorldLocationStruct worldLocation) {
    	//7.291122019556398e-304
    	//4114673.3659611
    	//-4190319.2556862
    		cabecalhoEncoder.setValue(worldLocation.getCabecalho());
		    xEncoder.setValue(worldLocation.getWorldLocationX());
		    yEncoder.setValue(worldLocation.getWorldLocationY());
		    zEncoder.setValue(worldLocation.getWorldLocationZ());  
		    	posEncoder.add(cabecalhoEncoder);
		        posEncoder.add(xEncoder);
		        posEncoder.add(yEncoder);
		        posEncoder.add(zEncoder);    
    }
    
    public byte[] toByteArray() {    	
    	return posEncoder.toByteArray();
    }
    public HLAfixedRecord getworldLocationStructEncoder() {
    	return posEncoder;
    }    
}
