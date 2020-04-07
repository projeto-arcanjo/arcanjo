//
// Filename: Create_Datum.java
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
 * Class: Create_Datum
 *
 * Description: *//**
 *   Converts GCC coordinate(s) to GCC changing the Datum.
 *
 *   This class provides the capability to convert from
 *   geocentric (GCC) coordinates to
 *   geocentric (GCC) coordinates.
 *   Methods are provided to convert either a single point
 *   or an array of points. This is a direct conversion.
 *
 * @author Jesus Diaz Centeno, Batmap S.A
 *
 * @version $Id: Create_Datum.java,v 1.2 2002/01/24 06:32:11 reddy Exp $
 *
 * @see Datum
 * @see Gcc_To_Utm_Converter
 * @see Utm_To_Gcc_Converter
 * @see Utm_To_Gdc_Converter
 * @see Gdc_To_Gcc_Converter
 * @see Gdc_To_Utm_Converter
 *
 */
public class Create_Datum {
   public Datum Lista[] = new Datum[240];
   public void Init()
   {
	Lista[0]=new Datum("WGS 1984(Global Definition)",0,0,0);
	Lista[1]=new Datum("Adindan(Burkina Faso)",-118,-14,218);
	Lista[2]=new Datum("Adindan(Cameroon)",-134,-2,210);
	Lista[3]=new Datum("Adindan(Ethiopia)",-165,-11,206);
	Lista[4]=new Datum("Adindan(Mali)",-123,-20,220);
	Lista[5]=new Datum("Adindan(MEAN FOR Ethiopia, Sudan)",-166,-15,204);
	Lista[6]=new Datum("Adindan(Senegal)",-128,-18,224);
	Lista[7]=new Datum("Adindan(Sudan)",-161,-14,205);
	Lista[8]=new Datum("Afgooye(Somalia)",-43,-163,45);
	Lista[9]=new Datum("Ain el Abd 1970(Bahrain)",-150,-250,-1);
	Lista[10]=new Datum("Ain el Abd 1970(Saudi Arabia)",-143,-236,7);
	Lista[11]=new Datum("American Samoa 1962(American Samoa Islands)",-115,118,426);
	Lista[12]=new Datum("Anna 1 Astro 1965(Cocos Islands)",-491,-22,435);
	Lista[13]=new Datum("Antigua Island Astro 1943(Antigua (Leeward Islands))",-270,13,62);
	Lista[14]=new Datum("Arc 1950(Botswana)",-138,-105,-289);
	Lista[15]=new Datum("Arc 1950(Burundi)",-153,-5,-292);
	Lista[16]=new Datum("Arc 1950(Lesotho)",-125,-108,-295);
	Lista[17]=new Datum("Arc 1950(Malawi)",-161,-73,-317);
	Lista[18]=new Datum("Arc 1950(MEAN FOR Botswana, Lesotho, Malawi,)",-143,-90,-294);
	Lista[19]=new Datum("Arc 1950(  Swaziland, Zaire, Zambia, Zimbabwe)",-143,-90,-294);
	Lista[20]=new Datum("Arc 1950(Swaziland)",-134,-105,-295);
	Lista[21]=new Datum("Arc 1950(Zaire)",-169,-19,-278);
	Lista[22]=new Datum("Arc 1950(Zambia)",-147,-74,-283);
	Lista[23]=new Datum("Arc 1950(Zimbabwe)",-142,-96,-293);
	Lista[24]=new Datum("Arc 1960(MEAN FOR Kenya, Tanzania)",-160,-6,-302);
	Lista[25]=new Datum("Ascension Island 1958(Ascension Island)",-205,107,53);
	Lista[26]=new Datum("Astro Beacon E 1945(Iwo Jima)",145,75,-272);
	Lista[27]=new Datum("Astro DOS 71/4(St Helena Island)",-320,550,-494);
	Lista[28]=new Datum("Astro Tern Island (FRIG) 1961(Tern Island)",114,-116,-333);
	Lista[29]=new Datum("Astronomical Station 1952(Marcus Island)",124,-234,-25);
	Lista[30]=new Datum("Australian Geodetic 1966(Australia, Tasmania)",-133,-48,148);
	Lista[31]=new Datum("Australian Geodetic 1984(Australia, Tasmania)",-134,-48,149);
	Lista[32]=new Datum("Ayabelle Lighthouse(Djibouti)",-79,-129,145);
	Lista[33]=new Datum("Bellevue (IGN)(Efate & Erromango Islands)",-127,-769,472);
	Lista[34]=new Datum("Bermuda 1957(Bermuda)",-73,213,296);
	Lista[35]=new Datum("Bissau(Guinea-Bissau)",-173,253,27);
	Lista[36]=new Datum("Bogota Observatory(Colombia)",307,304,-318);
	Lista[37]=new Datum("Bukit Rimpah(Indonesia (Bangka & Belitung Ids))",-384,664,-48);
	Lista[38]=new Datum("Camp Area Astro(Antarctica (McMurdo Camp Area))",-104,-129,239);
	Lista[39]=new Datum("Campo Inchauspe(Argentina)",-148,136,90);
	Lista[40]=new Datum("Canton Astro 1966(Phoenix Islands)",298,-304,-375);
	Lista[41]=new Datum("Cape(South Africa)",-136,-108,-292);
	Lista[42]=new Datum("Cape Canaveral(Bahamas, Florida)",-2,151,181);
	Lista[43]=new Datum("Carthage(Tunisia)",-263,6,431);
	Lista[44]=new Datum("Chatham Island Astro 1971(New Zealand (Chatham Island))",175,-38,113);
	Lista[45]=new Datum("Chua Astro(Paraguay)",-134,229,-29);
	Lista[46]=new Datum("Corrego Alegre(Brazil)",-206,172,-6);
	Lista[47]=new Datum("Dabola(Guinea)",-83,37,124);
	Lista[48]=new Datum("Deception Island(Deception Island, Antarctia)",260,12,-147);
	Lista[49]=new Datum("Djakarta (Batavia)(Indonesia (Sumatra))",-377,681,-50);
	Lista[50]=new Datum("DOS 1968(New Georgia Islands (Gizo Island))",230,-199,-752);
	Lista[51]=new Datum("Easter Island 1967(Easter Island)",211,147,111);
	Lista[52]=new Datum("European 1950(Cyprus)",-104,-101,-140);
	Lista[53]=new Datum("European 1950(Egypt)",-130,-117,-151);
	Lista[54]=new Datum("European 1950(England, Channel Islands, Scotland,)",-86,-96,-120);
	Lista[55]=new Datum("European 1950(  Shetland Islands)",-86,-96,-120);
	Lista[56]=new Datum("European 1950(England, Ireland, Scotland,)",-86,-96,-120);
	Lista[57]=new Datum("European 1950(  Shetland Islands)",-86,-96,-120);
	Lista[58]=new Datum("European 1950(Finland, Norway)",-87,-95,-120);
	Lista[59]=new Datum("European 1950(Greece)",-84,-95,-130);
	Lista[60]=new Datum("European 1950(Iran)",-117,-132,-164);
	Lista[61]=new Datum("European 1950(Italy (Sardinia))",-97,-103,-120);
	Lista[62]=new Datum("European 1950(Italy (Sicily))",-97,-88,-135);
	Lista[63]=new Datum("European 1950(Malta)",-107,-88,-149);
	Lista[64]=new Datum("European 1950(MEAN FOR Austria, Belgium, Denmark,)",-87,-98,-121);
	Lista[65]=new Datum("European 1950(  Finland, France, W Germany,)",-87,-98,-121);
	Lista[66]=new Datum("European 1950(  Gibralter, Greece, Italy,)",-87,-98,-121);
	Lista[67]=new Datum("European 1950(  Luxembourg, Netherlands, Norway,)",-87,-98,-121);
	Lista[68]=new Datum("European 1950(  Portugal, Spain, Sweden,)",-87,-98,-121);
	Lista[69]=new Datum("European 1950(  Switzerland)",-87,-98,-121);
	Lista[70]=new Datum("European 1950(MEAN FOR Austria, Denmark, France,)",-87,-96,-120);
	Lista[71]=new Datum("European 1950(  W Germany, Netherlands, Switzerland)",-87,-96,-120);
	Lista[72]=new Datum("European 1950(MEAN FOR Iraq, Israel, Jordan,)",-103,-106,-141);
	Lista[73]=new Datum("European 1950(  Lebanon, Kuwait, Saudi Arabia,)",-103,-106,-141);
	Lista[74]=new Datum("European 1950(  Syria)",-103,-106,-141);
	Lista[75]=new Datum("European 1950(Portugal, Spain)",-84,-107,-120);
	Lista[76]=new Datum("European 1950(Tunisia)",-112,-77,-145);
	Lista[77]=new Datum("European 1979(MEAN FOR Austria, Finland,)",-86,-98,-119);
	Lista[78]=new Datum("European 1979(  Netherlands, Norway, Spain, Sweden,)",-86,-98,-119);
	Lista[79]=new Datum("European 1979(  Switzerland)",-86,-98,-119);
	Lista[80]=new Datum("Fort Thomas 1955(Nevis, St. Kitts (Leeward Islands))",-7,215,225);
	Lista[81]=new Datum("Gan 1970(Republic of Maldives)",-133,-321,50);
	Lista[82]=new Datum("Geodetic Datum 1949(New Zealand)",84,-22,209);
	Lista[83]=new Datum("Graciosa Base SW 1948(Azores (Faial, Graciosa, Pico,)",-104,167,-38);
	Lista[84]=new Datum("Graciosa Base SW 1948(  Sao Jorge, Terceira))",-104,167,-38);
	Lista[85]=new Datum("Guam 1963(Guam)",-100,-248,259);
	Lista[86]=new Datum("Gunung Segara(Indonesia (Kalimantan))",-403,684,41);
	Lista[87]=new Datum("GUX 1 Astro(Guadalcanal Island)",252,-209,-751);
	Lista[88]=new Datum("Herat North(Afghanistan)",-333,-222,114);
	Lista[89]=new Datum("Hjorsey 1955(Iceland)",-73,46,-86);
	Lista[90]=new Datum("Hong Kong 1963(Hong Kong)",-156,-271,-189);
	Lista[91]=new Datum("Hu-Tzu-Shan(Taiwan)",-637,-549,-203);
	Lista[92]=new Datum("Indian(Bangladesh)",282,726,254);
	Lista[93]=new Datum("Indian(India, Nepal)",295,736,257);
	Lista[94]=new Datum("Indian(Pakistan)",283,682,231);
	Lista[95]=new Datum("Indian 1954(Thailand)",217,823,299);
	Lista[96]=new Datum("Indian 1960(Vietnam (Con Son Island))",182,915,344);
	Lista[97]=new Datum("Indian 1960(Vietnam (Near 16øN))",198,881,317);
	Lista[98]=new Datum("Indian 1975(Thailand)",209,818,290);
	Lista[99]=new Datum("Indonesian 1974(Indonesia)",-24,-15,5);
	Lista[100]=new Datum("Ireland 1965(Ireland)",506,-122,611);
	Lista[101]=new Datum("ISTS 061 Astro 1968(South Georgia Islands)",-794,119,-298);
	Lista[102]=new Datum("ISTS 073 Astro 1969(Diego Garcia)",208,-435,-229);
	Lista[103]=new Datum("Johnston Island 1961(Johnston Island)",189,-79,-202);
	Lista[104]=new Datum("Kandawala(Sri Lanka)",-97,787,86);
	Lista[105]=new Datum("Kerguelen Island 1949(Kerguelen Island)",145,-187,103);
	Lista[106]=new Datum("Kertau 1948(West Malaysia & Singapore)",-11,851,5);
	Lista[107]=new Datum("Kusaie Astro 1951(Caroline Islands)",647,1777,-1124);
	Lista[108]=new Datum("L. C. 5 Astro 1961(Cayman Brac Island)",42,124,147);
	Lista[109]=new Datum("Leigon(Ghana)",-130,29,364);
	Lista[110]=new Datum("Liberia 1964(Liberia)",-90,40,88);
	Lista[111]=new Datum("Luzon(Philippines (Excluding Mindanao))",-133,-77,-51);
	Lista[112]=new Datum("Luzon(Philippines (Mindanao))",-133,-79,-72);
	Lista[113]=new Datum("M'Poraloko(Gabon)",-74,-130,42);
	Lista[114]=new Datum("Mahe 1971(Mahe Island)",41,-220,-134);
	Lista[115]=new Datum("Massawa(Ethiopia (Eritrea))",639,405,60);
	Lista[116]=new Datum("Merchich(Morocco)",31,146,47);
	Lista[117]=new Datum("Midway Astro 1961(Midway Islands)",912,-58,1227);
	Lista[118]=new Datum("Minna(Cameroon)",-81,-84,115);
	Lista[119]=new Datum("Minna(Nigeria)",-92,-93,122);
	Lista[120]=new Datum("Montserrat Island Astro 1958(Montserrat (Leeward Islands))",174,359,365);
	Lista[121]=new Datum("Nahrwan(Oman (Masirah Island))",-247,-148,369);
	Lista[122]=new Datum("Nahrwan(Saudi Arabia)",-243,-192,477);
	Lista[123]=new Datum("Nahrwan(United Arab Emirates)",-249,-156,381);
	Lista[124]=new Datum("Naparima BWI(Trinidad & Tobago)",-10,375,165);
	Lista[125]=new Datum("North American 1927(Alaska (Excluding Aleutian Ids))",-5,135,172);
	Lista[126]=new Datum("North American 1927(Alaska (Aleutian Ids East of 180øW))",-2,152,149);
	Lista[127]=new Datum("North American 1927(Alaska (Aleutian Ids West of 180øW))",2,204,105);
	Lista[128]=new Datum("North American 1927(Bahamas (Except San Salvador Id))",-4,154,178);
	Lista[129]=new Datum("North American 1927(Bahamas (San Salvador Island))",1,140,165);
	Lista[130]=new Datum("North American 1927(Canada (Alberta, British Columbia))",-7,162,188);
	Lista[131]=new Datum("North American 1927(Canada (Manitoba, Ontario))",-9,157,184);
	Lista[132]=new Datum("North American 1927(Canada (New Brunswick,)",-22,160,190);
	Lista[133]=new Datum("North American 1927(  Newfoundland, Nova Scotia, Quebec))",-22,160,190);
	Lista[134]=new Datum("North American 1927(Canada (Northwest Territories,)",4,159,188);
	Lista[135]=new Datum("North American 1927(  Saskatchewan))",4,159,188);
	Lista[136]=new Datum("North American 1927(Canada (Yukon))",-7,139,181);
	Lista[137]=new Datum("North American 1927(Canal Zone)",0,125,201);
	Lista[138]=new Datum("North American 1927(Cuba)",-9,152,178);
	Lista[139]=new Datum("North American 1927(Greenland (Hayes Peninsula))",11,114,195);
	Lista[140]=new Datum("North American 1927(MEAN FOR Antigua, Barbados,)",-3,142,183);
	Lista[141]=new Datum("North American 1927(  Barbuda, Caicos Islands, Cuba,)",-3,142,183);
	Lista[142]=new Datum("North American 1927(  Dominican Republic, Grand Cayman,)",-3,142,183);
	Lista[143]=new Datum("North American 1927(  Jamaica, Turks Islands)",-3,142,183);
	Lista[144]=new Datum("North American 1927(MEAN FOR Belize, Costa Rica,)",0,125,194);
	Lista[145]=new Datum("North American 1927(  El Salvador, Guatemala, Honduras,)",0,125,194);
	Lista[146]=new Datum("North American 1927(  Nicaragua)",0,125,194);
	Lista[147]=new Datum("North American 1927(MEAN FOR Canada)",-10,158,187);
	Lista[148]=new Datum("North American 1927(MEAN FOR CONUS)",-8,160,176);
	Lista[149]=new Datum("North American 1927(MEAN FOR CONUS (East of Mississippi)",-9,161,179);
	Lista[150]=new Datum("North American 1927(  River Including Louisiana,)",-9,161,179);
	Lista[151]=new Datum("North American 1927(  Missouri, Minnesota))",-9,161,179);
	Lista[152]=new Datum("North American 1927(MEAN FOR CONUS (West of Mississippi)",-8,159,175);
	Lista[153]=new Datum("North American 1927(  River Excluding Louisiana,)",-8,159,175);
	Lista[154]=new Datum("North American 1927(  Minnisota, Missouri))",-8,159,175);
	Lista[155]=new Datum("North American 1927(Mexico)",-12,130,190);
	Lista[156]=new Datum("North American 1983(Alaska (Excluding Aleutian Ids))",0,0,0);
	Lista[157]=new Datum("North American 1983(Aleutian Ids)",-2,0,4);
	Lista[158]=new Datum("North American 1983(Canada)",0,0,0);
	Lista[159]=new Datum("North American 1983(CONUS)",0,0,0);
	Lista[160]=new Datum("North American 1983(Hawaii)",1,1,-1);
	Lista[161]=new Datum("North American 1983(Mexico, Central America)",0,0,0);
	Lista[162]=new Datum("North Sahara 1959(Algeria)",-186,-93,310);
	Lista[163]=new Datum("Observatorio Meteorologico 1939(Azores (Corvo & Flores Islands))",-425,-169,81);
	Lista[164]=new Datum("Old Egyptian 1907(Egypt)",-130,110,-13);
	Lista[165]=new Datum("Old Hawaiian(Hawaii)",89,-279,-183);
	Lista[166]=new Datum("Old Hawaiian(Kauai)",45,-290,-172);
	Lista[167]=new Datum("Old Hawaiian(Maui)",65,-290,-190);
	Lista[168]=new Datum("Old Hawaiian(MEAN FOR Hawaii, Kauai, Maui, Oahu)",61,-285,-181);
	Lista[169]=new Datum("Old Hawaiian(Oahu)",58,-283,-182);
	Lista[170]=new Datum("Oman(Oman)",-346,-1,224);
	Lista[171]=new Datum("Ordnance Survey Great Britain 1936(England)",371,-112,434);
	Lista[172]=new Datum("Ordnance Survey Great Britain 1936(England, Isle of Man, Wales)",371,-111,434);
	Lista[173]=new Datum("Ordnance Survey Great Britain 1936(MEAN FOR England, Isle of Man,)",375,-111,431);
	Lista[174]=new Datum("Ordnance Survey Great Britain 1936(  Scotland, Shetland Islands, Wales)",375,-111,431);
	Lista[175]=new Datum("Ordnance Survey Great Britain 1936(Scotland, Shetland Islands)",384,-111,425);
	Lista[176]=new Datum("Ordnance Survey Great Britain 1936(Wales)",370,-108,434);
	Lista[177]=new Datum("Pico de las Nieves(Canary Islands)",-307,-92,127);
	Lista[178]=new Datum("Pitcairn Astro 1967(Pitcairn Island)",185,165,42);
	Lista[179]=new Datum("Point 58(MEAN FOR Burkina Faso & Niger)",-106,-129,165);
	Lista[180]=new Datum("Pointe Noire 1948(Congo)",-148,51,-291);
	Lista[181]=new Datum("Porto Santo 1936(Porto Santo, Madeira Islands)",-499,-249,314);
	Lista[182]=new Datum("Provisional South American 1956(Bolivia)",-270,188,-388);
	Lista[183]=new Datum("Provisional South American 1956(Chile (Northern, Near 19øS))",-270,183,-390);
	Lista[184]=new Datum("Provisional South American 1956(Chile (Southern, Near 43øS))",-305,243,-442);
	Lista[185]=new Datum("Provisional South American 1956(Colombia)",-282,169,-371);
	Lista[186]=new Datum("Provisional South American 1956(Ecuador)",-278,171,-367);
	Lista[187]=new Datum("Provisional South American 1956(Guyana)",-298,159,-369);
	Lista[188]=new Datum("Provisional South American 1956(MEAN FOR Bolivia, Chile, Colombia,)",-288,175,-376);
	Lista[189]=new Datum("Provisional South American 1956(  Ecuador, Guyana, Peru, Venezuela)",-288,175,-376);
	Lista[190]=new Datum("Provisional South American 1956(Peru)",-279,175,-379);
	Lista[191]=new Datum("Provisional South American 1956(Venezuela)",-295,173,-371);
	Lista[192]=new Datum("Provisional South Chilean 1963(Chile (Near 53øS) (Hito XVIII))",16,196,93);
	Lista[193]=new Datum("Puerto Rico(Puerto Rico, Virgin Islands)",11,72,-101);
	Lista[194]=new Datum("Pulkovo 1942(Russia)",28,-130,-95);
	Lista[195]=new Datum("Qatar National(Qatar)",-128,-283,22);
	Lista[196]=new Datum("Qornoq(Greenland (South))",164,138,-189);
	Lista[197]=new Datum("Reunion(Mascarene Islands)",94,-948,-1262);
	Lista[198]=new Datum("Rome 1940(Italy (Sardinia))",-225,-65,9);
	Lista[199]=new Datum("S-42 (Pulkovo 1942)(Hungary)",28,-121,-77);
	Lista[200]=new Datum("S-JTSK(Czechoslavakia (Prior 1 JAN 1993))",589,76,480);
	Lista[201]=new Datum("Santo (DOS) 1965(Espirito Santo Island)",170,42,84);
	Lista[202]=new Datum("Sao Braz(Azores (Sao Miguel, Santa Maria Ids))",-203,141,53);
	Lista[203]=new Datum("Sapper Hill 1943(East Falkland Island)",-355,21,72);
	Lista[204]=new Datum("Schwarzeck(Namibia)",616,97,-251);
	Lista[205]=new Datum("Selvagem Grande 1938(Salvage Islands)",-289,-124,60);
	Lista[206]=new Datum("South American 1969(Argentina)",-62,-1,-37);
	Lista[207]=new Datum("South American 1969(Bolivia)",-61,2,-48);
	Lista[208]=new Datum("South American 1969(Brazil)",-60,-2,-41);
	Lista[209]=new Datum("South American 1969(Chile)",-75,-1,-44);
	Lista[210]=new Datum("South American 1969(Colombia)",-44,6,-36);
	Lista[211]=new Datum("South American 1969(Ecuador)",-48,3,-44);
	Lista[212]=new Datum("South American 1969(Ecuador (Baltra, Galapagos))",-47,26,-42);
	Lista[213]=new Datum("South American 1969(Guyana)",-53,3,-47);
	Lista[214]=new Datum("South American 1969(MEAN FOR Argentina, Bolivia,)",-57,1,-41);
	Lista[215]=new Datum("South American 1969(  Brazil, Chile, Colombia, Ecuador,)",-57,1,-41);
	Lista[216]=new Datum("South American 1969(  Guyana, Paraguay, Peru, Trinidad &)",-57,1,-41);
	Lista[217]=new Datum("South American 1969(  Tobago, Venezuela)",-57,1,-41);
	Lista[218]=new Datum("South American 1969(Paraguay)",-61,2,-33);
	Lista[219]=new Datum("South American 1969(Peru)",-58,0,-44);
	Lista[220]=new Datum("South American 1969(Trinidad & Tobago)",-45,12,-33);
	Lista[221]=new Datum("South American 1969(Venezuela)",-45,8,-33);
	Lista[222]=new Datum("South Asia(Singapore)",7,-10,-26);
	Lista[223]=new Datum("Tananarive Observatory 1925(Madagascar)",-189,-242,-91);
	Lista[224]=new Datum("Timbalai 1948(Brunei, E. Malaysia (Sabah Sarawak))",-679,669,-48);
	Lista[225]=new Datum("Tokyo(Japan)",-148,507,685);
	Lista[226]=new Datum("Tokyo(MEAN FOR Japan, South Korea,)",-148,507,685);
	Lista[227]=new Datum("Tokyo(  Okinawa)",-148,507,685);
	Lista[228]=new Datum("Tokyo(Okinawa)",-158,507,676);
	Lista[229]=new Datum("Tokyo(South Korea)",-146,507,687);
	Lista[230]=new Datum("Tristan Astro 1968(Tristan da Cunha)",-632,438,-609);
	Lista[231]=new Datum("Viti Levu 1916(Fiji (Viti Levu Island))",51,391,-36);
	Lista[232]=new Datum("Voirol 1960(Algeria)",-123,-206,219);
	Lista[233]=new Datum("Wake Island Astro 1952(Wake Atoll)",276,-57,149);
	Lista[234]=new Datum("Wake-Eniwetok 1960(Marshall Islands)",102,52,-38);
	Lista[235]=new Datum("WGS 1972(Global Definition)",0,0,0);
	Lista[236]=new Datum("WGS 1984(Global Definition)",0,0,0);
	Lista[237]=new Datum("Yacare(Uruguay)",-155,171,37);
	Lista[238]=new Datum("Zanderij(Suriname)",-265,120,-358);
	Lista[239]=new Datum("MyDatum",-84.87,-96.49,-116.95);
    }
    public Create_Datum()
    {
    }
}
