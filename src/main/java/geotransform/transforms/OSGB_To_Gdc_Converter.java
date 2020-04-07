//
// Filename: OSGB_To_Gdc_Converter.java
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
 * Class: OSGB_To_Gdc_Converter
 *
 * Description: *//**
 *   Converts OSGB coordinate(s) to GDC.
 *
 *   This class provides the capability to convert from
 *   Gauss Krugger (OSGB) coordinates to
 *   geodetic (GDC) lat/long.
 *   Methods are provided to convert either a single point
 *   or an array of points. This is a direct conversion.
 *
  * @author Jesus Diaz Centeno, Batmap S.A
 *
 * @version $Id: OSGB_To_Gdc_Converter.java,v 1.2 2002/01/24 06:32:12 reddy Exp $
 *
 * @see Gcc_To_Gdc_Converter
 * @see Gcc_To_UTM_Converter
 * @see UTM_To_Gcc_Converter
 * @see UTM_To_Gdc_Converter
 * @see Gdc_To_Gcc_Converter
 * @see Gdc_To_UTM_Converter
 * @see Gdc_To_OSGB_Converter
 */

public class OSGB_To_Gdc_Converter
{
    static final double DEGREES_PER_RADIAN = 57.295779513082320876798154814105;

        static double Origin_lon = -2,
                  Origin_lat = 49,
                  X0=4.0E+5,
                  Y0=-1.0E+5,
                  K0 = 0.9996012717; //Modified this 5 parameters for using as Gauss-Kruger transformation
    static double a,
                  f,
                  b,
                  Eps2,
                  e1,EE2,EE3,
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
        double polx1a,polx2a,polx4a,polx6a,polx8a;

        //  Create the ERM constants.

        a      =A;
        f      =1/F;
        b      =(a) * (1-(f));

        Eps2   =(f) * (2.0-f);
        e1 = (1-Math.sqrt(1-Eps2))/(1+Math.sqrt(1-Eps2));
        EE2    =Eps2*Eps2;
        EE3    =EE2*Eps2;
        Epps2  =(Eps2) / (1.0 - Eps2);

        Origin_lat = Origin_lat / DEGREES_PER_RADIAN;
        Origin_lon = Origin_lon / DEGREES_PER_RADIAN;
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
    * @param OSGB_coord   the input OSGB coordinate
    * @param gdc_coord   the output GDC coordinate
    *
    * @return void
    *
    * @since  1.0
    *
    * @see geotransform.coords.OSGB_Coord_3d
    * @see geotransform.coords.Gdc_Coord_3d
    *
    */

    public static void Convert(OSGB_Coord_3d OSGB_coord, Gdc_Coord_3d gdc_coord)
    {
        OSGB_Coord_3d OSGB[] = new OSGB_Coord_3d[1];
        Gdc_Coord_3d gdc[] = new Gdc_Coord_3d[1];

        OSGB[0] = OSGB_coord;
        gdc[0] = gdc_coord;

        Convert(OSGB,gdc);
    }

   /*
    * Method: Convert
    *
    * Description: *//**
    *   Performs multiple coordinate transforms.
    *
    *   This function will transform any array
    *   of coordinates. The input coordinate array is provided in
    *	the OSGB_Coord_3d[] instance.
    *   The results of the transformation will be put into each element
    *   of the Gdc_Coord_3d[] instance. You should have at least as
    *   many entries in the target array as exist in the source array.
    *
    * @param OSGB   the input array of OSGB coordinates
    * @param gdc   the output array of GDC coordinates
    *
    * @return void
    *
    * @since  1.0
    *
    * @see geotransform.coords.OSGB_Coord_3d
    * @see geotransform.coords.Gdc_Coord_3d
    *
    */

    public static void Convert(final OSGB_Coord_3d[] OSGB, Gdc_Coord_3d[] gdc)
    {
        double source_x, source_y,
               M0,M,mu,phi1Rad,phi1,
               sr,cr,tr,tr2,tr4,
               rn,rm,C1,D;

        for(int i=0;i<gdc.length;i++)
        {

            gdc[i].elevation = OSGB[i].z;

            source_x = OSGB[i].x-X0;
            source_y = OSGB[i].y-Y0;


	    M0 = a*((1	- Eps2/4 - 3*EE2/64	- 5*EE3/256)*Origin_lat
                - (3*Eps2/8	+ 3*EE2/32	+ 45*EE3/1024)*Math.sin(2*Origin_lat)
                + (15*EE2/256 + 45*EE3/1024)*Math.sin(4*Origin_lat)
		- (35*EE3/3072)*Math.sin(6*Origin_lat));

            M = M0 + source_y / K0;
            mu = M/(a*(1-Eps2/4-3*EE2/64-5*EE3/256));

      	    phi1Rad = mu	+ (3*e1/2-27*e1*e1*e1/32)*Math.sin(2*mu)
				+ (21*e1*e1/16-55*e1*e1*e1*e1/32)*Math.sin(4*mu)
				+(151*e1*e1*e1/96)*Math.sin(6*mu);
            phi1 = phi1Rad*DEGREES_PER_RADIAN;

            sr=Math.sin(phi1Rad);
            cr=Math.cos(phi1Rad);
            tr=sr/cr;
            tr2 = tr*tr;
            tr4 =tr2*tr2;

            rn  = a/Math.sqrt(1-Eps2*sr*sr);
	    rm  = a*(1-Eps2)/Math.pow(1-Eps2*sr*sr, 1.5);
            C1  = Epps2*cr*cr;
	    D   = source_x/(rn*K0);

	    gdc[i].latitude = phi1Rad - (rn*tr/rm)*(D*D/2-(5+3*tr2+10*C1-4*C1*C1-9*Epps2)*D*D*D*D/24
					+(61+90*tr2+298*C1+45*tr4-252*Epps2-3*C1*C1)*D*D*D*D*D*D/720);
	    gdc[i].latitude = gdc[i].latitude  * DEGREES_PER_RADIAN;


	    gdc[i].longitude = (D-(1+2*tr2+C1)*D*D*D/6+(5-2*C1+28*tr2-3*C1*C1+8*Epps2+24*tr4)
					*D*D*D*D*D/120)/cr;
	    gdc[i].longitude = (Origin_lon + gdc[i].longitude) * DEGREES_PER_RADIAN;


        } // end for

    } // end Convert

} // end OSGB_To_Gdc_Converter


