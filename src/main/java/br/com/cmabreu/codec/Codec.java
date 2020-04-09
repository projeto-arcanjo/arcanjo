package br.com.cmabreu.codec;

import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderFactory;
import hla.rti1516e.encoding.HLAfixedRecord;
import hla.rti1516e.encoding.HLAfloat32BE;
import hla.rti1516e.encoding.HLAfloat64BE;
import hla.rti1516e.encoding.HLAinteger16BE;
import hla.rti1516e.encoding.HLAoctet;
import hla.rti1516e.encoding.HLAvariantRecord;

/**
 *
 * @author bergtwvd
 */
public class Codec {

	EncoderFactory encoderFactory;
	
	HLAvariantRecord<HLAoctet> SpatialVariantStruct;
	HLAfixedRecord SpatialFixedStruct; //portico
	
	
	boolean useSpatialVariantDecoder;  // false for Portico

	public Codec(EncoderFactory encoderFactory) {
		// check if createHLAvariantRecord decoder is supported for spatials
		this.useSpatialVariantDecoder = (encoderFactory.createHLAvariantRecord(encoderFactory.createHLAoctet()) != null);

		this.encoderFactory = encoderFactory;

		if (useSpatialVariantDecoder) {
			this.SpatialVariantStruct = createSpatialVariantDecoder();
			this.SpatialFixedStruct = null;
		} else {
			this.SpatialVariantStruct = null;
			this.SpatialFixedStruct = createSpatialFixedDecoder();
		}

	}

	private HLAfixedRecord createEntityIdentifierDecoder() {
		HLAfixedRecord EntityIdentifierStruct = encoderFactory.createHLAfixedRecord();
		HLAinteger16BE siteId = encoderFactory.createHLAinteger16BE();
		HLAinteger16BE applicationId = encoderFactory.createHLAinteger16BE();
		HLAinteger16BE entityNumber = encoderFactory.createHLAinteger16BE();
		EntityIdentifierStruct.add(siteId);
		EntityIdentifierStruct.add(applicationId);
		EntityIdentifierStruct.add(entityNumber);
		return EntityIdentifierStruct;
	}
	
	
	private HLAfixedRecord createEntityTypeDecoder() {
		// EntityTypeStruct
		HLAfixedRecord EntityTypeStruct = encoderFactory.createHLAfixedRecord();
		HLAoctet entityKind = encoderFactory.createHLAoctet();
		HLAoctet domain = encoderFactory.createHLAoctet();
		HLAoctet countryCode = encoderFactory.createHLAoctet();
		HLAoctet category = encoderFactory.createHLAoctet();
		HLAoctet subCategory = encoderFactory.createHLAoctet();
		HLAoctet specific = encoderFactory.createHLAoctet();
		HLAoctet extra = encoderFactory.createHLAoctet();
		
		EntityTypeStruct.add( entityKind );
		EntityTypeStruct.add( domain );
		EntityTypeStruct.add( countryCode );
		EntityTypeStruct.add( category );
		EntityTypeStruct.add( subCategory );
		EntityTypeStruct.add( specific );
		EntityTypeStruct.add( extra );

		return EntityTypeStruct;
	}

	private HLAvariantRecord<HLAoctet> createSpatialVariantDecoder() {
		// WorldLocationStruct
		HLAfixedRecord WorldLocationStruct = encoderFactory.createHLAfixedRecord();
		HLAfloat64BE X = encoderFactory.createHLAfloat64BE();
		HLAfloat64BE Y = encoderFactory.createHLAfloat64BE();
		HLAfloat64BE Z = encoderFactory.createHLAfloat64BE();
		WorldLocationStruct.add(X);
		WorldLocationStruct.add(Y);
		WorldLocationStruct.add(Z);

		// isFrozen
		HLAoctet isFrozen = encoderFactory.createHLAoctet();

		// OrientationStruct
		HLAfixedRecord OrientationStruct = encoderFactory.createHLAfixedRecord();
		HLAfloat32BE Psi = encoderFactory.createHLAfloat32BE();
		HLAfloat32BE Theta = encoderFactory.createHLAfloat32BE();
		HLAfloat32BE Phi = encoderFactory.createHLAfloat32BE();
		OrientationStruct.add(Psi);
		OrientationStruct.add(Theta);
		OrientationStruct.add(Phi);

		// VelocityVectorStruct
		HLAfixedRecord VelocityVectorStruct = encoderFactory.createHLAfixedRecord();
		HLAfloat32BE XVelocity = encoderFactory.createHLAfloat32BE();
		HLAfloat32BE YVelocity = encoderFactory.createHLAfloat32BE();
		HLAfloat32BE ZVelocity = encoderFactory.createHLAfloat32BE();
		VelocityVectorStruct.add(XVelocity);
		VelocityVectorStruct.add(YVelocity);
		VelocityVectorStruct.add(ZVelocity);

		// AngularVelocityVectorStruct
		HLAfixedRecord AngularVelocityVectorStruct = encoderFactory.createHLAfixedRecord();
		HLAfloat32BE XAngularVelocity = encoderFactory.createHLAfloat32BE();
		HLAfloat32BE YAngularVelocity = encoderFactory.createHLAfloat32BE();
		HLAfloat32BE ZAngularVelocity = encoderFactory.createHLAfloat32BE();
		AngularVelocityVectorStruct.add(XAngularVelocity);
		AngularVelocityVectorStruct.add(YAngularVelocity);
		AngularVelocityVectorStruct.add(ZAngularVelocity);

		// AccelerationVectorStruct
		HLAfixedRecord AccelerationVectorStruct = encoderFactory.createHLAfixedRecord();
		HLAfloat32BE XAcceleration = encoderFactory.createHLAfloat32BE();
		HLAfloat32BE YAcceleration = encoderFactory.createHLAfloat32BE();
		HLAfloat32BE ZAcceleration = encoderFactory.createHLAfloat32BE();
		AccelerationVectorStruct.add(XAcceleration);
		AccelerationVectorStruct.add(YAcceleration);
		AccelerationVectorStruct.add(ZAcceleration);

		// SpatialOtherStruct DRM#0: None
		HLAfixedRecord SpatialOtherStruct = encoderFactory.createHLAfixedRecord();

		// SpatialStaticStruct DRM#1: Static
		HLAfixedRecord SpatialStaticStruct = encoderFactory.createHLAfixedRecord();
		SpatialStaticStruct.add(WorldLocationStruct);
		SpatialStaticStruct.add(isFrozen);
		SpatialStaticStruct.add(OrientationStruct);

		// SpatialFPStruct DRM#2: DRM_FPW
		HLAfixedRecord SpatialFPStruct = encoderFactory.createHLAfixedRecord();
		SpatialFPStruct.add(WorldLocationStruct);
		SpatialFPStruct.add(isFrozen);
		SpatialFPStruct.add(OrientationStruct);
		SpatialFPStruct.add(VelocityVectorStruct);

		// SpatialRPStruct DRM#3: DRM_RPW
		HLAfixedRecord SpatialRPStruct = encoderFactory.createHLAfixedRecord();
		SpatialRPStruct.add(WorldLocationStruct);
		SpatialRPStruct.add(isFrozen);
		SpatialRPStruct.add(OrientationStruct);
		SpatialRPStruct.add(VelocityVectorStruct);
		SpatialRPStruct.add(AngularVelocityVectorStruct);

		// SpatialRVStruct DRM#4: DRM_RVW
		HLAfixedRecord SpatialRVStruct = encoderFactory.createHLAfixedRecord();
		SpatialRVStruct.add(WorldLocationStruct);
		SpatialRVStruct.add(isFrozen);
		SpatialRVStruct.add(OrientationStruct);
		SpatialRVStruct.add(VelocityVectorStruct);
		SpatialRVStruct.add(AccelerationVectorStruct);
		SpatialRVStruct.add(AngularVelocityVectorStruct);

		// SpatialFVStruct DRM#5: DRM_FVW
		HLAfixedRecord SpatialFVStruct = encoderFactory.createHLAfixedRecord();
		SpatialFVStruct.add(WorldLocationStruct);
		SpatialFVStruct.add(isFrozen);
		SpatialFVStruct.add(OrientationStruct);
		SpatialFVStruct.add(VelocityVectorStruct);
		SpatialFVStruct.add(AccelerationVectorStruct);

		// DRM#6: DRM_FPB, same as SpatialFPStruct
		// DRM#7: DRM_RPB, same as SpatialRPStruct
		// DRM#8: DRM_RVB, same as SpatialRVStruct
		// DRM#9: DRM_FVB, same as SpatialFVStruct
		// SpatialVariantStruct Discriminators
		HLAoctet[] Discriminator = new HLAoctet[10];
		for (int i = 0; i < Discriminator.length; i++) {
			Discriminator[i] = encoderFactory.createHLAoctet();
			Discriminator[i].setValue((byte) i);
		}

		// SpatialVariantStruct
		HLAoctet discriminator = encoderFactory.createHLAoctet();
		SpatialVariantStruct = encoderFactory.createHLAvariantRecord(discriminator);
		SpatialVariantStruct.setVariant(Discriminator[0], SpatialOtherStruct);
		SpatialVariantStruct.setVariant(Discriminator[1], SpatialStaticStruct);
		SpatialVariantStruct.setVariant(Discriminator[2], SpatialFPStruct);
		SpatialVariantStruct.setVariant(Discriminator[3], SpatialRPStruct);
		SpatialVariantStruct.setVariant(Discriminator[4], SpatialRVStruct);
		SpatialVariantStruct.setVariant(Discriminator[5], SpatialFVStruct);
		SpatialVariantStruct.setVariant(Discriminator[6], SpatialFPStruct);
		SpatialVariantStruct.setVariant(Discriminator[7], SpatialRPStruct);
		SpatialVariantStruct.setVariant(Discriminator[8], SpatialRVStruct);
		SpatialVariantStruct.setVariant(Discriminator[9], SpatialFVStruct);

		return SpatialVariantStruct;
	}

