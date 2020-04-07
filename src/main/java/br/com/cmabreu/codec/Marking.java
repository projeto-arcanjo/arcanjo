package br.com.cmabreu.codec;

/**
 *
 * @author bergtwvd
 */
public class Marking {
	public final static byte MARKING_LENGTH = 11;
	private String text;

	public Marking( String text ) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}

	public void setText(char[] data) {
		// find terminating NULL byte, if any
		int i;
		for (i=0; i<MARKING_LENGTH; i++) {
			if (data[i] == (char)0) break;
		}
		// copy up to the NULL byte, or marking length
		this.text = new String(data, 0, i);
	}
}
