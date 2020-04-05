package br.com.cmabreu.datatypes;

import hla.rti1516e.RtiFactory;
import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.encoding.EncoderFactory;
import hla.rti1516e.encoding.HLAfixedRecord;
import hla.rti1516e.encoding.HLAfloat32BE;

public class VelocityVectorStructEncoder {
	
	
	private EncoderFactory encoderFactory;
	
	{
		try {
			RtiFactory rtiFactory = RtiFactoryFactory.getRtiFactory();
			encoderFactory = rtiFactory.getEncoderFactory(); 
		}
		catch (Exception RTIinternalError) { 		
		}
	}	
		
    private HLAfixedRecord velocityEncoder = encoderFactory.createHLAfixedRecord();     
    private HLAfloat32BE xVelEncoder = encoderFactory.createHLAfloat32BE();
    private HLAfloat32BE yVelEncoder = encoderFactory.createHLAfloat32BE();
    private HLAfloat32BE zVelEncoder = encoderFactory.createHLAfloat32BE();
    
    public VelocityVectorStructEncoder(VelocityVectorStruct velocityVectorStruct) {
    	velocityEncoder.add(xVelEncoder);
    	velocityEncoder.add(yVelEncoder);
    	velocityEncoder.add(zVelEncoder);         		
    	xVelEncoder.setValue((float) velocityVectorStruct.getxVelocity()); // ou velocityVectorStruct.getxVelocity();
    	yVelEncoder.setValue((float) velocityVectorStruct.getyVelocity());// ou velocityVectorStruct.getyVelocity();
    	zVelEncoder.setValue((float) velocityVectorStruct.getzVelocity());  		// ou velocityVectorStruct.getzVelocity();
    	
    }
    
    public byte[] toByteArray() {    	
    	return velocityEncoder.toByteArray();
    }  
    public HLAfixedRecord getvelocityVectorStructEncoder() {
    	return velocityEncoder;
    }
}