	private HLAfixedRecord createSpatialFixedDecoder() {
		// WorldLocationStruct
		HLAfixedRecord WorldLocationStruct = encoderFactory.createHLAfixedRecord();
		HLAfloat64BE X = encoderFactory.createHLAfloat64BE();
		HLAfloat64BE Y = encoderFactory.createHLAfloat64BE();
		HLAfloat64BE Z = encoderFactory.createHLAfloat64BE();
		WorldLocationStruct.add(X);
		WorldLocationStruct.add(Y);
		WorldLocationStruct.add(Z);

		// isFrozen
		HLAoctet isFrozen = encoderFactory.createHLAoctet();

		// OrientationStruct
		HLAfixedRecord OrientationStruct = encoderFactory.createHLAfixedRecord();
		HLAfloat32BE Psi = encoderFactory.createHLAfloat32BE();
		HLAfloat32BE Theta = encoderFactory.createHLAfloat32BE();
		HLAfloat32BE Phi = encoderFactory.createHLAfloat32BE();
		OrientationStruct.add(Psi);
		OrientationStruct.add(Theta);
		OrientationStruct.add(Phi);

		// VelocityVectorStruct
		HLAfixedRecord VelocityVectorStruct = encoderFactory.createHLAfixedRecord();
		HLAfloat32BE XVelocity = encoderFactory.createHLAfloat32BE();
		HLAfloat32BE YVelocity = encoderFactory.createHLAfloat32BE();
		HLAfloat32BE ZVelocity = encoderFactory.createHLAfloat32BE();
		VelocityVectorStruct.add(XVelocity);
		VelocityVectorStruct.add(YVelocity);
		VelocityVectorStruct.add(ZVelocity);

		// AngularVelocityVectorStruct
		HLAfixedRecord AngularVelocityVectorStruct = encoderFactory.createHLAfixedRecord();
		HLAfloat32BE XAngularVelocity = encoderFactory.createHLAfloat32BE();
		HLAfloat32BE YAngularVelocity = encoderFactory.createHLAfloat32BE();
		HLAfloat32BE ZAngularVelocity = encoderFactory.createHLAfloat32BE();
		AngularVelocityVectorStruct.add(XAngularVelocity);
		AngularVelocityVectorStruct.add(YAngularVelocity);
		AngularVelocityVectorStruct.add(ZAngularVelocity);

		// AccelerationVectorStruct
		HLAfixedRecord AccelerationVectorStruct = encoderFactory.createHLAfixedRecord();
		HLAfloat32BE XAcceleration = encoderFactory.createHLAfloat32BE();
		HLAfloat32BE YAcceleration = encoderFactory.createHLAfloat32BE();
		HLAfloat32BE ZAcceleration = encoderFactory.createHLAfloat32BE();
		AccelerationVectorStruct.add(XAcceleration);
		AccelerationVectorStruct.add(YAcceleration);
		AccelerationVectorStruct.add(ZAcceleration);

		// SpatialRVStruct DRM#4: DRM_RVW
		HLAfixedRecord SpatialRVStruct = encoderFactory.createHLAfixedRecord();
		SpatialRVStruct.add(WorldLocationStruct);
		SpatialRVStruct.add(isFrozen);
		SpatialRVStruct.add(OrientationStruct);
		SpatialRVStruct.add(VelocityVectorStruct);
		SpatialRVStruct.add(AccelerationVectorStruct);
		SpatialRVStruct.add(AngularVelocityVectorStruct);

		// SpatialFixedStruct
		HLAoctet discriminator = encoderFactory.createHLAoctet();
		SpatialFixedStruct = encoderFactory.createHLAfixedRecord();
		SpatialFixedStruct.add(discriminator);
		SpatialFixedStruct.add(SpatialRVStruct); // add most extensive one

		return SpatialFixedStruct;
	}

