var federates = [];

function receiveFederate( payload ){
	fireToast( 'info', 'Federado Online', payload.federateName );
	federates[ payload.hlaObjetName ] = payload;
	var ac = Object.keys(federates).length;
	$("#federatesCountNotification").text( ac );
	$("#objectsPanelCounter").text( ac );
}

function updateFederate( payload ){
	federates[ payload.hlaObjetName ] = payload;
	var ac = Object.keys(federates).length;
	$("#federatesCountNotification").text( ac );
	$("#objectsPanelCounter").text( ac );
	
	// Isso pode causar bagunça no painel. Os cards ficarão
	// trocando de posição porque o federado está sempre sendo atualizado.
	//jQuery("#"+payload.federateName).remove();
	//getTr( payload );
}

function showFederados(){
	jQuery(".federateFoundItem").remove();
	var fedKeys = Object.keys(federates);
	for( var x = 0; x < fedKeys.length; x++ ) {
		var key = fedKeys[x];
		jQuery("#federatesTable").append( getTr( federates[ key ] ) );
	}
}

function getTr( federate ){
	
	var icon = "nk.png";
	var nome = federate.federateName;
	var tipoCarto = federate.hlaObjetName;
	var sourceName = federate.classeTipo;
	
	var theDiv = "<div style='height:37px;width:100%;'>" +
	"<div style='float:left;width:35px;height:36px;'>" +
	"<img title='" + sourceName + "' style='border:1px solid #cacaca;width:30px;' src='/resources/img/"+icon+"'>" +
	"</div>" +
	"<div style='float:left;width:80%'><div style='height:18px;border-bottom:1px solid #cacaca'><b>"+nome+"</b></div><div style='height:15px;'>"+tipoCarto+"</div></div>" + 
	"</div>";
	
	var theTr = "<tr style='margin-bottom:2px;border-bottom: 2px solid #3c8dbc;' id='"+nome+"' class='federateFoundItem' ><td colspan='2' class='layerTable'>" + theDiv + "</td></tr>";
	return theTr;
}