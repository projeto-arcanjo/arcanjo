//
// Filename: Gcc_To_Gdc_Converter.java
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
 * Class: Gcc_To_Gdc_Converter
 * 
 * Description: *//**
 *   Converts GCC coordinate(s) to GDC.
 *  
 *   This class provides the capability to convert from
 *   geocentric (GCC) coordinates to 
 *   geodetic (GDC), i.e. lat/long.
 *   Methods are provided to convert either a single point
 *   or an array of points. This is a direct conversion.
 *
 * @author Dan Toms, SRI International
 *
 * @version $Id: Gcc_To_Gdc_Converter.java,v 1.3 2003/07/21 17:30:47 mccann Exp $
 *
 * @see Gcc_To_Utm_Converter
 * @see Utm_To_Gcc_Converter
 * @see Utm_To_Gdc_Converter
 * @see Gdc_To_Gcc_Converter
 * @see Gdc_To_Utm_Converter
 *
 */

public class Gcc_To_Gdc_Converter
{
    static final double DEGREES_PER_RADIAN = 57.2957795130823208768;
    static final double RADIANS_PER_DEGREE = 0.0174532925199432957692;
    
    static double A,
                  F,
                  C,
                  A2,
                  C2,
                  Eps2,
                  Eps21,
                  Eps25,
                  C254,
                  C2DA,
                  CEE,
                  CE2,
                  CEEps2,
                  TwoCEE,
                  tem,
                  ARat1,
                  ARat2,
                  BRat1,
                  BRat2,
                  B1,B2,B3,B4,B5;
                  
    static private Ellipsoid e;

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
      // Note that Gdc to Gcc must be called by an ellipsoid, as the optimized
      // conversions depend on knowing the ellipsoid type.

