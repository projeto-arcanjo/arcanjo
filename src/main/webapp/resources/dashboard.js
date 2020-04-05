var countLogInteraction = 0;
var countLogAttribute = 0;
var maximumLogTable = 30;

var totalInteractionsCouter = 0;
var totalAttributesCounter = 0;


function processInteraction( interaction ){
	countLogInteraction++;
	totalInteractionsCouter++;
	if ( countLogInteraction == maximumLogTable ) {
		countLogInteraction--;
		$('#interactionsTable tbody tr:first').remove();
	}

	var line = '<tr class="fieldsModuleBody"><td style="border: 1px solid">'+interaction.handle+'</td>' +
	'<td style="border: 1px solid">'+ interaction.fullName +'</td><td style="border: 1px solid">'+ interaction.parameterCount +'</td></tr>';

	$( line ).appendTo('#interactionsTable tbody');
	$("#totalInteractionsCouter").text( totalInteractionsCouter );
	
}

function processReflectAttr( payload ){
	var values = payload.values;
	var objectInstanceHandle = payload.objectInstanceHandle;
	var tag = payload.tag;
	
	for( var x=0; x < values.length; x++  ) {
		var attribute = values[x].attribute;
		var value = values[x].value;

		countLogAttribute++;
		totalAttributesCounter++;
		if ( countLogAttribute == maximumLogTable ) {
			countLogAttribute--;
			$('#attributesTable tbody tr:first').remove();
		}

		var line = '<tr class="fieldsModuleBody"><td style="border: 1px solid">'+attribute.handle+'</td>' +
		'<td style="border: 1px solid">'+ attribute.name +'</td><td style="border: 1px solid">'+ attribute.dataType +'</td><td style="border: 1px solid">'+ value +'</td></tr>';

		$( line ).appendTo('#attributesTable tbody'); 
		$("#totalAttributesCounter").text( totalAttributesCounter );
		
	}

}