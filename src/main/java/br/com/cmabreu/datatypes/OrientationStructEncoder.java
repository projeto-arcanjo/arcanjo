package br.com.cmabreu.datatypes;

import hla.rti1516e.RtiFactory;
import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.encoding.EncoderFactory;
import hla.rti1516e.encoding.HLAfixedRecord;
import hla.rti1516e.encoding.HLAfloat32BE;

public class OrientationStructEncoder {
	
	
	private EncoderFactory encoderFactory;
	
	{
		try {
			RtiFactory rtiFactory = RtiFactoryFactory.getRtiFactory();
			encoderFactory = rtiFactory.getEncoderFactory(); 
		}
		catch (Exception RTIinternalError) { 		
		}
	}	
		
    private HLAfixedRecord orientationEncoder = encoderFactory.createHLAfixedRecord();     
    private HLAfloat32BE psiEncoder = encoderFactory.createHLAfloat32BE();
    private HLAfloat32BE thetaEncoder = encoderFactory.createHLAfloat32BE();
    private HLAfloat32BE phiEncoder = encoderFactory.createHLAfloat32BE();
    
    public OrientationStructEncoder(OrientationStruct orientationStruct) {

    	orientationEncoder.add(psiEncoder);
    	orientationEncoder.add(thetaEncoder);
    	orientationEncoder.add(phiEncoder);         		
    	psiEncoder.setValue((float) orientationStruct.getpsi()); // ou orientationStruct.getpsi();
    	thetaEncoder.setValue((float) orientationStruct.gettheta());// ou orientationStruct.gettheta();
    	phiEncoder.setValue((float) orientationStruct.getphi());  		// ou orientationStruct.getphi();
    	
    }
    
    public byte[] toByteArray() {    	
    	return orientationEncoder.toByteArray();
    }  
    public HLAfixedRecord getorientationStructEncoder() {
    	return orientationEncoder;
    }
}
