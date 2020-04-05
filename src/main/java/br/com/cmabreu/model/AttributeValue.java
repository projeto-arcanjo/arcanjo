package br.com.cmabreu.model;

import java.io.Serializable;

import br.com.cmabreu.rti1516e.Attribute;

public class AttributeValue implements Serializable {
	private static final long serialVersionUID = 1L;
	private Attribute attribute;
	private String value;
	
	public AttributeValue(Attribute attribute, String value) {
		this.attribute = attribute;
		this.value = value;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public String getValue() {
		return value;
	}

}
