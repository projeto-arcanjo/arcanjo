//
// Filename: Gdc_To_Gcc_Converter.java
//
// Author: Dan Toms, SRI International
//
// Package: GeoTransform <http://www.ai.sri.com/geotransform/>
//
// Acknowledgements:
//   The algorithms used in the package were created by Ralph Toms and
//   first appeared as part of the SEDRIS Coordinate Transformation API.
//   These were subsequently modified for this package. This package is
//   not part of the SEDRIS project, and the Java code written for this
//   package has not been certified or tested for correctness by NIMA.
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
//   Portions are Copyright (c) SRI International, 1998.
//

package geotransform.transforms;

import java.lang.*;
import geotransform.ellipsoids.*;
import geotransform.coords.*;

/*
 * Class: Gdc_To_Gcc_Converter
 *
 * Description: *//**
 *   Converts GDC coordinate(s) to GCC.
 *
 *   This class provides the capability to convert from
 *   geodetic (GDC), i.e. lat/long coordinates to
 *   geocentric (GCC).
 *   Methods are provided to convert either a single point
 *   or an array of points. This is a direct conversion.
 *
 * @author Dan Toms, SRI International
 *
 * @version $Id: Gdc_To_Gcc_Converter.java,v 1.2 2002/01/24 06:32:11 reddy Exp $
 *
 * @see Gcc_To_Gdc_Converter
 * @see Gcc_To_Utm_Converter
 * @see Utm_To_Gcc_Converter
 * @see Utm_To_Gdc_Converter
 * @see Gdc_To_Utm_Converter
 *
 */

public class Gdc_To_Gcc_Converter
{
    static final double RADIANS_PER_DEGREE = 0.0174532925199432957692;

    static double A,
                  F,
                  A2,
                  C,
                  C2,
                  Eps2,
                  Eps25;

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
    *   The WGS 84 ellipsoid will be assumed.
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
        CreateConstants(6378137,298.257223563); // default to wgs 84
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

    protected static void CreateConstants(double a, double f)
    {

        A = a;
        F = f;
        A2     = A * A;
        F      =1/(F);
        C      =(A) * (1-F);
        C2     = C * C;
        Eps2   =(F) * (2.0-F);
        Eps25  =.25 * (Eps2);
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
    *   Gcc_Coord_3d instance.
    *
    * @param gdc_coord   the input GDC coordinate
    * @param gcc_coord   the output GCC coordinate
    *
    * @return void
    *
    * @since  1.0
    *
    * @see geotransform.coords.Gdc_Coord_3d
    * @see geotransform.coords.Gcc_Coord_3d
    *
    */

    public static void Convert(Gdc_Coord_3d gdc_coord, Gcc_Coord_3d gcc_coord)
    {
        Gdc_Coord_3d gdc[] = new Gdc_Coord_3d[1];
        Gcc_Coord_3d gcc[] = new Gcc_Coord_3d[1];

        gdc[0] = gdc_coord;
        gcc[0] = gcc_coord;

        Convert(gdc,gcc);
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
    *   of the Gcc_Coord_3d[] instance. You should have at least as
    *   many entries in the target array as exist in the source array.
    *
    * @param gdc   the input array of GDC coordinates
    * @param gcc   the output array of GCC coordinates
    *
    * @return void
    *
    * @since  1.0
    *
    * @see geotransform.coords.Gdc_Coord_3d
    * @see geotransform.coords.Gcc_Coord_3d
    *
    */


    public static void Convert(final Gdc_Coord_3d[] gdc,Gcc_Coord_3d[] gcc)
    {

        double source_lat, source_lon, slat, slat2, clat, Rn, RnPh;

        for (int i = 0; i<gdc.length;i++)
        {
	  //if ( gdc[i].longitude < 0.0 )  // XXX - reddy, 11 Sep 98
	  //    gdc[i].longitude += 360.0;

            source_lat = RADIANS_PER_DEGREE * gdc[i].latitude;
            source_lon = RADIANS_PER_DEGREE * gdc[i].longitude;

            slat=Math.sin(source_lat);
            slat2 = slat * slat;

            clat=Math.cos(source_lat);
            // square root approximation for Rn
            Rn = A / ( (.25 - Eps25 * slat2 + .9999944354799/4) +
                (.25-Eps25 * slat2)/(.25 - Eps25 * slat2 + .9999944354799/4));

            RnPh= Rn + gdc[i].elevation;

            gcc[i].x = RnPh * clat * Math.cos(source_lon);
            gcc[i].y = RnPh * clat * Math.sin(source_lon);
            gcc[i].z = ((C2 / A2) * Rn + gdc[i].elevation) * slat;

        } // end for

    } // end Convert

} // end Gdc_To_Utm_Converter


