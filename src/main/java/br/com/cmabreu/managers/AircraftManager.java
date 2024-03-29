package br.com.cmabreu.managers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import br.com.cmabreu.dto.AircraftDTO;
import br.com.cmabreu.entities.Aircraft;
import br.com.cmabreu.misc.EncoderDecoder;
import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleSet;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.OrderType;
import hla.rti1516e.RTIambassador;

public class AircraftManager implements IEntityManager 	{
	private RTIambassador rtiAmb;
	private SimpMessagingTemplate simpMessagingTemplate;
	protected AttributeHandleSet attributes;
	protected ObjectClassHandle entityHandle;
	protected AttributeHandle entityTypeHandle;
	protected AttributeHandle spatialHandle;
	protected AttributeHandle forceIdentifierHandle;
	protected AttributeHandle markingHandle;	
	protected AttributeHandle isConcealedHandle;
	protected AttributeHandle entityIdentifierHandle;
	protected AttributeHandle damageStateHandle;
	private String classFomName;
	private List<Aircraft> aircrafts;
	private EncoderDecoder decoder;
	private Logger logger = LoggerFactory.getLogger( AircraftManager.class );

	/** **************************************************************************************************************  
	* 
	* 	Metodos da interface IPhysicalEntityManager
	* 
	******************************************************************************************************************/

	@Override
	public boolean reflectAttributeValues(ObjectInstanceHandle theObject, AttributeHandleValueMap theAttributes, byte[] tag, OrderType sentOrder) {
		Aircraft aircraft = this.doIHaveThisObject( theObject );
		if( aircraft != null ) {
			try {
				aircraft.reflectAttributeValues( theObject, theAttributes, tag, sentOrder );
				this.simpMessagingTemplate.convertAndSend("/platform/aircraft/reflectvalues", new AircraftDTO( aircraft ) );
				return true;
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	
	@Override
	public synchronized int sendObjectsToInterface() {
		logger.info( " > " + this.aircrafts.size() + " objetos enviados por " + this.getClassFomName() );
		for( Aircraft aircraft : aircrafts  ) {
			try {
				this.simpMessagingTemplate.convertAndSend("/platform/aircraft/reflectvalues", new AircraftDTO( aircraft ) );
			} catch ( Exception e ) {
				
			}
		}
		return aircrafts.size();
	}
	
	
	@Override
	public boolean isAKindOfMe( ObjectClassHandle classHandle ) {
		return classHandle.equals( this.entityHandle );
	}
	
	@Override
	public void discoverObjectInstance( ObjectInstanceHandle theObject, ObjectClassHandle theObjectClass, String objectName, String classeTipo ) {
		try {
			Aircraft xpac = new Aircraft( theObject, this, objectName, classeTipo );
			this.aircrafts.add( xpac );
			this.simpMessagingTemplate.convertAndSend("/platform/aircraft/discovered", new AircraftDTO( xpac ) ); 
			this.rtiAmb.requestAttributeValueUpdate( theObject, this.attributes, "ARCANJO_ATTR_REQ".getBytes() );
		} catch ( Exception e ) {
			logger.error("Erro ao criar aeronave: " + e.getMessage() );
			e.printStackTrace();
		}
	}
	
	// Verifica se este controlador possui algum objeto instanciado com este handle
	@Override
	public synchronized Aircraft doIHaveThisObject( ObjectInstanceHandle theObject ) {
		for( Aircraft ac : this.aircrafts ) {
			if( theObject.equals( ac.getTheObjectInstance() ) ) return ac;
		}
		return null;
	}
	
	@Override
	public void removeObjectInstance(ObjectInstanceHandle theObject){
		for( Aircraft ac : this.aircrafts ) {
			if( theObject.equals( ac.getTheObjectInstance() ) ) {
				logger.error("Preciso remover a aeronave '" + ac.getObjectName() + "' mas nao sei como. Ela continua comigo!");
				// REMOVE DA LISTA
			}
		}
	}
	
	@Override
	public String getClassFomName() {
		return classFomName;
	}
	
	
	/** **************************************************************************************************************  */
	
	
	public AircraftManager( RTIambassador rtiAmb, SimpMessagingTemplate simpMessagingTemplate ) throws Exception {
		this.classFomName = "BaseEntity.PhysicalEntity.Platform.Aircraft";
		this.simpMessagingTemplate = simpMessagingTemplate;
		this.decoder = new EncoderDecoder();
		this.aircrafts = new ArrayList<Aircraft>();
		this.rtiAmb = rtiAmb;
		this.entityHandle = rtiAmb.getObjectClassHandle( classFomName );
		logger.info("Aircraft Manager ativo");
	}
	
	@Override
	public void subscribe() throws Exception {
		this.entityTypeHandle = this.rtiAmb.getAttributeHandle( this.entityHandle, "EntityType");        
		this.entityIdentifierHandle = this.rtiAmb.getAttributeHandle( this.entityHandle, "EntityIdentifier");  
		this.spatialHandle = this.rtiAmb.getAttributeHandle( this.entityHandle, "Spatial");  
		this.forceIdentifierHandle = this.rtiAmb.getAttributeHandle( this.entityHandle, "ForceIdentifier");  
		this.isConcealedHandle = this.rtiAmb.getAttributeHandle( this.entityHandle, "IsConcealed");   
		this.markingHandle = this.rtiAmb.getAttributeHandle( this.entityHandle, "Marking");  	                   
		this.damageStateHandle = this.rtiAmb.getAttributeHandle(entityHandle, "DamageState");
		
		this.attributes = this.rtiAmb.getAttributeHandleSetFactory().create();
		this.attributes.add( this.entityTypeHandle );
		this.attributes.add( this.spatialHandle );
		this.attributes.add( this.forceIdentifierHandle );
		this.attributes.add( this.markingHandle );
		this.attributes.add( this.isConcealedHandle );
		this.attributes.add( this.entityIdentifierHandle );
		this.attributes.add( this.damageStateHandle );
        
        this.rtiAmb.subscribeObjectClassAttributes( this.entityHandle, attributes );   
        
        // Agora que eu me inscrevi, preciso ser atualizado da situacao atual, caso entre com outros federados em execucao 
        this.rtiAmb.requestAttributeValueUpdate( this.entityHandle, attributes, "INITIAL_REQUEST".getBytes() );
        
        logger.info("subscribe em " + this.classFomName);

	}

	/* GETTERS e SETTERS */
	
	public RTIambassador getRtiAmb() {
		return rtiAmb;
	}

	public void setRtiAmb(RTIambassador rtiAmb) {
		this.rtiAmb = rtiAmb;
	}

	public List<Aircraft> getAircrafts() {
		return aircrafts;
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
