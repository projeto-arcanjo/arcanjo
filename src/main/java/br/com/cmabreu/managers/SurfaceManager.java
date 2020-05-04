package br.com.cmabreu.managers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import br.com.cmabreu.dto.SurfaceVesselDTO;
import br.com.cmabreu.entities.SurfaceVessel;
import br.com.cmabreu.misc.EncoderDecoder;
import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleSet;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.OrderType;
import hla.rti1516e.RTIambassador;

public class SurfaceManager implements IEntityManager 	{
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
	private List<SurfaceVessel> vessels;
	private EncoderDecoder decoder;
	private Logger logger = LoggerFactory.getLogger( SurfaceManager.class );

	/** **************************************************************************************************************  
	* 
	* 	Metodos da interface IPhysicalEntityManager
	* 
	******************************************************************************************************************/
	@Override
	public void reflectAttributeValues( ObjectInstanceHandle theObject, AttributeHandleValueMap theAttributes, byte[] tag, OrderType sentOrder ) {
		SurfaceVessel vessel = this.doIHaveThisObject( theObject );
		if( vessel != null ) {
			try {
				vessel.reflectAttributeValues( theObject, theAttributes, tag, sentOrder );
				// Envia este objeto JA ATUALIZADO para a interface WEB
				this.simpMessagingTemplate.convertAndSend("/platform/surface/reflectvalues", new SurfaceVesselDTO( vessel ) ); 
			} catch ( Exception e ) {
				//
			}
		}
	}
	
	
	@Override
	public int sendObjectsToInterface() {
		for( SurfaceVessel vessel : vessels ) {
			try {
				simpMessagingTemplate.convertAndSend("/platform/surface/reflectvalues", new SurfaceVesselDTO( vessel ) );
			} catch ( Exception e ) {
				
			}
		}
		return vessels.size();
	}

	
	@Override
	public boolean isAKindOfMe( ObjectClassHandle classHandle ) {
		return classHandle.equals( this.entityHandle );
	}
	
	@Override
	public void discoverObjectInstance( ObjectInstanceHandle theObject, ObjectClassHandle theObjectClass, String objectName, String classeTipo ) {
		try {
			SurfaceVessel xpac = new SurfaceVessel( theObject, this, objectName, classeTipo );
			this.vessels.add( xpac );
			this.simpMessagingTemplate.convertAndSend("/platform/surface/discovered", new SurfaceVesselDTO( xpac ) );
			this.rtiAmb.requestAttributeValueUpdate( theObject, this.attributes, "ARCANJO_ATTR_REQ".getBytes() );
		} catch ( Exception e ) {
			logger.error("Erro ao criar aeronave: " + e.getMessage() );
		}
	}
	
	// Verifica se este controlador possui algum objeto instanciado com este handle
	@Override
	public synchronized SurfaceVessel doIHaveThisObject( ObjectInstanceHandle theObject ) {
		for( SurfaceVessel ac : this.vessels ) {
			if( theObject.equals( ac.getTheObjectInstance() ) ) return ac;
		}
		return null;
	}
	
	@Override
	public void removeObjectInstance(ObjectInstanceHandle theObject){
		for( SurfaceVessel ac : this.vessels ) {
			if( theObject.equals( ac.getTheObjectInstance() ) ) {
				logger.error("Preciso remover o navio '" + ac.getObjectName() + "' mas nao sei como. Ele continua comigo!");
				// REMOVE DA LISTA
			}
		}
	}
	
	@Override
	public String getClassFomName() {
		return classFomName;
	}
	
	/** **************************************************************************************************************  */
	
	
	public SurfaceManager( RTIambassador rtiAmb, SimpMessagingTemplate simpMessagingTemplate ) throws Exception {
		this.classFomName = "BaseEntity.PhysicalEntity.Platform.SurfaceVessel";
		this.simpMessagingTemplate = simpMessagingTemplate;
		logger.info("SurfaceVessel Manager ativo");
		this.decoder = new EncoderDecoder();
		this.vessels = new ArrayList<SurfaceVessel>();
		this.rtiAmb = rtiAmb;
		this.entityHandle = rtiAmb.getObjectClassHandle( classFomName );
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
		
        this.attributes = this.rtiAmb.getAttributeHandleSetFactory().create();
        this.attributes.add( this.entityTypeHandle );
        this.attributes.add( this.spatialHandle );
        this.attributes.add( this.forceIdentifierHandle );
        this.attributes.add( this.markingHandle );
        this.attributes.add( this.isConcealedHandle );
        this.attributes.add( this.entityIdentifierHandle );
        this.attributes.add( this.damageStateHandle );
        
        this.rtiAmb.subscribeObjectClassAttributes( this.entityHandle, this.attributes );   
        
	}

	/* GETTERS e SETTERS */
	
	public RTIambassador getRtiAmb() {
		return rtiAmb;
	}

	public void setRtiAmb(RTIambassador rtiAmb) {
		this.rtiAmb = rtiAmb;
	}

	public List<SurfaceVessel> getVessels() {
		return vessels;
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
