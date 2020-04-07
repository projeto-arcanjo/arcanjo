//
// Filename: Gdc_To_Lambert_Converter.java
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
 * Class: Gdc_To_Lambert_Converter
 *
 * Description: *//**7
 *   Converts GDC coordinate(s) to Lambert.
 *
 *   This class provides the capability to convert from
 *   geodetic (GDC), i.e. lat/long coordinates to
 *   Lambert.
 *   Methods are provided to convert either a single point
 *   or an array of points. This is a direct conversion.
 *
  * @author Jesus Diaz Centeno, Batmap S.A
 *
 * @version $Id: Gdc_To_Lambert_Converter.java,v 1.1 2002/01/24 06:32:11 reddy Exp $
 *
 * @see Gcc_To_Gdc_Converter
 * @see Gcc_To_UTM_Converter
 * @see UTM_To_Gcc_Converter
 * @see UTM_To_Gdc_Converter
 * @see Gdc_To_Gcc_Converter
 * @see Gdc_To_UTM_Converter
 * @see Lambert_To_Gdc_Converter
 */

public class Gdc_To_Lambert_Converter
{
    static final double RADIANS_PER_DEGREE = 1.74532925199432957692369076848E-2;
    static final double NUM_e = 2.3025850929940456840179914546844;
    static double Origin_lon_g = 4.35693972222222222, //Modified this 6 parameters for using as Lambert Conic Conformal
                  Origin_lat_g = 90,                  // with two standart paralles
                  X0=150000.01,
                  Y0=5400088.44,
                  SP1_lat_g = 49.83333333333333333333,
                  SP2_lat_g = 51.16666666666666666666; //This example: Belge Lambert 1972
    static double a,
                  f,
                  b,
                  Eps2,
                  EE2,EE3,
                  Epps2,
                  sl1,sl2,
                  Origin_lon,Origin_lat,
                  SP1_lat,SP2_lat,
                  m1,m2,t1,t2,
                  slF,tF,
                  n,FF,RF;




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
        CreateConstants(6378388,297); // default to International 1924 for
                                      //this example: Belge Lambert 1972
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

        Origin_lon = Origin_lon_g * RADIANS_PER_DEGREE;
        Origin_lat = Origin_lat_g * RADIANS_PER_DEGREE;
        SP1_lat    = SP1_lat_g * RADIANS_PER_DEGREE;
        SP2_lat    = SP2_lat_g * RADIANS_PER_DEGREE;

        sl1    = Math.sin(SP1_lat);
        m1     = (Math.cos (SP1_lat))/Math.sqrt((1-Eps2*(sl1*sl1)));
        sl2    = Math.sin(SP2_lat);
        m2     = (Math.cos (SP2_lat))/Math.sqrt((1-Eps2*(sl2*sl2)));
        t1     = Math.tan((Math.PI/4)-(SP1_lat/2))/Math.pow(((1-Math.sqrt(Eps2)*sl1)/(1+Math.sqrt(Eps2)*sl1)),(Math.sqrt(Eps2)/2));
        t2     = Math.tan((Math.PI/4)-(SP2_lat/2))/Math.pow(((1-Math.sqrt(Eps2)*sl2)/(1+Math.sqrt(Eps2)*sl2)),(Math.sqrt(Eps2)/2));

        slF    = Math.sin(Origin_lat);
        tF     = Math.tan((Math.PI/4)-(Origin_lat/2))/Math.pow(((1-Math.sqrt(Eps2)*slF)/(1+Math.sqrt(Eps2)*slF)),(Math.sqrt(Eps2)/2));

        n      = (Math.log(m1)-Math.log(m2))/(Math.log(t1)-Math.log(t2));
        FF      = m1/(n*Math.pow(t1,n));
        RF     = a*FF*Math.pow(tF,n);




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
    *   Lambert_Coord_3d instance.
    *
    * @param gdc_coord   the input GDC coordinate
    * @param Lambert_coord   the output Lambert coordinate
    *
    * @return void
    *
    * @since  1.0
    *
    * @see geotransform.coords.Gdc_Coord_3d
    * @see geotransform.coords.Lambert_Coord_3d
    *
    */

    public static void Convert(Gdc_Coord_3d gdc_coord, Lambert_Coord_3d Lambert_coord)
    {
        Gdc_Coord_3d gdc[] = new Gdc_Coord_3d[1];
        Lambert_Coord_3d Lambert[] = new Lambert_Coord_3d[1];

        gdc[0] = gdc_coord;
        Lambert[0] = Lambert_coord;

        Convert(gdc,Lambert);
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
    *   of the Lambert_Coord_3d[] instance. You should have at least as
    *   many entries in the target array as exist in the source array.
    *
    * @param gdc   the input array of GDC coordinates
    * @param Lambert   the output array of Lambert coordinates
    *
    * @return void
    *
    * @since  1.0
    *
    * @see geotransform.coords.Gdc_Coord_3d
    * @see geotransform.coords.Lambert_Coord_3d
    *
    */

    public static void Convert(final Gdc_Coord_3d[] gdc, Lambert_Coord_3d[] Lambert)
    {
        double source_lat, source_lon, sl,m,t,R,A0,x;

        for(int i=0;i<gdc.length;i++)
        {

            Lambert[i].z = gdc[i].elevation;

            if (gdc[i].latitude < 0)
                Lambert[i].hemisphere_north=false;
            else
                Lambert[i].hemisphere_north=true;

	    //if ( gdc[i].longitude < 0.0 )  // XXX - reddy, 11 Sep 98
	    //  gdc[i].longitude += 360.0;


            source_lat = gdc[i].latitude * RADIANS_PER_DEGREE;
            source_lon = gdc[i].longitude * RADIANS_PER_DEGREE;


            sl    = Math.sin(source_lat);
            t     = Math.tan((Math.PI/4)-(source_lat/2))/Math.pow(((1-Math.sqrt(Eps2)*sl)/(1+Math.sqrt(Eps2)*sl)),(Math.sqrt(Eps2)/2));


			R=a*FF*Math.pow(t,n);
			A0=n*(source_lon-Origin_lon);


            /* COMPUTE Lambert COORDINATES */


           /* COMPUTE EASTING */

           Lambert[i].x = X0+R*Math.sin(A0);

            /* COMPUTE NORTHING */

            Lambert[i].y= Y0+RF-(R*Math.cos(A0));
            } // end for


    } // end Convert

} // end