	private HLAoctet createForceIdentifierDecoder() {
		HLAoctet ForceIdentifier = encoderFactory.createHLAoctet();
		return ForceIdentifier;
	}

	private HLAfixedRecord createMarkingDecoder() {
		HLAfixedRecord MarkingStruct = encoderFactory.createHLAfixedRecord();
		// MarkingEncodingEnum8
		MarkingStruct.add(encoderFactory.createHLAoctet());
		// MarkingArray11
		for (int i = 0; i < Marking.MARKING_LENGTH; i++) {
			MarkingStruct.add(encoderFactory.createHLAoctet());
		}
		return MarkingStruct;
	}

	public EntityType decodeEntityType(byte[] bytes) throws DecoderException {
		HLAfixedRecord EntityTypeStruct = createEntityTypeDecoder();
		// decode
		EntityTypeStruct.decode(bytes);
		EntityType et = new EntityType();
		et.setEntityKind( ((HLAoctet) EntityTypeStruct.get( 0 ) ).getValue() );
		et.setDomain( ((HLAoctet) EntityTypeStruct.get( 1 ) ).getValue() );
		et.setCountryCode( ((HLAoctet) EntityTypeStruct.get( 2 ) ).getValue() );
		et.setCategory( ((HLAoctet) EntityTypeStruct.get( 3 ) ).getValue() );
		et.setSubCategory( ((HLAoctet) EntityTypeStruct.get( 4 ) ).getValue() );
		et.setSpecific( ((HLAoctet) EntityTypeStruct.get( 5 ) ).getValue() );
		et.setExtra( ((HLAoctet) EntityTypeStruct.get( 6 ) ).getValue() );
		return et;
	}
	
	public byte[] encodeEntityType( EntityType et ) {
		HLAfixedRecord EntityTypeStruct = createEntityTypeDecoder();
		((HLAoctet) EntityTypeStruct.get(0)).setValue( et.getEntityKind() );
		((HLAoctet) EntityTypeStruct.get(1)).setValue( et.getDomain() );
		((HLAoctet) EntityTypeStruct.get(2)).setValue( et.getCountryCode() );
		((HLAoctet) EntityTypeStruct.get(3)).setValue( et.getCategory() );
		((HLAoctet) EntityTypeStruct.get(4)).setValue( et.getSubCategory() );
		((HLAoctet) EntityTypeStruct.get(5)).setValue( et.getSpecific() );
		((HLAoctet) EntityTypeStruct.get(6)).setValue( et.getExtra() );
		return EntityTypeStruct.toByteArray();		
	}

