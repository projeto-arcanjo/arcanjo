package br.com.cmabreu.managers;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import br.com.cmabreu.entities.IEntity;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;

public interface IEntityManager {
	boolean isAKindOfMe( ObjectClassHandle classHandle );
	IEntity doIHaveThisObject( ObjectInstanceHandle theObject );
	void discoverObjectInstance( ObjectInstanceHandle theObject, ObjectClassHandle theObjectClass, String objectName, SimpMessagingTemplate simpMessagingTemplate );
	void removeObjectInstance( ObjectInstanceHandle theObject );
}
