package br.com.cmabreu.interfaces;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import br.com.cmabreu.federates.XPlaneAircraft;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.OrderType;

public interface IPhysicalEntity {
	XPlaneAircraft reflectAttributeValues(ObjectInstanceHandle theObject, 
			AttributeHandleValueMap theAttributes, byte[] tag, OrderType sentOrder, SimpMessagingTemplate simpMessagingTemplate) throws Exception;
}
