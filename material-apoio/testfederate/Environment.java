package testfederate;

import geotransform.coords.Gcc_Coord_3d;
import geotransform.coords.Gdc_Coord_3d;
import geotransform.ellipsoids.WE_Ellipsoid;
import geotransform.transforms.Gcc_To_Gdc_Converter;
import geotransform.transforms.Gdc_To_Gcc_Converter;
import hla.rti1516e.ObjectInstanceHandle;
import static java.lang.System.currentTimeMillis;
import java.util.Collection;
import java.util.HashMap;
import static testfederate.codec.SpatialVariant.X;
import static testfederate.codec.SpatialVariant.Y;
import static testfederate.codec.SpatialVariant.Z;

/**
 *
 * @author bergtwvd
 */
public class Environment {

	HashMap<Integer, PhysicalEntity> entities;	// set of entities

	private double simTime;						// simulation time from current TAG
	private final long startRealTime;			// start time of simulation (realtime)
	TestConfiguration config;					// configuration data
	boolean doRequestAttributeValueUpdate;

	/**
	 * Construct an Environment class instance with the given set of images for
	 * visualizing entities.
	 *
	 * @param config
	 */
	public Environment(TestConfiguration config) {
		this.config = config;
		this.entities = new HashMap<>();
		this.simTime = 0.0;
		this.startRealTime = currentTimeMillis();
		this.doRequestAttributeValueUpdate = false;

		// initialise coordinate conversion system
		Gcc_To_Gdc_Converter.Init(new WE_Ellipsoid());
		Gdc_To_Gcc_Converter.Init(new WE_Ellipsoid());
	}

	public TestConfiguration getConfig() {
		return config;
	}

	public boolean isDoRequestAttributeValueUpdate() {
		return doRequestAttributeValueUpdate;
	}

	public void setDoRequestAttributeValueUpdate(boolean doRequestAttributeValueUpdate) {
		this.doRequestAttributeValueUpdate = doRequestAttributeValueUpdate;
	}

	/**
	 * Get the current Real Time.
	 *
	 * @return real time
	 */
	public double getRealTime() {
		return (currentTimeMillis() - this.startRealTime) / 1000.0;
	}

	/**
	 * Set the current Simulation time.
	 *
	 * @param simTime
	 */
	public void setSimTim(double simTime) {
		this.simTime = simTime;
	}

	/**
	 * Get the current Simulation time.
	 *
	 * @return simulation time
	 */
	public double getSimTime() {
		return this.simTime;
	}

	/**
	 * Create a new entity and add this to the environment.
	 *
	 * @param discoveredObjectInstance
	 * @param theObject
	 * @param name	Unique name of the entity
	 * @return	Entity instance
	 */
	public PhysicalEntity createPhysicalEntity(boolean discoveredObjectInstance, ObjectInstanceHandle theObject, String name) {
		PhysicalEntity e = new PhysicalEntity(discoveredObjectInstance, theObject, name);
		entities.put(theObject.hashCode(), e);

		if (discoveredObjectInstance) {
			this.doRequestAttributeValueUpdate = true;
		}
		return e;
	}

	/**
	 * Remove entity from the environment.
	 *
	 * @param theObject
	 */
	public void removePhysicalEntity(ObjectInstanceHandle theObject) {
		entities.remove(theObject.hashCode());
	}

	/**
	 * Get an entity.
	 *
	 * @param theObject
	 * @return entity
	 */
	public PhysicalEntity getPhysicalEntity(ObjectInstanceHandle theObject) {
		return entities.get(theObject.hashCode());
	}

	/**
	 *
	 * @return collection of entities
	 */
	public Collection<PhysicalEntity> getPhysicalEntityList() {
		return entities.values();
	}

	public final static byte LAT = 0;
	public final static byte LON = 1;
	public final static byte ALT = 2;

	public double[] getGeodeticLocation(double[] geocentricLocation) {
		Gcc_Coord_3d gcc_coord = new Gcc_Coord_3d(geocentricLocation[X], geocentricLocation[Y], geocentricLocation[Z]);
		Gdc_Coord_3d gdc_coord = new Gdc_Coord_3d();
		Gcc_To_Gdc_Converter.Convert(gcc_coord, gdc_coord);

		double[] geodeticLocation = new double[3];
		geodeticLocation[LAT] = gdc_coord.latitude;
		geodeticLocation[LON] = gdc_coord.longitude;
		geodeticLocation[ALT] = gdc_coord.elevation;

		return geodeticLocation;
	}

	public double[] getGeocentricLocation(double[] geodeticLocation) {
		Gcc_Coord_3d gcc_coord = new Gcc_Coord_3d();
		Gdc_Coord_3d gdc_coord = new Gdc_Coord_3d(geodeticLocation[LAT], geodeticLocation[LON], geodeticLocation[ALT]);
		Gdc_To_Gcc_Converter.Convert(gdc_coord, gcc_coord);

		double[] geocentricLocation = new double[3];
		geocentricLocation[X] = gcc_coord.x;
		geocentricLocation[Y] = gcc_coord.y;
		geocentricLocation[Z] = gcc_coord.z;

		return geocentricLocation;
	}

}
