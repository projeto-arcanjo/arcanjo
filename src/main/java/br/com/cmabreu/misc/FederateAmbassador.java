package br.com.cmabreu.misc;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.cmabreu.services.FederateService;
import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleSet;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.FederateHandle;
import hla.rti1516e.FederateHandleSaveStatusPair;
import hla.rti1516e.FederateHandleSet;
import hla.rti1516e.FederateRestoreStatus;
import hla.rti1516e.FederationExecutionInformationSet;
import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.LogicalTime;
import hla.rti1516e.MessageRetractionHandle;
import hla.rti1516e.NullFederateAmbassador;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.OrderType;
import hla.rti1516e.ParameterHandleValueMap;
import hla.rti1516e.RestoreFailureReason;
import hla.rti1516e.SaveFailureReason;
import hla.rti1516e.SynchronizationPointFailureReason;
import hla.rti1516e.TransportationTypeHandle;
import hla.rti1516e.exceptions.FederateInternalError;
import hla.rti1516e.time.HLAfloat64Time;

public class FederateAmbassador extends NullFederateAmbassador {
	private Logger logger = LoggerFactory.getLogger( FederateAmbassador.class );
	private FederateService federateService;
	
	public FederateAmbassador( FederateService federateService ) {
		this.federateService = federateService;
	}
	
	@Override
	public void receiveInteraction(InteractionClassHandle arg0, ParameterHandleValueMap arg1, byte[] arg2,
			OrderType arg3, TransportationTypeHandle arg4, SupplementalReceiveInfo arg5 ) throws FederateInternalError {
		//logger.info( "receiveInteraction " );
		federateService.receiveInteraction( arg0, arg1, arg2, arg3, arg4, arg5 );
	}	

	@Override
	public void provideAttributeValueUpdate(ObjectInstanceHandle theObject,	AttributeHandleSet theAttributes, byte[] userSuppliedTag) throws FederateInternalError {
		//logger.info( "provideAttributeValueUpdate " + theObject.toString() );
		//federateService.provideAttributeValueUpdate( theObject, theAttributes, userSuppliedTag );
	}	
	
	@Override
	public void discoverObjectInstance( ObjectInstanceHandle theObject, ObjectClassHandle theObjectClass, String objectName ) throws FederateInternalError {
		//logger.info( "discoverObjectInstance " + theObject.toString() );
		federateService.discoverObjectInstance(theObject, theObjectClass, objectName);
	}

	@Override
	public void reflectAttributeValues( ObjectInstanceHandle theObject, AttributeHandleValueMap theAttributes, byte[] tag, OrderType sentOrder,
			TransportationTypeHandle transport, SupplementalReflectInfo reflectInfo ) throws FederateInternalError {
		//logger.info( "reflectAttributeValues " + theObject.toString() );
		federateService.reflectAttributeValues( theObject, theAttributes, tag, sentOrder );
	}

	
	@Override
	public void removeObjectInstance(ObjectInstanceHandle theObject, byte[] tag, OrderType orderType, SupplementalRemoveInfo supInfo) throws FederateInternalError {
		logger.info( "removeObjectInstance " + theObject.toString() );
		federateService.removeObjectInstance( theObject, tag, orderType, supInfo );
	}

	
	@Override
	public void timeRegulationEnabled( LogicalTime time ) {
		logger.info( "Time regulation enabled ");
		federateService.setFederateTime ( ((HLAfloat64Time) time).getValue() );
		federateService.setRegulating( true );
	}

	@Override
	public void timeConstrainedEnabled( LogicalTime time ) {
		logger.info( "Time constrained enabled ");
		federateService.setFederateTime ( ((HLAfloat64Time) time).getValue() );
		federateService.setConstrained( true );
	}

	@Override
	public void timeAdvanceGrant( LogicalTime time ) {
		logger.info( "Advance time grant");
		federateService.setFederateTime ( ((HLAfloat64Time) time).getValue() );
		federateService.setAdvancing( false );
	}	
	
	
	@Override
	public void synchronizationPointRegistrationFailed(String label, SynchronizationPointFailureReason reason) {
		logger.error("Failed to register sync point: " + label + ", reason=" + reason);
		federateService.synchronizationPointRegistrationFailed( label, reason );

	}

	@Override
	public void synchronizationPointRegistrationSucceeded(String label) {
		logger.info("Successfully registered sync point: " + label);
		federateService.synchronizationPointRegistrationSucceeded( label );
	}

	@Override
	public void announceSynchronizationPoint(String label, byte[] tag) {
		logger.info("Synchronization point announced: " + label);
		federateService.announceSynchronizationPoint( label, tag );
	}

	@Override
	public void federationSynchronized(String label, FederateHandleSet failed) {
		logger.info("Federation Synchronized: " + label);
		federateService.federationSynchronized( label );
	}
	
	
	
	
	
	
	// ******************************************************************************************************************************
	// ******************************************************************************************************************************
	// ******************************************************************************************************************************
	
	@Override
	public void removeObjectInstance(ObjectInstanceHandle arg0, byte[] arg1, OrderType arg2, LogicalTime arg3, OrderType arg4, SupplementalRemoveInfo arg5) throws FederateInternalError {
		logger.info( "removeObjectInstance 2 " + arg0.toString() );
		
	}

	@Override
	public void removeObjectInstance(ObjectInstanceHandle theObject, byte[] tage, OrderType arg2, LogicalTime arg3,	OrderType arg4, MessageRetractionHandle arg5, SupplementalRemoveInfo arg6) throws FederateInternalError {
		logger.info( "removeObjectInstance 3 " + theObject.toString() );
		
	}

