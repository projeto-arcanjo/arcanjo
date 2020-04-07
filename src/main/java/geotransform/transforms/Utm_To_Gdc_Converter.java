//
// Filename: Utm_To_Gdc_Converter.java
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
 * Class: Utm_To_Gdc_Converter
 *
 * Description: *//**
 *   Converts UTM coordinate(s) to GDC.
 *   <p>
 *   This class provides the capability to convert from
 *   Universal Transverse Mercator (UTM) coordinates to
 *   geodetic (GDC), i.e. lat/long.
 *   Methods are provided to convert either a single point
 *   or an array of points. This is a direct conversion.
 *
 * @author Dan Toms, SRI International
 *
 * @version $Id: Utm_To_Gdc_Converter.java,v 1.2 2002/01/24 06:32:12 reddy Exp $
 *
 * @see Gcc_To_Gdc_Converter
 * @see Gcc_To_Utm_Converter
 * @see Utm_To_Gcc_Converter
 * @see Gdc_To_Gcc_Converter
 * @see Gdc_To_Utm_Converter
 *
 */

public class Utm_To_Gdc_Converter
{
    static final double DEGREES_PER_RADIAN = 57.2957795130823208768;

    static double A,
                  F,
                  C,
                  Eps2,
                  Eps21,
                  Eps25,
                  Con,
                  Con2,
                  EF,
                  Epsp2,
                  Con6,
                  Con24,
                  Con120,
                  Con720,
                  polx2b,
                  polx3b,
                  polx4b,
                  polx5b,
                  conap;

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
        double polx1a,polx2a,polx4a,polx6a,polx8a;

        A = a;
        F = f;

        //  Create the ERM constants.

        F      = 1/(F);
        C      = (A) * (1-F);
        Eps2   = (F) * (2.0-F);
        Eps25  = .25 * (Eps2);
        EF     = F/(2.0-F);
        Con    = (1.0-Eps2);
        Con2   = 2 / (1.0-Eps2);
        Con6   = .166666666666667;
        Con24  = 4 * .0416666666666667/(1-Eps2);
        Con120 = .00833333333333333;
        Con720 = 4 * .00138888888888888/(1-Eps2);
        polx1a = 1.0 - Eps2 / 4.0 - 3.0/64.0 *
                          Math.pow(Eps2,2) - 5.0/256.0 * Math.pow(Eps2,3)
                          - 175.0/16384.0 * Math.pow(Eps2,4);

        conap  = A * polx1a;

        polx2a = 3.0/2.0 * EF - 27.0/32.0 * Math.pow(EF,3);

        polx4a = 21.0/16.0 * Math.pow(EF,2) - 55.0/32.0 * Math.pow(EF,4);

        polx6a = 151.0/96.0 * Math.pow(EF,3);

        polx8a = 1097.0/512.0 * Math.pow(EF,4);

        polx2b = polx2a * 2.0 + polx4a * 4.0 + polx6a * 6.0 + polx8a * 8.0;

