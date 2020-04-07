//
// Filename: HO_Ellipsoid.java
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

package geotransform.ellipsoids;

/*
 * Class: HO_Ellipsoid
 * 
 * Description: *//**
 *   Defines a Hough 1960 ellipsoid.
 *  
 *   This class defines an ellipsoid to model the shape earth. 
 *   The two variables that are used are the semi-major axis (a),
 *   and the inverse flattening (1/f). The semi-minor axis can be
 *   recovered from these two variables given the relationship,
 *   b = a(1-f).
 *
 *   For the Hough 1960 ellipsoid,
 *   a   = 6378270,
 *   1/f = 297.
 *
 * @author Dan Toms, SRI International
 *
 * @version $Id: HO_Ellipsoid.java,v 1.1.1.1 2000/06/15 16:49:27 reddy Exp $
 *
 * @since 1.0
 *
 * @see Ellipsoid
 *
 */

public class HO_Ellipsoid extends Ellipsoid {

  public byte get_id() { return HO; }

  public HO_Ellipsoid() {
    a = 6378270;
    f = 297;
  }
}
