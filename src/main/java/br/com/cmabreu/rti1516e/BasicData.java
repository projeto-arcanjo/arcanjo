package br.com.cmabreu.rti1516e;

import java.io.Serializable;

/*
     <basicData>
        <name>HLAinteger16BE</name>
        <size>16</size>
        <interpretation>Integer in the range [-2^15, 2^15 - 1]</interpretation>
        <endian>Big</endian>
        <encoding>16-bit two's complement signed integer. The most significant bit contains the sign.</encoding>
     </basicData>
*/

public class BasicData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Integer size;
	private String interpretation;
	private String endian;
	private String encoding;
	
	public BasicData(String name, Integer size, String interpretation, String endian, String encoding) {
		this.name = name;
		this.size = size;
		this.interpretation = interpretation;
		this.endian = endian;
		this.encoding = encoding;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getSize() {
		return size;
	}
	
	public String getInterpretation() {
		return interpretation;
	}
	
	public String getEndian() {
		return endian;
	}
	
	public String getEncoding() {
		return encoding;
	}

}
