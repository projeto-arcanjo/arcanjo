package br.com.cmabreu.misc;

import org.json.JSONObject;
import org.portico.impl.hla1516e.types.HLA1516eHandle;

import hla.rti1516e.AttributeHandle;
import hla.rti1516e.FederateHandle;
import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.ParameterHandle;
import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderFactory;
import hla.rti1516e.encoding.HLAboolean;
import hla.rti1516e.encoding.HLAbyte;
import hla.rti1516e.encoding.HLAfloat32BE;
import hla.rti1516e.encoding.HLAfloat64BE;
import hla.rti1516e.encoding.HLAinteger16BE;
import hla.rti1516e.encoding.HLAinteger32BE;
import hla.rti1516e.encoding.HLAinteger64BE;
import hla.rti1516e.encoding.HLAoctet;
import hla.rti1516e.encoding.HLAunicodeString;

public class EncoderDecoder {
	private EncoderFactory encoderFactory;
	
	public HLAunicodeString createHLAunicodeString( String value ) {
		return encoderFactory.createHLAunicodeString( value );
	}
	
	public HLAboolean createHLAboolean( boolean value ) {
		return encoderFactory.createHLAboolean( value );
	}
	
	public HLAfloat32BE createHLAfloat32BE( float value ) {
		return encoderFactory.createHLAfloat32BE( value );
	}
	
	public HLAfloat64BE createHLAfloat64BE( double value ) {
		return encoderFactory.createHLAfloat64BE( value );
	}

	public HLAinteger32BE createHLAinteger32BE( int value ) {
		return encoderFactory.createHLAinteger32BE( value );
	}

	public HLAinteger32BE createHLAinteger32BE() {
		return encoderFactory.createHLAinteger32BE();
	}
	
	public HLAinteger16BE createHLAinteger16BE() {
		return encoderFactory.createHLAinteger16BE();
	}
	
	public HLAinteger16BE createHLAinteger16BE( short value ) {
		return encoderFactory.createHLAinteger16BE( value );
	}	
	
	public HLAinteger64BE createHLAinteger64BE( long value ) {
		return encoderFactory.createHLAinteger64BE( value );
	}
	
	public ObjectInstanceHandle getObjectHandle( int handle ) {
		return new HLA1516eHandle( handle );
	}
	public int getObjectHandle( ObjectInstanceHandle handle ) {
		return HLA1516eHandle.fromHandle( handle );
	}

	public ObjectClassHandle getObjectClassHandle( int handle )	{
		return new HLA1516eHandle( handle );
	}	
	
	public int getObjectClassHandle( ObjectClassHandle handle ){
		return HLA1516eHandle.fromHandle( handle );
	}	
	
	public AttributeHandle getAttributeHandle( int handle )	{
		return new HLA1516eHandle( handle );
	}
	public int getAttributeHandle( AttributeHandle handle )	{
		return HLA1516eHandle.fromHandle( handle );
	}	
	
	public ParameterHandle getParameterHandle( int handle ){
		return new HLA1516eHandle( handle );
	}
	public int getParameterHandle( ParameterHandle handle ){
		return HLA1516eHandle.fromHandle( handle );
	}
	
	public InteractionClassHandle getInteractionHandle( int handle )	{
		return new HLA1516eHandle( handle );
	}
	public int getInteractionHandle( InteractionClassHandle handle )	{
		return HLA1516eHandle.fromHandle( handle );
	}
	
	public FederateHandle getFederateHandle( int handle ){
		return new HLA1516eHandle( handle );
	}
	public int getFederateHandle( FederateHandle handle ){
		return HLA1516eHandle.fromHandle( handle );
	}	
	
	
	/*
	public byte[] encodePosition( Position position ) throws Exception {
		HLAfixedRecord value = encoderFactory.createHLAfixedRecord();
		HLAfloat64BE longitude = encoderFactory.createHLAfloat64BE();
		HLAfloat64BE latitude = encoderFactory.createHLAfloat64BE();

		longitude.setValue( position.getLongitude() );
		latitude.setValue( position.getLatitude() );
		
		value.add(longitude);
		value.add(latitude);
		
		return value.toByteArray();
	}

	public Position decodePosition( byte[] bytes ) throws Exception {
		HLAfixedRecord value = encoderFactory.createHLAfixedRecord();
		HLAfloat64BE longitude = encoderFactory.createHLAfloat64BE();
		HLAfloat64BE latitude = encoderFactory.createHLAfloat64BE();
		value.add(longitude);
		value.add(latitude);
		value.decode(bytes);
		return new Position( longitude.getValue(), latitude.getValue() );
	}	
	*/
	
