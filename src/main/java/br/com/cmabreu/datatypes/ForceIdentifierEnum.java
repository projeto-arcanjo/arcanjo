package br.com.cmabreu.datatypes;

public enum ForceIdentifierEnum {
	OTHER(0),FRIENDLY(1),OPPOSING(2),NEUTRAL(3),FRIENDLY_2(4),OPPOSING_2(5),NEUTRAL_2(6),
	FRIENDLY_3(7),OPPOSING_3(8),NEUTRAL_3(9),FRIENDLY_4(10),OPPOSING_4(11),NEUTRAL_4(12),
	FRIENDLY_5(13),OPPOSING_5(14),NEUTRAL_5(15),FRIENDLY_6(16),OPPOSING_6(17),NEUTRAL_6(18),
	FRIENDLY_7(19),OPPOSING_7(20),NEUTRAL_7(21),FRIENDLY_8(22),OPPOSING_8(23),NEUTRAL_8(24),
	FRIENDLY_9(25),OPPOSING_9(26),NEUTRAL_9(27),FRIENDLY_10(28),OPPOSING_10(29),NEUTRAL_10(30);
	
	private int forceId;
	private String forceIdString;
	
	ForceIdentifierEnum() {
	}
	ForceIdentifierEnum(int valorOp) {
		forceId = valorOp;
	}
	ForceIdentifierEnum(String valorOp) {
		forceIdString = valorOp;
	}
	public int getForceId() {
		return forceId;
	}
	public void setForceId(int forceId) {
		this.forceId = forceId;
	}
	public String getForceIdString() {
		return forceIdString;
	}
	public void setForceIdString(String forceIdString) {
		this.forceIdString = forceIdString;
	}



}
