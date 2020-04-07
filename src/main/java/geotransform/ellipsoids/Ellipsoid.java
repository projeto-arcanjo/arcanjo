//
// Filename: Ellipsoid.java
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
 * Class: Ellipsoid
 * 
 * Description: *//**
 *   An abstract class used for all specific ellipsoids.
 *  
 *   This abstract class is used by the implementation of
 *   all of the ellipsoid models that are implemented by the
 *   GeoTransform library. A two letter code is used to
 *   represent all ellipsoids. These are the SEDRIS Ellipsoid
 *   names, as follows.
 *
 *   <UL>
 *     <LI> AA = Airy 1830 Ellipsoid
 *     <LI> AM = Modified Airy Ellipsoid
 *     <LI> AN = Australian National Ellipsoid
 *     <LI> BN = Bessel 1841 (Namibia) Ellipsoid
 *     <LI> BR = Bessel 1841 (Ethiopia Indonesia Japan Korea)               
 *     <LI> CC = Clarke 1866 Ellipsoid
 *     <LI> CD = Clarke 1880 Ellipsoid
 *     <LI> EA = Everest (India 1830) Ellipsoid
 *     <LI> EB = Everest (Sabah & Sarawak) Ellipsoid
 *     <LI> EC = Everest (India 1956) Ellipsoid
 *     <LI> ED = Everest (West Malaysia 1969) Ellipsoid
 *     <LI> EE = Everest (West Malaysia & Singapore 1948)
 *     <LI> FA = Modified Fischer 1960 Ellipsoid
 *     <LI> HE = Helmert 1906 Ellipsoid
 *     <LI> HO = Hough 1960 Ellipsoid
 *     <LI> IN = International 1924 Ellipsoid
 *     <LI> KA = Krassovsky 1940 Ellipsoid
 *     <LI> RF = Geodetic Reference System 1980 (GRS 80)
 *     <LI> SA = South American 1969 Ellipsoid
 *     <LI> WD = WGS 72 Ellipsoid
 *     <LI> WE = WGS 84 Ellipsoid
 *   </UL>
 *
 * @author Dan Toms, SRI International
 *
 * @version $Id: Ellipsoid.java,v 1.1.1.1 2000/06/15 16:49:27 reddy Exp $
 *
 * @see BR_Ellipsoid
 * @see EB_Ellipsoid
 * @see FA_Ellipsoid
 * @see RF_Ellipsoid
 * @see AA_Ellipsoid
 * @see CC_Ellipsoid
 * @see EC_Ellipsoid
 * @see HE_Ellipsoid
 * @see SA_Ellipsoid
 * @see AM_Ellipsoid
 * @see CD_Ellipsoid
 * @see ED_Ellipsoid
 * @see HO_Ellipsoid
 * @see WD_Ellipsoid
 * @see AN_Ellipsoid
 * @see EE_Ellipsoid
 * @see IN_Ellipsoid
 * @see WE_Ellipsoid
 * @see BN_Ellipsoid
 * @see EA_Ellipsoid
 * @see KA_Ellipsoid
 *
 */

public abstract class Ellipsoid {

  public double a;
  public double f;

  public static final byte AA = 0; // Airy 1830 Ellipsoid
  public static final byte AM = 1; // Modified Airy Ellipsoid
  public static final byte AN = 2; // Australian National Ellipsoid
  public static final byte BN = 3; // Bessel 1841 (Namibia) Ellipsoid
  public static final byte BR = 4; // Bessel 1841 (Ethiopia Indonesia Japan Korea)               
  public static final byte CC = 5; // Clarke 1866 Ellipsoid
  public static final byte CD = 6; // Clarke 1880 Ellipsoid
  public static final byte EA = 7; // Everest (India 1830) Ellipsoid
  public static final byte EB = 8; // Everest (Sabah & Sarawak) Ellipsoid
  public static final byte EC = 9; // Everest (India 1956) Ellipsoid
  public static final byte ED = 10; // Everest (West Malaysia 1969) Ellipsoid
  public static final byte EE = 11; // Everest (West Malaysia & Singapore 1948)
  public static final byte FA = 12; // Modified Fischer 1960 Ellipsoid
  public static final byte HE = 13; // Helmert 1906 Ellipsoid
  public static final byte HO = 14; // Hough 1960 Ellipsoid
  public static final byte IN = 15; // International 1924 Ellipsoid
  public static final byte KA = 16; // Krassovsky 1940 Ellipsoid
  public static final byte RF = 17; // Geodetic Reference System 1980 (GRS 80)
  public static final byte SA = 18; // South American 1969 Ellipsoid
  public static final byte WD = 19; // WGS 72 Ellipsoid
  public static final byte WE = 20; // WGS 84 Ellipsoid
  
  public abstract byte get_id();

}
