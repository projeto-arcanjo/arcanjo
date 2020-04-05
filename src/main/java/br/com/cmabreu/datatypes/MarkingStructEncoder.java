package br.com.cmabreu.datatypes;

import hla.rti1516e.RtiFactory;
import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.encoding.EncoderFactory;
import hla.rti1516e.encoding.HLAfixedRecord;
import hla.rti1516e.encoding.HLAoctet;

public class MarkingStructEncoder {
	private EncoderFactory encoderFactory;
	
	{
		try {
			RtiFactory rtiFactory = RtiFactoryFactory.getRtiFactory();
			encoderFactory = rtiFactory.getEncoderFactory(); 
		}
		catch (Exception RTIinternalError) { 		
		}
	}		
    HLAfixedRecord markingTypeEncoder = encoderFactory.createHLAfixedRecord();
    HLAoctet markingEncodingType = encoderFactory.createHLAoctet();   
    
    
	//Implementacao Forca Bruta
	// Nao consegui trabalhar com HLAfixedArray
	// Nao consegui setar as info em loops
    HLAfixedRecord markingData = encoderFactory.createHLAfixedRecord();
    HLAoctet char00 = encoderFactory.createHLAoctet();
    HLAoctet char01 = encoderFactory.createHLAoctet();
    HLAoctet char02 = encoderFactory.createHLAoctet();
    HLAoctet char03 = encoderFactory.createHLAoctet();
    HLAoctet char04 = encoderFactory.createHLAoctet();
    HLAoctet char05 = encoderFactory.createHLAoctet();
    HLAoctet char06 = encoderFactory.createHLAoctet();
    HLAoctet char07 = encoderFactory.createHLAoctet();
    HLAoctet char08 = encoderFactory.createHLAoctet();
    HLAoctet char09 = encoderFactory.createHLAoctet();
    HLAoctet char10 = encoderFactory.createHLAoctet();  
    
    public MarkingStructEncoder(MarkingStruct markingStruct) {
    	//Implementacao Forca Bruta
    	// Nao consegui trabalhar com HLAfixedArray
    	// Nao consegui setar as info em loops
    	markingEncodingType.setValue((byte) markingStruct.getMarkingEncodingType());
    	char00.setValue((byte) markingStruct.getMarkingData(0)); 
    	char01.setValue((byte) markingStruct.getMarkingData(1));
    	char02.setValue((byte) markingStruct.getMarkingData(2));
    	char03.setValue((byte) markingStruct.getMarkingData(3));
    	char04.setValue((byte) markingStruct.getMarkingData(4));
    	char05.setValue((byte) markingStruct.getMarkingData(5));
    	char06.setValue((byte) markingStruct.getMarkingData(6));
    	char07.setValue((byte) markingStruct.getMarkingData(7));
    	char08.setValue((byte) markingStruct.getMarkingData(8));
    	char09.setValue((byte) markingStruct.getMarkingData(9));
    	char10.setValue((byte) markingStruct.getMarkingData(10));

	    markingTypeEncoder.add(markingEncodingType);    
	    markingData.add(char00);    
	    markingData.add(char01);
	    markingData.add(char02);
	    markingData.add(char03);
	    markingData.add(char04);
	    markingData.add(char05);
	    markingData.add(char06);
	    markingData.add(char07);
	    markingData.add(char08);
	    markingData.add(char09);
	    markingData.add(char10);
	    markingTypeEncoder.add(markingData);
    }
    
    public byte[] toByteArray() {    	
       	return markingTypeEncoder.toByteArray();
       }
       public HLAfixedRecord getmarkingTypeEncoder() {
       	return markingTypeEncoder;
       }
    
    
}
