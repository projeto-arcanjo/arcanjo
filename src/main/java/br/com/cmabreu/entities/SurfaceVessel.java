package br.com.cmabreu.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.cmabreu.codec.Codec;
import br.com.cmabreu.codec.EntityIdentifier;
import br.com.cmabreu.codec.EntityType;
import br.com.cmabreu.codec.ForceIdentifier;
import br.com.cmabreu.codec.Marking;
import br.com.cmabreu.codec.SpatialVariant;
import br.com.cmabreu.managers.SurfaceManager;
import br.com.cmabreu.misc.Environment;
import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.OrderType;
import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.encoding.EncoderFactory;

public class SurfaceVessel implements IEntity {
	
	// Atributos da entidade no RPR-FOM ************
	private SpatialVariant spatialVariant;
	private EntityType entityType;
	private ForceIdentifier forceIdentifier;
	private EntityIdentifier entityIdentifier;
	private Marking marking;
	private byte isConcealed;
	private byte damageState;	
	// *********************************************
	
	private String classeTipo;
	
	private Logger logger = LoggerFactory.getLogger( SurfaceVessel.class );
	private ObjectInstanceHandle objectInstanceHandle;
	private String objectName;
	private SurfaceManager manager;
	private Codec codec;
	private EncoderFactory encoderFactory;
	private Environment env;
	
	
	private double velocityX;
	private double velocityY;
	private double velocityZ;
	
	private float orientationPsi;
	private float orientationTheta;
	private float orientationPhi;
	
	
	private double latitude;
	private double longitude;
	private double altitude;	
	
	public SurfaceVessel( ObjectInstanceHandle theObjectInstance, SurfaceManager manager, String objectName, String classeTipo ) throws Exception {
		this.classeTipo = classeTipo;
		this.encoderFactory = RtiFactoryFactory.getRtiFactory().getEncoderFactory(); 
		logger.info("Novo SurfaceVessel criado: " + objectName );
		this.objectInstanceHandle = theObjectInstance;
		this.objectName = objectName;
		this.manager = manager;
		this.codec = new Codec( this.encoderFactory );
		this.env = new Environment();
	}
	
	public String getClasseTipo() {
		return classeTipo;
	}
	
	public String getObjectName() {
		return objectName;
	}
	
	public ObjectInstanceHandle getTheObjectInstance() {
		return this.objectInstanceHandle;
	}

	@Override
	public SurfaceVessel reflectAttributeValues(ObjectInstanceHandle theObject, AttributeHandleValueMap theAttributes, 
			byte[] tag, OrderType sentOrder ) throws Exception {
		 
		for (AttributeHandle attributeHandle : theAttributes.keySet() ) {
			// Guarda os valores do atributo 
			byte[] attributeData = theAttributes.get(attributeHandle);
			
			// Procura que atributo eh esse
			if( attributeHandle.equals( this.manager.getSpatialHandle() ) ) {
				processaSpatial( attributeData );
			}
			if( attributeHandle.equals( this.manager.getDamageStateHandle() ) ) {
				processaDamagedState( attributeData );
			}
			if( attributeHandle.equals( this.manager.getEntityIdentifierHandle() ) ) {
				processaEntityIdentifier( attributeData );
			}
			if( attributeHandle.equals( this.manager.getEntityTypeHandle() ) ) {
				processaEntityType( attributeData );
			}
			if( attributeHandle.equals( this.manager.getForceIdentifierHandle() ) ) {
				processaForceIdentifier( attributeData );
			}
			if( attributeHandle.equals( this.manager.getMarkingHandle() ) ) {
				processaMarking( attributeData );
			}
			if( attributeHandle.equals( this.manager.getIsConcealedHandle() ) ) {
				processaIsConcealed( attributeData );
			}
			
			
		}
		
		// Devolve este objeto atualizado para quem chamou. Vai que... 
		return this;
	}
	
	/**
	 * 
	 * 		PROCESSA CADA ATRIBUTO E ATUALIZA ESTE OBJETO
	 * 
	 */

	private void processaIsConcealed(byte[] bytes) throws Exception {
		this.isConcealed = this.codec.decodeIsConcealed(bytes);
	}

	private void processaMarking(byte[] bytes) throws Exception {
		this.marking = this.codec.decodeMarking(bytes);
	}

	private void processaForceIdentifier(byte[] bytes) throws Exception {
		this.forceIdentifier = this.codec.decodeForceIdentifier(bytes);
	}

	private void processaEntityType(byte[] bytes) throws Exception {
		this.entityType = this.codec.decodeEntityType(bytes);
	}

	private void processaEntityIdentifier(byte[] bytes) throws Exception {
		this.entityIdentifier = this.codec.decodeEntityIdentifier(bytes);
	}

	private void processaDamagedState(byte[] bytes) throws Exception {
		this.damageState = this.codec.decodeDamageState(bytes);
	}

	private void processaSpatial( byte[] bytes ) throws Exception {
		this.spatialVariant = this.codec.decodeSpatialVariant( bytes );

		double[] geo = this.env.getGeodesicLocation(  this.spatialVariant.getWorldLocation() ); 
		float[] orientation = this.spatialVariant.getOrientation();
		
		this.latitude = geo[ Environment.LAT ];
		this.longitude = geo[ Environment.LON ];
		this.altitude = geo[ Environment.ALT ];
		
		this.orientationPhi = orientation[ SpatialVariant.PHI ]; 
		this.orientationTheta = orientation[ SpatialVariant.THETA ]; 
		this.orientationPsi = orientation[ SpatialVariant.PSI ];
		
	}

	
	/**
	 * 		GETTERS 
	 * 
	 */
	
	
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

	public double getAltitude() {
		return altitude;
	}
	
	

}
