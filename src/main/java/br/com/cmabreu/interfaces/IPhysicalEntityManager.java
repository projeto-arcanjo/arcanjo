package br.com.cmabreu.interfaces;

import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;

public interface IPhysicalEntityManager {
	boolean isAKindOfMe( ObjectClassHandle classHandle );
	IPhysicalEntity doIHaveThisObject( ObjectInstanceHandle theObject );
	void discoverObjectInstance( ObjectInstanceHandle theObject, ObjectClassHandle theObjectClass, String objectName );
	void removeObjectInstance( ObjectInstanceHandle theObject );
}
