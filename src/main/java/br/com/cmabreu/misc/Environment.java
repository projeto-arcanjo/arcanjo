package br.com.cmabreu.misc;

import br.com.cmabreu.codec.SpatialVariant;
import geotransform.coords.Gcc_Coord_3d;
import geotransform.coords.Gdc_Coord_3d;
import geotransform.ellipsoids.WE_Ellipsoid;
import geotransform.transforms.Gdc_To_Gcc_Converter;

public class Environment {
	public final static byte LAT = 0;
	public final static byte LON = 1;
	public final static byte ALT = 2;

	// Confere com os dados de http://www.apsalin.com/convert-geodetic-to-cartesian.aspx	
	public double[] getGeocentricLocation(double[] geodeticLocation) {
		Gdc_Coord_3d gdc_coord = new Gdc_Coord_3d(geodeticLocation[LAT], geodeticLocation[LON], geodeticLocation[ALT]);
		Gcc_Coord_3d gcc_coord = new Gcc_Coord_3d();
		Gdc_To_Gcc_Converter.Init( new WE_Ellipsoid() );
		Gdc_To_Gcc_Converter.Convert(gdc_coord, gcc_coord);
		double[] geocentricLocation = new double[3];
		geocentricLocation[ SpatialVariant.X ] = gcc_coord.x;
		geocentricLocation[ SpatialVariant.Y ] = gcc_coord.y;
		geocentricLocation[ SpatialVariant.Z ] = gcc_coord.z;
		return geocentricLocation;
	}
	
	
}
