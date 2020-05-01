
function airTest(){
	
	var lat = -23.2138;
	var lon = -46.0934;
	var thePosition = Cesium.Cartesian3.fromDegrees( lon, lat, 0.00 );
	
	var svgUrl = "http://192.168.0.101:36002/SHAPMF------.png?size="+natoSymbolSize;
	//var svgUrl = "/resources/img/pin-start.png";
	
	var airPlane = new Cesium.Entity({
		name : "AIRCRAFT_C",
		position: thePosition,
		billboard: {
			image: svgUrl,
            eyeOffset: new Cesium.Cartesian3(0.0, 0.0, 0.0),
            horizontalOrigin: Cesium.HorizontalOrigin.CENTER,
            verticalOrigin: Cesium.VerticalOrigin.BOTTOM,
            scaleByDistance : new Cesium.NearFarScalar(1.5e2, 1.0, 8.5e7, 0.3),
            disableDepthTestDistance : Number.POSITIVE_INFINITY, 
            heightReference : Cesium.HeightReference.CLAMP_TO_GROUND, // Construtiva senta no chao
		}
	});	
	
	viewer.entities.add( airPlane );
	aircrafts[ "teste" ] = airPlane;
	
	updateCounters();
	
}


function loadTest(){
	
	//airTest();
	
  	var jsondata = new Cesium.GeoJsonDataSource();
  	
  	var units = jsondata.load('/resources/test/senario.json', {clampToGround:true}).then(function(jsondata) {
    	var unitarray = jsondata.entities.values;
    	var lastOne = null;
    	
    	for (var i = 0; i < unitarray.length; i++) {
			var feature = unitarray[i];
			var svgUrl = "http://192.168.0.101:36002/" + feature.properties.SIDC + ".png?size="+natoSymbolSize+"&additionalInformation="+ feature.properties.name;

			feature.billboard = {
				image: svgUrl,
	            eyeOffset: new Cesium.Cartesian3(0.0, 0.0, 0.0),
	            horizontalOrigin: Cesium.HorizontalOrigin.CENTER,
	            verticalOrigin: Cesium.VerticalOrigin.BOTTOM,
	            scaleByDistance : new Cesium.NearFarScalar(1.5e2, 1.0, 8.5e7, 0.3),
	            disableDepthTestDistance : Number.POSITIVE_INFINITY, 
	            heightReference : Cesium.HeightReference.CLAMP_TO_GROUND, // Construtiva senta no chao
			};

			/*
			feature.billboard = undefined;
			feature.ellipse = {
		    	outline : true,
		    	extrudedHeight : 100,
		    	heightReference : Cesium.HeightReference.CLAMP_TO_GROUND,
		    	height : 10,
		        semiMajorAxis: 200,
		        semiMinorAxis: 200,
		        material: svgUrl,
		    };			
			*/
		}
    	
		viewer.dataSources.add(jsondata);
    	viewer.zoomTo(jsondata);
    	
    }).otherwise(function(error){
    	console.log( error.message );
	});		
}
