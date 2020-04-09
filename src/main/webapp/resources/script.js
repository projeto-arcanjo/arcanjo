var ws = null;
var configuration = null;
var stompClient = null;

var instancesMainCatalog = [];
var federatesMainCatalog = [];
var federationsMainCatalog = [];

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.heartbeat.outgoing = 2000;
    stompClient.heartbeat.incoming = 2000;    
    stompClient.debug = null;
	stompClient.connect({}, function(frame) {
		
		stompClient.subscribe('/attributes/reflectvalues', function(notification) {
			var payload = JSON.parse( notification.body );
			if( $('#attributesTable').length ){
				processReflectAttr( payload );
			}
		});

		stompClient.subscribe('/interactions/receive', function(notification) {
			var payload =  JSON.parse( notification.body );
			// in dashboard.js
			processInteraction( payload );
		});
		
		stompClient.subscribe('/instances/new', function(notification) {
			var payload =  JSON.parse( notification.body );
			newInstance( payload );
		});

		stompClient.subscribe('/instances/remove', function(notification) {
			var payload =  JSON.parse( notification.body );
			removeInstance( payload );
		});

		stompClient.subscribe('/instances/update', function(notification) {
			var payload =  JSON.parse( notification.body );
			updateInstances( payload );
		});

		stompClient.subscribe('/aircrafts/reflectvalues', function(notification) {
			var payload =  JSON.parse( notification.body );
			updateAircrafts( payload );
		});
		
		stompClient.subscribe('/aircrafts/discovered', function(notification) {
			var payload =  JSON.parse( notification.body );
			newAircraft( payload );
		});
		
		$("#federationStatusText").text("Starting...");
		$("#federationStatusIcon").removeClass("text-danger");
		$("#federationStatusIcon").addClass("text-warning");

	}, function( theMessage ) {
		console.log( "Connect: " + theMessage );
	});    
    
}

function startFederation(){
	$.ajax({
		url:"/federation/start", 
		type: "GET", 
		success: function( obj ) {
			var started = true;
			if( obj == "STARTED_NOW" ){
				//
			} else if( obj == "ALREADY_STARTED" ) {
				//
			} else {
				$("#federationStatusIcon").removeClass("text-warning");
				$("#federationStatusIcon").addClass("text-danger");
				fireToast( 'error', 'Federation not started', 'Was not possible to start the federation: ' + obj );
				started = false;
			}
			
			if( started === true ){
				$("#federationStatusIcon").removeClass("text-warning");
				$("#federationStatusIcon").addClass("text-success");
				$("#federationStatusText").text("Online");
				getConfig();
			}
			
		},
	    error: function(xhr, textStatus) {
			fireToast( 'error', 'Backend Offline', 'The backend application is offline.');
			$("#federationStatusIcon").removeClass("text-warning");
			$("#federationStatusIcon").addClass("text-danger");
			$("#federationStatusText").text("Offline");
	    }, 		
    });
	
}




function refreshData(){
	$.ajax({
		url:"/federation/refresh", 
		type: "GET", 
		success: function( obj ) {
			console.log( obj );
		},
	    error: function(xhr, textStatus) {
	    	//
	    }, 		
    });
}

/*
		Update interface counters
*/
function getModulesCount(){
	$("#totalModules").text( "0" );
	$.ajax({
		url:"/modules/count", 
		type: "GET", 
		success: function( obj ) {
			$("#totalModules").text( obj );
		},
	    error: function(xhr, textStatus) {
	    }, 		
    });
}
function getClassesCount(){
	$("#totalClasses").text( "0" );
	$.ajax({
		url:"/classes/count", 
		type: "GET", 
		success: function( obj ) {
			$("#totalClasses").text( obj );
		},
	    error: function(xhr, textStatus) {
	    }, 		
    });
}
function getInstancesCount(){
	$("#totalObjects").text( "0" );
	$("#instancesCountNotification").text( "0" );
	
	// The backend are sending total count of instances not including the Federation and Federates.
	$.ajax({
		url:"/instances/count", 
		type: "GET", 
		success: function( obj ) {
			$("#totalObjects").text( obj );
			$("#instancesCountNotification").text( obj );
		},
	    error: function(xhr, textStatus) {
	    }, 		
    });
	
}