	private synchronized SpatialVariant decodeSpatialFixed(byte[] bytes) throws DecoderException {
		// decode
		SpatialFixedStruct.decode(bytes);

		// get the discriminant
		HLAoctet discriminator = (HLAoctet) SpatialFixedStruct.get(0);

		// the Spatial to be returned
		SpatialVariant sv = new SpatialVariant(discriminator.getValue());

		if (sv.getDiscriminator() == SpatialVariant.NONE) { // DRM#0
			return sv;
		}

		// set world location
		HLAfixedRecord SpatialStaticStruct = (HLAfixedRecord) SpatialFixedStruct.get(1);
		HLAfixedRecord WorldLocationStruct = (HLAfixedRecord) SpatialStaticStruct.get(0);
		HLAfloat64BE X = (HLAfloat64BE) WorldLocationStruct.get(0);
		HLAfloat64BE Y = (HLAfloat64BE) WorldLocationStruct.get(1);
		HLAfloat64BE Z = (HLAfloat64BE) WorldLocationStruct.get(2);
		sv.setWorldLocation(X.getValue(), Y.getValue(), Z.getValue());

		// set frozen flag
		HLAoctet isFrozen = (HLAoctet) SpatialStaticStruct.get(1);
		sv.setFrozen(isFrozen.getValue() != 0);

		// set orientation
		HLAfixedRecord OrientationStruct = (HLAfixedRecord) SpatialStaticStruct.get(2);
		HLAfloat32BE Psi = (HLAfloat32BE) OrientationStruct.get(0);
		HLAfloat32BE Theta = (HLAfloat32BE) OrientationStruct.get(1);
		HLAfloat32BE Phi = (HLAfloat32BE) OrientationStruct.get(2);
		sv.setOrientation(Psi.getValue(), Theta.getValue(), Phi.getValue());

		if (sv.getDiscriminator() == SpatialVariant.STATIC) { // DRM#1
			return sv;
		}

		if (sv.getDiscriminator() == SpatialVariant.DRM_FPW
				|| sv.getDiscriminator() == SpatialVariant.DRM_FPB) { // DRM#2, DRM#6

			HLAfixedRecord SpatialFPStruct = (HLAfixedRecord) SpatialFixedStruct.get(1);

			// set velocity
			HLAfixedRecord VelocityVectorStruct = (HLAfixedRecord) SpatialFPStruct.get(3);
			HLAfloat32BE XVelocity = (HLAfloat32BE) VelocityVectorStruct.get(0);
			HLAfloat32BE YVelocity = (HLAfloat32BE) VelocityVectorStruct.get(1);
			HLAfloat32BE ZVelocity = (HLAfloat32BE) VelocityVectorStruct.get(2);
			sv.setVelocityVector(XVelocity.getValue(), YVelocity.getValue(), ZVelocity.getValue());

			return sv;
		}

		if (sv.getDiscriminator() == SpatialVariant.DRM_RPW
				|| sv.getDiscriminator() == SpatialVariant.DRM_RPB) { // DRM#3, DRM#7

			HLAfixedRecord SpatialRPStruct = (HLAfixedRecord) SpatialFixedStruct.get(1);

			// set velocity
			HLAfixedRecord VelocityVectorStruct = (HLAfixedRecord) SpatialRPStruct.get(3);
			HLAfloat32BE XVelocity = (HLAfloat32BE) VelocityVectorStruct.get(0);
			HLAfloat32BE YVelocity = (HLAfloat32BE) VelocityVectorStruct.get(1);
			HLAfloat32BE ZVelocity = (HLAfloat32BE) VelocityVectorStruct.get(2);
			sv.setVelocityVector(XVelocity.getValue(), YVelocity.getValue(), ZVelocity.getValue());

			// set angular velocity
			HLAfixedRecord AngularVelocityVector = (HLAfixedRecord) SpatialRPStruct.get(4);
			HLAfloat32BE XAngularVelocity = (HLAfloat32BE) AngularVelocityVector.get(0);
			HLAfloat32BE YAngularVelocity = (HLAfloat32BE) AngularVelocityVector.get(1);
			HLAfloat32BE ZAngularVelocity = (HLAfloat32BE) AngularVelocityVector.get(2);
			sv.setVelocityVector(XAngularVelocity.getValue(), YAngularVelocity.getValue(), ZAngularVelocity.getValue());

			return sv;
		}

		if (sv.getDiscriminator() == SpatialVariant.DRM_RVW
				|| sv.getDiscriminator() == SpatialVariant.DRM_RVB) { // DRM#4, DRM#8

			HLAfixedRecord SpatialRVStruct = (HLAfixedRecord) SpatialFixedStruct.get(1);

			// set velocity
			HLAfixedRecord VelocityVectorStruct = (HLAfixedRecord) SpatialRVStruct.get(3);
			HLAfloat32BE XVelocity = (HLAfloat32BE) VelocityVectorStruct.get(0);
			HLAfloat32BE YVelocity = (HLAfloat32BE) VelocityVectorStruct.get(1);
			HLAfloat32BE ZVelocity = (HLAfloat32BE) VelocityVectorStruct.get(2);
			sv.setVelocityVector(XVelocity.getValue(), YVelocity.getValue(), ZVelocity.getValue());

			// set acceleration
			HLAfixedRecord AccelerationVectorStruct = (HLAfixedRecord) SpatialRVStruct.get(4);
			HLAfloat32BE XAcceleration = (HLAfloat32BE) AccelerationVectorStruct.get(0);
			HLAfloat32BE YAcceleration = (HLAfloat32BE) AccelerationVectorStruct.get(1);
			HLAfloat32BE ZAcceleration = (HLAfloat32BE) AccelerationVectorStruct.get(2);
			sv.setAccelerationVector(XAcceleration.getValue(), YAcceleration.getValue(), ZAcceleration.getValue());

			// set angular velocity
			HLAfixedRecord AngularVelocityVector = (HLAfixedRecord) SpatialRVStruct.get(5);
			HLAfloat32BE XAngularVelocity = (HLAfloat32BE) AngularVelocityVector.get(0);
			HLAfloat32BE YAngularVelocity = (HLAfloat32BE) AngularVelocityVector.get(1);
			HLAfloat32BE ZAngularVelocity = (HLAfloat32BE) AngularVelocityVector.get(2);
			sv.setVelocityVector(XAngularVelocity.getValue(), YAngularVelocity.getValue(), ZAngularVelocity.getValue());

			return sv;
		}

		if (sv.getDiscriminator() == SpatialVariant.DRM_FVW
				|| sv.getDiscriminator() == SpatialVariant.DRM_FVB) { // DRM#5, DRM#9

			HLAfixedRecord SpatialFVStruct = (HLAfixedRecord) SpatialFixedStruct.get(1);

			// set velocity
			HLAfixedRecord VelocityVectorStruct = (HLAfixedRecord) SpatialFVStruct.get(3);
			HLAfloat32BE XVelocity = (HLAfloat32BE) VelocityVectorStruct.get(0);
			HLAfloat32BE YVelocity = (HLAfloat32BE) VelocityVectorStruct.get(1);
			HLAfloat32BE ZVelocity = (HLAfloat32BE) VelocityVectorStruct.get(2);
			sv.setVelocityVector(XVelocity.getValue(), YVelocity.getValue(), ZVelocity.getValue());

			// set acceleration
			HLAfixedRecord AccelerationVectorStruct = (HLAfixedRecord) SpatialFVStruct.get(4);
			HLAfloat32BE XAcceleration = (HLAfloat32BE) AccelerationVectorStruct.get(0);
			HLAfloat32BE YAcceleration = (HLAfloat32BE) AccelerationVectorStruct.get(1);
			HLAfloat32BE ZAcceleration = (HLAfloat32BE) AccelerationVectorStruct.get(2);
			sv.setAccelerationVector(XAcceleration.getValue(), YAcceleration.getValue(), ZAcceleration.getValue());

			return sv;
		}

		// must be something else, make it NONE
		sv.setDiscriminator(SpatialVariant.NONE);
		return sv;
	}

