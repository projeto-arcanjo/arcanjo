package br.com.cmabreu.datatypes;

public class MarkingStruct {
	//HLAoctet

	private char[] markingData = new char[11];
	private int markingEncodingType;
	String teste = "12345678910aaa";
	
	public MarkingStruct(int a,String b) {
		//Implementacao CACHORRENTA para resolver o problema
		//de a String ser manor do que 11 caracters
		//eu somo 11 espacos
		teste = b + "           ";
		markingEncodingType = a;
		for (int i = 0 ; i == 10 ; i++) {
		markingData[i] = b.charAt(i);
		}
		
	}

	public char getMarkingData(int i) {
		return  teste.charAt(i);
	}

	public void setMarkingData(String markingData) {
		this.markingData = markingData.toCharArray();
	}

	public int getMarkingEncodingType() {
		return markingEncodingType;
	}

	public void setMarkingEncodingType(int markingEncodingType) {
		this.markingEncodingType = markingEncodingType;
	}
	

	

}
