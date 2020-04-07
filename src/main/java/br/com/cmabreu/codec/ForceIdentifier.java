package br.com.cmabreu.codec;

/**
 *
 * @author bergtwvd
 */
public class ForceIdentifier {
	
	public final static byte OTHER = 0;
	public final static byte FRIENDLY = 1;
	public final static byte OPPOSING = 2;
	public final static byte NEUTRAL = 3;
	
	public final static byte TOTAL_IDENTIFIERS = 4;

	// define force names
	public final static String FORCE_NAME[] = {"Other", "Friendly", "Opposing", "Neutral"};

	byte forceId;

	public ForceIdentifier() {
		this.forceId = OTHER;
	}
	
	public ForceIdentifier(byte forceId) {
		this.setForceId(forceId);
	}

	public byte getForceId() {
		return this.forceId;
	}
	
	public void setForceId(byte forceId) {
		if (forceId < 0) forceId += 128;
		forceId %= TOTAL_IDENTIFIERS;
		this.forceId = forceId;
	}
	
	@Override
	public String toString() {
		return FORCE_NAME[this.forceId] ;
	}
	
}
