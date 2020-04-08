package br.com.cmabreu.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import br.com.cmabreu.federates.XPlaneAircraft;
import br.com.cmabreu.interfaces.IPhysicalEntityManager;
import br.com.cmabreu.misc.EncoderDecoder;
import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleSet;
import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.RTIambassador;

public class XPlaneAircraftManager implements IPhysicalEntityManager 	{
	private RTIambassador rtiAmb;
	
	private InteractionClassHandle interactionHandle; 
	protected AttributeHandleSet attributes;
	protected ObjectClassHandle entityHandle;
	protected AttributeHandle entityTypeHandle;
	protected AttributeHandle spatialHandle;
	protected AttributeHandle forceIdentifierHandle;
	protected AttributeHandle markingHandle;	
	protected AttributeHandle isConcealedHandle;
	protected AttributeHandle entityIdentifierHandle;
	protected AttributeHandle damageStateHandle;
	
	private List<XPlaneAircraft> aircrafts;
	private EncoderDecoder decoder;
	private Logger logger = LoggerFactory.getLogger( XPlaneAircraftManager.class );

	/** **************************************************************************************************************  
	* 
	* 	Metodos da interface IPhysicalEntityManager
	* 
	******************************************************************************************************************/
	@Override
	public boolean isAKindOfMe( ObjectClassHandle classHandle ) {
		int other = decoder.getObjectClassHandle( classHandle );
		return  other == decoder.getObjectClassHandle( this.entityHandle );
	}
	
	@Override
	public void discoverObjectInstance( ObjectInstanceHandle theObject, ObjectClassHandle theObjectClass, String objectName, SimpMessagingTemplate simpMessagingTemplate ) {
		int handle = decoder.getObjectHandle( theObject );
		System.out.println("Nova aeronave '" + objectName + "' descoberta: Handle " + handle );
		try {
			XPlaneAircraft xpac = new XPlaneAircraft( theObject, this, objectName );
			aircrafts.add( xpac );
			simpMessagingTemplate.convertAndSend("/aircrafts/discovered", xpac ); 
		} catch ( Exception e ) {
			logger.error("Erro ao criar aeronave: " + e.getMessage() );
		}
	}
	
	// Verifica se este controlador possui algum objeto instanciado com este handle
	@Override
	public XPlaneAircraft doIHaveThisObject( ObjectInstanceHandle theObject ) {
		int other = decoder.getObjectHandle( theObject );
		for( XPlaneAircraft ac : this.aircrafts ) {
			if( other == decoder.getObjectHandle( ac.getTheObjectInstance() ) ) return ac;
		}
		return null;
	}
	
	@Override
	public void removeObjectInstance(ObjectInstanceHandle theObject){
		int other = decoder.getObjectHandle( theObject );
		for( XPlaneAircraft ac : this.aircrafts ) {
			if( other == decoder.getObjectHandle( ac.getTheObjectInstance() ) ) {
				logger.error("Preciso remover a aeronave '" + ac.getObjectName() + "' mas nao sei como. Ela continua comigo!");
				// REMOVE DA LISTA
			}
		}
	}
	
	/** **************************************************************************************************************  */
	
	
	public XPlaneAircraftManager( RTIambassador rtiAmb) throws Exception {
		logger.info("X-Plane Aircraft Manager ativo");
		this.decoder = new EncoderDecoder();
		this.aircrafts = new ArrayList<XPlaneAircraft>();
		this.rtiAmb = rtiAmb;
		this.entityHandle = rtiAmb.getObjectClassHandle("BaseEntity.PhysicalEntity.Platform.Aircraft");
		this.subscribe();
	}
	
	
	private void subscribe() throws Exception {
		this.entityTypeHandle = this.rtiAmb.getAttributeHandle( this.entityHandle, "EntityType");        
		this.entityIdentifierHandle = this.rtiAmb.getAttributeHandle( this.entityHandle, "EntityIdentifier");  
		this.spatialHandle = this.rtiAmb.getAttributeHandle( this.entityHandle, "Spatial");  
		this.forceIdentifierHandle = this.rtiAmb.getAttributeHandle( this.entityHandle, "ForceIdentifier");  
		this.isConcealedHandle = this.rtiAmb.getAttributeHandle( this.entityHandle, "IsConcealed");   
		this.markingHandle = this.rtiAmb.getAttributeHandle( this.entityHandle, "Marking");  	                   
		this.damageStateHandle = this.rtiAmb.getAttributeHandle(entityHandle, "DamageState");
		
        AttributeHandleSet attributes = this.rtiAmb.getAttributeHandleSetFactory().create();
		attributes.add( this.entityTypeHandle );
		attributes.add( this.spatialHandle );
		attributes.add( this.forceIdentifierHandle );
		attributes.add( this.markingHandle );
		attributes.add( this.isConcealedHandle );
		attributes.add( this.entityIdentifierHandle );
		attributes.add( this.damageStateHandle );
        
        this.rtiAmb.subscribeObjectClassAttributes( this.entityHandle, attributes );   
        
        this.interactionHandle = this.rtiAmb.getInteractionClassHandle("Acknowledge");
        this.rtiAmb.subscribeInteractionClass(interactionHandle);
        
	}

	/* GETTERS e SETTERS */
	
	public RTIambassador getRtiAmb() {
		return rtiAmb;
	}

	public void setRtiAmb(RTIambassador rtiAmb) {
		this.rtiAmb = rtiAmb;
	}

	public List<XPlaneAircraft> getAircrafts() {
		return aircrafts;
	}

	public InteractionClassHandle getInteractionHandle() {
		return interactionHandle;
	}

	public ObjectClassHandle getEntityHandle() {
		return entityHandle;
	}

	public AttributeHandle getEntityTypeHandle() {
		return entityTypeHandle;
	}

	public AttributeHandle getSpatialHandle() {
		return spatialHandle;
	}

	public AttributeHandle getForceIdentifierHandle() {
		return forceIdentifierHandle;
	}

	public AttributeHandle getMarkingHandle() {
		return markingHandle;
	}

	public AttributeHandle getIsConcealedHandle() {
		return isConcealedHandle;
	}

	public AttributeHandle getEntityIdentifierHandle() {
		return entityIdentifierHandle;
	}

	public AttributeHandle getDamageStateHandle() {
		return damageStateHandle;
	}

	public EncoderDecoder getDecoder() {
		return decoder;
	}
	
	
}
