package br.com.cmabreu.datatypes;


// SISO-REF-010-2019


public class EntityTypeStruct {
	private byte entityKind;
	private byte domain;
	private short countryCode;
	private byte category;
	private byte subCategory;
	private byte specific;
	private byte extra;        
	
    public EntityTypeStruct(int entityKind, int domain , int countryCode, int category, int subCategory, int specific, int extra){
    	this.entityKind = (byte) entityKind;
    	this.domain =(byte) domain;
    	this.countryCode =(byte) countryCode;
    	this.category =(byte) category;
    	this.subCategory =(byte) subCategory;
    	this.specific = (byte)specific;
    	this.extra = (byte)extra;
    }    
    
	public byte getentityKind() {
		return entityKind;
	}

	public void setentityKind(byte entityKind) {
		this.entityKind = entityKind;
	}

	public byte getdomain() {
		return domain;
	}

	public void setdomain(byte domain) {
		this.domain = domain;
	}

	public short getcountryCode() {
		return countryCode;
	}

	public void setcountryCode(short countryCode) {
		this.countryCode = countryCode;
	}

	public byte getcategory() {
		return category;
	}

	public void setcategory(byte category) {
		this.category = category;
	}

	public byte getsubCategory() {
		return subCategory;
	}

	public void setsubCategory(byte subCategory) {
		this.subCategory = subCategory;
	}

	public byte getspecific() {
		return specific;
	}

	public void setspecific(byte specific) {
		this.specific = specific;
	}

	public byte getextra() {
		return extra;
	}

	public void setextra(byte extra) {
		this.extra = extra;
	}
	
}