	@Override
	public void attributeIsNotOwned(ObjectInstanceHandle arg0, AttributeHandle arg1) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void attributeIsOwnedByRTI(ObjectInstanceHandle arg0, AttributeHandle arg1) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void attributeOwnershipAcquisitionNotification(ObjectInstanceHandle arg0, AttributeHandleSet arg1,
			byte[] arg2) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void attributeOwnershipUnavailable(ObjectInstanceHandle arg0, AttributeHandleSet arg1)
			throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void attributesInScope(ObjectInstanceHandle arg0, AttributeHandleSet arg1) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void attributesOutOfScope(ObjectInstanceHandle arg0, AttributeHandleSet arg1) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void confirmAttributeOwnershipAcquisitionCancellation(ObjectInstanceHandle arg0, AttributeHandleSet arg1)
			throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void confirmAttributeTransportationTypeChange(ObjectInstanceHandle arg0, AttributeHandleSet arg1,
			TransportationTypeHandle arg2) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void confirmInteractionTransportationTypeChange(InteractionClassHandle arg0, TransportationTypeHandle arg1)
			throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void connectionLost(String arg0) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void discoverObjectInstance(ObjectInstanceHandle arg0, ObjectClassHandle arg1, String arg2,	FederateHandle arg3) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void federationNotRestored(RestoreFailureReason arg0) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void federationNotSaved(SaveFailureReason arg0) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void federationRestoreBegun() throws FederateInternalError {
		//logger.info( arg0.toString() );
		
	}

	@Override
	public void federationRestoreStatusResponse(FederateRestoreStatus[] arg0) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void federationRestored() throws FederateInternalError {
		//logger.info( arg0.toString() );
		
	}

	@Override
	public void federationSaveStatusResponse(FederateHandleSaveStatusPair[] arg0) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void federationSaved() throws FederateInternalError {
		//logger.info( arg0.toString() );
		
	}

	@Override
	public void informAttributeOwnership(ObjectInstanceHandle arg0, AttributeHandle arg1, FederateHandle arg2)
			throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void initiateFederateRestore(String arg0, String arg1, FederateHandle arg2) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void initiateFederateSave(String arg0) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void initiateFederateSave(String arg0, LogicalTime arg1) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void multipleObjectInstanceNameReservationFailed(Set<String> arg0) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void multipleObjectInstanceNameReservationSucceeded(Set<String> arg0) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void objectInstanceNameReservationFailed(String arg0) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void objectInstanceNameReservationSucceeded(String arg0) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void receiveInteraction(InteractionClassHandle arg0, ParameterHandleValueMap arg1, byte[] arg2,
			OrderType arg3, TransportationTypeHandle arg4, LogicalTime arg5, OrderType arg6,
			SupplementalReceiveInfo arg7) throws FederateInternalError {
		
		logger.info( "receiveInteraction 2" );
		
	}

	@Override
	public void receiveInteraction(InteractionClassHandle arg0, ParameterHandleValueMap arg1, byte[] arg2,
			OrderType arg3, TransportationTypeHandle arg4, LogicalTime arg5, OrderType arg6,
			MessageRetractionHandle arg7, SupplementalReceiveInfo arg8) throws FederateInternalError {
		
		logger.info( "receiveInteraction 3" );

		
	}

	@Override
	public void reflectAttributeValues(ObjectInstanceHandle arg0, AttributeHandleValueMap arg1, byte[] arg2,
			OrderType arg3, TransportationTypeHandle arg4, LogicalTime arg5, OrderType arg6,
			SupplementalReflectInfo arg7) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void reflectAttributeValues(ObjectInstanceHandle arg0, AttributeHandleValueMap arg1, byte[] arg2,
			OrderType arg3, TransportationTypeHandle arg4, LogicalTime arg5, OrderType arg6,
			MessageRetractionHandle arg7, SupplementalReflectInfo arg8) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}


	@Override
	public void reportAttributeTransportationType(ObjectInstanceHandle arg0, AttributeHandle arg1,
			TransportationTypeHandle arg2) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void reportFederationExecutions(FederationExecutionInformationSet arg0) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void reportInteractionTransportationType(FederateHandle arg0, InteractionClassHandle arg1,
			TransportationTypeHandle arg2) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void requestAttributeOwnershipAssumption(ObjectInstanceHandle arg0, AttributeHandleSet arg1, byte[] arg2)
			throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void requestAttributeOwnershipRelease(ObjectInstanceHandle arg0, AttributeHandleSet arg1, byte[] arg2)
			throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void requestDivestitureConfirmation(ObjectInstanceHandle arg0, AttributeHandleSet arg1)
			throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void requestFederationRestoreFailed(String arg0) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void requestFederationRestoreSucceeded(String arg0) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void requestRetraction(MessageRetractionHandle arg0) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void startRegistrationForObjectClass(ObjectClassHandle arg0) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void stopRegistrationForObjectClass(ObjectClassHandle arg0) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void turnInteractionsOff(InteractionClassHandle arg0) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void turnInteractionsOn(InteractionClassHandle arg0) throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void turnUpdatesOffForObjectInstance(ObjectInstanceHandle arg0, AttributeHandleSet arg1)
			throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void turnUpdatesOnForObjectInstance(ObjectInstanceHandle arg0, AttributeHandleSet arg1)
			throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}

	@Override
	public void turnUpdatesOnForObjectInstance(ObjectInstanceHandle arg0, AttributeHandleSet arg1, String arg2)
			throws FederateInternalError {
		logger.info( arg0.toString() );
		
	}
	
	
	
}
