

function createInteractionTable( interactions ){
	
	var theTable = '<div class="col-md-12 col-sm-12 col-xs-12"><div class="box"><div class="box-header"><h3 class="box-title">Interactions</h3></div>' +
		'<div class="box-body table-responsive">' +
		'<table id="interactionDataTable" class="table table-bordered table-striped">';

	theTable = theTable + '<thead><tr>' +
          '<th>File</th>' +
          '<th>Handle</th>' +
          '<th>Name</th>' +
          '<th>Sharing</th>' +
          '<th>Transportation</th>' +
          '<th>Order</th>' +
          '<th>Semantics</th>' +
          '<th style="width:30%">Parameters</th>' +
        '</tr></thead><tbody>';
	
	for( var x = 0; x < interactions.length; x++ ){
		var interaction = interactions[x];
		var fileName = interaction.moduleName;
		var name = interaction.name;
		var sharing = interaction.sharing;
		var transportation = interaction.transportation;
		var order = interaction.order;
		var semantics = interaction.semantics;
		var parameters = interaction.parameters;
		var handle = interaction.handle;

		var parameterCard = '<table class="tableModule">' +
		'<tr class="fieldsModuleHeader">' +
		'<th style="width:25%;border: 1px solid">Name</th>' +
		'<th style="width:25%;border: 1px solid">Data Type</th>' +
		'<th style="width:50%;border: 1px solid">Semantics</th>' +
		'</tr>'; 		
		
		for( var yy=0; yy<parameters.length; yy++ ) {
			parameterCard = parameterCard + '<tr class="fieldsModuleBody">' +
			'<td style="border: 1px solid">'+parameters[yy].name+'</td>' +
			'<td style="border: 1px solid">'+parameters[yy].dataType+'</td>' +
			'<td style="border: 1px solid">'+parameters[yy].semantics+'</td>' +
			'</tr>';		
		}
		parameterCard = parameterCard + '</table>';			
		

		if( parameters.length == 0 ) parameterCard = "&nbsp;";
		
		theTable = theTable + '<tr>' +
        '<td>'+fileName+'</td>' +
        '<td>'+handle+'</td>' +
        '<td>'+name+'</td>' +
        '<td>'+sharing+'</td>' +
        '<td>'+transportation+'</td>' +
        '<td>'+order+'</td>' +
        '<td>'+semantics+'</td>' +
        '<td>'+ parameterCard +'</td>' +
      '</tr>';
        		
	}
	
	theTable = theTable + '</tbody></table></div></div></div>';
	$("#mainContentData").append( theTable );
	
	$('#interactionDataTable').DataTable();
	
}


