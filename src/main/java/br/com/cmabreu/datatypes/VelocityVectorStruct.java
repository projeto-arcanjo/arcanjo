package br.com.cmabreu.datatypes;

public class VelocityVectorStruct  {

	float xVelocity;
	float yVelocity;
	float zVelocity;	
	
	
	public VelocityVectorStruct(float a, float b, float c) {
		xVelocity = a;
		yVelocity = b;
		zVelocity = c;
		
	}
	
	public VelocityVectorStruct(double a, double b, double c) {
		xVelocity = (float) a;
		yVelocity = (float) b;
		zVelocity = (float) c;
		
	}
	
	public float getxVelocity() {
		return xVelocity;
	}
	public void setxVelocity(float xVelocity) {
		this.xVelocity = xVelocity;
	}
	public float getyVelocity() {
		return yVelocity;
	}
	public void setyVelocity(float yVelocity) {
		this.yVelocity = yVelocity;
	}
	public float getzVelocity() {
		return zVelocity;
	}
	public void setzVelocity(float zVelocity) {
		this.zVelocity = zVelocity;
	}
	
	@Override
	public String toString() {
		return "VelocityVectorStruct [xVelocity=" + xVelocity + ", yVelocity=" + yVelocity + ", zVelocity=" + zVelocity
				+ "]";
	}	
}