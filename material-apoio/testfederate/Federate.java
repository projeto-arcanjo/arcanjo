package testfederate;

/**
 *
 * @author bergtwvd
 */
import hla.rti1516e.*;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.exceptions.*;
import hla.rti1516e.time.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import static java.lang.Thread.sleep;
import static testfederate.Environment.ALT;
import static testfederate.Environment.LAT;
import static testfederate.Environment.LON;
import testfederate.codec.SpatialVariant;
import static testfederate.codec.SpatialVariant.X;
import static testfederate.codec.SpatialVariant.Y;
import static testfederate.codec.SpatialVariant.Z;

public class Federate {

	protected RTIambassador rtiamb;
	protected FederateAmbassador fedamb;
	protected HLAfloat64TimeFactory timeFactory;

	// caches of handle types - set once we join a federation
	protected AttributeHandleSet attributes;
	protected ObjectClassHandle entityHandle;
	protected AttributeHandle entityTypeHandle;
	protected AttributeHandle spatialHandle;
	protected AttributeHandle forceHandle;
	protected AttributeHandle markingHandle;

	// environment for all object instances
	protected Environment env;

	// time management states
	protected boolean isConstrained = false;
	protected boolean isRegulating = false;
	protected boolean isAdvancing = false;

	// configuration data
	protected TestConfiguration config;

	public Federate() {
	}

	/**
	 * This is the main simulation loop. It can be thought of as the main method
	 * of the federate. For a description of the basic flow of this federate,
	 * see the class level comments
	 *
	 * @param env
	 * @throws java.lang.Exception
	 */
	public void runFederate(Environment env) throws Exception {
		Main.logger.log(Level.INFO, "run Federate ...");

		this.config = env.getConfig();
		this.env = env;
		this.rtiamb = RtiFactoryFactory.getRtiFactory().getRtiAmbassador();

		// create the federate ambassasor after setting the above
		this.fedamb = new FederateAmbassador(this);

		connectAndJoin();

		if (config.isTimeManagedMode()) {
			enableTimePolicy();
		}

		publishAndSubscribe();

		registerObjects();

		advanceTime();

		resignAndDisconnect();
	}

	private void connectAndJoin() throws RTIexception, MalformedURLException, InterruptedException {
		Main.logger.log(Level.INFO, "Connecting to RTI ...");

		for (int i = 1; i <= config.getNrConnectionAttempts(); i++) {
			Main.logger.log(Level.INFO, "Attempt #{0} of {1} to connect ...", new Object[]{i, config.getNrConnectionAttempts()});
			try {
				rtiamb.connect(fedamb, CallbackModel.HLA_EVOKED);
				break;
			} catch (ConnectionFailed ex) {
				if (i == config.getNrConnectionAttempts()) {
					Main.logger.log(Level.SEVERE, null, ex);
					throw ex;
				}
			}

			// sleep 1 sec before trying again
			sleep(1000);
		}

		Main.logger.log(Level.INFO, "Connected to RTI");

		String fom = "foms/RPR_FOM_v2.0_1516-2010.xml";

		try {
			Main.logger.log(Level.INFO, "Creating Federation...");
			// We attempt to create a new federation
			URL[] createModules = new URL[]{(new File(fom)).toURI().toURL()};

			try {
				rtiamb.createFederationExecution(config.getFederationName(), createModules);
				Main.logger.log(Level.INFO, "Created Federation");
			} catch (FederationExecutionAlreadyExists ex) {
				Main.logger.log(Level.INFO, "Didn't create federation, it already existed");
			}

			URL[] joinModules = new URL[]{(new File(fom)).toURI().toURL()};

			rtiamb.joinFederationExecution(config.getFederateName(), // name for the federate
					"Test Federate", // federate type
					config.getFederationName(), // name of federation
					joinModules);           // modules we want to add

			Main.logger.log(Level.INFO, "Joined Federation as {0}", config.getFederateName());
		} catch (RTIexception ex) {
			rtiamb.disconnect();
			throw ex;
		}
	}

	/**
	 * This method will inform the RTI about the types of data that we are
	 * interested in.
	 */
	private void publishAndSubscribe() throws RTIexception {
		// get all the handle information for the attributes
		this.entityHandle = rtiamb.getObjectClassHandle("HLAobjectRoot.BaseEntity.PhysicalEntity.Platform.Aircraft");
		this.entityTypeHandle = rtiamb.getAttributeHandle(entityHandle, "EntityType");
		this.spatialHandle = rtiamb.getAttributeHandle(entityHandle, "Spatial");
		this.forceHandle = rtiamb.getAttributeHandle(entityHandle, "ForceIdentifier");
		this.markingHandle = rtiamb.getAttributeHandle(entityHandle, "Marking");

		// package the information into a handle set
		attributes = rtiamb.getAttributeHandleSetFactory().create();
		attributes.add(entityTypeHandle);
		attributes.add(spatialHandle);
		attributes.add(forceHandle);
		attributes.add(markingHandle);

		rtiamb.subscribeObjectClassAttributes(entityHandle, attributes);
		rtiamb.publishObjectClassAttributes(entityHandle, attributes);

		Main.logger.log(Level.INFO, "Published and Subscribed");
	}

	private void resignAndDisconnect() throws Exception {
		rtiamb.resignFederationExecution(ResignAction.DELETE_OBJECTS);
		Main.logger.log(Level.INFO, "Resigned from Federation");

		try {
			rtiamb.destroyFederationExecution("ExampleFederation");
			Main.logger.log(Level.INFO, "Destroyed Federation");
		} catch (FederationExecutionDoesNotExist ex) {
			Main.logger.log(Level.INFO, "No need to destroy federation, it doesn't exist");
		} catch (FederatesCurrentlyJoined ex) {
			Main.logger.log(Level.INFO, "Didn't destroy federation, federates still joined");
		}

		rtiamb.disconnect();
		Main.logger.log(Level.INFO, "Disconnected from Federation");
	}

