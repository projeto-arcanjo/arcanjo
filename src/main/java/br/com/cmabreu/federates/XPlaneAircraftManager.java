package br.com.cmabreu.federates;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.cmabreu.misc.EncoderDecoder;
import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleSet;
import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.RTIambassador;

public class XPlaneAircraftManager {
	private RTIambassador rtiAmb;
	private ObjectClassHandle classHandle;
	private InteractionClassHandle interactionHandle;   
	private AttributeHandle attributeEntityType;
	private AttributeHandle attributeSpatial;   
	private AttributeHandle attributeEntityId; 
	private AttributeHandle attributeDamageState; 
	private AttributeHandle attributeForceId; 
	private AttributeHandle attributeIsConcealed; 
	private AttributeHandle attributeMarking; 
	private List<XPlaneAircraft> aircrafts;
	private EncoderDecoder decoder;
	private Logger logger = LoggerFactory.getLogger( XPlaneAircraftManager.class );
	
	public boolean isAKindOfMe( ObjectClassHandle classHandle ) {
		int other = decoder.getObjectClassHandle( classHandle );
		return  other == decoder.getObjectClassHandle( this.classHandle );
	}
	
	public XPlaneAircraftManager( RTIambassador rtiAmb ) throws Exception {
		logger.info("X-Plane Aircraft Manager ativo");
		this.decoder = new EncoderDecoder();
		this.aircrafts = new ArrayList<XPlaneAircraft>();
		this.rtiAmb = rtiAmb;
		this.classHandle = rtiAmb.getObjectClassHandle("BaseEntity.PhysicalEntity.Platform.Aircraft");
		this.subscribe();
	}
	
	
	private void subscribe() throws Exception {
		this.attributeEntityType = this.rtiAmb.getAttributeHandle( this.classHandle, "EntityType");        
		this.attributeEntityId = this.rtiAmb.getAttributeHandle( this.classHandle, "EntityIdentifier");  
		this.attributeSpatial = this.rtiAmb.getAttributeHandle( this.classHandle, "Spatial");  
		this.attributeDamageState = this.rtiAmb.getAttributeHandle( this.classHandle, "DamageState");   
		this.attributeForceId = this.rtiAmb.getAttributeHandle( this.classHandle, "ForceIdentifier");  
		this.attributeIsConcealed = this.rtiAmb.getAttributeHandle( this.classHandle, "IsConcealed");   
		this.attributeMarking = this.rtiAmb.getAttributeHandle( this.classHandle, "Marking");  	                   
        
        AttributeHandleSet attributeSet = this.rtiAmb.getAttributeHandleSetFactory().create();
        attributeSet.add( this.attributeEntityType );
        attributeSet.add( this.attributeSpatial );
        attributeSet.add( this.attributeEntityId );
        attributeSet.add( this.attributeDamageState );
        attributeSet.add( this.attributeForceId );
        attributeSet.add( this.attributeIsConcealed );
        attributeSet.add( this.attributeMarking );
        this.rtiAmb.subscribeObjectClassAttributes( this.classHandle, attributeSet );   
        
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


	public ObjectClassHandle getClassHandle() {
		return classHandle;
	}


	public void setClassHandle(ObjectClassHandle classHandle) {
		this.classHandle = classHandle;
	}


	public InteractionClassHandle getInteractionHandle() {
		return interactionHandle;
	}


	public void setInteractionHandle(InteractionClassHandle interactionHandle) {
		this.interactionHandle = interactionHandle;
	}


	public AttributeHandle getAttributeEntityType() {
		return attributeEntityType;
	}


	public void setAttributeEntityType(AttributeHandle attributeEntityType) {
		this.attributeEntityType = attributeEntityType;
	}


	public AttributeHandle getAttributeSpatial() {
		return attributeSpatial;
	}


	public void setAttributeSpatial(AttributeHandle attributeSpatial) {
		this.attributeSpatial = attributeSpatial;
	}


	public AttributeHandle getAttributeEntityId() {
		return attributeEntityId;
	}


	public void setAttributeEntityId(AttributeHandle attributeEntityId) {
		this.attributeEntityId = attributeEntityId;
	}


	public AttributeHandle getAttributeDamageState() {
		return attributeDamageState;
	}


	public void setAttributeDamageState(AttributeHandle attributeDamageState) {
		this.attributeDamageState = attributeDamageState;
	}


	public AttributeHandle getAttributeForceId() {
		return attributeForceId;
	}


	public void setAttributeForceId(AttributeHandle attributeForceId) {
		this.attributeForceId = attributeForceId;
	}


	public AttributeHandle getAttributeIsConcealed() {
		return attributeIsConcealed;
	}


	public void setAttributeIsConcealed(AttributeHandle attributeIsConcealed) {
		this.attributeIsConcealed = attributeIsConcealed;
	}


	public AttributeHandle getAttributeMarking() {
		return attributeMarking;
	}


	public void setAttributeMarking(AttributeHandle attributeMarking) {
		this.attributeMarking = attributeMarking;
	}
	
	public List<XPlaneAircraft> getAircrafts() {
		return aircrafts;
	}
}