function getFederationsCount(){
	$("#totalObjects").text( "0" );
	$("#instancesCountNotification").text( "0" );
	
	// The backend are sending total count of instances wich are Federation and Federates.
	$.ajax({
		url:"/federation/federatecount", 
		type: "GET", 
		success: function( obj ) {
			$("#totalMOMObjects").text( obj );
			$("#instancesMOMCountNotification").text( obj );
		},
	    error: function(xhr, textStatus) {
	    }, 		
    });
	
}

function getInteractionsCount(){
	$("#totalInteractions").text( "0" );
	$.ajax({
		url:"/interactions/count", 
		type: "GET", 
		success: function( obj ) {
			$("#totalInteractions").text( obj );
		},
	    error: function(xhr, textStatus) {
	    }, 		
    });
}
 
function emptyPanels(){
	$("#mainContentData").empty();
	$("#mainContentDataLine2").empty();
}

function showModule( moduleFileName ){
	emptyPanels();
	$.ajax({
		url:"/modules/" + moduleFileName.replace(".",""), 
		type: "GET", 
		success: function( obj ) {
			if(obj) createModuleDetails( obj );
		},
	    error: function(xhr, textStatus) {
	    	//
	    }, 		
    });	
}

function showInteractions(){
	emptyPanels();
	$.ajax({
		url:"/interactions/list", 
		type: "GET", 
		success: function( obj ) {
			createInteractionTable( obj );
		},
	    error: function(xhr, textStatus) {
	    	//
	    }, 		
    });	
}

function showModules(){
	emptyPanels();

	$.ajax({
		url:"/modules/list", 
		type: "GET", 
		success: function( obj ) {
			createModuleTable( obj );
		},
	    error: function(xhr, textStatus) {
	    	//
	    }, 		
    });	
}

function showClasses(){
	emptyPanels();
	$.ajax({
		url:"/classes/list", 
		type: "GET", 
		success: function( obj ) {
			createClassTable( obj );
		},
	    error: function(xhr, textStatus) {
	    	//
	    }, 		
    });		
}

function showInstances(){
	emptyPanels();
	federatesMainCatalog = [];
	federationsMainCatalog = [];
	instancesMainCatalog = [];
	$.ajax({
		url:"/instances/list", 
		type: "GET", 
		success: function( instances ) {
			createInstanceTable( instances );
		},
	    error: function(xhr, textStatus) {
	    	//
	    }, 		
    });		
}

function getConfig(){
    $.ajax({
		url:"/federation/config", 
		type: "GET", 
		success: function( obj ) {
			$("#federationName").text( obj.federationName );
			fireToast( 'success', 'Federation Connected', 'Federation ' + obj.federationName + ' is online.' );
			getModulesCount();
			getClassesCount();
			getInstancesCount();
			getFederationsCount();
			getInteractionsCount();
			showInstances();
		},
	    error: function(xhr, textStatus) {
			fireToast( 'error', 'Critical Error', 'Backend is offline.' );
	    }, 		
    });
	
}

/*
function socketBroadcast( channel, data ){
	stompClient.send( channel, {}, data );
}
*/


function newInstance( objectInstance ){
	var fullClass = objectInstance.objectClass.myName.replace('HLAobjectRoot.','').replace('HLAmanager.','');
	fireToast( 'info', 'New Instance ' + objectInstance.objectName, 'New instance of class ' + fullClass + ' is connected.' );
	addInstanceToIFace( objectInstance );
}

// Need to use the object instance ID to remove the "LI" here
function removeInstance( objectInstanceHandle ){
	var elId = "id" + objectInstanceHandle;
	$( "#" + elId ).remove();
	var instance = removeObjectInstance( objectInstanceHandle );
	var objName = instance.objectName.replace('MOM.Federate(','').replace(')','').replace('MOM.Federation(','');
	var className = instance.objectClass.myName;
	fireToast( 'warning',  'Instance lost.', 'Object "' + objName + '" instance of "' + className + '" gone.' );
	$("#totalObjects").text( instances.length );
	$("#instancesCountNotification").text( instances.length );
	
}

