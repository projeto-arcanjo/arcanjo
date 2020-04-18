function loadTest(){
  	var jsondata = new Cesium.GeoJsonDataSource();
  	
  	var units = jsondata.load('/resources/test/senario.json', {clampToGround:true}).then(function(jsondata) {
    	var unitarray = jsondata.entities.values;
    	var lastOne = null;
    	
    	for (var i = 0; i < unitarray.length; i++) {
			var feature = unitarray[i];
			var svgUrl = "http://192.168.0.101:36002/" + feature.properties.SIDC + ".png?size=30";

			feature.billboard = {
				image: svgUrl,
	            eyeOffset: new Cesium.Cartesian3(0.0, 0.0, 0.0),
	            horizontalOrigin: Cesium.HorizontalOrigin.CENTER,
	            verticalOrigin: Cesium.VerticalOrigin.BOTTOM,
	            scaleByDistance : new Cesium.NearFarScalar(1.5e2, 1.0, 8.5e7, 0.3),
	            disableDepthTestDistance : Number.POSITIVE_INFINITY, 
	            heightReference : Cesium.HeightReference.CLAMP_TO_GROUND, // Construtiva senta no chao
			};
			
			
			feature.label = {
				text: feature.properties.name,
				style: Cesium.LabelStyle.FILL,
				fillColor: Cesium.Color.BLACK,
				font: '10px Consolas',
				eyeOffset: new Cesium.Cartesian3(0.0, 130.0, 0.0)
			};
			
		
		}
    	
		viewer.dataSources.add(jsondata);
    	viewer.zoomTo(jsondata);
    	
    }).otherwise(function(error){
    	console.log( error.message );
	});		
}