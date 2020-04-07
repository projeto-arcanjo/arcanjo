package br.com.cmabreu.codec;

import edu.nps.moves.disenum.CountryType;
import edu.nps.moves.disenum.EntityDomain;
import edu.nps.moves.disenum.EntityKind;
import edu.nps.moves.disenum.PlatformAir;

/**
 *
 * @author bergtwvd
 */
public class EntityType  {
	private byte entityKind =	(byte) EntityKind.PLATFORM.value;
	private byte domain = 		(byte) EntityDomain.AIR.value;
	private byte countryCode = 	(byte) CountryType.BRAZIL.value;
	private byte category = 	(byte) PlatformAir.ATTACK_HELICOPTER.value;
	private byte subCategory = 	13;	// Nao sei o que eh
	private byte specific = 	3;	// Nao sei o que eh
	private byte extra = 		0;	// Nao sei o que eh
	
	public EntityType() {
		//
	}
	
	public EntityType(byte entityKind, byte domain, byte countryCode, byte category, byte subCategory, byte specific, byte extra) {
		this.entityKind = entityKind;
		this.domain = domain;
		this.countryCode = countryCode;
		this.category = category;
		this.subCategory = subCategory;
		this.specific = specific;
		this.extra = extra;
	}

	public byte getEntityKind() {
		return entityKind;
	}

	public void setEntityKind(byte entityKind) {
		this.entityKind = entityKind;
	}

	public byte getDomain() {
		return domain;
	}

	public void setDomain(byte domain) {
		this.domain = domain;
	}

	public byte getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(byte countryCode) {
		this.countryCode = countryCode;
	}

	public byte getCategory() {
		return category;
	}

	public void setCategory(byte category) {
		this.category = category;
	}

	public byte getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(byte subCategory) {
		this.subCategory = subCategory;
	}

	public byte getSpecific() {
		return specific;
	}

	public void setSpecific(byte specific) {
		this.specific = specific;
	}

	public byte getExtra() {
		return extra;
	}

	public void setExtra(byte extra) {
		this.extra = extra;
	}
	
}
