//
// Filename: Gcc_Coord_3d.java
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

package geotransform.coords;

/*
 * Class: Gcc_Coord_3d
 * 
 * Description: *//**
 *   Represent an (x,y,z) geocentric coordinate.
 *  
 *   This class is used to represent a single geocentric coordinate
 *   (GCC), i.e. distance from the center of the ellipsoid (0, 0, 0).
 *   In other words, an (x, y, z) offset from the center of the earth.
 *   The values x, y, and z are specified in units of meters.
 *
 * @author Dan Toms, SRI International
 *
 * @version $Id: Gcc_Coord_3d.java,v 1.2 2002/01/24 06:32:10 reddy Exp $
 *
 * @see Utm_Coord_3d
 * @see Gdc_Coord_3d
 *
 */

public class Gcc_Coord_3d {

  public double x;
  public double y;
  public double z;

  /*
   * Method: Gcc_Coord_3d::Gcc_Coord_3d
   * 
   * Description: *//**
   *   Constructor for Gcc_Coord_3d.
   *  
   *   This constructor will create a new instance of the Gcc_Coord_3d
   *   class. It accepts no parameters. All of the class's member
   *   variables will be set to their default initialization values (0).
   *
   * @since  1.0
   *
   */

  public Gcc_Coord_3d() {
    super();
  }

  /*
   * Method: Gcc_Coord_3d::Gcc_Coord_3d
   * 
   * Description: *//**
   *   Constructor for Gcc_Coord_3d.
   *  
   *   This constructor will create a new instance of the Gcc_Coord_3d
   *   class. It lets you specify the default value for all of the 
   *   member variable of the class.
   *
   * @param X  the x component of the geocentric coordinate
   * @param Y  the x component of the geocentric coordinate
   * @param Z  the x component of the geocentric coordinate
   *
   * @since  1.0
   *
   */

  public Gcc_Coord_3d( double X, double Y, double Z ) {
    x = X;
    y = Y;
    z = Z;
  }

}
