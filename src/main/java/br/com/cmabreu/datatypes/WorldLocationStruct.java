package br.com.cmabreu.datatypes;
/**
 * 
 * 
 * @author ciavex
 * @descricao Por padrao a georeferenciacao do Combater
 * feita em coordenadas geocentricas, portanto , ha a necessidade
 * de converter a informacao para coordenadas geocentricas quando
 * for lancar na estrutura.
 */

public class WorldLocationStruct {
	private double cabecalho;
	private double worldLocationX;
	private double worldLocationY;
	private double worldLocationZ;
	//private double[] worldLoc = new double[3];
	
	public WorldLocationStruct(double x, double y, double z) {
		cabecalho = 7.291122019556398e-304;
		worldLocationX = x;
		worldLocationY = y;
		worldLocationZ = z;
	}	
	public WorldLocationStruct(double[] xyz) {
		cabecalho = 7.291122019556398e-304;
		worldLocationX = xyz[0];
		worldLocationY = xyz[1];
		worldLocationZ = xyz[2];
	}	
	public double getCabecalho() {
		return cabecalho;
	}
	public double getWorldLocationX() {
		return worldLocationX;
	}
	public void setWorldLocationX(double worldLocationX) {
		this.worldLocationX = worldLocationX;
	}
	public double getWorldLocationY() {
		return worldLocationY;
	}
	public void setWorldLocationY(double worldLocationY) {
		this.worldLocationY = worldLocationY;
	}
	public double getWorldLocationZ() {
		return worldLocationZ;
	}
	public void setWorldLocationZ(double worldLocationZ) {
		this.worldLocationZ = worldLocationZ;
	}

	@Override
	public String toString() {
		return "WorldLocationStruct [worldLocationX=" + worldLocationX + ", worldLocationY=" + worldLocationY
				+ ", worldLocationZ=" + worldLocationZ + "]";
	}
	
}
