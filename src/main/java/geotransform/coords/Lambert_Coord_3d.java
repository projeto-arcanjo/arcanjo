//
// Filename: Lambert_Coord_3d.java
//
// Author: Jesus Diaz Centeno, Batmap S.A. <URL:mailto:jesus.diaz@batmap.com>
// <URL:http://www.batmap.com>
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
//   Portions are Copyright (c) Batmap S.A. 2002.
//

package geotransform.coords;

/*
 * Class: Lambert_Coord_3d
 *
 * Description: *//**
 *   Represents a Lambert coordinate.
 *
 *   This class is used to represent a single point in the
 *	 Lambert projection. This is represented with an
 *   easting value (x) in meters, a northing value (y) in meters,
 *   an elevation (z) in meters, a zone number (zone), and a flag
 *   to determine whether the point is in the northern or southern
 *   hemisphere (hemisphere_north).
 *
 * @Author: Jesus Diaz Centeno, Batmap S.A. <URL:mailto:jesus.diaz@batmap.com>
 *
 * @version $Id: Lambert_Coord_3d.java,v 1.1 2002/01/24 06:32:11 reddy Exp $
 *
 * @see Gcc_Coord_3d
 * @see Gdc_Coord_3d
 *
 */

public class Lambert_Coord_3d {

  public double x;
  public double y;
  public double z;
  public boolean hemisphere_north; // true == north false == south

  /*
   * Method: Lambert_Coord_3d::Lambert_Coord_3d
   *
   * Description: *//**
   *   Constructor for Lambert_Coord_3d.
   *
   *   This constructor will create a new instance of the Lambert_Coord_3d
   *   class. It accepts no parameters. All of the class's member
   *   variables will be set to their default initialization values (0).
   *
   * @since 1.0
   *
   */

  public Lambert_Coord_3d() {
    super();
  }

  /*
   * Method: Lambert_Coord_3d::Lambert_Coord_3d
   *
   * Description: *//**
   *   Constructor for Lambert_Coord_3d.
   *
   *   This constructor will create a new instance of the Lambert_Coord_3d
   *   class. It lets you specify the default value for all of the
   *   member variable of the class.
   *
   * @param X      the Lambert easting coordinate (meters)
   * @param Y      the Lambert northing coordinate (meters)
   * @param Z      the Lambert elevation value (meters)
   * @param hemi_n true if the Lambert coordinate is in the northern hemisphere
   *
   * @since  1.0
   *
   */

  public Lambert_Coord_3d(double X, double Y, double Z, boolean hemi_n){
    x = X;
    y = Y;
    z = Z;

    hemisphere_north = hemi_n;
  }

}
