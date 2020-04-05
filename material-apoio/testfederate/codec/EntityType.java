package testfederate.codec;

/**
 *
 * @author bergtwvd
 */
public class EntityType implements Comparable {

	// define fields
	public final static byte ENTITYKIND = 0;
	public final static byte DOMAIN = 1;
	public final static byte COUNTRYCODE = 2;
	public final static byte CATEGORY = 3;
	public final static byte SUBCATEGORY = 4;
	public final static byte SPECIFIC = 5;
	public final static byte EXTRA = 6;
	
	public final static byte TOTAL_FIELDS = 7;

	// define domain names
	public final static String DOMAIN_NAME[] = {"Other", "Land", "Air", "Surface", "Subsurface", "Space"};

	byte[] field;		// the entity type fields
	String fieldStr;	// the fields in String format

	public EntityType() {
		newEntityType(null);
	}

	public EntityType(String value) {
		newEntityType(value);
	}

	private void newEntityType(String value) {
		field = new byte[TOTAL_FIELDS];

		String sval[] = (value != null) ? value.split("\\.", 7) : new String[0];
		for (int i = 0; i < sval.length; i++) {
			sval[i] = (sval[i] == null) ? null : sval[i].trim();
			try {
				field[i] = (sval[i] == null || sval[i].isEmpty()) ? 0 : Byte.parseByte(sval[i]);
			} catch (NumberFormatException e) {
				field[i] = 0;
			}
		}

		// fill the rest up with zeros
		for (int i = sval.length; i < field.length; i++) {
			field[i] = 0;
		}
		
		// ensure domain is within bounds
		if (field[DOMAIN] < 0) field[DOMAIN] += 128;
		field[DOMAIN] %= DOMAIN_NAME.length;

		createString();
	}

	public byte[] getField() {
		return this.field;
	}

	public void setField(byte[] field) {
		this.field = field;
		
		// ensure domain is within bounds
		if (field[DOMAIN] < 0) field[DOMAIN] += 128;
		field[DOMAIN] %= DOMAIN_NAME.length;
		
		createString();
	}

	// this compareTo to:
	// return -1: this enumeration is more restrictive
	// return +1: this enumeration is less restrictive
	// return 0: otherwise
	@Override
	public int compareTo(Object o) {
		EntityType e = (EntityType) o;

		for (int i = field.length - 1; i >= 0; i--) {
			if (field[i] == e.field[i]) {
				continue;
			} else if (field[i] == 0) {
				return +1;
			} else {
				return -1;
			}
		}
		return 0;
	}

	// test if e is compatible with this enumeration
	public boolean isCompatible(EntityType e) {
		for (int i = field.length - 1; i >= 0; i--) {
			if (this.field[i] != 0) {
				if (this.field[i] != e.field[i]) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return this.fieldStr;
	}
	
	private void createString() {
		this.fieldStr = "" + Byte.toUnsignedInt(field[0]);
		for (int i = 1; i < EntityType.TOTAL_FIELDS; i++) {
			this.fieldStr += "." + Byte.toUnsignedInt(field[i]);
		}
	}

}
