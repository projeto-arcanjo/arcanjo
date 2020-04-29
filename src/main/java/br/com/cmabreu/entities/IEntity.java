package br.com.cmabreu.entities;

import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.OrderType;

public interface IEntity {
	IEntity reflectAttributeValues(ObjectInstanceHandle theObject, 
			AttributeHandleValueMap theAttributes, byte[] tag, OrderType sentOrder) throws Exception;
}