      e = E;
      CreateConstants(E.a,E.f);
    }
                   
    protected static void CreateConstants(double a, double f)
    {
 
        double polx2b,polx3b,polx4b,polx5b;
 
        A = a;
        F = f;
            
        //  Create the ERM constants.
        A2     = A * A;
        F      =1/(F);
        C      =(A) * (1-F);
        C2     = C * C;
        Eps2   =(F) * (2.0-F);
        Eps21  =Eps2 - 1.0;
        Eps25  =.25 * (Eps2);
        C254   =54.0 * C2;        
        
        C2DA   = C2 / A;
        CE2    = A2 - C2;
        tem    = CE2 / C2;
        CEE    = Eps2 * Eps2;        
        TwoCEE =2.0 * CEE;
        CEEps2 =Eps2 * CE2;
         
        // UPPER BOUNDS ON POINT
     

        ARat1  =Math.pow((A + 50005.0),2);
        ARat2  =(ARat1) / Math.pow((C+50005.0),2);
    
        // LOWER BOUNDS ON POINT
        
        BRat1  =Math.pow((A-10005.0),2);
        BRat2  =(BRat1) / Math.pow((C-10005.0),2);
          
        switch (e.get_id())
        {

            case Ellipsoid.WE:                                
                               B1=0.100225438677758E+01;
                               B2=-0.393246903633930E-04;
                               B3=0.241216653453483E+12;
                               B4=0.133733602228679E+14;
                               B5=0.984537701867943E+00;
                               break;            
            case Ellipsoid.AA:
                               B1=0.100224625101002E+01;
                               B2=-0.390460083288143E-04;
                               B3=0.240320189037138E+12;
                               B4=0.133719260538966E+14;
                               B5=0.984592458596161E+00;
                               break;
                               
            case Ellipsoid.AM:
                               B1=0.100224625075368E+01;
                               B2=-0.390459999900173E-04;
                               B3=0.240303394872251E+12;
                               B4=0.133709931084284E+14;
                               B5=0.984592460957268E+00;
                               break;
                               
            case Ellipsoid.AN:
                               B1=0.100225444204291E+01;
                               B2=-0.393265865328642E-04;
                               B3=0.241224183614829E+12;
                               B4=0.133734497953958E+14;
                               B5=0.984537329904682E+00;
            		       break;
            
            case Ellipsoid.BN:
                               B1=0.100224755857767E+01;
                               B2=-0.390907329587448E-04;
                               B3=0.240451345711468E+12;
                               B4=0.133714386800225E+14;
                               B5=0.984583658241425E+00;
                               break;
                               
            case Ellipsoid.BR:
                               B1=0.100224755848503E+01;
                               B2=-0.390907295861776E-04;
                               B3=0.240444817155162E+12;
                               B4=0.133710761751998E+14;
                               B5=0.984583659174593E+00;
                               break;
                               
            case Ellipsoid.CC:
                               B1=0.100227973833064E+01;
                               B2=-0.401992216347506E-04;
                               B3=0.243880311870541E+12;
                               B4=0.133706476542196E+14;
                               B5=0.984367129494488E+00;
                               break;
                               
            case Ellipsoid.CD:
                               B1=0.100229163655042E+01;
                               B2=-0.406128691119799E-04;
                               B3=0.245130859537107E+12;
                               B4=0.133694175832394E+14;
                               B5=0.984287099560456E+00;
                               break;
                               
            case Ellipsoid.EA:
                               B1=0.100223509569433E+01;
                               B2=-0.386654527077781E-04;
                               B3=0.239128650594500E+12;
                               B4=0.133720475335561E+14;
                               B5=0.984667548438242E+00;
              
            case Ellipsoid.EB:
                               B1=0.100223509571370E+01;
                               B2=-0.386654536421315E-04;
                               B3=0.239130314228725E+12;
                               B4=0.133721404499933E+14;
                               B5=0.984667548190955E+00;
                               break;
                               
            case Ellipsoid.EC:
                               B1=0.100223509572519E+01;
                               B2=-0.386654535736479E-04;
                               B3=0.239130514500415E+12;
                               B4=0.133721515789086E+14;
                               B5=0.984667548183952E+00;
                               break;
                               
            case Ellipsoid.ED:
                               B1=0.100223509571418E+01;
                               B2=-0.386654534742569E-04;
                               B3=0.239130097288096E+12;
                               B4=0.133721283147546E+14;
                               B5=0.984667548227722E+00;
                               break;
                               
            case Ellipsoid.EE:
                               B1=0.100223509572941E+01;
                               B2=-0.386654536185472E-04;
                               B3=0.239130725532003E+12;
                               B4=0.133721633543519E+14;
                               B5=0.984667548165743E+00;
                               break;
                               
            case Ellipsoid.FA:
                               B1=0.100225438677758E+01;
			       B2=-0.393246903633930E-04;
			       B3=0.241216653453483E+12;
			       B4=0.133733602228679E+14;
			       B5=0.984537701867943E+00;
			       break;
  
            case Ellipsoid.HE:
                               B1=0.100225405980964E+01;
                               B2=-0.393134714450825E-04;
                               B3=0.241187107678002E+12;
                               B4=0.133736622218590E+14;
                               B5=0.984539902154414E+00;
                               break;
                               
            case Ellipsoid.HO:                               
                               B1=0.100226404159165E+01;
                               B2=-0.396566479838024E-04;
                               B3=0.242239266725463E+12;
                               B4=0.133727724324161E+14;
                               B5=0.984472732821240E+00;
                               break;
                               
            case Ellipsoid.IN:                               
                               B1=0.100226404171336E+01;
                               B2=-0.396566527027543E-04;
                               B3=0.242248216831710E+12;
                               B4=0.133732658091673E+14;
                               B5=0.984472731538793E+00;
                               break;
                               
            case Ellipsoid.KA:                               
                               B1=0.100225405984221E+01;
                               B2=-0.393134735007025E-04;
                               B3=0.241190507521621E+12;
                               B4=0.133738505527850E+14;
                               B5=0.984539901633388E+00;
                               break;
                               
            case Ellipsoid.RF:                               
                               B1=0.100225438679850E+01;
                               B2=-0.393246905835223E-04;
                               B3=0.241216653573729E+12;
                               B4=0.133733601038204E+14;
                               B5=0.984537701810060E+00;
                               break;
                               
            case Ellipsoid.SA:                               
                               B1=0.100225444204291E+01;
                               B2=-0.393265865328642E-04;
                               B3=0.241224183614829E+12;
                               B4=0.133734497953958E+14;
                               B5=0.984537329904682E+00;
                               break;
                               
            case Ellipsoid.WD:                               
                               B1=0.100225436554849E+01;
                               B2=-0.393239617638985E-04;
                               B3=0.241214275377192E+12;
                               B4=0.133733543378504E+14;
                               B5=0.984537844768728E+00;
                               break;
                               
        } // end switch
                            
        
        
    } // end init

   /*
    * Method: Convert
    * 
    * Description: *//**
    *   Performs a single coordinate transform.
    *  
    *   This function will transform a single coordinate. The
    *   input coordinate is provided in the Gcc_Coord_3d instance.
    *   The result of the transformation will be put in the
    *   Gdc_Coord_3d instance.
    *
    * @param gcc_coord   the input GCC coordinate
    * @param gdc_coord   the output GDC coordinate
    *
    * @return void
    *
    * @since  1.0
    *
    * @see geotransform.coords.Gcc_Coord_3d
    * @see geotransform.coords.Gdc_Coord_3d
    *
    */

    public static void Convert(Gcc_Coord_3d gcc_coord, Gdc_Coord_3d gdc_coord)
    {
        Gdc_Coord_3d gdc[] = new Gdc_Coord_3d[1];
        Gcc_Coord_3d gcc[] = new Gcc_Coord_3d[1];
        
        gdc[0] = gdc_coord;
        gcc[0] = gcc_coord;
        
        Convert(gcc,gdc);
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
    *   of the Gdc_Coord_3d[] instance. You should have at least as
    *   many entries in the target array as exist in the source array.
    *
    * @param gcc   the input array of GCC coordinates
    * @param gdc   the output array of GDC coordinates
    *
    * @return void
    *
    * @since  1.0
    *
    * @see geotransform.coords.Gcc_Coord_3d
    * @see geotransform.coords.Gdc_Coord_3d
    *
    */
    
    public static void Convert(final Gcc_Coord_3d[] gcc,Gdc_Coord_3d[] gdc )
    {
        double w2,w,z2,testu,testb,top,top2,rr,q,s12,rnn,s1,zp2,wp,wp2,cf,gee,alpha,cl,arg2,p,xarg,r2,r1,ro,
               arg0,s,roe,arg,v,zo;

        for(int i=0;i<gdc.length;i++)
        {

        /* CHECK FOR SPECIAL CASES*/
             
            if (!(gcc[i].x == 0.0)) ; /* null statement */
            else
            {
                if (gcc[i].y > 0.0) 
                    gdc[i].latitude = Math.PI/2;
                else
                {
                    if (gcc[i].y < 0.0)
                        gdc[i].longitude = -Math.PI/2;
                    else
                    {
                        if (gcc[i].z > 0)
                        {
                            gdc[i].latitude = Math.PI/2;
                            gdc[i].longitude = 0.0;
                            gdc[i].elevation = gcc[i].z;
                            return;
                        }
                        else
                        {
                            if (gcc[i].z < 0.0)
                            {
                                gdc[i].latitude = -Math.PI/2;
                                gdc[i].longitude   =  0.0;
                                gdc[i].elevation   =  gcc[i].z;
                            
                                return;
                            }
                        else
                        {
                            gdc[i].latitude = 0.0;
                            gdc[i].longitude = 0.0;
                            gdc[i].elevation = 0.0;
                            return;
                        }
                    }
                }
            }
        }

        /* END OF SPECIAL CASES */

        w2=gcc[i].x * gcc[i].x + gcc[i].y * gcc[i].y;
        w=Math.sqrt(w2);
        z2=gcc[i].z * gcc[i].z;

        testu=w2 + ARat2 * z2;
        testb=w2 + BRat2 * z2;

        if ((testb > BRat1) && (testu < ARat1)) 
        {    

            /*POINT IS BETWEEN-10 KIL AND 50 KIL, SO COMPUTE TANGENT LATITUDE */
    
            top= gcc[i].z * (B1 + (B2 * w2 + B3) /
                 (B4 + w2 * B5 + z2));

            top2=top*top;

            rr=top2+w2;
                  
            q=Math.sqrt(rr);
                  
            /* ****************************************************************
                  
               COMPUTE H IN LINE SQUARE ROOT OF 1-EPS2*SIN*SIN.  USE SHORT BINOMIAL
               EXPANSION PLUS ONE ITERATION OF NEWTON'S METHOD FOR SQUARE ROOTS.
            */

            s12=top2/rr;

            rnn = A / ( (.25 - Eps25*s12 + .9999944354799/4) + (.25-Eps25*s12)/(.25 - Eps25*s12 + .9999944354799/4));
            s1=top/q;
        
            /******************************************************************/

            /* TEST FOR H NEAR POLE.  if SIN(¯)**2 <= SIN(45.)**2 THEN NOT NEAR A POLE.*/  
    
            if (s12 < .50)
                gdc[i].elevation = q-rnn;
            else
                gdc[i].elevation = gcc[i].z / s1 + (Eps21 * rnn);
                gdc[i].latitude = Math.atan(top / w);
                gdc[i].longitude = Math.atan2(gcc[i].y,gcc[i].x);
        }
              /* POINT ABOVE 50 KILOMETERS OR BELOW -10 KILOMETERS  */
        else /* Do Exact Solution  ************ */
        { 
            wp2=gcc[i].x * gcc[i].x + gcc[i].y * gcc[i].y;

            zp2=gcc[i].z * gcc[i].z;

            wp=Math.sqrt(wp2);

            cf=C254 * zp2;

            gee=wp2 - (Eps21 * zp2) - CEEps2;
    
            alpha=cf / (gee*gee);

            cl=CEE * wp2 * alpha / gee;

            arg2=cl * (cl + 2.0);
    
            s1=1.0 + cl + Math.sqrt(arg2);
    
            s=Math.pow(s1,(1.0/3.0));
    
            p=alpha / (3.0 * Math.pow(( s + (1.0/s) + 1.0),2));
    
            xarg= 1.0 + (TwoCEE * p);
    
            q=Math.sqrt(xarg);

            r2= -p * (2.0 * (1.0 - Eps2) * zp2 / ( q * ( 1.0 + q) ) + wp2);
      
            r1=(1.0 + (1.0 / q));
      
            r2 /=A2;


            /*    DUE TO PRECISION ERRORS THE ARGUMENT MAY BECOME NEGATIVE IF SO SET THE ARGUMENT TO ZERO.*/

            if (r1+r2 > 0.0)
                ro = A * Math.sqrt( .50 * (r1+r2));
            else
                ro=0.0;

            ro=ro - p * Eps2 * wp / ( 1.0 + q);
    
            arg0 = Math.pow(( wp - Eps2 * ro),2) + zp2;
    
            roe = Eps2 * ro;
            arg = Math.pow(( wp - roe),2) + zp2;
            v=Math.sqrt(arg - Eps2 * zp2);

            zo=C2DA * gcc[i].z / v;

            gdc[i].elevation = Math.sqrt(arg) * (1.0 - C2DA / v);

            top=gcc[i].z+ tem*zo;
    
            gdc[i].latitude = Math.atan( top / wp );
            gdc[i].longitude =Math.atan2(gcc[i].y,gcc[i].x);
        }  /* end of Exact solution */

        gdc[i].latitude *= DEGREES_PER_RADIAN;
        gdc[i].longitude *= DEGREES_PER_RADIAN;

            
        } // end for
            
                    
    } // end Convert    
    
} // end

    
