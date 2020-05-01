package br.com.cmabreu.dto;

import java.io.Serializable;

import br.com.cmabreu.codec.EntityIdentifier;
import br.com.cmabreu.codec.EntityType;
import br.com.cmabreu.codec.ForceIdentifier;
import br.com.cmabreu.codec.Marking;
import br.com.cmabreu.codec.SpatialVariant;
import br.com.cmabreu.entities.Aircraft;

public class AircraftDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String classeTipo;
	private String hlaObjetName;
	
	private double velocityX;
	private double velocityY;
	private double velocityZ;
	
	private float orientationPsi;
	private float orientationTheta;
	private float orientationPhi;
	
	private double latitude;
	private double longitude;
	private String altitude;	

	private SpatialVariant spatialVariant;
	private EntityType entityType;
	private ForceIdentifier forceIdentifier;
	private EntityIdentifier entityIdentifier;
	private Marking marking;
	private byte isConcealed;
	private byte damageState;		
	
	public AircraftDTO( Aircraft ac ) {
		this.classeTipo = ac.getClasseTipo();
		this.velocityX = ac.getVelocityX();
		this.velocityY = ac.getVelocityY();
		this.velocityZ = ac.getVelocityZ();
		this.orientationPsi = ac.getOrientationPsi();
		this.orientationTheta = ac.getOrientationTheta();
		this.orientationPhi = ac.getOrientationPhi();
		this.latitude = ac.getLatitude();
		this.longitude = ac.getLongitude();
		this.altitude = String.valueOf( ac.getAltitude() );
		this.hlaObjetName = ac.getObjectName();
		this.spatialVariant = ac.getSpatialVariant();
		this.entityIdentifier = ac.getEntityIdentifier();
		this.entityType = ac.getEntityType();
		this.forceIdentifier = ac.getForceIdentifier();
		this.marking = ac.getMarking();
		this.isConcealed = ac.getIsConcealed();
		this.damageState = ac.getDamageState();
	}

	public String getClasseTipo() {
		return classeTipo;
	}

	public double getVelocityX() {
		return velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public double getVelocityZ() {
		return velocityZ;
	}

	public float getOrientationPsi() {
		return orientationPsi;
	}

	public float getOrientationTheta() {
		return orientationTheta;
	}

	public float getOrientationPhi() {
		return orientationPhi;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getAltitude() {
		return altitude;
	}

	public String getHlaObjetName() {
		return hlaObjetName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SpatialVariant getSpatialVariant() {
		return spatialVariant;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public ForceIdentifier getForceIdentifier() {
		return forceIdentifier;
	}

	public EntityIdentifier getEntityIdentifier() {
		return entityIdentifier;
	}

	public Marking getMarking() {
		return marking;
	}

	public byte getIsConcealed() {
		return isConcealed;
	}

	public byte getDamageState() {
		return damageState;
	}
	
	
}
