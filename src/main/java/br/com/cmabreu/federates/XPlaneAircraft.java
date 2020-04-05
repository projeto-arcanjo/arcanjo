package br.com.cmabreu.federates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hla.rti1516e.ObjectClassHandle;

public class XPlaneAircraft {
	private double latitude;
	private double longitude;
	private double altitude;
	private Logger logger = LoggerFactory.getLogger( XPlaneAircraft.class );
	private ObjectClassHandle theObjectClass;
	
	public XPlaneAircraft( ObjectClassHandle theObjectClass ) {
		logger.info("Nova aeronave vinda do X-Plane");
		this.theObjectClass = theObjectClass;
	}
	
	public ObjectClassHandle getTheObjectClass() {
		return theObjectClass;
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


}
