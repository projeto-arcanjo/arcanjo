package br.com.cmabreu.interfaces;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import br.com.cmabreu.federates.Aircraft;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.OrderType;

public interface IPhysicalEntity {
	Aircraft reflectAttributeValues(ObjectInstanceHandle theObject, 
			AttributeHandleValueMap theAttributes, byte[] tag, OrderType sentOrder, SimpMessagingTemplate simpMessagingTemplate) throws Exception;
}
