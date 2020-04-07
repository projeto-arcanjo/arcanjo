//
// Filename: Gcc_To_Utm_Converter.java
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
 * Class: Gcc_To_Utm_Converter
 * 
 * Description: *//**
 *   Converts GCC coordinate(s) to UTM.
 *  
 *   This class provides the capability to convert from
 *   geocentric (GCC) coordinates to 
 *   Universal Transverse Mercator (UTM).
 *   Methods are provided to convert either a single point
 *   or an array of points. This is an indirect conversion,
 *   GCC -> GDC -> UTM.
 *
 * @author Dan Toms, SRI International
 *
 * @version $Id: Gcc_To_Utm_Converter.java,v 1.2 2002/01/24 06:32:11 reddy Exp $
 *
 * @see Gcc_To_Gdc_Converter
 * @see Utm_To_Gcc_Converter
 * @see Utm_To_Gdc_Converter
 * @see Gdc_To_Gcc_Converter
 * @see Gdc_To_Utm_Converter
 *
 */

public class Gcc_To_Utm_Converter
{
    protected static Ellipsoid e;

 
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
    *   You MUST call this Init method before calling any of 
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
    }

   /*
    * Method: Convert
    * 
    * Description: *//**
    *   Performs a single coordinate transform.
    *  
    *   This function will transform a single coordinate. The
    *   input coordinate is provided in the Gcc_Coord_3d instance.
    *   The result of the transformation will be put in the
    *   Utm_Coord_3d instance.
    *
    * @param gcc_coord   the input GCC coordinate
    * @param utm_coord   the output UTM coordinate
    *
    * @return void
    *
    * @since  1.0
    *
    * @see geotransform.coords.Gcc_Coord_3d
    * @see geotransform.coords.Utm_Coord_3d
    *
    */

    public static void Convert(Gcc_Coord_3d gcc_coord, Utm_Coord_3d utm_coord)
    {
        Utm_Coord_3d utm[] = new Utm_Coord_3d[1];
        Gcc_Coord_3d gcc[] = new Gcc_Coord_3d[1];
        
        utm[0] = utm_coord;
        gcc[0] = gcc_coord;
        
        Convert(gcc,utm);
    }
    
   /*
    * Method: Convert
    * 
    * Description: *//**
    *   Performs multiple coordinate transforms.
    *  
    *   This function will transform any array
    *   of coordinates. The
    *   input coordinate array is provided in the Gcc_Coord_3d[] instance.
    *   The results of the transformation will be put into each element
    *   of the Utm_Coord_3d[] instance. You should have at least as
    *   many entries in the target array as exist in the source array.
    *
    * @param gcc   the input array of GCC coordinates
    * @param utm   the output array of UTM coordinates
    *
    * @return void
    *
    * @since  1.0
    *
    * @see geotransform.coords.Gcc_Coord_3d
    * @see geotransform.coords.Utm_Coord_3d
    *
    */
    
    public static void Convert(final Gcc_Coord_3d[] gcc,Utm_Coord_3d[] utm)
    {
        Gdc_Coord_3d gdc[] = new Gdc_Coord_3d[utm.length];

        // create gdc array
        for (int i = 0; i<utm.length;i++)
            gdc[i] = new Gdc_Coord_3d();
        
        // convert gcc to gdc
        Gcc_To_Gdc_Converter.Init(e);
        Gcc_To_Gdc_Converter.Convert(gcc,gdc);   
        
        // convert gdc to utm
        Gdc_To_Utm_Converter.Init(e);            
        Gdc_To_Utm_Converter.Convert(gdc,utm);
        
                  
    } // end Convert    
    
} // end Gcc_To_Utm_Converter

    
