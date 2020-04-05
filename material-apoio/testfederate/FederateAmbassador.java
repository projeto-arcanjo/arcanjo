package testfederate;

/**
 *
 * @author bergtwvd
 */
import testfederate.codec.*;
import hla.rti1516e.*;
import hla.rti1516e.encoding.*;
import hla.rti1516e.exceptions.*;
import hla.rti1516e.time.*;
import java.util.logging.*;

/**
 * This class handles all call backs from the RTI.
 */
public class FederateAmbassador extends NullFederateAmbassador {

	private final Federate federate;
	protected final Codec codec;

	public FederateAmbassador(Federate federate) throws RTIinternalError {
		this.federate = federate;
		EncoderFactory encoderFactory = RtiFactoryFactory.getRtiFactory().getEncoderFactory();
		this.codec = new Codec(encoderFactory);
	}

	/**
	 *
	 * @param theObject
	 * @param theObjectClass
	 * @param objectName
	 * @throws FederateInternalError
	 */
	@Override
	public void discoverObjectInstance(ObjectInstanceHandle theObject,
			ObjectClassHandle theObjectClass,
			String objectName)
			throws FederateInternalError {

		Main.logger.log(Level.INFO,
				"Discovered Object: handle={0}, classHandle={1}, name={2}", new Object[]{theObject, theObjectClass, objectName});
		federate.env.createPhysicalEntity(true, theObject, objectName);
	}

	/**
	 *
	 * @param theObject
	 * @param theAttributes
	 * @param tag
	 * @param sentOrder
	 * @param transport
	 * @param reflectInfo
	 * @throws FederateInternalError
	 */
	@Override
	public void reflectAttributeValues(ObjectInstanceHandle theObject,
			AttributeHandleValueMap theAttributes,
			byte[] tag,
			OrderType sentOrder,
			TransportationTypeHandle transport,
			SupplementalReflectInfo reflectInfo)
			throws FederateInternalError {
		// just pass it on to the other method for printing purposes
		// passing null as the time will let the other method know it
		// is from us, not from the RTI
		reflectAttributeValues(theObject,
				theAttributes,
				tag,
				sentOrder,
				transport,
				null,
				sentOrder,
				reflectInfo);
	}

	/**
	 *
	 * @param theObject
	 * @param theAttributes
	 * @param tag
	 * @param sentOrdering
	 * @param theTransport
	 * @param time
	 * @param receivedOrdering
	 * @param reflectInfo
	 * @throws FederateInternalError
	 */
	@Override
	public void reflectAttributeValues(ObjectInstanceHandle theObject,
			AttributeHandleValueMap theAttributes,
			byte[] tag,
			OrderType sentOrdering,
			TransportationTypeHandle theTransport,
			LogicalTime time,
			OrderType receivedOrdering,
			SupplementalReflectInfo reflectInfo)
			throws FederateInternalError {

		PhysicalEntity e = federate.env.getPhysicalEntity(theObject);

		try {
			for (AttributeHandle attributeHandle : theAttributes.keySet()) {
				if (attributeHandle.equals(federate.entityTypeHandle)) {
					EntityType et = codec.decodeEntityType(theAttributes.get(attributeHandle));
					e.setEntityType(et);
				} else if (attributeHandle.equals(federate.forceHandle)) {
					ForceIdentifier forceId = codec.decodeForceIdentifier(theAttributes.get(attributeHandle));
					e.setForceIdentifier(forceId);
				} else if (attributeHandle.equals(federate.spatialHandle)) {
					SpatialVariant sv = codec.decodeSpatialVariant(theAttributes.get(attributeHandle));
					e.setSpatialVariant(sv);
				} else if (attributeHandle.equals(federate.markingHandle)) {
					Marking m = codec.decodeMarking(theAttributes.get(attributeHandle));
					e.setMarking(m);
				}
			}
		} catch (DecoderException ex) {
			Main.logger.log(Level.SEVERE, null, ex);
		}

		if (Main.logger.getLevel() == Level.FINER) {
			StringBuilder builder = new StringBuilder("Reflection for object:");

			// print the handle
			builder.append(" handle=" + theObject);

			// print the tag
			if (tag != null) {
				builder.append(", tag=" + new String(tag));
			}

			// print the time (if we have it) we'll get null if we are just receiving
			// a forwarded call from the other reflect callback above
			if (time != null) {
				builder.append(", time=" + ((HLAfloat64Time) time).getValue());
			}

			// print the attribute information
			builder.append(", attributeCount=" + theAttributes.size());
			builder.append("\n");
			for (AttributeHandle attributeHandle : theAttributes.keySet()) {
				// print the attibute handle
				builder.append("\tattributeHandle=");

				// if we're dealing with Flavor, decode into the appropriate enum value
				if (attributeHandle.equals(federate.entityTypeHandle)) {
					builder.append(attributeHandle);
					builder.append(" (entityType)    ");
					builder.append(", attributeValue=");
					builder.append(e.getEntityType().toString());
				} else if (attributeHandle.equals(federate.forceHandle)) {
					builder.append(attributeHandle);
					builder.append(" (forceIdentifier)");
					builder.append(", attributeValue=");
					builder.append(e.getForceIdentifier().toString());
				} else if (attributeHandle.equals(federate.spatialHandle)) {
					builder.append(attributeHandle);
					builder.append(" (spatial)");
					builder.append(", attributeValue=");
					builder.append(e.getSpatialVariant().toString());
				} else if (attributeHandle.equals(federate.markingHandle)) {
					builder.append(attributeHandle);
					builder.append(" (marking)");
					builder.append(", attributeValue=");
					builder.append(e.getMarking().toString());
				} else {
					builder.append(attributeHandle);
					builder.append(" (Unknown)   ");
				}

				builder.append("\n");
			}

			Main.logger.log(Level.FINER, builder.toString());
		}
	}

