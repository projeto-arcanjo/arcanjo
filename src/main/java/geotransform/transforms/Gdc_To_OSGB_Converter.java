//
// Filename: Gdc_To_OSGB_Converter.java
//
// Author: Jesus Diaz Centeno, Batmap S.A. <URL:mailto:jesus.diaz@batmap.com>
// <URL:http://www.batmap.com>
// Package: GeoTransform <http://www.ai.sri.com/geotransform/>
//
// Acknowledgements:
//   The algorithms used in the package were created by Jesus Diaz Centeno.
//
// License:
//   The contents of this file are subject to GeoTransform License Agreement
//   (the "License"); you may not use this file except in compliance with
//   the License. You may obtain a copy of the License at
//   http://www.ai.sri.com/geotransform/license.html
//
//   Software distributed under the License is distributed on an "AS IS"
//   basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
//   the License for the specific language governing rights and limitations
//   under the License.
//
//   Portions are Copyright (c) Batmap S.A. 2002.
//

package geotransform.transforms;

import java.lang.*;
import geotransform.ellipsoids.*;
import geotransform.coords.*;

/*
 * Class: Gdc_To_OSGB_Converter
 *
 * Description: *//**
 *   Converts GDC coordinate(s) to OSGB.
 *
 *   This class provides the capability to convert from
 *   geodetic (GDC), i.e. lat/long coordinates to
 *   Gauss Krugger (OSGB).
 *   Methods are provided to convert either a single point
 *   or an array of points. This is a direct conversion.
 *
  * @author Jesus Diaz Centeno, Batmap S.A
 *
 * @version $Id: Gdc_To_OSGB_Converter.java,v 1.2 2002/01/24 06:32:12 reddy Exp $
 *
 * @see Gcc_To_Gdc_Converter
 * @see Gcc_To_UTM_Converter
 * @see UTM_To_Gcc_Converter
 * @see UTM_To_Gdc_Converter
 * @see Gdc_To_Gcc_Converter
 * @see Gdc_To_UTM_Converter
 * @see OSGB_To_Gdc_Converter
 */

public class Gdc_To_OSGB_Converter
{
    static final double RADIANS_PER_DEGREE = 1.74532925199432957692369076848E-2;

    static double Origin_lon_g = -2,
                  Origin_lat_g = 49,
                  X0=4.0E+5,
                  Y0=-1.0E+5,
                  K0 = 0.9996012717; //Modified this 5 parameters for using as Gauss-Kruger transformation
    static double a,
                  f,
                  b,
                  Eps2,
                  EE2,EE3,
                  Epps2;



   /*
    * Method: Init
    *
    * Description: *//**
    *   Initializes the class for a specific ellipsoid.
    *
    *   This method will set up various internal variables
    *   that are used to perform the coordinate conversions.
    *   You need to supply an ellipsoid to initialise these
    *   conversions.
    *   <p>
    *   You MUST call one of the Init methods before calling any of
    *   the Convert methods.
    *
    * @param a   the semi-major axis (meters) for the ellipsoid
    * @param f   the inverse flattening for the ellipsoid
    *
    * @return void
    *
    * @since  1.0
    *
    */

    public static void Init(double a, double f)
    {
        CreateConstants(a,f);
    }

   /*
    * Method: Init
    *
    * Description: *//**
    *   Initializes the class for a specific ellipsoid.
    *
    *   This method will set up various internal variables
    *   that are used to perform the coordinate conversions.
    *   The AIRY ellipsoid will be assumed.
    *   <p>
    *   You MUST call one of the Init methods before calling any of
    *   the Convert methods.
    *
    * @return void
    *
    * @since  1.0
    *
    */

    public static void Init()
    {
        CreateConstants(6377563.396,299.3249646); // default to Airy
    }

   /*
    * Method: Init
    *
    * Description: *//**
    *   Initializes the class for a specific ellipsoid.
    *
    *   This method will set up various internal variables
    *   that are used to perform the coordinate conversions.
    *   You need to supply an ellipsoid to initialise these
    *   conversions.
    *   <p>
    *   You MUST call one of the Init methods before calling any of
    *   the Convert methods.
    *
    * @param E   an Ellipsoid instance for the ellipsoid, e.g. WE_Ellipsoid
    *
    * @return void
    *
    * @since  1.0
    *
    * @see geotransform.ellipsoids.Ellipsoid
    *
    */

    public static void Init(Ellipsoid E)
    {
        CreateConstants(E.a,E.f);
    }

    protected static void CreateConstants(double A, double F)
    {


        //  Create the ERM constants.
        a      =A;
        f      =1/F;
        b      =(a) * (1-(f));

        Eps2   =(f) * (2.0-f);
        EE2    =Eps2*Eps2;
        EE3    =EE2*Eps2;
        Epps2  =(Eps2) / (1.0 - Eps2);


    } // end init