        polx3b = polx4a * -8.0 - polx6a * 32.0- 80.0 *polx8a;
        polx4b = polx6a * 32.0 + 192.0*polx8a;
        polx5b = -128.0 *polx8a;


    } // end init

   /*
    * Method: Convert
    *
    * Description: *//**
    *   Performs a single coordinate transform.
    *
    *   This function will transform a single coordinate. The
    *   input coordinate is provided in the Utm_Coord_3d instance.
    *   The result of the transformation will be put in the
    *   Gdc_Coord_3d instance.
    *
    * @param utm_coord   the input UTM coordinate
    * @param gdc_coord   the output GDC coordinate
    *
    * @return void
    *
    * @since  1.0
    *
    * @see geotransform.coords.Utm_Coord_3d
    * @see geotransform.coords.Gdc_Coord_3d
    *
    */

    public static void Convert(Utm_Coord_3d utm_coord, Gdc_Coord_3d gdc_coord)
    {
        Utm_Coord_3d utm[] = new Utm_Coord_3d[1];
        Gdc_Coord_3d gdc[] = new Gdc_Coord_3d[1];

        utm[0] = utm_coord;
        gdc[0] = gdc_coord;

        Convert(utm,gdc);
    }

   /*
    * Method: Convert
    *
    * Description: *//**
    *   Performs multiple coordinate transforms.
    *
    *   This function will transform any array
    *   of coordinates. The
    *   input coordinate array is provided in the Utm_Coord_3d[] instance.
    *   The results of the transformation will be put into each element
    *   of the Gdc_Coord_3d[] instance. You should have at least as
    *   many entries in the target array as exist in the source array.
    *
    * @param utm   the input array of UTM coordinates
    * @param gdc   the output array of GDC coordinates
    *
    * @return void
    *
    * @since  1.0
    *
    * @see geotransform.coords.Utm_Coord_3d
    * @see geotransform.coords.Gdc_Coord_3d
    *
    */

    public static void Convert(final Utm_Coord_3d[] utm, Gdc_Coord_3d[] gdc)
    {
        double source_x, source_y,u,su,cu,su2,xlon0,temp,phi1,sp,sp2,cp,cp2,tp,tp2,eta2,top,rn,b3,b4,b5,b6,d1,d2;

        for(int i=0;i<gdc.length;i++)
        {

            gdc[i].elevation = utm[i].z;

            source_x = utm[i].x;

            source_x = (source_x - 500000.0)/.9996;

            if (utm[i].hemisphere_north==true)
                source_y = utm[i].y / .9996;
            else
                source_y = (utm[i].y - 1.0E7)/.9996;

            u = source_y / conap;

            /* TEST U TO SEE IF AT POLES */

            su = Math.sin(u);
            cu = Math.cos(u);
            su2 = su * su;

            /* THE SNYDER FORMULA FOR PHI1 IS OF THE FORM
             PHI1=U+POLY2A*SIN(2U)+POLY3A*SIN(4U)+POLY4ASIN(6U)+...
             BY USING MULTIPLE ANGLE TRIGONOMETRIC IDENTITIES AND APPROPRIATE FACTORING
            JUST THE SINE AND COSINE ARE REQUIRED
             NOW READY TO GET PHI1
             */

            xlon0= ( 6.0 * ((double) utm[i].zone) - 183.0) / DEGREES_PER_RADIAN;

            temp = polx2b + su2 * (polx3b + su2 * (polx4b + su2 * polx5b));

            phi1 = u + su * cu * temp;

             /* COMPUTE VARIABLE COEFFICIENTS FOR FINAL RESULT
                COMPUTE THE VARIABLE COEFFICIENTS OF THE LAT AND LON
                EXPANSIONS */

            sp = Math.sin(phi1);
            cp = Math.cos(phi1);
            tp = sp / cp;
            tp2 = tp * tp;
            sp2 = sp * sp;
            cp2 = cp * cp;
            eta2 = Epsp2 * cp2;

            top = .25-(sp2*(Eps2 / 4));

             /* inline sq root*/

            rn = A / ( (.25 - Eps25 * sp2 + .9999944354799/4) +
                (.25-Eps25 * sp2)/(.25 - Eps25 * sp2 + .9999944354799/4));

            b3 = 1.0 + tp2 + tp2 + eta2;

            b4 = 5 + tp2 * (3 - 9 * eta2) + eta2 * ( 1 - 4 * eta2);

            b5 = 5 + tp2 * (tp2 * 24.0 + 28.0);

            b5 += eta2 * (tp2 * 8.0 + 6.0);

            b6 = 46.0 - 3.0 * eta2 + tp2 * (-252.0 - tp2 * 90.0);

            b6 = eta2 * (b6 + eta2 * tp2 * (tp2 * 225.0 - 66.0));
            b6 += 61.0 + tp2 * (tp2 * 45.0 + 90.0);

            d1 = source_x / rn;
            d2 = d1 * d1;

            gdc[i].latitude = phi1 - tp * top * (d2 * (Con2 + d2 * ((-Con24) * b4 + d2 *
                        Con720 * b6)));

            gdc[i].longitude = xlon0 + d1 * (1.0 + d2 * (-Con6 * b3 + d2 * Con120 * b5)) / cp;

             /* TRANSVERSE MERCATOR COMPUTATIONS DONE */

            gdc[i].latitude *= DEGREES_PER_RADIAN;

            gdc[i].longitude *= DEGREES_PER_RADIAN;

        } // end for

    } // end Convert

} // end Utm_To_Gdc_Converter