	/**
	 *
	 * @param theObject
	 * @param tag
	 * @param sentOrdering
	 * @param removeInfo
	 * @throws FederateInternalError
	 */
	@Override
	public void removeObjectInstance(ObjectInstanceHandle theObject,
			byte[] tag,
			OrderType sentOrdering,
			SupplementalRemoveInfo removeInfo)
			throws FederateInternalError {

		PhysicalEntity e = federate.env.getPhysicalEntity(theObject);

		Main.logger.log(Level.INFO, "Object Removed: handle={0} name={1}",
				new Object[]{theObject, e.getName()});

		federate.env.removePhysicalEntity(theObject);
	}

	/**
	 *
	 * @param time
	 */
	@Override
	public void timeConstrainedEnabled(LogicalTime time) {
		federate.env.setSimTim(((HLAfloat64Time) time).getValue());
		federate.isConstrained = true;
		Main.logger.log(Level.INFO, "timeConstrainedEnabled Time={0}", time);
	}

	/**
	 *
	 * @param time
	 * @throws FederateInternalError
	 */
	@Override
	public void timeRegulationEnabled(LogicalTime time) throws FederateInternalError {
		federate.isRegulating = true;
		Main.logger.log(Level.INFO, "timeRegulationEnabled Time={0}", time);
	}
	
	/**
	 *
	 * @param time
	 */
	@Override
	public void timeAdvanceGrant(LogicalTime time) {
		federate.env.setSimTim(((HLAfloat64Time) time).getValue());
		federate.isAdvancing = false;
		Main.logger.log(Level.FINE, "timeAdvanceGrant Time={0}", time);
	}

	/**
	 *
	 * @param synchronizationPointLabel
	 * @param userSuppliedTag
	 * @throws FederateInternalError
	 */
	@Override
	public void announceSynchronizationPoint(String synchronizationPointLabel, byte[] userSuppliedTag) throws FederateInternalError {
		Main.logger.log(Level.INFO, "announceSynchronizationPoint label={0}", synchronizationPointLabel);

		try {
			federate.rtiamb.synchronizationPointAchieved(synchronizationPointLabel);
			Main.logger.log(Level.INFO, "synchronizationPointAchieved label={0}", synchronizationPointLabel);
		} catch (RTIexception ex) {
			Main.logger.log(Level.SEVERE, null, ex);
		}
	}

	/**
	 *
	 * @param synchronizationPointLabel
	 * @param failedToSyncSet
	 * @throws FederateInternalError
	 */
	@Override
	public void federationSynchronized(String synchronizationPointLabel, FederateHandleSet failedToSyncSet) throws FederateInternalError {
		Main.logger.log(Level.INFO, "federationSynchronized label={0}", synchronizationPointLabel);
	}

}
