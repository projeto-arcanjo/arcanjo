var ws = null;
var configuration = null;
var stompClient = null;
var mainEventHandler = null;
var west = -45;
var south = -24;
var east = -40;
var north = -20;	
var homeLocation = Cesium.Rectangle.fromDegrees(west, south, east, north);

function connect() {
	var socket = new SockJS('/ws');
	stompClient = Stomp.over(socket);
	stompClient.heartbeat.outgoing = 2000;
	stompClient.heartbeat.incoming = 2000;    
	stompClient.debug = null;
	
	stompClient.connect({}, function(frame) {
		
		stompClient.subscribe('/platform/aircraft/reflectvalues', function(notification) {
			var payload =  JSON.parse( notification.body );
			updateAircrafts( payload );
		});
		
		stompClient.subscribe('/platform/aircraft/discovered', function(notification) {
			var payload =  JSON.parse( notification.body );
			newAircraft( payload );
		});
	
	}, function( theMessage ) {
		console.log( "Connect: " + theMessage );
	});    
    
	
}


function startMap(){
	
	var baseOsmProvider = new Cesium.createOpenStreetMapImageryProvider({
		url : 'https://a.tile.openstreetmap.org/'
	});	
	
	viewer = new Cesium.Viewer('cesiumContainer',{
		//terrainProvider : terrainProvider,
		timeline: false,
		animation: false,
		baseLayerPicker: false,
		skyAtmosphere: false,
		fullscreenButton : false,
		geocoder : false,
		homeButton : false,
		infoBox : false,
		sceneModePicker : false,
		selectionIndicator : false,
		navigationHelpButton : false,
		requestRenderMode : true,
	    imageryProvider: baseOsmProvider,
	    scene3DOnly : false,
	    shouldAnimate : true
	});
	
	camera = viewer.camera;
	scene = viewer.scene;
	scene.scene3DOnly = true;	
	
	scene.highDynamicRange = false;
	scene.globe.enableLighting = false;
	scene.globe.baseColor = Cesium.Color.WHITE;
	scene.screenSpaceCameraController.enableLook = false;
	scene.screenSpaceCameraController.enableCollisionDetection = false;
	scene.screenSpaceCameraController.inertiaZoom = 0.8;
	scene.screenSpaceCameraController.inertiaTranslate = 0.8;
	scene.globe.maximumScreenSpaceError = 1;
	scene.globe.depthTestAgainstTerrain = true;
	scene.globe.tileCacheSize = 250;
	scene.pickTranslucentDepth = true;
	scene.useDepthPicking = true;

	mainEventHandler = new Cesium.ScreenSpaceEventHandler( scene.canvas );
	
    // MACETES - ESCONDER ELEMENTOS "DESNECESSARIOS"
    jQuery(".cesium-viewer-bottom").hide();
    jQuery(".cesium-viewer-navigationContainer").hide();
    jQuery(".cesium-viewer-zoomIndicatorContainer").hide();
    jQuery(".cesium-viewer-toolbar").hide();
    jQuery(".navigation-controls").hide();
    jQuery(".compass").hide();
    jQuery(".distance-legend").css( {"border": "none", "background-color" : "rgb(60, 141, 188, 0.5)", "height" : 25, "bottom": 60, "right" : 61, "border-radius": 0} );
    jQuery(".distance-legend-label").css( {"font-size": "11px", "font-weight":"bold",  "line-height" : 0, "color" : "white", "font-family": "Consolas"} );
    jQuery(".distance-legend-scale-bar").css( {"height": "9px", "top" : 10, "border-color" : "white"} );
    
    
	var helper = new Cesium.EventHelper();
	helper.add( viewer.scene.globe.tileLoadProgressEvent, function (event) {
		
		jQuery("#lyrCount").text( event );
		if (event == 0) {
			jQuery('.layerCounter').hide();
			jQuery("#lyrCount").text( "" );
		} else {
			jQuery('.layerCounter').show();
		}
		
	});
    
	addMouseHoverListener();
}

function applyMargins() {
	var totalHeight= jQuery(window).height();
	var contentHeight= totalHeight - 100;
	jQuery(".content-wrapper").css({"height": contentHeight});
	jQuery(".content-wrapper").css({"min-height": contentHeight});	
	
	
	jQuery(".cesium-viewer").css({"height": contentHeight, "width": "100%"});
	jQuery(".cesium-viewer-cesiumWidgetContainer").css({"height": contentHeight, "width": "100%"});
	jQuery(".cesium-widget").css({"height": contentHeight, "width": "100%"});
	jQuery(".cesium-widget canvas").css({"height": contentHeight, "width": "100%"});
}


function goToOperationArea( operationArea ) {
	var center = Cesium.Rectangle.center(operationArea);
	var initialPosition = Cesium.Cartesian3.fromRadians(center.longitude, center.latitude, 980000);
	var initialOrientation = new Cesium.HeadingPitchRoll.fromDegrees(0, -90, 0);
	scene.camera.setView({
	    destination: initialPosition,
	    orientation: initialOrientation,
	    endTransform: Cesium.Matrix4.IDENTITY
	});	
}

function addMouseHoverListener() {
	mainEventHandler.setInputAction( function(movement) {
		var ray = viewer.camera.getPickRay(movement.endPosition);
		var position = viewer.scene.globe.pick(ray, viewer.scene);
		if ( position ) updatePanelFooter( position );
	}, Cesium.ScreenSpaceEventType.MOUSE_MOVE );
};


function updatePanelFooter( position ) {
	cartographic = Cesium.Ellipsoid.WGS84.cartesianToCartographic(position);
	var longitudeString = Cesium.Math.toDegrees(cartographic.longitude).toFixed(10);
	var latitudeString = Cesium.Math.toDegrees(cartographic.latitude).toFixed(10);    	    

	var mapPointerLatitude = latitudeString.slice(-15);
	var mapPointerLongitude = longitudeString.slice(-15);
	var tempHeight = cartographic.height;
	if( tempHeight < 0 ) tempHeight = 0; 
	mapPointerHeight = tempHeight.toFixed(2);

	var coordHDMS = convertDMS(mapPointerLatitude,mapPointerLongitude);

	jQuery( document ).ready(function( jQuery ) {
		jQuery("#mapLat").text( mapPointerLatitude );
		jQuery("#mapLon").text( mapPointerLongitude );    	    
		jQuery("#mapHei").text( mapPointerHeight + 'm' );    	    
		jQuery("#mapUtm").text( latLonToUTM(mapPointerLongitude, mapPointerLatitude  ) );    	    
		jQuery("#mapHdmsLat").text( coordHDMS.lat + " " + coordHDMS.latCard );
		jQuery("#mapHdmsLon").text( coordHDMS.lon + " " + coordHDMS.lonCard );
	});
	
}

jQuery(window).on("resize", applyMargins);