	public String valueToString( byte[] value, String dataType, String attributeName ) throws Exception {
		String result = "";
		JSONObject json = new JSONObject();
        switch( dataType ) { 
            case "HLAunicodeString":
            	json.put( attributeName, this.toString( value ) );
                result = json.toString(); 
                break; 
            case "HLAboolean": 
            	json.put( attributeName, this.toBoolean( value ) );
                result = json.toString(); 
                break; 
            case "HLAcount": 
            	json.put( attributeName, this.toInteger32( value ) );
                result = json.toString(); 
                break; 
            case "HLAfederateState": 
            	json.put( attributeName, this.toInteger32( value ) );
                result = json.toString(); 
                break; 
            case "HLAtimeInterval": 
            	json.put( attributeName, this.toByte( value ) );
                result = json.toString(); 
            	break; 
            case "HLAhandle": 
            	json.put( attributeName, this.toByte( value ) );
                result = json.toString(); 
            	break; 
            case "HLAmsec": 
            	json.put( attributeName, this.toInteger32( value ) );
            	result = json.toString(); 
            	break; 
            case "HLAlogicalTime": 
            	json.put( attributeName, this.toByte( value ) );
            	result = json.toString(); 
            	break; 
            case "HLAtimeState": 
            	json.put( attributeName, this.toInteger32( value ) );
            	result = json.toString(); 
            	break; 
            case "HLAmoduleDesignatorList": 
            	json.put( attributeName, "Cannot decode this yet" );
            	result = json.toString(); 
            	break; 
            case "HLAhandleList": 
            	json.put( attributeName, "Cannot decode this yet" );
            	result = json.toString(); 
            	break; 
            case "HLAswitch": 
            	json.put( attributeName, "Cannot decode this yet" );
            	result = json.toString(); 
            	break; 
            default:
            	result = "Cannot cast from " + dataType; 
        }
        
		return result;
	}
	
	
	public byte toHLAhandle( byte[] bytes ) {
		HLAbyte value = encoderFactory.createHLAbyte();
		try {
			value.decode( bytes );
			return value.getValue();
		} catch ( DecoderException de ) {
			de.printStackTrace();
			return -1;
		}
	}
	
	public byte toByte( byte[] bytes ) {
		HLAoctet value = encoderFactory.createHLAoctet();
		try	{
			value.decode( bytes );
			return value.getValue();
		} catch( DecoderException de )	{
			de.printStackTrace();
			return -1;
		}
	}
	
	public String toString ( byte[] bytes )	{
		HLAunicodeString value = encoderFactory.createHLAunicodeString();
		try	{
			value.decode( bytes );
			return value.getValue();
		} catch( DecoderException de )	{
			de.printStackTrace();
			return "";
		}
	}	
	
	public double toFloat64 ( byte[] bytes )	{
		HLAfloat64BE value = encoderFactory.createHLAfloat64BE();
		try	{
			value.decode( bytes );
			return value.getValue();
		} catch( DecoderException de )	{
			de.printStackTrace();
			return -1;
		}
	}
	
	public int toInteger32 ( byte[] bytes )	{
		HLAinteger32BE value = encoderFactory.createHLAinteger32BE();
		try	{
			value.decode( bytes );
			return value.getValue();
		} catch( DecoderException de )	{
			de.printStackTrace();
			return -1;
		}
	}	

	public long toInteger64 ( byte[] bytes )	{
		HLAinteger64BE value = encoderFactory.createHLAinteger64BE();
		try	{
			value.decode( bytes );
			return value.getValue();
		} catch( DecoderException de )	{
			de.printStackTrace();
			return -1;
		}
	}	

	public boolean toBoolean( byte[] bytes ) {
		HLAboolean value = encoderFactory.createHLAboolean();
		try	{
			value.decode( bytes );
		} catch( DecoderException de )	{
			return false;
		}
		return value.getValue();
	}

	
	public EncoderDecoder() throws Exception {
		encoderFactory = RtiFactoryFactory.getRtiFactory().getEncoderFactory();
	}


	
}