// Remove the object instance from internal list
function removeObjectInstance( objectInstanceHandle ) {
	
	// Search for objects
	for( var i = 0; i < instancesMainCatalog.length; i++){
		var instance = instancesMainCatalog[i];
		if ( instance.objectInstanceHandle == objectInstanceHandle ) {
			instances.splice(i, 1);
			return instance;
		}
	}
	
	// Search for Federates 
	for( var i = 0; i < federatesMainCatalog.length; i++){
		var instance = federatesMainCatalog[i];
		if ( instance.objectInstanceHandle == objectInstanceHandle ) {
			instances.splice(i, 1);
			return instance;
		}
	}	
	
	// Search for Federations
	for( var i = 0; i < federationsMainCatalog.length; i++){
		var instance = federationsMainCatalog[i];
		if ( instance.objectInstanceHandle == objectInstanceHandle ) {
			instances.splice(i, 1);
			return instance;
		}
	}	
	
	return null;
}


function addMOMObjectToIface( objectInstance ){
	
	// Exists already?
	for( var x=0; x<federatesMainCatalog.length;x++  ) {
		if( federatesMainCatalog[x].objectInstanceHandle == objectInstance.objectInstanceHandle ) return;
	}
	for( var x=0; x<federationsMainCatalog.length;x++  ) {
		if( federationsMainCatalog[x].objectInstanceHandle == objectInstance.objectInstanceHandle ) return;
	}	
	
	var className = objectInstance.objectClass.myName;
	var objectHandle = objectInstance.objectInstanceHandle;
	var elId = "id" + objectHandle;
	var objName = objectInstance.objectName;
	var elementId = "";

	if( objName.startsWith( 'MOM.Federate(' ) ) {
		elementId = "#menuFederates";
		// Add only federates to my internal list
		federatesMainCatalog.push( objectInstance );

	} else if ( objName.startsWith( 'MOM.Federation(' ) ) {
		elementId = "#menuFederations";
		federationsMainCatalog.push( objectInstance );
	} else {
		fireToast( 'error', 'Unknown Object', 'Unknown MOM object "' + objName + '" received. Class "' + className + '"');
		return;
	}	
	
	// Add to interface
	objName = objectInstance.objectName.replace('MOM.Federate(','').replace(')','').replace('MOM.Federation(','');
	$( elementId ).append( '<li id=' + elId + '><a href="#"><i class="fa fa-circle-o text-yellow"></i> '+objName+'</a></li>' );
	
	$("#totalMOMObjects").text( federatesMainCatalog.length );
	$("#instancesMOMCountNotification").text( federatesMainCatalog.length );
	
}


// Need to ID the "LI" with the object instance ID to allow remove later
function addInstanceToIFace( objectInstance ) {
	var className = objectInstance.objectClass.myName;
	var objectHandle = objectInstance.objectInstanceHandle;
	var elId = "id" + objectHandle;
	var objName = objectInstance.objectName;

	if( objName.startsWith( 'MOM.Federat' ) ) {
		addMOMObjectToIface( objectInstance );
		return;
	}

	// Exists already?
	for( var x=0; x<instancesMainCatalog.length;x++  ) {
		if( instancesMainCatalog[x].objectInstanceHandle == objectInstance.objectInstanceHandle ) return;
	}
	
	// Add it to my internal list
	instancesMainCatalog.push( objectInstance );
	
	// Add to interface
	$("#menuInstances").append( '<li id=' + elId + '><a href="#"><i class="fa fa-circle-o text-aqua"></i> '+objName+'</a></li>' );
	$("#totalObjects").text( instancesMainCatalog.length );
	$("#instancesCountNotification").text( instancesMainCatalog.length );
}

function updateInstances( objectInstance ){
	addInstanceToIFace( objectInstance );
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
	
}

function applyMargins() {
	jQuery(".cesium-viewer").css({"height": "100%", "width": "100%"});
	jQuery(".cesium-viewer-cesiumWidgetContainer").css({"height": "100%", "width": "100%"});
	jQuery(".cesium-widget").css({"height": "100%", "width": "100%"});
	jQuery(".cesium-widget canvas").css({"height": "100%", "width": "100%"});
}

jQuery(window).on("resize", applyMargins);

connect();


