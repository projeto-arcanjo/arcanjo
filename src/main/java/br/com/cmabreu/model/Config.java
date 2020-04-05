package br.com.cmabreu.model;

public class Config {
	private String federationName;
	private String hlaVersion;
	
	public Config( String federationName, String hlaVersion ) {
		this.federationName = federationName;
		this.hlaVersion = hlaVersion;
	}

	public String getFederationName() {
		return federationName;
	}

	public String getHlaVersion() {
		return hlaVersion;
	}
	
}