	/**
	 * This method will attempt to enable the various time related properties
	 * for the federate
	 */
	private void enableTimePolicy() throws Exception {
		Main.logger.log(Level.INFO, "Enable time policy");

		// deliver RO messages when in either the Time Advancing state or Time Granted state
		this.rtiamb.enableAsynchronousDelivery();

		// get time factory AFTER we have connected
		this.timeFactory = (HLAfloat64TimeFactory) rtiamb.getTimeFactory();

		// enable time constrained
		this.rtiamb.enableTimeConstrained();

		// wait until we get the callback
		while (this.isConstrained == false) {
			rtiamb.evokeCallback(1);
		}

		// enable time regulation
		HLAfloat64Interval lti = timeFactory.makeInterval(config.getLookahead());
		this.rtiamb.enableTimeRegulation(lti);

		// wait until we get the callback
		while (this.isRegulating == false) {
			rtiamb.evokeCallback(1);
		}

		Main.logger.log(Level.INFO, "Time policy enabled");
	}

	protected void registerObjects() throws RTIexception, DecoderException {
		// instantiate an object
		String name = "Entity1";
		ObjectInstanceHandle theObject = this.rtiamb.registerObjectInstance(this.entityHandle);
		PhysicalEntity e = env.createPhysicalEntity(false, theObject, name);

		// give it a position
		SpatialVariant sv = e.getSpatialVariant();
		double[] geodetic = new double[3];
		geodetic[LAT] = 51.0;
		geodetic[LON] = 12.0;
		geodetic[ALT] = 1001.0;
		double[] geocentric = env.getGeocentricLocation(geodetic);
		sv.setWorldLocation(geocentric[X], geocentric[Y], geocentric[Z]);
		sv.setOrientation(0.0f, 0.0f, 0.0f);
		sv.setFrozen(false);
		sv.setVelocityVector(10.0f, 0.0f, 0.0f);
		sv.setDiscriminator(SpatialVariant.DRM_FPW);
	}

	protected void requestTimeAdvancementIf() throws RTIexception {
		if (this.config.isTimeManagedMode()) {
			if (!this.isAdvancing) {
				HLAfloat64Time timeRequest = this.timeFactory.makeTime(env.getSimTime() + config.getSimTimeStep());
				rtiamb.timeAdvanceRequest(timeRequest);
				Main.logger.log(Level.FINE, "timeAdvanceRequest Time={0}", new Object[]{timeRequest});
				this.isAdvancing = true;
			}
		}
	}

	private void advanceTime() throws Exception {
		Main.logger.log(Level.INFO, "Start processing ...");

		// at this point we may still be in Time Granted state if the GALT was undefined
		for (;;) {
			// request time advancement
			requestTimeAdvancementIf();

			// handle CBs for realTimeStep seconds
			rtiamb.evokeMultipleCallbacks(config.getRealTimeStep(), config.getRealTimeStep());

			// request attribute value updates for discovered entities
			requestUpdatePhysicalEntities();

			if (!this.isAdvancing) {
				updateOwnEntities();
			}
		}
	}

	private void requestUpdatePhysicalEntities() {
		if (env.isDoRequestAttributeValueUpdate()) {
			for (PhysicalEntity e : env.getPhysicalEntityList()) {
				if (!e.isAttributeValueUpdateRequested()) {
					// request attribute values
					try {
						rtiamb.requestAttributeValueUpdate(e.getTheObject(), attributes, null);
						e.setAttributeValueUpdateRequested(true);
					} catch (AttributeNotDefined | ObjectInstanceNotKnown | SaveInProgress | RestoreInProgress | FederateNotExecutionMember | NotConnected | RTIinternalError ex) {
						Main.logger.log(Level.SEVERE, null, ex);
					}
				}
				env.setDoRequestAttributeValueUpdate(false);
			}
		}
	}

	private void updateOwnEntities() throws DecoderException, AttributeNotOwned, AttributeNotDefined, FederateNotExecutionMember, NotConnected, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, RTIinternalError, InvalidLogicalTime {
		for (PhysicalEntity e : env.getPhysicalEntityList()) {
			if (!e.isDiscoveredObjectInstance()) {
				SpatialVariant sv = e.getSpatialVariant();
				double[] location = sv.getWorldLocation();
				float[] velocity = sv.getVelocityVector();
				location[SpatialVariant.X] += velocity[SpatialVariant.X] * config.getSimTimeStep();
				location[SpatialVariant.Y] += velocity[SpatialVariant.Y] * config.getSimTimeStep();
				location[SpatialVariant.Z] += velocity[SpatialVariant.Z] * config.getSimTimeStep();

				byte[] b = this.fedamb.codec.encodeSpatialVariant(sv);
				AttributeHandleValueMap ahvm = this.rtiamb.getAttributeHandleValueMapFactory().create(1);
				ahvm.put(this.spatialHandle, b);

				// create timestamp
				HLAfloat64Time time = this.timeFactory.makeTime(env.getSimTime() + config.getSimTimeStep());

				this.rtiamb.updateAttributeValues(e.getTheObject(), ahvm, b, time);

				Main.logger.log(Level.INFO, "updateAttributeValues for {0} at Time={1}", new Object[]{e.getName(), time});
			}
		}
	}

}
