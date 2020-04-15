var aircrafts = [];


function solicitaAircrafts(){
	// pede as aeronaves ao backend.
	// posso ter chegado aqui apos a simulaçao ja ter comecado
	aircrafts = [];
}


function newAircraft( payload ){
	// Decidi nao fazer nada.
	// Nao tem atributos ou sao atributos padrao.
}

function updateAircrafts( payload ){
	// verifica se ja tenho essa aeronave
	for( var x = 0; x < aircrafts.length; x++ ) {
		var aircraft = aircrafts[x];
		// Verifico se este objeto ja foi recebido
		if( aircraft.objectName === payload.objectName ) {
			// se ele ja esta na interface, so atualizo e saio
			// coloco a aeronave Cesium no objeto de atualizacao
			payload.airPlane = aircraft.airPlane;
			atualizaAircraft( payload );
			aircrafts[x] = payload;
			return;
		}
	}

	// Se por qualquer motivo eu recebi uma atualizacao deste objeto
	// mas nao conhecia ele ainda, preciso armazenar e criar na interface
	// Ja vou criar logo na interface porque ele ja tem atributos atualizados
	var aircraft = criaAircraft( payload );
	payload.airPlane = aircraft;
	aircrafts.push( payload );

}

function atualizaAircraft( payload ){
	// atualizo um objeto que ja esta no mapa
	var po = getPositionOrientationData( payload );
	payload.airPlane.position = po.thePosition;
	payload.airPlane.orientation = po.theOrientation;
	
}

function getPositionOrientationData( payload ){
	var result = {};
	var lat = payload.latitude;
	var lon = payload.longitude;
	var alt = payload.altitude;
	
	// https://mathworld.wolfram.com/EulerAngles.html
	// theta is pitch, psi is roll, and phi is yaw/heading.	
	var orientation = payload.spatialVariant.orientation;
	var psi = orientation[0];
	var theta = orientation[1];
	var phi = orientation[2];

	//var ellipsoid = viewer.scene.globe.ellipsoid;
	var thePosition = Cesium.Cartesian3.fromDegrees( lon, lat, alt );
	
	var pitch = Cesium.Math.toRadians( theta );
	var roll = Cesium.Math.toRadians( psi );
	var heading = Cesium.Math.toRadians( phi );

	var hpr = new Cesium.HeadingPitchRoll(heading, roll, pitch);
	var theOrientation = Cesium.Transforms.headingPitchRollQuaternion(thePosition, hpr);
	result.theOrientation = theOrientation;
	result.thePosition = thePosition;
	return result;
}

function criaAircraft( payload ){
	// Cria o objeto na interface
	// e posiciona ele no mapa
	
	var po = getPositionOrientationData( payload );
	
	var airPlane = new Cesium.Entity({
		name : payload.objectName,
		position: po.thePosition,
		orientation: po.theOrientation,
		show: true,
		model: {
			uri: '/resources/models/a319.glb',
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
			eyeOffset: new Cesium.Cartesian3(0.0, 170.0, 0.0)
		}
	});
	viewer.entities.add( airPlane );

	return airPlane;
}


function criaAircraftContrutiva( payload ){
	var svgUrl = "http://192.168.0.101:36002/SFG-UCI---.png";
	var po = getPositionOrientationData( payload );
	
	viewer.entities.add({
		position: po.thePosition,
		billboard: {
			image: svgUrl
		}
	});	
	
}
