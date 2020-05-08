package br.com.cmabreu.managers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import br.com.cmabreu.dto.FederateDTO;
import br.com.cmabreu.entities.Federate;
import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleSet;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.OrderType;
import hla.rti1516e.RTIambassador;

public class FederateManager implements IEntityManager {
	private RTIambassador rtiAmb;
	private SimpMessagingTemplate simpMessagingTemplate;
	protected AttributeHandleSet attributes;
	
	private AttributeHandle hLAfederateHandleHandle;
	private AttributeHandle hLAfederateNameHandle;
	private AttributeHandle hLAfederateTypeHandle;
	private AttributeHandle hLAfederateHostHandle;
	private AttributeHandle hLARTIversionHandle;
	private AttributeHandle hLAreflectionsReceivedHandle;
	private AttributeHandle hLAupdatesSentHandle;
	private AttributeHandle hLAinteractionsReceivedHandle;
	private AttributeHandle hLAinteractionsSentHandle;
	private AttributeHandle hLAobjectInstancesUpdatedHandle;
	private AttributeHandle hLAobjectInstancesReflectedHandle;
	private AttributeHandle hLAobjectInstancesDeletedHandle;
	private AttributeHandle hLAobjectInstancesRemovedHandle;
	private AttributeHandle hLAobjectInstancesRegisteredHandle;
	private AttributeHandle hLAobjectInstancesDiscoveredHandle;
	
	protected ObjectClassHandle entityHandle;
	private String classFomName;
	private Logger logger = LoggerFactory.getLogger( FederateManager.class );
	private List<Federate> federates;
	
	public FederateManager( RTIambassador rtiAmb, SimpMessagingTemplate simpMessagingTemplate ) throws Exception {
		this.classFomName = "HLAobjectRoot.HLAmanager.HLAfederate";
		this.simpMessagingTemplate = simpMessagingTemplate;
		logger.info("Federate Manager ativo");
		this.federates = new ArrayList<Federate>();
		this.rtiAmb = rtiAmb;
		this.entityHandle = rtiAmb.getObjectClassHandle( classFomName );
		this.subscribe();
	}
	
	
	private void subscribe() throws Exception {

		this.hLAfederateHandleHandle = this.rtiAmb.getAttributeHandle( this.entityHandle, "HLAfederateHandle");        
		this.hLAfederateNameHandle = this.rtiAmb.getAttributeHandle( this.entityHandle, "HLAfederateName");  
		this.hLAfederateTypeHandle = this.rtiAmb.getAttributeHandle( this.entityHandle, "HLAfederateType");  
		this.hLAfederateHostHandle = this.rtiAmb.getAttributeHandle( this.entityHandle, "HLAfederateHost");  
		this.hLARTIversionHandle = this.rtiAmb.getAttributeHandle( this.entityHandle, "HLARTIversion");   
		this.hLAreflectionsReceivedHandle = this.rtiAmb.getAttributeHandle( this.entityHandle, "HLAreflectionsReceived");  	                   
		this.hLAupdatesSentHandle = this.rtiAmb.getAttributeHandle(entityHandle, "HLAupdatesSent");
		this.hLAinteractionsReceivedHandle = this.rtiAmb.getAttributeHandle(entityHandle, "HLAinteractionsReceived");
		this.hLAinteractionsSentHandle = this.rtiAmb.getAttributeHandle(entityHandle, "HLAinteractionsSent");
		this.hLAobjectInstancesUpdatedHandle = this.rtiAmb.getAttributeHandle(entityHandle, "HLAobjectInstancesUpdated");
		this.hLAobjectInstancesReflectedHandle = this.rtiAmb.getAttributeHandle(entityHandle, "HLAobjectInstancesReflected");
		this.hLAobjectInstancesDeletedHandle = this.rtiAmb.getAttributeHandle(entityHandle, "HLAobjectInstancesDeleted");
		this.hLAobjectInstancesRemovedHandle = this.rtiAmb.getAttributeHandle(entityHandle, "HLAobjectInstancesRemoved");
		this.hLAobjectInstancesRegisteredHandle = this.rtiAmb.getAttributeHandle(entityHandle, "HLAobjectInstancesRegistered");
		this.hLAobjectInstancesDiscoveredHandle = this.rtiAmb.getAttributeHandle(entityHandle, "HLAobjectInstancesDiscovered");
		
		
		this.attributes = this.rtiAmb.getAttributeHandleSetFactory().create();
		this.attributes.add( this.hLAfederateHandleHandle );
		this.attributes.add( this.hLAfederateNameHandle );
		this.attributes.add( this.hLAfederateTypeHandle );
		this.attributes.add( this.hLAfederateHostHandle );
		this.attributes.add( this.hLARTIversionHandle );
		this.attributes.add( this.hLAreflectionsReceivedHandle );
		this.attributes.add( this.hLAupdatesSentHandle );
		this.attributes.add( this.hLAinteractionsReceivedHandle );
		this.attributes.add( this.hLAinteractionsSentHandle );
		this.attributes.add( this.hLAobjectInstancesUpdatedHandle );
		this.attributes.add( this.hLAobjectInstancesReflectedHandle );
		this.attributes.add( this.hLAobjectInstancesDeletedHandle );
		this.attributes.add( this.hLAobjectInstancesRemovedHandle );
		this.attributes.add( this.hLAobjectInstancesRegisteredHandle );
		this.attributes.add( this.hLAobjectInstancesDiscoveredHandle );
        
        this.rtiAmb.subscribeObjectClassAttributes( this.entityHandle, attributes );
        
        // Agora que eu me inscrevi, preciso ser atualizado da situacao atual, caso entre com outros federados em execucao 
        this.rtiAmb.requestAttributeValueUpdate( this.entityHandle, attributes, "INITIAL_REQUEST".getBytes() );
		
	}
	
