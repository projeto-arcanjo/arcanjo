package br.com.cmabreu.datatypes;


public class SpatialFPStruct {
		WorldLocationStruct worldLocationStruct;
		RPRboolean IsFrozen;
		OrientationStruct orientationStruct;
		VelocityVectorStruct velocityVectorStruct;			
		
	public SpatialFPStruct(WorldLocationStruct a, RPRboolean b ,VelocityVectorStruct d, OrientationStruct c) {
		worldLocationStruct = a;
	//	IsFrozen = b;
		orientationStruct = c;
		velocityVectorStruct = d;		
	}
	public WorldLocationStruct getWorldLocationStruct() {
		return worldLocationStruct;
	}
	public void setWorldLocationStruct(WorldLocationStruct worldLocationStruct) {
		this.worldLocationStruct = worldLocationStruct;
	}
	public RPRboolean getIsFrozen() {
		return IsFrozen;
	}
	public void setIsFrozen(RPRboolean isFrozen) {
		IsFrozen = isFrozen;
	}
	
	public OrientationStruct getOrientationStruct() {
		return orientationStruct;
	}
	public void setOrientationStruct(OrientationStruct orientationStruct) {
		this.orientationStruct = orientationStruct;
	}
	public VelocityVectorStruct getVelocityVectorStruct() {
		return velocityVectorStruct;
	}
	public void setVelocityVectorStruct(VelocityVectorStruct velocityVectorStruct) {
		this.velocityVectorStruct = velocityVectorStruct;
	}


/*
	@Override
	public String toString() {
		return "SpatialFPStruct [worldLocationStruct=" + worldLocationStruct + ", IsFrozen=" + IsFrozen
				+ ", orientationStruct=" + orientationStruct + ", velocityVectorStruct=" + velocityVectorStruct + "]";
	}
	
	*/
	
} 