	private synchronized byte[] encodeSpatialFixed(SpatialVariant sv) throws DecoderException {

		// set the discriminant
		((HLAoctet) SpatialFixedStruct.get(0)).setValue(sv.getDiscriminator());

		if (sv.getDiscriminator() == SpatialVariant.NONE) { // DRM#0
			return SpatialFixedStruct.toByteArray();
		}

		HLAfixedRecord SpatialStaticStruct = (HLAfixedRecord) SpatialFixedStruct.get(1);

		// set world location
		double[] worldLocation = sv.getWorldLocation();
		HLAfixedRecord WorldLocationStruct = (HLAfixedRecord) SpatialStaticStruct.get(0);
		((HLAfloat64BE) WorldLocationStruct.get(0)).setValue(worldLocation[SpatialVariant.X]);
		((HLAfloat64BE) WorldLocationStruct.get(1)).setValue(worldLocation[SpatialVariant.Y]);
		((HLAfloat64BE) WorldLocationStruct.get(2)).setValue(worldLocation[SpatialVariant.Z]);

		// set frozen flag
		((HLAoctet) SpatialStaticStruct.get(1)).setValue(sv.isFrozen() ? (byte) 1 : (byte) 0);

		// set orientation
		float[] orientation = sv.getOrientation();
		HLAfixedRecord OrientationStruct = (HLAfixedRecord) SpatialStaticStruct.get(2);
		((HLAfloat32BE) OrientationStruct.get(0)).setValue(orientation[SpatialVariant.PSI]);
		((HLAfloat32BE) OrientationStruct.get(1)).setValue(orientation[SpatialVariant.THETA]);
		((HLAfloat32BE) OrientationStruct.get(2)).setValue(orientation[SpatialVariant.PHI]);

		if (sv.getDiscriminator() == SpatialVariant.STATIC) { // DRM#1
			return SpatialFixedStruct.toByteArray();
		}

		if (sv.getDiscriminator() == SpatialVariant.DRM_FPW
				|| sv.getDiscriminator() == SpatialVariant.DRM_FPB) { // DRM#2, DRM#6

			HLAfixedRecord SpatialFPStruct = (HLAfixedRecord) SpatialFixedStruct.get(1);

			// set velocity
			float[] velocity = sv.getVelocityVector();
			HLAfixedRecord VelocityVectorStruct = (HLAfixedRecord) SpatialFPStruct.get(3);
			((HLAfloat32BE) VelocityVectorStruct.get(0)).setValue(velocity[SpatialVariant.X]);
			((HLAfloat32BE) VelocityVectorStruct.get(1)).setValue(velocity[SpatialVariant.Y]);
			((HLAfloat32BE) VelocityVectorStruct.get(2)).setValue(velocity[SpatialVariant.Z]);

			return SpatialFixedStruct.toByteArray();
		}

		if (sv.getDiscriminator() == SpatialVariant.DRM_RPW
				|| sv.getDiscriminator() == SpatialVariant.DRM_RPB) { // DRM#3, DRM#7

			HLAfixedRecord SpatialRPStruct = (HLAfixedRecord) SpatialFixedStruct.get(1);

			// set velocity
			float[] velocity = sv.getVelocityVector();
			HLAfixedRecord VelocityVectorStruct = (HLAfixedRecord) SpatialRPStruct.get(3);
			((HLAfloat32BE) VelocityVectorStruct.get(0)).setValue(velocity[SpatialVariant.X]);
			((HLAfloat32BE) VelocityVectorStruct.get(1)).setValue(velocity[SpatialVariant.Y]);
			((HLAfloat32BE) VelocityVectorStruct.get(2)).setValue(velocity[SpatialVariant.Z]);

			// set angular velocity
			float[] angularVelocity = sv.getAngularVelocityVector();
			HLAfixedRecord AngularVelocityVector = (HLAfixedRecord) SpatialRPStruct.get(4);
			((HLAfloat32BE) AngularVelocityVector.get(0)).setValue(angularVelocity[SpatialVariant.X]);
			((HLAfloat32BE) AngularVelocityVector.get(1)).setValue(angularVelocity[SpatialVariant.Y]);
			((HLAfloat32BE) AngularVelocityVector.get(2)).setValue(angularVelocity[SpatialVariant.Z]);

			return SpatialFixedStruct.toByteArray();
		}

		if (sv.getDiscriminator() == SpatialVariant.DRM_RVW
				|| sv.getDiscriminator() == SpatialVariant.DRM_RVB) { // DRM#4, DRM#8

			HLAfixedRecord SpatialRVStruct = (HLAfixedRecord) SpatialFixedStruct.get(1);

			// set velocity
			float[] velocity = sv.getVelocityVector();
			HLAfixedRecord VelocityVectorStruct = (HLAfixedRecord) SpatialRVStruct.get(3);
			((HLAfloat32BE) VelocityVectorStruct.get(0)).setValue(velocity[SpatialVariant.X]);
			((HLAfloat32BE) VelocityVectorStruct.get(1)).setValue(velocity[SpatialVariant.Y]);
			((HLAfloat32BE) VelocityVectorStruct.get(2)).setValue(velocity[SpatialVariant.Z]);

			// set acceleration
			float[] accelaration = sv.getAccelerationVector();
			HLAfixedRecord AccelerationVectorStruct = (HLAfixedRecord) SpatialRVStruct.get(4);
			((HLAfloat32BE) AccelerationVectorStruct.get(0)).setValue(accelaration[SpatialVariant.X]);
			((HLAfloat32BE) AccelerationVectorStruct.get(1)).setValue(accelaration[SpatialVariant.Y]);
			((HLAfloat32BE) AccelerationVectorStruct.get(2)).setValue(accelaration[SpatialVariant.Z]);

			// set angular velocity
			float[] angularVelocity = sv.getAngularVelocityVector();
			HLAfixedRecord AngularVelocityVector = (HLAfixedRecord) SpatialRVStruct.get(5);
			((HLAfloat32BE) AngularVelocityVector.get(0)).setValue(angularVelocity[SpatialVariant.X]);
			((HLAfloat32BE) AngularVelocityVector.get(1)).setValue(angularVelocity[SpatialVariant.Y]);
			((HLAfloat32BE) AngularVelocityVector.get(2)).setValue(angularVelocity[SpatialVariant.Z]);

			return SpatialFixedStruct.toByteArray();
		}

		if (sv.getDiscriminator() == SpatialVariant.DRM_FVW
				|| sv.getDiscriminator() == SpatialVariant.DRM_FVB) { // DRM#5, DRM#9

			HLAfixedRecord SpatialFVStruct = (HLAfixedRecord) SpatialFixedStruct.get(1);

			// set velocity
			float[] velocity = sv.getVelocityVector();
			HLAfixedRecord VelocityVectorStruct = (HLAfixedRecord) SpatialFVStruct.get(3);
			((HLAfloat32BE) VelocityVectorStruct.get(0)).setValue(velocity[SpatialVariant.X]);
			((HLAfloat32BE) VelocityVectorStruct.get(1)).setValue(velocity[SpatialVariant.Y]);
			((HLAfloat32BE) VelocityVectorStruct.get(2)).setValue(velocity[SpatialVariant.Z]);

			// set acceleration
			float[] accelaration = sv.getAccelerationVector();
			HLAfixedRecord AccelerationVectorStruct = (HLAfixedRecord) SpatialFVStruct.get(4);
			((HLAfloat32BE) AccelerationVectorStruct.get(0)).setValue(accelaration[SpatialVariant.X]);
			((HLAfloat32BE) AccelerationVectorStruct.get(1)).setValue(accelaration[SpatialVariant.Y]);
			((HLAfloat32BE) AccelerationVectorStruct.get(2)).setValue(accelaration[SpatialVariant.Z]);

			return SpatialFixedStruct.toByteArray();
		}

		// must be something else, make it NONE
		((HLAoctet) SpatialFixedStruct.get(0)).setValue(SpatialVariant.NONE);
		return SpatialFixedStruct.toByteArray();
	}