	@Override
	public boolean isAKindOfMe(ObjectClassHandle classHandle) {
		return classHandle.equals( this.entityHandle );
	}

	@Override
	public synchronized Federate doIHaveThisObject(ObjectInstanceHandle theObject) {
		for( Federate fd : this.federates ) {
			if( theObject.equals( fd.getTheObjectInstance() ) ) return fd;
		}
		return null;
	}

	@Override
	public void discoverObjectInstance(ObjectInstanceHandle theObject, ObjectClassHandle theObjectClass, String objectName, String classeTipo) {
		objectName = objectName.replace("MOM.Federate(", "").replace(")","");
		try {
			Federate xpac = new Federate( theObject, this, objectName, classeTipo );
			logger.info("Novo Federado " + objectName + " adicionado. Solicitando atributos...");
			this.federates.add( xpac );
			this.simpMessagingTemplate.convertAndSend("/federation/federate/discovered", new FederateDTO( xpac ) ); 
			this.rtiAmb.requestAttributeValueUpdate( theObject, this.attributes, "ARCANJO_ATTR_REQ".getBytes() );
		} catch ( Exception e ) {
			logger.error("Erro ao criar Federado " + objectName + ": " + e.getMessage() );
		}

		
		
	}

	@Override
	public void removeObjectInstance(ObjectInstanceHandle theObject) {
		for( Federate fd : this.federates ) {
			if( theObject.equals( fd.getTheObjectInstance() ) ) {
				logger.error("Preciso remover o federado '" + fd.getObjectName() + "' mas nao sei como. Ele continua comigo!");
				// REMOVE DA LISTA
			}
		}
	}


	/*
	 * Neste caso especifico eu preciso responder com zero
	 * pois um federado não eh um objeto (intancia de classe) e
	 * sim um programa de simulacao que instancia os objetos.
	 * Dessa forma eu nao posso interferir na contagem de objetos.
	 */
	@Override
	public synchronized int sendObjectsToInterface() {
		logger.info( " > " + this.federates.size() + " objetos enviados por " + this.getClassFomName() );
		for( Federate federate : this.federates  ) {
			try {
				this.simpMessagingTemplate.convertAndSend("/federation/federate/reflectvalues", new FederateDTO( federate ) );
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	// **************************************************************************************************
	
	
	@Override
	public void reflectAttributeValues(ObjectInstanceHandle theObject, AttributeHandleValueMap theAttributes, byte[] tag, OrderType sentOrder) {
		Federate federate = this.doIHaveThisObject( theObject );
		if( federate != null ) {
			try {
				federate.reflectAttributeValues( theObject, theAttributes, tag, sentOrder );
				this.simpMessagingTemplate.convertAndSend("/federation/federate/reflectvalues", new FederateDTO( federate ) );
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		
		
	}

	@Override
	public String getClassFomName() {
		return classFomName;
	}


	public AttributeHandle gethLAfederateHandleHandle() {
		return hLAfederateHandleHandle;
	}


	public AttributeHandle gethLAfederateNameHandle() {
		return hLAfederateNameHandle;
	}


	public AttributeHandle gethLAfederateTypeHandle() {
		return hLAfederateTypeHandle;
	}


	public AttributeHandle gethLAfederateHostHandle() {
		return hLAfederateHostHandle;
	}


	public AttributeHandle gethLARTIversionHandle() {
		return hLARTIversionHandle;
	}


	public AttributeHandle gethLAreflectionsReceivedHandle() {
		return hLAreflectionsReceivedHandle;
	}


	public AttributeHandle gethLAupdatesSentHandle() {
		return hLAupdatesSentHandle;
	}


	public AttributeHandle gethLAinteractionsReceivedHandle() {
		return hLAinteractionsReceivedHandle;
	}


	public AttributeHandle gethLAinteractionsSentHandle() {
		return hLAinteractionsSentHandle;
	}


	public AttributeHandle gethLAobjectInstancesUpdatedHandle() {
		return hLAobjectInstancesUpdatedHandle;
	}


	public AttributeHandle gethLAobjectInstancesReflectedHandle() {
		return hLAobjectInstancesReflectedHandle;
	}


	public AttributeHandle gethLAobjectInstancesDeletedHandle() {
		return hLAobjectInstancesDeletedHandle;
	}


	public AttributeHandle gethLAobjectInstancesRemovedHandle() {
		return hLAobjectInstancesRemovedHandle;
	}


	public AttributeHandle gethLAobjectInstancesRegisteredHandle() {
		return hLAobjectInstancesRegisteredHandle;
	}


	public AttributeHandle gethLAobjectInstancesDiscoveredHandle() {
		return hLAobjectInstancesDiscoveredHandle;
	}


	public ObjectClassHandle getEntityHandle() {
		return entityHandle;
	}

	
}
