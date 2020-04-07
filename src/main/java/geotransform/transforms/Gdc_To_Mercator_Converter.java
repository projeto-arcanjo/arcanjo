//
// Filename: Gdc_To_Mercator_Converter.java
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
 * Class: Gdc_To_Mercator_Converter
 *
 * Description: *//**
 *   Converts GDC coordinate(s) to Mercator.
 *
 *   This class provides the capability to convert from
 *   geodetic (GDC), i.e. lat/long coordinates to
 *   Mercator.
 *   Methods are provided to convert either a single point
 *   or an array of points. This is a direct conversion.
 *
  * @author Jesus Diaz Centeno, Batmap S.A
 *
 * @version $Id: Gdc_To_Mercator_Converter.java,v 1.1 2002/01/24 06:32:12 reddy Exp $
 *
 * @see Gcc_To_Gdc_Converter
 * @see Gcc_To_UTM_Converter
 * @see UTM_To_Gcc_Converter
 * @see UTM_To_Gdc_Converter
 * @see Gdc_To_Gcc_Converter
 * @see Gdc_To_UTM_Converter
 * @see Mercator_To_Gdc_Converter
 */

public class Gdc_To_Mercator_Converter
{
    static final double RADIANS_PER_DEGREE = 1.74532925199432957692369076848E-2;
    static final double NUM_e = 2.3025850929940456840179914546844;
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
    *   Mercator_Coord_3d instance.
    *
    * @param gdc_coord   the input GDC coordinate
    * @param Mercator_coord   the output Mercator coordinate
    *
    * @return void
    *
    * @since  1.0
    *
    * @see geotransform.coords.Gdc_Coord_3d
    * @see geotransform.coords.Mercator_Coord_3d
    *
    */

    public static void Convert(Gdc_Coord_3d gdc_coord, Mercator_Coord_3d Mercator_coord)
    {
        Gdc_Coord_3d gdc[] = new Gdc_Coord_3d[1];
        Mercator_Coord_3d Mercator[] = new Mercator_Coord_3d[1];

        gdc[0] = gdc_coord;
        Mercator[0] = Mercator_coord;

        Convert(gdc,Mercator);
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
    *   of the Mercator_Coord_3d[] instance. You should have at least as
    *   many entries in the target array as exist in the source array.
    *
    * @param gdc   the input array of GDC coordinates
    * @param Mercator   the output array of Mercator coordinates
    *
    * @return void
    *
    * @since  1.0
    *
    * @see geotransform.coords.Gdc_Coord_3d
    * @see geotransform.coords.Mercator_Coord_3d
    *
    */

    public static void Convert(final Gdc_Coord_3d[] gdc, Mercator_Coord_3d[] Mercator)
    {
        double source_lat, source_lon, sl,cl,tl,x;

        for(int i=0;i<gdc.length;i++)
        {

            Mercator[i].z = gdc[i].elevation;

            if (gdc[i].latitude < 0)
                Mercator[i].hemisphere_north=false;
            else
                Mercator[i].hemisphere_north=true;

	    //if ( gdc[i].longitude < 0.0 )  // XXX - reddy, 11 Sep 98
	    //  gdc[i].longitude += 360.0;


            source_lat = gdc[i].latitude * RADIANS_PER_DEGREE;
            source_lon = gdc[i].longitude * RADIANS_PER_DEGREE;

            sl = Math.sin(source_lat);
            cl = Math.cos(source_lat);

            tl =(1+sl)/(1-sl);// Math.tan((Math.PI/4)+(source_lat/2));



            /* COMPUTE Mercator COORDINATES */


           /* COMPUTE EASTING */

           Mercator[i].x = a * source_lon;

            /* COMPUTE NORTHING */
            x=Math.pow(((1-Math.sqrt(Eps2)*sl)/(1+Math.sqrt(Eps2)*sl)),(Math.sqrt(Eps2)));
            //x=1;
            Mercator[i].y= a/2 *(Math.log(tl*x));
            } // end for


    } // end Convert

} // end