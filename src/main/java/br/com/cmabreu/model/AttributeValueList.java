package br.com.cmabreu.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AttributeValueList implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<AttributeValue> values;
	private Integer objectInstanceHandle;
	private String tag;
	
	public AttributeValueList() {
		this.values = new ArrayList<AttributeValue>();
	}
	
	public Integer getObjectInstanceHandle() {
		return objectInstanceHandle;
	}
	
	public void setObjectInstanceHandle(Integer objectInstanceHandle) {
		this.objectInstanceHandle = objectInstanceHandle;
	}
	
	public List<AttributeValue> getValues() {
		return values;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
