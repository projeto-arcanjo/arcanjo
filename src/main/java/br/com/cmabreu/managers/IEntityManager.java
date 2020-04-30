package br.com.cmabreu.managers;

import br.com.cmabreu.entities.IEntity;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.OrderType;

public interface IEntityManager {
	boolean isAKindOfMe( ObjectClassHandle classHandle );
	IEntity doIHaveThisObject( ObjectInstanceHandle theObject );
	void discoverObjectInstance( ObjectInstanceHandle theObject, ObjectClassHandle theObjectClass, String objectName, String classeTipo );
	void removeObjectInstance( ObjectInstanceHandle theObject );
	int sendObjectsToInterface();
	void reflectAttributeValues( ObjectInstanceHandle theObject, AttributeHandleValueMap theAttributes, byte[] tag, OrderType sentOrder );
	String getClassFomName();
}
