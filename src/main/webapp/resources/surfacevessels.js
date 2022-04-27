var surfaceVessels = [];

function newSurfaceVessel( payload ){
	// Decidi nao fazer nada.
	// Nao tem atributos ou sao atributos padrao.
}

function updateSurfaceVessel( payload ){
	
	if( surfaceVessels[ payload.hlaObjetName ] ){
		atualizaSurfaceVessel( payload );
	} else {
		// Se por qualquer motivo eu recebi uma atualizacao deste objeto
		// mas nao conhecia ele ainda, preciso armazenar e criar na interface
		// Ja vou criar logo na interface porque ele ja tem atributos atualizados
		criaSurfaceVessel( payload );
		updateCounters();
	}

}

function atualizaSurfaceVessel( payload ){
	// atualizo um objeto que ja esta no mapa
	if( mapSimulationType == "CONSTRUTIVA" ) {
		var lat = payload.latitude;
		var lon = payload.longitude;
		surfaceVessels[ payload.hlaObjetName ].position = Cesium.Cartesian3.fromDegrees( lon, lat, 0.0 );
		surfaceVessels[ payload.hlaObjetName ].properties = payload;
	}
	
	if( mapSimulationType == "VIRTUAL" ) {
		console.log("ALERTA!! Atualizacao para VIRTUAL precisa resolver o problema da altitude como String")
	}
	
}

// Funcao centralizadora.
function criaSurfaceVessel( payload ){
	if( mapSimulationType == "CONSTRUTIVA" )  criaSurfaceVesselContrutiva( payload );	
	if( mapSimulationType == "VIRTUAL" )  criaSurfaceVesselVirtual( payload );	
}

/*
function criaSurfaceVesselVirtual( payload ){
	// Cria o objeto na interface
	// e posiciona ele no mapa
	
	var po = getPositionOrientationData( payload );

	var surfaceVessel = new Cesium.Entity({
		name : "SURFACE_S",
		position: po.thePosition,
		orientation: po.theOrientation,
		show: true,
		model: {
			uri: '/resources/models/surface/nimitz/scene.gltf',
            minimumPixelSize : 10,
            maximumScale : 200,
            allowPicking : false
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
	viewer.entities.add( surfaceVessel );
	
	surfaceVessels[ payload.hlaObjetName ] = surfaceVessel;
	
}
*/

function criaSurfaceVesselContrutiva( payload ){
	
	var phi = payload.orientationPhi;
	var lat = payload.latitude;
	var lon = payload.longitude;
	var alt = 0;
	var thePosition = Cesium.Cartesian3.fromDegrees( lon, lat, 0.00 );
	var ip = document.location.host.split(':',2)[0];
	
	// https://spatialillusions.com/milsymbol/documentation.html
	// https://spatialillusions.com/unitgenerator/
	// MIL-STD-2525D
	var svgUrl = milsymbolLocation + ":35002/10033000001202040400.png?size="+natoSymbolSize+"&additionalInformation=" + payload.hlaObjetName
	/*
	 * 		Simulation
	 * 		Sea Surface
	 * 		Friend
	 * 		IconMilitary Combatant - Surface Combatant, Line - Frigate
	 * 		Escort
	 */
	
	var surfaceVessel = new Cesium.Entity({
		name : "SURFACE_C",
		position: thePosition,
		properties: payload,
		billboard: {
			image: svgUrl,
            eyeOffset: new Cesium.Cartesian3(0.0, 0.0, 0.0),
            horizontalOrigin: Cesium.HorizontalOrigin.CENTER,
            verticalOrigin: Cesium.VerticalOrigin.BOTTOM,
            scaleByDistance : new Cesium.NearFarScalar(1.5e2, 1.0, 8.5e7, 0.3),
            disableDepthTestDistance : Number.POSITIVE_INFINITY, 
            heightReference : Cesium.HeightReference.CLAMP_TO_GROUND, // Construtiva senta no chao
            //translucencyByDistance : new Cesium.NearFarScalar(1.5e2, 2.0, 1.5e7, 0.5),
		}
	});	
	
	viewer.entities.add( surfaceVessel );
	surfaceVessels[ payload.hlaObjetName ] = surfaceVessel;
	
}

function getTr( vessel ){
	var icon = "nk.png";
	var nome = federate.federateName;
	var tipoCarto = federate.hlaObjetName;
	var sourceName = federate.classTipo;
	
	var theDiv = "<div style='height:37px;width:100%;'>" +
	"<div style='float:left;width:35px;height:36px;'>" +
	"<img title='" + sourceName + "' style='border:1px solid #cacaca;width:30px;' src='/resources/img/"+icon+"'>" +
	"</div>" +
	"<div style='float:left;width:80%'><div style='height:18px;border-bottom:1px solid #cacaca'><b>"+nome+"</b></div><div style='height:15px;'>"+tipoCarto+"</div></div>" + 
	"</div>";
	
	var theTr = "<tr style='margin-bottom:2px;border-bottom: 2px solid #3c8dbc;' id='"+nome+"' class='instanceFoundItem' ><td colspan='2' class='layerTable'>" + theDiv + "</td></tr>";
	return theTr;
}