	public synchronized SpatialVariant decodeSpatialVariant(byte[] bytes) throws DecoderException {
		if (!useSpatialVariantDecoder) {
			return decodeSpatialFixed(bytes);
		}

		// decode
		SpatialVariantStruct.decode(bytes);

		// get the discriminant
		HLAoctet discriminator = SpatialVariantStruct.getDiscriminant();

		// the Spatial to be returned
		SpatialVariant sv = new SpatialVariant(discriminator.getValue());

		if (sv.getDiscriminator() == SpatialVariant.NONE) { // DRM#0
			return sv;
		}

		// set world location
		HLAfixedRecord SpatialStaticStruct = (HLAfixedRecord) SpatialVariantStruct.getValue();
		HLAfixedRecord WorldLocationStruct = (HLAfixedRecord) SpatialStaticStruct.get(0);
		HLAfloat64BE X = (HLAfloat64BE) WorldLocationStruct.get(0);
		HLAfloat64BE Y = (HLAfloat64BE) WorldLocationStruct.get(1);
		HLAfloat64BE Z = (HLAfloat64BE) WorldLocationStruct.get(2);
		sv.setWorldLocation(X.getValue(), Y.getValue(), Z.getValue());

		// set frozen flag
		HLAoctet isFrozen = (HLAoctet) SpatialStaticStruct.get(1);
		sv.setFrozen(isFrozen.getValue() != 0);

		// set orientation
		HLAfixedRecord OrientationStruct = (HLAfixedRecord) SpatialStaticStruct.get(2);
		HLAfloat32BE Psi = (HLAfloat32BE) OrientationStruct.get(0);
		HLAfloat32BE Theta = (HLAfloat32BE) OrientationStruct.get(1);
		HLAfloat32BE Phi = (HLAfloat32BE) OrientationStruct.get(2);
		sv.setOrientation(Psi.getValue(), Theta.getValue(), Phi.getValue());

		if (sv.getDiscriminator() == SpatialVariant.STATIC) { // DRM#1
			return sv;
		}

		if (sv.getDiscriminator() == SpatialVariant.DRM_FPW
				|| sv.getDiscriminator() == SpatialVariant.DRM_FPB) { // DRM#2, DRM#6

			HLAfixedRecord SpatialFPStruct = (HLAfixedRecord) SpatialVariantStruct.getValue();

			// set velocity
			HLAfixedRecord VelocityVectorStruct = (HLAfixedRecord) SpatialFPStruct.get(3);
			HLAfloat32BE XVelocity = (HLAfloat32BE) VelocityVectorStruct.get(0);
			HLAfloat32BE YVelocity = (HLAfloat32BE) VelocityVectorStruct.get(1);
			HLAfloat32BE ZVelocity = (HLAfloat32BE) VelocityVectorStruct.get(2);
			sv.setVelocityVector(XVelocity.getValue(), YVelocity.getValue(), ZVelocity.getValue());

			return sv;
		}

		if (sv.getDiscriminator() == SpatialVariant.DRM_RPW
				|| sv.getDiscriminator() == SpatialVariant.DRM_RPB) { // DRM#3, DRM#7

			HLAfixedRecord SpatialRPStruct = (HLAfixedRecord) SpatialVariantStruct.getValue();

			// set velocity
			HLAfixedRecord VelocityVectorStruct = (HLAfixedRecord) SpatialRPStruct.get(3);
			HLAfloat32BE XVelocity = (HLAfloat32BE) VelocityVectorStruct.get(0);
			HLAfloat32BE YVelocity = (HLAfloat32BE) VelocityVectorStruct.get(1);
			HLAfloat32BE ZVelocity = (HLAfloat32BE) VelocityVectorStruct.get(2);
			sv.setVelocityVector(XVelocity.getValue(), YVelocity.getValue(), ZVelocity.getValue());

			// set angular velocity
			HLAfixedRecord AngularVelocityVector = (HLAfixedRecord) SpatialRPStruct.get(4);
			HLAfloat32BE XAngularVelocity = (HLAfloat32BE) AngularVelocityVector.get(0);
			HLAfloat32BE YAngularVelocity = (HLAfloat32BE) AngularVelocityVector.get(1);
			HLAfloat32BE ZAngularVelocity = (HLAfloat32BE) AngularVelocityVector.get(2);
			sv.setVelocityVector(XAngularVelocity.getValue(), YAngularVelocity.getValue(), ZAngularVelocity.getValue());

			return sv;
		}

		if (sv.getDiscriminator() == SpatialVariant.DRM_RVW
				|| sv.getDiscriminator() == SpatialVariant.DRM_RVB) { // DRM#4, DRM#8

			HLAfixedRecord SpatialRVStruct = (HLAfixedRecord) SpatialVariantStruct.getValue();

			// set velocity
			HLAfixedRecord VelocityVectorStruct = (HLAfixedRecord) SpatialRVStruct.get(3);
			HLAfloat32BE XVelocity = (HLAfloat32BE) VelocityVectorStruct.get(0);
			HLAfloat32BE YVelocity = (HLAfloat32BE) VelocityVectorStruct.get(1);
			HLAfloat32BE ZVelocity = (HLAfloat32BE) VelocityVectorStruct.get(2);
			sv.setVelocityVector(XVelocity.getValue(), YVelocity.getValue(), ZVelocity.getValue());

			// set acceleration
			HLAfixedRecord AccelerationVectorStruct = (HLAfixedRecord) SpatialRVStruct.get(4);
			HLAfloat32BE XAcceleration = (HLAfloat32BE) AccelerationVectorStruct.get(0);
			HLAfloat32BE YAcceleration = (HLAfloat32BE) AccelerationVectorStruct.get(1);
			HLAfloat32BE ZAcceleration = (HLAfloat32BE) AccelerationVectorStruct.get(2);
			sv.setAccelerationVector(XAcceleration.getValue(), YAcceleration.getValue(), ZAcceleration.getValue());

			// set angular velocity
			HLAfixedRecord AngularVelocityVector = (HLAfixedRecord) SpatialRVStruct.get(5);
			HLAfloat32BE XAngularVelocity = (HLAfloat32BE) AngularVelocityVector.get(0);
			HLAfloat32BE YAngularVelocity = (HLAfloat32BE) AngularVelocityVector.get(1);
			HLAfloat32BE ZAngularVelocity = (HLAfloat32BE) AngularVelocityVector.get(2);
			sv.setVelocityVector(XAngularVelocity.getValue(), YAngularVelocity.getValue(), ZAngularVelocity.getValue());

			return sv;
		}

		if (sv.getDiscriminator() == SpatialVariant.DRM_FVW
				|| sv.getDiscriminator() == SpatialVariant.DRM_FVB) { // DRM#5, DRM#9

			HLAfixedRecord SpatialFVStruct = (HLAfixedRecord) SpatialVariantStruct.getValue();

			// set velocity
			HLAfixedRecord VelocityVectorStruct = (HLAfixedRecord) SpatialFVStruct.get(3);
			HLAfloat32BE XVelocity = (HLAfloat32BE) VelocityVectorStruct.get(0);
			HLAfloat32BE YVelocity = (HLAfloat32BE) VelocityVectorStruct.get(1);
			HLAfloat32BE ZVelocity = (HLAfloat32BE) VelocityVectorStruct.get(2);
			sv.setVelocityVector(XVelocity.getValue(), YVelocity.getValue(), ZVelocity.getValue());

			// set acceleration
			HLAfixedRecord AccelerationVectorStruct = (HLAfixedRecord) SpatialFVStruct.get(4);
			HLAfloat32BE XAcceleration = (HLAfloat32BE) AccelerationVectorStruct.get(0);
			HLAfloat32BE YAcceleration = (HLAfloat32BE) AccelerationVectorStruct.get(1);
			HLAfloat32BE ZAcceleration = (HLAfloat32BE) AccelerationVectorStruct.get(2);
			sv.setAccelerationVector(XAcceleration.getValue(), YAcceleration.getValue(), ZAcceleration.getValue());

			return sv;
		}

		// must be something else, make it NONE
		sv.setDiscriminator(SpatialVariant.NONE);
		return sv;
	}

