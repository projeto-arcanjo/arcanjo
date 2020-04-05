package br.com.cmabreu.datatypes;


import hla.rti1516e.RtiFactory;
import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.encoding.EncoderFactory;
import hla.rti1516e.encoding.HLAfixedRecord;
import hla.rti1516e.encoding.HLAoctet;
import br.com.cmabreu.datatypes.WorldLocationStructEncoder;


public class SpatialFPStructEncoder {
	private EncoderFactory encoderFactory;	
	{
		try {
			RtiFactory rtiFactory = RtiFactoryFactory.getRtiFactory();
			encoderFactory = rtiFactory.getEncoderFactory(); 
		}
		catch (Exception RTIinternalError) { 		
		}
	}	
	
	HLAfixedRecord spatialFPEncoder = encoderFactory.createHLAfixedRecord();    
	HLAoctet isFrozen = encoderFactory.createHLAoctet();	
	
	public SpatialFPStructEncoder(WorldLocationStructEncoder worldLocationStructEncoder, 
			VelocityVectorStructEncoder velocityVectorStructEncoder,
			OrientationStructEncoder orientationStructEncoder) {
		spatialFPEncoder.add(worldLocationStructEncoder.getworldLocationStructEncoder());
		spatialFPEncoder.add(orientationStructEncoder.getorientationStructEncoder());
		spatialFPEncoder.add(velocityVectorStructEncoder.getvelocityVectorStructEncoder());
		isFrozen.setValue((byte) 0);
		spatialFPEncoder.add(isFrozen);	

	}
	public SpatialFPStructEncoder(SpatialFPStruct spatialFPStruct) {
		WorldLocationStructEncoder worldLocStructEnc = new WorldLocationStructEncoder(
				spatialFPStruct.getWorldLocationStruct());
		OrientationStructEncoder orientStructEnc = new OrientationStructEncoder(
				spatialFPStruct.getOrientationStruct());
		VelocityVectorStructEncoder velocityVecStruct = new VelocityVectorStructEncoder(
				spatialFPStruct.getVelocityVectorStruct());		
		spatialFPEncoder.add(worldLocStructEnc.getworldLocationStructEncoder());
		spatialFPEncoder.add(orientStructEnc.getorientationStructEncoder());
		spatialFPEncoder.add(velocityVecStruct.getvelocityVectorStructEncoder());
		isFrozen.setValue((byte) 0);
		spatialFPEncoder.add(isFrozen);	
	}
	
	
	
    public byte[] toByteArray() {    	
    	return spatialFPEncoder.toByteArray();
    }  
	
	
}

