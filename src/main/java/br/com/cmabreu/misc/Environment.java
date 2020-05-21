package br.com.cmabreu.misc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Environment {
	private Logger logger = LoggerFactory.getLogger( Environment.class );

	public final static byte LAT = 0;
	public final static byte LON = 1;
	public final static byte ALT = 2;

	// Confere com os dados de http://www.apsalin.com/convert-geodetic-to-cartesian.aspx
	// http://www.ai.sri.com/~reddy/geovrml/release/geovrml/geotransform/examples/test_gdc_gcc.java
	public double[] getGeocentricLocation(double[] geodeticLocation) {
		
		/*
		Gdc_To_Gcc_Converter.Init( new WE_Ellipsoid() );

		double theLat = geodeticLocation[LAT];
		double theLon = geodeticLocation[LON];
		double theAlt = geodeticLocation[ALT];
		
		Gcc_Coord_3d gcc_coord = new Gcc_Coord_3d();
		Gdc_Coord_3d gdc_coord = new Gdc_Coord_3d( theLat, theLon, theAlt);
		
		Gdc_To_Gcc_Converter.Convert(gdc_coord, gcc_coord);
		
		double[] geocentricLocation = new double[3];
		geocentricLocation[ SpatialVariant.X ] = gcc_coord.x;
		geocentricLocation[ SpatialVariant.Y ] = gcc_coord.y;
		geocentricLocation[ SpatialVariant.Z ] = gcc_coord.z;
		
		logger.info("Converti (" + theLat + "," + theLon + "," + theAlt + " ) para ( " + gcc_coord.x + "," + gcc_coord.y + "," + gcc_coord.z + " )" );
		
		return geocentricLocation;
		*/
		
		return geodeticLocation;
	}
	

	// http://www.ai.sri.com/~reddy/geovrml/release/geovrml/geotransform/examples/test_gcc_gdc.java
	public double[] getGeodesicLocation( double[] geocentricLocation ) {
		/*
		Gcc_To_Gdc_Converter.Init(new WE_Ellipsoid());

		double theX = geocentricLocation[ SpatialVariant.X ]; 
		double theY = geocentricLocation[ SpatialVariant.Y ]; 
		double theZ = geocentricLocation[ SpatialVariant.Z ]; 
		
		Gdc_Coord_3d gdc_coord = new Gdc_Coord_3d();
		Gcc_Coord_3d gcc_coord = new Gcc_Coord_3d( theX, theY, theZ ); 
		Gcc_To_Gdc_Converter.Convert( gcc_coord, gdc_coord );
		
		double[] geodeticLocation = new double[3];
		geodeticLocation[ LAT ] = gdc_coord.latitude; 
		geodeticLocation[ LON ] = gdc_coord.longitude; 
		geodeticLocation[ ALT ] = gdc_coord.elevation;
		
		logger.info("Converti (" + theX + "," + theY + "," + theZ + " ) para ( " + gdc_coord.latitude + "," + gdc_coord.longitude + "," + gdc_coord.elevation + " )" );
		
		return geodeticLocation;
		*/
		return geocentricLocation;
	}
	
}