	public synchronized byte[] encodeSpatialVariant(SpatialVariant sv) throws DecoderException {
		if (!this.useSpatialVariantDecoder) {
			return this.encodeSpatialFixed(sv);
		}

		// set the discriminant
		SpatialVariantStruct.getDiscriminant().setValue(sv.getDiscriminator());

		if (sv.getDiscriminator() == SpatialVariant.NONE) {
			return SpatialVariantStruct.toByteArray();
		}

		HLAfixedRecord SpatialStaticStruct = (HLAfixedRecord) SpatialVariantStruct.getValue();

		// set world location
		double[] worldLocation = sv.getWorldLocation();
		HLAfixedRecord WorldLocationStruct = (HLAfixedRecord) SpatialStaticStruct.get(0);
		((HLAfloat64BE) WorldLocationStruct.get(0)).setValue(worldLocation[SpatialVariant.X]);
		((HLAfloat64BE) WorldLocationStruct.get(1)).setValue(worldLocation[SpatialVariant.Y]);
		((HLAfloat64BE) WorldLocationStruct.get(2)).setValue(worldLocation[SpatialVariant.Z]);

		// set frozen flag
		((HLAoctet) SpatialStaticStruct.get(1)).setValue(sv.isFrozen() ? (byte) 1 : (byte) 0);

		// set orientation
		float[] orientation = sv.getOrientation();
		HLAfixedRecord OrientationStruct = (HLAfixedRecord) SpatialStaticStruct.get(2);
		((HLAfloat32BE) OrientationStruct.get(0)).setValue(orientation[SpatialVariant.PSI]);
		((HLAfloat32BE) OrientationStruct.get(1)).setValue(orientation[SpatialVariant.THETA]);
		((HLAfloat32BE) OrientationStruct.get(2)).setValue(orientation[SpatialVariant.PHI]);

		if (sv.getDiscriminator() == SpatialVariant.STATIC) { // DRM#1
			return SpatialVariantStruct.toByteArray();
		}

		if (sv.getDiscriminator() == SpatialVariant.DRM_FPW
				|| sv.getDiscriminator() == SpatialVariant.DRM_FPB) { // DRM#2, DRM#6

			HLAfixedRecord SpatialFPStruct = (HLAfixedRecord) SpatialVariantStruct.getValue();

			// set velocity
			float[] velocity = sv.getVelocityVector();
			HLAfixedRecord VelocityVectorStruct = (HLAfixedRecord) SpatialFPStruct.get(3);
			((HLAfloat32BE) VelocityVectorStruct.get(0)).setValue(velocity[SpatialVariant.X]);
			((HLAfloat32BE) VelocityVectorStruct.get(1)).setValue(velocity[SpatialVariant.Y]);
			((HLAfloat32BE) VelocityVectorStruct.get(2)).setValue(velocity[SpatialVariant.Z]);

			return SpatialVariantStruct.toByteArray();
		}

		if (sv.getDiscriminator() == SpatialVariant.DRM_RPW
				|| sv.getDiscriminator() == SpatialVariant.DRM_RPB) { // DRM#3, DRM#7

			HLAfixedRecord SpatialRPStruct = (HLAfixedRecord) SpatialVariantStruct.getValue();

			// set velocity
			float[] velocity = sv.getVelocityVector();
			HLAfixedRecord VelocityVectorStruct = (HLAfixedRecord) SpatialRPStruct.get(3);
			((HLAfloat32BE) VelocityVectorStruct.get(0)).setValue(velocity[SpatialVariant.X]);
			((HLAfloat32BE) VelocityVectorStruct.get(1)).setValue(velocity[SpatialVariant.Y]);
			((HLAfloat32BE) VelocityVectorStruct.get(2)).setValue(velocity[SpatialVariant.Z]);

			// set angular velocity
			float[] angularVelocity = sv.getAngularVelocityVector();
			HLAfixedRecord AngularVelocityVector = (HLAfixedRecord) SpatialRPStruct.get(4);
			((HLAfloat32BE) AngularVelocityVector.get(0)).setValue(angularVelocity[SpatialVariant.X]);
			((HLAfloat32BE) AngularVelocityVector.get(1)).setValue(angularVelocity[SpatialVariant.Y]);
			((HLAfloat32BE) AngularVelocityVector.get(2)).setValue(angularVelocity[SpatialVariant.Z]);

			return SpatialVariantStruct.toByteArray();
		}

		if (sv.getDiscriminator() == SpatialVariant.DRM_RVW
				|| sv.getDiscriminator() == SpatialVariant.DRM_RVB) { // DRM#4, DRM#8

			HLAfixedRecord SpatialRVStruct = (HLAfixedRecord) SpatialVariantStruct.getValue();

			// set velocity
			float[] velocity = sv.getVelocityVector();
			HLAfixedRecord VelocityVectorStruct = (HLAfixedRecord) SpatialRVStruct.get(3);
			((HLAfloat32BE) VelocityVectorStruct.get(0)).setValue(velocity[SpatialVariant.X]);
			((HLAfloat32BE) VelocityVectorStruct.get(1)).setValue(velocity[SpatialVariant.Y]);
			((HLAfloat32BE) VelocityVectorStruct.get(2)).setValue(velocity[SpatialVariant.Z]);

			// set acceleration
			float[] accelaration = sv.getAccelerationVector();
			HLAfixedRecord AccelerationVectorStruct = (HLAfixedRecord) SpatialRVStruct.get(4);
			((HLAfloat32BE) AccelerationVectorStruct.get(0)).setValue(accelaration[SpatialVariant.X]);
			((HLAfloat32BE) AccelerationVectorStruct.get(1)).setValue(accelaration[SpatialVariant.Y]);
			((HLAfloat32BE) AccelerationVectorStruct.get(2)).setValue(accelaration[SpatialVariant.Z]);

			// set angular velocity
			float[] angularVelocity = sv.getAngularVelocityVector();
			HLAfixedRecord AngularVelocityVector = (HLAfixedRecord) SpatialRVStruct.get(5);
			((HLAfloat32BE) AngularVelocityVector.get(0)).setValue(angularVelocity[SpatialVariant.X]);
			((HLAfloat32BE) AngularVelocityVector.get(1)).setValue(angularVelocity[SpatialVariant.Y]);
			((HLAfloat32BE) AngularVelocityVector.get(2)).setValue(angularVelocity[SpatialVariant.Z]);

			return SpatialVariantStruct.toByteArray();
		}

		if (sv.getDiscriminator() == SpatialVariant.DRM_FVW
				|| sv.getDiscriminator() == SpatialVariant.DRM_FVB) { // DRM#5, DRM#9

			HLAfixedRecord SpatialFVStruct = (HLAfixedRecord) SpatialVariantStruct.getValue();

			// set velocity
			float[] velocity = sv.getVelocityVector();
			HLAfixedRecord VelocityVectorStruct = (HLAfixedRecord) SpatialFVStruct.get(3);
			((HLAfloat32BE) VelocityVectorStruct.get(0)).setValue(velocity[SpatialVariant.X]);
			((HLAfloat32BE) VelocityVectorStruct.get(1)).setValue(velocity[SpatialVariant.Y]);
			((HLAfloat32BE) VelocityVectorStruct.get(2)).setValue(velocity[SpatialVariant.Z]);

			// set acceleration
			float[] accelaration = sv.getAccelerationVector();
			HLAfixedRecord AccelerationVectorStruct = (HLAfixedRecord) SpatialFVStruct.get(4);
			((HLAfloat32BE) AccelerationVectorStruct.get(0)).setValue(accelaration[SpatialVariant.X]);
			((HLAfloat32BE) AccelerationVectorStruct.get(1)).setValue(accelaration[SpatialVariant.Y]);
			((HLAfloat32BE) AccelerationVectorStruct.get(2)).setValue(accelaration[SpatialVariant.Z]);

			return SpatialVariantStruct.toByteArray();
		}

		// must be something else, make it NONE
		SpatialVariantStruct.getDiscriminant().setValue(SpatialVariant.NONE);
		return SpatialVariantStruct.toByteArray();
	}

