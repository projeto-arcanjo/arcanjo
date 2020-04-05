package br.com.cmabreu.datatypes;

import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.encoding.HLAoctet;

public class ForceIdentifierEnumEncoder {
	private HLAoctet forceIdEncoder;
	
	public ForceIdentifierEnumEncoder( int forceId ) {		
		try {
			forceIdEncoder = RtiFactoryFactory.getRtiFactory().getEncoderFactory().createHLAoctet();
		} catch (Exception RTIinternalError) {
			//
		}
		forceIdEncoder.setValue( (byte)forceId );   
	}	

    public byte[] toByteArray() {    	
    	return forceIdEncoder.toByteArray();
    }  

}
