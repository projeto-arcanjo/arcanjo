package br.com.cmabreu.federates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.cmabreu.codec.Codec;
import br.com.cmabreu.codec.SpatialVariant;
import br.com.cmabreu.interfaces.IPhysicalEntity;
import br.com.cmabreu.misc.Environment;
import br.com.cmabreu.services.XPlaneAircraftManager;
import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.OrderType;
import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.encoding.EncoderFactory;

public class XPlaneAircraft implements IPhysicalEntity {
	private double latitude;
	private double longitude;
	private double altitude;
	private Logger logger = LoggerFactory.getLogger( XPlaneAircraft.class );
	private ObjectInstanceHandle objectInstanceHandle;
	private String objectName;
	private XPlaneAircraftManager manager;
	private Codec codec;
	//private Environment env;
	private EncoderFactory encoderFactory;
	
	public XPlaneAircraft( ObjectInstanceHandle theObjectInstance, XPlaneAircraftManager manager, String objectName ) throws Exception {
		this.encoderFactory = RtiFactoryFactory.getRtiFactory().getEncoderFactory(); 
		logger.info("Nova aeronave vinda do X-Plane");
		this.objectInstanceHandle = theObjectInstance;
		this.objectName = objectName;
		this.manager = manager;
		//this.env = new Environment();
		this.codec = new Codec( this.encoderFactory );		
	}
	
	public String getObjectName() {
		return objectName;
	}
	
	public ObjectInstanceHandle getTheObjectInstance() {
		return this.objectInstanceHandle;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	@Override
	public void reflectAttributeValues(ObjectInstanceHandle theObject, AttributeHandleValueMap theAttributes, byte[] tag, OrderType sentOrder) throws Exception {
		 
		for (AttributeHandle attributeHandle : theAttributes.keySet() ) {
			// Guarda os valores do atributo 
			byte[] attributeData = theAttributes.get(attributeHandle);
			
			// Procura que atributo eh esse
			if( attributeHandle.equals( this.manager.getSpatialHandle() ) ) {
				processaSpatial( attributeData );
			}
			
			
		}
		
	}

	// Processa os atributos que chegaram via RTI
	private void processaSpatial( byte[] bytes ) throws Exception {
		SpatialVariant sv = this.codec.decodeSpatialVariant( bytes );
		double[] wl = sv.getWorldLocation();
		
		System.out.println("Recebi os dados de posicao: ");
		System.out.println(" > LAT " + wl[Environment.LAT] );
		System.out.println(" > LON " + wl[Environment.LON] );
		System.out.println(" > ALT " + wl[Environment.ALT] );
		
	}

}
