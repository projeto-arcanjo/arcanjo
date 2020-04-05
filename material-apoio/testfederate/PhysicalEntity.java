package testfederate;

/**
 *
 * @author bergtwvd
 */
import hla.rti1516e.ObjectInstanceHandle;
import testfederate.codec.EntityType;
import testfederate.codec.SpatialVariant;
import testfederate.codec.ForceIdentifier;
import testfederate.codec.Marking;

public class PhysicalEntity {
	boolean discoveredObjectInstance;
	boolean attributeValueUpdateRequested;
	
	ObjectInstanceHandle theObject;
	String name;
	EntityType entityType;
	SpatialVariant spatialVariant;
	ForceIdentifier forceIdentifier;
	Marking marking;

	public PhysicalEntity(boolean discoveredObjectInstance, ObjectInstanceHandle theObject, String name) {
		this.discoveredObjectInstance = discoveredObjectInstance;
		this.attributeValueUpdateRequested = false;
		this.theObject = theObject;
		this.name = name;
		this.entityType = new EntityType();
		this.spatialVariant = new SpatialVariant();
		this.forceIdentifier = new ForceIdentifier();
		this.marking = new Marking();
	}

	public boolean isAttributeValueUpdateRequested() {
		return attributeValueUpdateRequested;
	}

	public void setAttributeValueUpdateRequested(boolean attributeValueUpdateRequested) {
		this.attributeValueUpdateRequested = attributeValueUpdateRequested;
	}

	public boolean isDiscoveredObjectInstance() {
		return discoveredObjectInstance;
	}

	public ObjectInstanceHandle getTheObject() {
		return theObject;
	}
	
	public String getName() {
		return name;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	public SpatialVariant getSpatialVariant() {
		return spatialVariant;
	}

	public void setSpatialVariant(SpatialVariant spatialVariant) {
		this.spatialVariant = spatialVariant;
	}

	public ForceIdentifier getForceIdentifier() {
		return forceIdentifier;
	}

	public void setForceIdentifier(ForceIdentifier forceIdentifier) {
		this.forceIdentifier = forceIdentifier;
	}

	public Marking getMarking() {
		return marking;
	}

	public void setMarking(Marking marking) {
		this.marking = marking;
	}

}
