package br.com.cmabreu.codec;

/**
 *
 * @author bergtwvd
 */

public class SpatialVariant {

	public final static byte NONE = 0;
	public final static byte STATIC = 1;
	public final static byte DRM_FPW = 2;
	public final static byte DRM_RPW = 3;
	public final static byte DRM_RVW = 4;
	public final static byte DRM_FVW = 5;
	public final static byte DRM_FPB = 6;
	public final static byte DRM_RPB = 7;
	public final static byte DRM_RVB = 8;
	public final static byte DRM_FVB = 9;

	public final static byte X = 0;
	public final static byte Y = 1;
	public final static byte Z = 2;

	public final static byte PSI = 0;
	public final static byte THETA = 1;
	public final static byte PHI = 2;

	byte discriminator;

	// world location (Geocentric)
	double[] geocentricLocation = new double[3];

	// is frozen flag
	boolean frozen;

	// orientation
	float[] orientation = new float[3];

	// velocity vector
	float[] velocityVector = new float[3];

	// angular velocity vector
	float[] angularVelocityVector = new float[3];

	// acceleration vector
	float[] accelerationVector = new float[3];

	public SpatialVariant() {
		this.discriminator = NONE;
	}

	public SpatialVariant(byte discriminant) {
		this.discriminator = discriminant;
	}

	public byte getDiscriminator() {
		return this.discriminator;
	}

	public void setDiscriminator(byte discriminator) {
		this.discriminator = discriminator;
	}

	public double[] getWorldLocation() {
		return this.geocentricLocation;
	}

	public void setWorldLocation(double x, double y, double z) {
		this.geocentricLocation[X] = x;
		this.geocentricLocation[Y] = y;
		this.geocentricLocation[Z] = z;
	}

	public boolean isFrozen() {
		return this.frozen;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	public float[] getOrientation() {
		return this.orientation;
	}

	public void setOrientation(float psi, float theta, float phi) {
		this.orientation[PSI] = psi;
		this.orientation[THETA] = theta;
		this.orientation[PHI] = phi;
	}

	public float[] getVelocityVector() {
		return this.velocityVector;
	}

	public void setVelocityVector(float xVelocity, float yVelocity, float zVelocity) {
		this.velocityVector[X] = xVelocity;
		this.velocityVector[Y] = yVelocity;
		this.velocityVector[Z] = zVelocity;
	}

	public float[] getAngularVelocityVector() {
		return this.angularVelocityVector;
	}

	public void setAngularVelocityVector(float xAngularVelocity, float yAngularVelocity, float zAngularVelocity) {
		this.angularVelocityVector[X] = xAngularVelocity;
		this.angularVelocityVector[Y] = yAngularVelocity;
		this.angularVelocityVector[Z] = zAngularVelocity;
	}

	public float[] getAccelerationVector() {
		return this.accelerationVector;
	}

	public void setAccelerationVector(float xAcceleration, float yAcceleration, float zAcceleration) {
		this.accelerationVector[X] = xAcceleration;
		this.accelerationVector[Y] = yAcceleration;
		this.accelerationVector[Z] = zAcceleration;
	}

	@Override
	public String toString() {
		return "D=" + this.discriminator
				+ ": x=" + this.geocentricLocation[X] + " y=" + this.geocentricLocation[Y] + " z=" + this.geocentricLocation[Z]
				+ " psi=" + this.orientation[PSI] + " theta=" + this.orientation[THETA] + " phi" + this.orientation[PHI];
	}
}
