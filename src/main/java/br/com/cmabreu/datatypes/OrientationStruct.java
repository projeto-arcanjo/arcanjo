package br.com.cmabreu.datatypes;

public class OrientationStruct {
	float psi;
	float theta;
	float phi;
	
	public OrientationStruct(float x, float y , float z) {
		psi = x;
		theta = y;
		phi = z;		
	}
	public OrientationStruct(double x, double y , double z) {
		psi = (float) x;
		theta = (float) y;
		phi = (float) z;		
	}

	public float getpsi() {
		return psi;
	}

	public void setpsi(float psi) {
		this.psi = psi;
	}

	public float gettheta() {
		return theta;
	}

	public void settheta(float theta) {
		this.theta = theta;
	}

	public float getphi() {
		return phi;
	}

	public void setphi(float phi) {
		this.phi = phi;
	}

	@Override
	public String toString() {
		return "OrientationStruct [Psi=" + this.psi + ", Theta=" + this.theta + ", Phi=" + this.phi + "]";
	}	
	
	
}