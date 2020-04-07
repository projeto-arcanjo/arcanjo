//
// Filename: Gdc_To_Utm_Converter.java
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
 * Class: Gdc_To_Utm_Converter
 *
 * Description: *//**
 *   Converts GDC coordinate(s) to UTM.
 *
 *   This class provides the capability to convert from
 *   geodetic (GDC), i.e. lat/long coordinates to
 *   Universal Transverse Mercator (UTM).
 *   Methods are provided to convert either a single point
 *   or an array of points. This is a direct conversion.
 *
 * @author Dan Toms, SRI International
 *
 * @version $Id: Gdc_To_Utm_Converter.java,v 1.2 2002/01/24 06:32:12 reddy Exp $
 *
 * @see Gcc_To_Gdc_Converter
 * @see Gcc_To_Utm_Converter
 * @see Utm_To_Gcc_Converter
 * @see Utm_To_Gdc_Converter
 * @see Gdc_To_Gcc_Converter
 *
 */

public class Gdc_To_Utm_Converter
{
    static final double RADIANS_PER_DEGREE = 0.0174532925199432957692;

    static double A,
                  F,
                  C,
                  Eps2,
                  Eps25,
                  Epps2,
                  CScale = .9996,
                  poly1b,
                  poly2b,
                  poly3b,
                  poly4b,
                  poly5b;

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

        double polx2b,polx3b,polx4b,polx5b;

        A = a;
        F = f;

        //  Create the ERM constants.

        F      =1/(F);
        C      =(A) * (1-F);
        Eps2   =(F) * (2.0-F);
        Eps25  =.25 * (Eps2);
        Epps2  =(Eps2) / (1.0 - Eps2);
        polx2b = 1.0 * Eps2 + 1.0/4.0 * Math.pow(Eps2,2)
                 + 15.0/128.0 * Math.pow(Eps2,3) - 455.0/4096.0 *
                 Math.pow(Eps2,4);

        polx2b = 3.0/8.0 * polx2b;

        polx3b = 1.0 * Math.pow(Eps2,2) + 3.0/4.0 *
                 Math.pow(Eps2,3) - 77.0/128.0 * Math.pow(Eps2,4);

        polx3b = 15.0 / 256.0 * polx3b;

        polx4b = Math.pow(Eps2,3) - 41.0/32.0 * Math.pow(Eps2,4);

        polx4b = polx4b * 35.0 / 3072.0;

        polx5b =  -315.0/131072.0 * Math.pow(Eps2,4);

        poly1b = 1.0 - (1.0/4.0 * Eps2) - ( 3.0/64.0 * Math.pow(Eps2,2))
                 - (5.0/256.0 * Math.pow(Eps2,3) ) -
                 (175.0/16384.0 * Math.pow(Eps2,4));

        poly2b = polx2b * -2.0 + polx3b * 4.0 - polx4b * 6.0 + polx5b * 8.0;

        poly3b = polx3b * -8.0 + polx4b * 32.0 - polx5b * 80.0;

        poly4b = polx4b * -32.0 + polx5b * 192.0;

        poly5b = polx5b * -128.0;

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
    *   Utm_Coord_3d instance.
    *
    * @param gdc_coord   the input GDC coordinate
    * @param utm_coord   the output UTM coordinate
    *
    * @return void
    *
    * @since  1.0
    *
    * @see geotransform.coords.Gdc_Coord_3d
    * @see geotransform.coords.Utm_Coord_3d
    *
    */

    public static void Convert(Gdc_Coord_3d gdc_coord, Utm_Coord_3d utm_coord)
    {
        Convert(gdc_coord, utm_coord, -1 );
    }

    public static void Convert(Gdc_Coord_3d gdc_coord, Utm_Coord_3d utm_coord,int Huso)
    {
        Gdc_Coord_3d gdc[] = new Gdc_Coord_3d[1];
        Utm_Coord_3d utm[] = new Utm_Coord_3d[1];

        gdc[0] = gdc_coord;
        utm[0] = utm_coord;

        Convert(gdc,utm,Huso);
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
    *   of the Utm_Coord_3d[] instance. You should have at least as
    *   many entries in the target array as exist in the source array.
    *
    * @param gdc   the input array of GDC coordinates
    * @param utm   the output array of UTM coordinates
    *
    * @return void
    *
    * @since  1.0
    *
    * @see geotransform.coords.Gdc_Coord_3d
    * @see geotransform.coords.Utm_Coord_3d
    *
    */

    public static void Convert(final Gdc_Coord_3d[] gdc, Utm_Coord_3d[] utm)
    {
        Convert(gdc, utm, -1);
    }

    public static void Convert(final Gdc_Coord_3d[] gdc, Utm_Coord_3d[] utm, int Huso)
    {
        double source_lat, source_lon, s1,c1,tx,s12,rn,axlon0,al,al2,sm,tn2,cee,poly1,poly2;

        for(int i=0;i<gdc.length;i++)
        {

            utm[i].z = gdc[i].elevation;

            if (gdc[i].latitude < 0)
                utm[i].hemisphere_north=false;
            else
                utm[i].hemisphere_north=true;

	    //if ( gdc[i].longitude < 0.0 )  // XXX - reddy, 11 Sep 98
	    //  gdc[i].longitude += 360.0;

            source_lat = gdc[i].latitude * RADIANS_PER_DEGREE;
            source_lon = gdc[i].longitude * RADIANS_PER_DEGREE;

            s1 = Math.sin(source_lat);
            c1 = Math.cos(source_lat);
            tx = s1 / c1;
            s12 = s1 * s1;

            /* USE IN-LINE SQUARE ROOT */

            rn = A / ( (.25 - Eps25*s12 + .9999944354799/4) + (.25-Eps25*s12)/(.25 - Eps25*s12 + .9999944354799/4));


            /* COMPUTE UTM COORDINATES */

            /* Compute Zone */
            if (Huso==-1 )
            {
              utm[i].zone = (byte) (source_lon * 30.0 / Math.PI + 31);

              if(utm[i].zone <=0)
                  utm[i].zone = 1;
              else
                  if(utm[i].zone >=61)
                      utm[i].zone=60;
            }
            else
            {
              utm[i].zone=(byte)Huso;
            }
            axlon0 = (utm[i].zone * 6 - 183) * RADIANS_PER_DEGREE;

            al = (source_lon - axlon0) * c1;

            sm = s1 * c1 * (poly2b + s12 * (poly3b + s12 *
                   (poly4b + s12 * poly5b)));

            sm = A * (poly1b * source_lat + sm);

            tn2 = tx * tx;
            cee = Epps2 * c1 * c1;
            al2 = al * al;
            poly1 = 1.0 - tn2 + cee;
            poly2 = 5.0 + tn2 * (tn2 - 18.0)  + cee * (14.0 - tn2 * 58.0);

           /* COMPUTE EASTING */

           utm[i].x = CScale * rn * al * (1.0 + al2 *(.166666666666667 * poly1 +.00833333333333333 * al2 * poly2));

           utm[i].x += 5.0E5;

            /* COMPUTE NORTHING */

            poly1 = 5.0 - tn2 + cee * (cee * 4.0 + 9.0);
            poly2 = 61.0 + tn2 * (tn2 - 58.0) + cee * (270.0 - tn2 * 330.0);

            utm[i].y = CScale * (sm + rn * tx * al2 * (0.5 + al2 * (.0416666666666667 * poly1 + .00138888888888888 * al2 * poly2)));

            if (source_lat < 0.0)
                utm[i].y += 1.0E7;
            } // end for


    } // end Convert

} // end


