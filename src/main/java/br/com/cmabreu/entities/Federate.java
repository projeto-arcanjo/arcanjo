package br.com.cmabreu.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.cmabreu.managers.FederateManager;
import br.com.cmabreu.misc.EncoderDecoder;
import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.OrderType;

public class Federate implements IEntity {
	private Logger logger = LoggerFactory.getLogger( Federate.class );
	private ObjectInstanceHandle objectInstanceHandle;
	private String objectName;
	private FederateManager manager;
	private EncoderDecoder decoder;
	private String classeTipo;

	
	private byte hLAfederateHandle;
	private String hLAfederateName;
	private String hLAfederateType;
	private String hLAfederateHost;
	private String hLARTIversion;
	private int hLAreflectionsReceived;
	private int hLAupdatesSent;
	private int hLAinteractionsReceived;
	private int hLAinteractionsSent;
	private int hLAobjectInstancesUpdated;
	
	// TODO: FALTA CADASTRAR ESTES:
	private int hLAobjectInstancesReflected = 0;
	private int hLAobjectInstancesDeleted = 0;
	private int hLAobjectInstancesRemoved = 0;
	private int hLAobjectInstancesRegistered = 0;
	private int hLAobjectInstancesDiscovered = 0;
	
	@Override
	public Federate reflectAttributeValues(ObjectInstanceHandle theObject, AttributeHandleValueMap theAttributes, byte[] tag, OrderType sentOrder) throws Exception {
		
		for (AttributeHandle attributeHandle : theAttributes.keySet() ) {
			byte[] attributeData = theAttributes.get(attributeHandle);
			
			if( attributeHandle.equals( this.manager.gethLAfederateHandleHandle() ) ){
				this.hLAfederateHandle = this.decoder.toHLAhandle( attributeData );
			}
			
			if( attributeHandle.equals( this.manager.gethLAfederateNameHandle() ) ) {
				this.hLAfederateName = this.decoder.toString( attributeData );
			}

			if( attributeHandle.equals( this.manager.gethLAfederateTypeHandle() ) ) {
				this.hLAfederateType = this.decoder.toString( attributeData );
			}

			if( attributeHandle.equals( this.manager.gethLAfederateHostHandle() ) ) {
				this.hLAfederateHost = this.decoder.toString( attributeData );
			}
			
			if( attributeHandle.equals( this.manager.gethLARTIversionHandle() ) ) {
				this.hLARTIversion = this.decoder.toString( attributeData );
			}

			if( attributeHandle.equals( this.manager.gethLAreflectionsReceivedHandle() ) ) {
				this.hLAreflectionsReceived = this.decoder.toInteger32( attributeData );
			}
			
			if( attributeHandle.equals( this.manager.gethLAupdatesSentHandle() ) ) {
				this.hLAupdatesSent = this.decoder.toInteger32( attributeData );
			}
			
			if( attributeHandle.equals( this.manager.gethLAinteractionsReceivedHandle() ) ) {
				this.hLAinteractionsReceived = this.decoder.toInteger32( attributeData );
			}
			if( attributeHandle.equals( this.manager.gethLAinteractionsSentHandle() ) ) {
				this.hLAinteractionsSent = this.decoder.toInteger32( attributeData );
			}
			if( attributeHandle.equals( this.manager.gethLAobjectInstancesUpdatedHandle() ) ) {
				this.hLAobjectInstancesUpdated = this.decoder.toInteger32( attributeData );
			}

			/**			TODO:
			 * 			FALTA CADASTRAR OS OUTROS ATRIBUTOS.
			 * 			ESTOU SEM TEMPO !
			 */
			
		}
		
		return this;
		
	}
	
	public Federate( ObjectInstanceHandle theObjectInstance, FederateManager manager, String objectName, String classeTipo ) throws Exception {
		this.classeTipo = classeTipo;
		logger.info("Novo Federado criado: " + objectName );
		this.decoder = new EncoderDecoder();
		this.objectInstanceHandle = theObjectInstance;
		this.objectName = objectName;
		this.manager = manager;
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
	
	public String gethLAfederateName() {
		return hLAfederateName;
	}
	
	public byte gethLAfederateHandle() {
		return hLAfederateHandle;
	}
	
	public String gethLAfederateType() {
		return hLAfederateType;
	}
	
	public String gethLAfederateHost() {
		return hLAfederateHost;
	}
	
	public String gethLARTIversion() {
		return hLARTIversion;
	}
	
	public int gethLAreflectionsReceived() {
		return hLAreflectionsReceived;
	}
	
	public int gethLAupdatesSent() {
		return hLAupdatesSent;
	}
	
	public int gethLAinteractionsReceived() {
		return hLAinteractionsReceived;
	}
	
	public int gethLAinteractionsSent() {
		return hLAinteractionsSent;
	}
	
	public int gethLAobjectInstancesUpdated() {
		return hLAobjectInstancesUpdated;
	}
	
	public int gethLAobjectInstancesReflected() {
		return hLAobjectInstancesReflected;
	}

	public int gethLAobjectInstancesDeleted() {
		return hLAobjectInstancesDeleted;
	}

	public int gethLAobjectInstancesRemoved() {
		return hLAobjectInstancesRemoved;
	}

	public int gethLAobjectInstancesRegistered() {
		return hLAobjectInstancesRegistered;
	}

	public int gethLAobjectInstancesDiscovered() {
		return hLAobjectInstancesDiscovered;
	}
	
	
}