	public ForceIdentifier decodeForceIdentifier(byte[] bytes) throws DecoderException {
		HLAoctet forceIdentifier = createForceIdentifierDecoder();
		forceIdentifier.decode(bytes);
		ForceIdentifier forceId = new ForceIdentifier();
		forceId.setForceId(forceIdentifier.getValue());
		return forceId;
	}
	
	
	public byte[] encodeEntityIdentifier( EntityIdentifier ei ) throws DecoderException {
		HLAfixedRecord EntityIdentifierStruct = createEntityIdentifierDecoder();
		((HLAinteger16BE) EntityIdentifierStruct.get(0)).setValue( ei.getSideId() );
		((HLAinteger16BE) EntityIdentifierStruct.get(1)).setValue( ei.getApplicationId() );
		((HLAinteger16BE) EntityIdentifierStruct.get(2)).setValue( ei.getEntityNumber() );
		return EntityIdentifierStruct.toByteArray();
	}
	
	public EntityIdentifier decodeEntityIdentifier( byte[] bytes ) throws DecoderException {
		HLAfixedRecord EntityIdentifierStruct = createEntityIdentifierDecoder();
		EntityIdentifierStruct.decode( bytes );
		HLAinteger16BE sideId = (HLAinteger16BE)EntityIdentifierStruct.get(0);
		HLAinteger16BE applicationId = (HLAinteger16BE)EntityIdentifierStruct.get(1);
		HLAinteger16BE entityNumber = (HLAinteger16BE)EntityIdentifierStruct.get(2);
		EntityIdentifier ei = new EntityIdentifier( sideId.getValue(), applicationId.getValue(), entityNumber.getValue() );
		return ei;
	}
	
	
	public byte[] encodeForceIdentifier(ForceIdentifier forceId) throws DecoderException {
		HLAoctet forceIdentifier = createForceIdentifierDecoder();
		forceIdentifier.setValue(forceId.getForceId());
		return forceIdentifier.toByteArray();
	}
	
	public byte[] encodeIsConcealed( byte isConcealedVal ) throws DecoderException{
		HLAoctet isConcealed = encoderFactory.createHLAoctet();
        isConcealed.setValue( isConcealedVal );
        return isConcealed.toByteArray();
	}

	public byte[] encodeDamageState( byte damageVal ) throws DecoderException{
		HLAoctet damageState = encoderFactory.createHLAoctet();
        damageState.setValue( damageVal );
        return damageState.toByteArray();
	}

	public byte decodeDamageState( byte[] bytes ) throws DecoderException {
		HLAoctet damageState = encoderFactory.createHLAoctet();
		damageState.decode( bytes );
		return damageState.getValue();
	}

	public byte decodeIsConcealed( byte[] bytes ) throws DecoderException {
		HLAoctet isConcealed = encoderFactory.createHLAoctet();
		isConcealed.decode( bytes );
		return isConcealed.getValue();
	}
	
	public Marking decodeMarking(byte[] bytes) throws DecoderException {
		HLAfixedRecord MarkingStruct = createMarkingDecoder();
				
		// decode
		MarkingStruct.decode(bytes);

		Marking m = new Marking("");
		char[] data = new char[Marking.MARKING_LENGTH];

		//byte coding = ((HLAoctet) MarkingStruct.get(0)).getValue();

		for (int i = 0; i < Marking.MARKING_LENGTH; i++) {
			data[i] = (char) ((HLAoctet) MarkingStruct.get(i + 1)).getValue();
		}

		m.setText(data);
		return m;
	}

	public byte[] encodeMarking(Marking m) {
		HLAfixedRecord MarkingStruct = createMarkingDecoder();

		((HLAoctet) MarkingStruct.get(0)).setValue((byte) 0);

		char[] data = m.getText().toCharArray();

		for (int i = 0; i < Marking.MARKING_LENGTH; i++) {
			if (i < data.length) {
				((HLAoctet) MarkingStruct.get(i + 1)).setValue((byte) data[i]);
			} else {
				((HLAoctet) MarkingStruct.get(i + 1)).setValue((byte) 0);
			}
		}

		return MarkingStruct.toByteArray();
	}
}
