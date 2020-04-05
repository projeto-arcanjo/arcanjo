package br.com.cmabreu.rti1516e;

import java.io.Serializable;

/*
    <enumerator>
       <name>SupportServices</name>
       <value>6</value>
    </enumerator>
*/

public class Enumerator implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer value;

	public Enumerator(String name, Integer value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Integer getValue() {
		return value;
	}
	
	
	
}
