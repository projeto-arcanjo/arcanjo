//
// Filename: Utm_Coord_3d.java
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
 * Class: Utm_Coord_3d
 * 
 * Description: *//**
 *   Represents a UTM coordinate.
 *  
 *   This class is used to represent a single point in the Universal
 *   Transverse Mercator projection. This is represented with an
 *   easting value (x) in meters, a northing value (y) in meters,
 *   an elevation (z) in meters, a zone number (zone), and a flag
 *   to determine whether the point is in the northern or southern
 *   hemisphere (hemisphere_north).
 *
 * @author Dan Toms, SRI International
 *
 * @version $Id: Utm_Coord_3d.java,v 1.2 2002/01/24 06:32:11 reddy Exp $
 *
 * @see Gcc_Coord_3d
 * @see Gdc_Coord_3d
 *
 */

public class Utm_Coord_3d {
  
  public double x;
  public double y;
  public double z;
  public byte   zone;
  public boolean hemisphere_north; // true == north false == south
    
  /*
   * Method: Utm_Coord_3d::Utm_Coord_3d
   * 
   * Description: *//**
   *   Constructor for Utm_Coord_3d.
   *  
   *   This constructor will create a new instance of the Utm_Coord_3d
   *   class. It accepts no parameters. All of the class's member
   *   variables will be set to their default initialization values (0).
   *
   * @since 1.0
   *
   */

  public Utm_Coord_3d() {
    super();
  }
    
  /*
   * Method: Utm_Coord_3d::Utm_Coord_3d
   * 
   * Description: *//**
   *   Constructor for Utm_Coord_3d.
   *  
   *   This constructor will create a new instance of the Utm_Coord_3d
   *   class. It lets you specify the default value for all of the 
   *   member variable of the class.
   *
   * @param X      the UTM easting coordinate (meters)
   * @param Y      the UTM northing coordinate (meters)
   * @param Z      the UTM elevation value (meters)
   * @param Zone   the UTM zone number (1..60)
   * @param hemi_n true if the UTM coordinate is in the northern hemisphere
   *
   * @since  1.0
   *
   */

  public Utm_Coord_3d(double X, double Y, double Z, byte Zone, boolean hemi_n){
    x = X;
    y = Y;
    z = Z;
    zone = Zone;
    hemisphere_north = hemi_n;
  }

}