   /*
    * Method: Convert
    *
    * Description: *//**
    *   Performs a single coordinate transform.
    *
    *   This function will transform a single coordinate. The
    *   input coordinate is provided in the Gdc_Coord_3d instance.
    *   The result of the transformation will be put in the
    *   OSGB_Coord_3d instance.
    *
    * @param gdc_coord   the input GDC coordinate
    * @param OSGB_coord   the output OSGB coordinate
    *
    * @return void
    *
    * @since  1.0
    *
    * @see geotransform.coords.Gdc_Coord_3d
    * @see geotransform.coords.OSGB_Coord_3d
    *
    */

    public static void Convert(Gdc_Coord_3d gdc_coord, OSGB_Coord_3d OSGB_coord)
    {
        Gdc_Coord_3d gdc[] = new Gdc_Coord_3d[1];
        OSGB_Coord_3d OSGB[] = new OSGB_Coord_3d[1];

        gdc[0] = gdc_coord;
        OSGB[0] = OSGB_coord;

        Convert(gdc,OSGB);
    }

   /*
    * Method: Convert
    *
    * Description: *//**
    *   Performs multiple coordinate transforms.
    *
    *   This function will transform any array
    *   of coordinates. The
    *   input coordinate array is provided in the Gdc_Coord_3d[] instance.
    *   The results of the transformation will be put into each element
    *   of the OSGB_Coord_3d[] instance. You should have at least as
    *   many entries in the target array as exist in the source array.
    *
    * @param gdc   the input array of GDC coordinates
    * @param OSGB   the output array of OSGB coordinates
    *
    * @return void
    *
    * @since  1.0
    *
    * @see geotransform.coords.Gdc_Coord_3d
    * @see geotransform.coords.OSGB_Coord_3d
    *
    */

    public static void Convert(final Gdc_Coord_3d[] gdc, OSGB_Coord_3d[] OSGB)
    {
        double Origin_lat,Origin_lon,source_lat, source_lon, sl,cl,tl,sl2,cl2,tl2,tl4,rn,
                ilat,ilon,
                M0,N,C,A,M;

        for(int i=0;i<gdc.length;i++)
        {

            OSGB[i].z = gdc[i].elevation;

            if (gdc[i].latitude < 0)
                OSGB[i].hemisphere_north=false;
            else
                OSGB[i].hemisphere_north=true;


            Origin_lat = Origin_lat_g * RADIANS_PER_DEGREE;
            Origin_lon = Origin_lon_g * RADIANS_PER_DEGREE;

            source_lat = gdc[i].latitude * RADIANS_PER_DEGREE;
            source_lon = gdc[i].longitude * RADIANS_PER_DEGREE;

            sl = Math.sin(source_lat);
            cl = Math.cos(source_lat);
            sl2 = sl * sl;
            cl2=cl*cl;
            tl = sl / cl;
            tl2=tl*tl;
            tl4=tl2*tl2;

            ilat= (source_lat-Origin_lat);
            ilon= (source_lon-Origin_lon);

            /* COMPUTE OSGB COORDINATES */

	    M0 = a*((1	- Eps2/4 - 3*EE2/64	- 5*EE3/256)*Origin_lat
                - (3*Eps2/8	+ 3*EE2/32	+ 45*EE3/1024)*Math.sin(2*Origin_lat)
                + (15*EE2/256 + 45*EE3/1024)*Math.sin(4*Origin_lat)
		- (35*EE3/3072)*Math.sin(6*Origin_lat));

            rn = a/Math.sqrt(1-Eps2*sl2);
	    C = Epps2*cl2;
	    A = cl*ilon;

	    M = a*((1	- Eps2/4    - 3*EE2/64	- 5*EE3/256)*source_lat
                - (3*Eps2/8	+ 3*EE2/32	+ 45*EE3/1024)*Math.sin(2*source_lat)
	        + (15*EE2/256 + 45*EE3/1024)*Math.sin(4*source_lat)
		- (35*EE3/3072)*Math.sin(6*source_lat));



           /* COMPUTE EASTING */

           OSGB[i].x = X0+(K0*rn*(A+(1-tl2+C)*A*A*A/6+ (5-18*tl2+tl2*tl2+72*C-58*Epps2)*A*A*A*A*A/120));

            /* COMPUTE NORTHING */

            OSGB[i].y=Y0+(K0*(M-M0+rn*tl*(A*A/2+(5-tl2+9*C+4*C*C)*A*A*A*A/24
				 + (61-58*tl2+tl4+600*C-330*Epps2)*A*A*A*A*A*A/720)));

            } // end for


    } // end Convert

} // end