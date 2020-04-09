package br.com.cmabreu.misc;

import br.com.cmabreu.codec.SpatialVariant;
import geotransform.coords.Gcc_Coord_3d;
import geotransform.coords.Gdc_Coord_3d;
import geotransform.ellipsoids.WE_Ellipsoid;
import geotransform.transforms.Gcc_To_Gdc_Converter;
import geotransform.transforms.Gdc_To_Gcc_Converter;

public class Environment {
	public final static byte LAT = 0;
	public final static byte LON = 1;
	public final static byte ALT = 2;

	// Confere com os dados de http://www.apsalin.com/convert-geodetic-to-cartesian.aspx
	// http://www.ai.sri.com/~reddy/geovrml/release/geovrml/geotransform/examples/test_gdc_gcc.java
	public double[] getGeocentricLocation(double[] geodeticLocation) {
		Gdc_To_Gcc_Converter.Init( new WE_Ellipsoid() );

		Gcc_Coord_3d gcc_coord = new Gcc_Coord_3d();
		Gdc_Coord_3d gdc_coord = new Gdc_Coord_3d(geodeticLocation[LAT], geodeticLocation[LON], geodeticLocation[ALT]);
		
		Gdc_To_Gcc_Converter.Convert(gdc_coord, gcc_coord);
		
		double[] geocentricLocation = new double[3];
		geocentricLocation[ SpatialVariant.X ] = gcc_coord.x;
		geocentricLocation[ SpatialVariant.Y ] = gcc_coord.y;
		geocentricLocation[ SpatialVariant.Z ] = gcc_coord.z;
		return geocentricLocation;
	}
	

	// http://www.ai.sri.com/~reddy/geovrml/release/geovrml/geotransform/examples/test_gcc_gdc.java
	public double[] getGeodesicLocation( double[] geocentricLocation ) {
		Gcc_To_Gdc_Converter.Init(new WE_Ellipsoid());

		Gdc_Coord_3d gdc_coord = new Gdc_Coord_3d();
		Gcc_Coord_3d gcc_coord = new Gcc_Coord_3d( 
				geocentricLocation[ SpatialVariant.X ], 
				geocentricLocation[ SpatialVariant.Y ], 
				geocentricLocation[ SpatialVariant.Z ] );

		Gcc_To_Gdc_Converter.Convert( gcc_coord, gdc_coord );
		
		double[] geodeticLocation = new double[3];
		geodeticLocation[ LAT ] = gdc_coord.latitude; 
		geodeticLocation[ LON ] = gdc_coord.longitude; 
		geodeticLocation[ ALT ] = gdc_coord.elevation; 
		return geodeticLocation;
	}
	
}
