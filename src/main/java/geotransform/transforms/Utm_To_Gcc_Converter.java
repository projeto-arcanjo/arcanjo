//
// Filename: Utm_To_Gcc_Converter.java
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
 * Class: Utm_To_Gcc_Converter
 * 
 * Description: *//**
 *   Converts UTM coordinate(s) to GCC.
 *  
 *   This class provides the capability to convert from
 *   Universal Transverse Mercator (UTM) coordinates to 
 *   geocentric (GCC).
 *   Methods are provided to convert either a single point
 *   or an array of points. This is an indirect conversion,
 *   UTM -> GDC -> GCC.
 *
 * @author Dan Toms, SRI International
 *
 * @version $Id: Utm_To_Gcc_Converter.java,v 1.2 2002/01/24 06:32:12 reddy Exp $
 *
 * @see Gcc_To_Gdc_Converter
 * @see Gcc_To_Utm_Converter
 * @see Utm_To_Gcc_Converter
 * @see Gdc_To_Gcc_Converter
 * @see Gdc_To_Utm_Converter
 *
 */

public class Utm_To_Gcc_Converter
{
    protected static Ellipsoid e;
    protected static double A;
    protected static double F;
 
                  
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
        A = a;
        F = f;
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
        A = 6378137;
        F = 298.257223563; // default to wgs 84
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
        e = E;
        A = e.a;
        F = e.f;
        
    }

   /*
    * Method: Convert
    * 
    * Description: *//**
    *   Performs a single coordinate transform.
    *  
    *   This function will transform a single coordinate. The
    *   input coordinate is provided in the Utm_Coord_3d instance.
    *   The result of the transformation will be put in the
    *   Gcc_Coord_3d instance.
    *
    * @param utm_coord   the input UTM coordinate
    * @param gcc_coord   the output GCC coordinate
    *
    * @return void
    *
    * @since  1.0
    *
    * @see geotransform.coords.Utm_Coord_3d
    * @see geotransform.coords.Gcc_Coord_3d
    *
    */

    public static void Convert(Utm_Coord_3d utm_coord, Gcc_Coord_3d gcc_coord)
    {
        Utm_Coord_3d utm[] = new Utm_Coord_3d[1];
        Gcc_Coord_3d gcc[] = new Gcc_Coord_3d[1];
        
        utm[0] = utm_coord;
        gcc[0] = gcc_coord;
        
        Convert(utm,gcc);
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
    *   of the Gcc_Coord_3d[] instance. You should have at least as
    *   many entries in the target array as exist in the source array.
    *
    * @param utm   the input array of UTM coordinates
    * @param gcc   the output array of GCC coordinates
    *
    * @return void
    *
    * @since  1.0
    *
    * @see geotransform.coords.Utm_Coord_3d
    * @see geotransform.coords.Gcc_Coord_3d
    *
    */
    
    public static void Convert(final Utm_Coord_3d[] utm,Gcc_Coord_3d[] gcc)
    {
        Gdc_Coord_3d gdc[] = new Gdc_Coord_3d[utm.length];

        // create gdc array
        for (int i = 0; i<utm.length;i++)
            gdc[i] = new Gdc_Coord_3d();
        
        // convert to gdc
        Utm_To_Gdc_Converter.Init(A,F);            
        Utm_To_Gdc_Converter.Convert(utm,gdc);
        
        // convert to gcc
        Gdc_To_Gcc_Converter.Init(A,F);
        Gdc_To_Gcc_Converter.Convert(gdc,gcc);
                  
    } // end Convert    
    
} // end 

    
