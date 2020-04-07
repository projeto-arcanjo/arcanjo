//
// Filename: Datum.java
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
package geotransform.datum;

/*
 * Class: Datum
 *
 * Description: *//**
 *   Converts GCC coordinate(s) to GCC changing the Datum.
 *
 *   This class provides the capability to convert from
 *   geocentric (GCC) coordinates to
 *   geocentric (GCC) coordinates.
 *   Methods are provided to convert either a single point
 *   or an array of points.
 *   This is a direct and inverse conversion.
 *
 * @author Jesus Diaz Centeno, Batmap S.A
 *
 * @version $Id: Datum.java,v 1.2 2002/01/24 06:32:11 reddy Exp $
 *
 * @see Create_Datum
 * @see Gcc_To_Utm_Converter
 * @see Utm_To_Gcc_Converter
 * @see Utm_To_Gdc_Converter
 * @see Gdc_To_Gcc_Converter
 * @see Gdc_To_Utm_Converter
 *
 */

import geotransform.coords.Gcc_Coord_3d;
import geotransform.ellipsoids.Ellipsoid;



public class Datum {
    public String name;
    public double	dx;
    public double	dy;
    public double	dz;

    public Datum(String eName, double x, double y, double z)
    {
        name = eName;
        dx = x;
        dy = y;
        dz = z;
        //System.out.println(name);
    }


    public static void Convert(Datum myDatum,boolean direct,Gcc_Coord_3d gcc_coord_3d_in, Gcc_Coord_3d gcc_coord_3d_out)
    {
        Gcc_Coord_3d agcc_coord_3d_in[] = new Gcc_Coord_3d[1];
        Gcc_Coord_3d agcc_coord_3d_out[] = new Gcc_Coord_3d[1];
        agcc_coord_3d_in[0] = gcc_coord_3d_in;
        agcc_coord_3d_out[0] = gcc_coord_3d_out;
        Convert(myDatum,direct,agcc_coord_3d_in, agcc_coord_3d_out);
    }


     public static void Convert(Datum myDatum,boolean direct,Gcc_Coord_3d agcc_coord_3d_in[], Gcc_Coord_3d agcc_coord_3d_out[])
    {
        if (direct)
        {
          for(int i = 0; i < agcc_coord_3d_in.length; i++)
          {
              agcc_coord_3d_out[i].x = agcc_coord_3d_in[i].x-myDatum.dx;
              agcc_coord_3d_out[i].y = agcc_coord_3d_in[i].y-myDatum.dy;
              agcc_coord_3d_out[i].z = agcc_coord_3d_in[i].z-myDatum.dz;
          }
        }
        else
        {
          for(int i = 0; i < agcc_coord_3d_in.length; i++)
          {
              agcc_coord_3d_out[i].x = agcc_coord_3d_in[i].x+myDatum.dx;
              agcc_coord_3d_out[i].y = agcc_coord_3d_in[i].y+myDatum.dy;
              agcc_coord_3d_out[i].z = agcc_coord_3d_in[i].z+myDatum.dz;
          }
        }

    }

    public Datum()
    {
    }

}