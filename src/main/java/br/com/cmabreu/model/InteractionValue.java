package br.com.cmabreu.model;

import java.io.Serializable;

public class InteractionValue implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	private Integer parameterCount;
	private String fullName;
	private String moduleName;
	private Integer handle;
	private String federateName;
	
	public InteractionValue(Integer parameterCount, String fullName, String moduleName, Integer handle, String federateName ) {
		this.parameterCount = parameterCount;
		this.fullName = fullName;
		this.moduleName = moduleName;
		this.handle = handle;
		this.federateName = federateName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getParameterCount() {
		return parameterCount;
	}

	public String getFullName() {
		return fullName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public Integer getHandle() {
		return handle;
	}

	public String getFederateName() {
		return federateName;
	}

}
