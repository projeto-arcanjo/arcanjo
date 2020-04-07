package br.com.cmabreu.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.cmabreu.federates.XPlaneAircraft;
import br.com.cmabreu.misc.EncoderDecoder;
import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleSet;
import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.RTIambassador;

public class XPlaneAircraftManager {
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
	
	public boolean isAKindOfMe( ObjectClassHandle classHandle ) {
		int other = decoder.getObjectClassHandle( classHandle );
		return  other == decoder.getObjectClassHandle( this.entityHandle );
	}
	
	public XPlaneAircraftManager( RTIambassador rtiAmb ) throws Exception {
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
		// this.attributeDamageState = this.rtiAmb.getAttributeHandle( this.classHandle, "DamageState");   
		this.forceIdentifierHandle = this.rtiAmb.getAttributeHandle( this.entityHandle, "ForceIdentifier");  
		this.isConcealedHandle = this.rtiAmb.getAttributeHandle( this.entityHandle, "IsConcealed");   
		this.markingHandle = this.rtiAmb.getAttributeHandle( this.entityHandle, "Marking");  	                   
		this.damageStateHandle = this.rtiAmb.getAttributeHandle(entityHandle, "DamageState");
		
        AttributeHandleSet attributeSet = this.rtiAmb.getAttributeHandleSetFactory().create();
		attributes.add(entityTypeHandle);
		attributes.add(spatialHandle);
		attributes.add(forceIdentifierHandle);
		attributes.add(markingHandle);
		attributes.add(isConcealedHandle);
		attributes.add(entityIdentifierHandle);
		attributeSet.add( this.damageStateHandle );
        
        this.rtiAmb.subscribeObjectClassAttributes( this.entityHandle, attributeSet );   
        
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
}
