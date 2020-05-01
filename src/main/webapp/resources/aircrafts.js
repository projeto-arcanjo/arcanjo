var aircrafts = [];

function newAircraft( payload ){
	// Decidi nao fazer nada.
	// Nao tem atributos ou sao atributos padrao.
}

function updateAircrafts( payload ){
	// verifica se ja tenho essa aeronave
	if( aircrafts[ payload.hlaObjetName ] ){
		atualizaAircraft( payload );
	} else {
		// Se por qualquer motivo eu recebi uma atualizacao deste objeto
		// mas nao conhecia ele ainda, preciso armazenar e criar na interface
		// Ja vou criar logo na interface porque ele ja tem atributos atualizados
		criaAircraft( payload );
		updateCounters();
	}

}

function atualizaAircraft( payload ){
	// atualizo um objeto que ja esta no mapa
	if( mapSimulationType == "CONSTRUTIVA" ) {
		var lat = payload.latitude;
		var lon = payload.longitude;
		aircrafts[ payload.hlaObjetName ].position = Cesium.Cartesian3.fromDegrees( lon, lat, 0.0 );
		aircrafts[ payload.hlaObjetName ].properties = payload;
	}
	
	if( mapSimulationType == "VIRTUAL" ) {
		console.log("ALERTA!! Atualizacao para VIRTUAL precisa resolver o problema da altitude como String")
	}
	
}

// Funcao centralizadora.
function criaAircraft( payload ){
	if( mapSimulationType == "CONSTRUTIVA" ) criaAircraftContrutiva( payload );	
	if( mapSimulationType == "VIRTUAL" ) criaAircraftVirtual( payload );	
}

/*
function criaAircraftVirtual( payload ){
	// Cria o objeto na interface
	// e posiciona ele no mapa
	
	
	var xyz = payload.spatialVariant.worldLocation;
	console.log( getLatLogFromCartesian( new Cesium.Cartesian3( xyz[0], xyz[1], xyz[2] ) ) );	
	
	var po = getPositionOrientationData( payload );
	
	var airPlane = new Cesium.Entity({
		name : name : "AIRCRAFT_S",
		position: po.thePosition,
		orientation: po.theOrientation,
		show: true,
		model: {
			uri: '/resources/models/air/a319.glb',
			minimumPixelSize : 128,
			maximumScale : 500,
			color: Cesium.Color.RED

		},
		label: {
			text: payload.marking.text,
			style: Cesium.LabelStyle.FILL,
			fillColor: Cesium.Color.BLACK,
			outlineWidth: 1,
			font: '10px Consolas',
			eyeOffset: new Cesium.Cartesian3(0.0, 230.0, 0.0)
		}
	});
	viewer.entities.add( airPlane );

	aircrafts[ payload.hlaObjetName ] = airPlane;
}
*/

function criaAircraftContrutiva( payload ){
	// https://spatialillusions.com/milsymbol/documentation.html

	var phi = payload.orientationPhi;
	var lat = payload.latitude;
	var lon = payload.longitude;
	var thePosition = Cesium.Cartesian3.fromDegrees( lon, lat, 0.00 );
	
	var svgUrl = "http://192.168.0.101:36002/SHAPMF------.png?size="+natoSymbolSize+"&additionalInformation=" + payload.hlaObjetName;
	
	var airPlane = new Cesium.Entity({
		name : "AIRCRAFT_C",
		properties : payload,
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
	aircrafts[ payload.hlaObjetName ] = airPlane;
	
}
