function createClassTable( classes ){
	var theTable = '<div class="col-md-12 col-sm-12 col-xs-12"><div class="box"><div class="box-header"><h3 class="box-title">Classes</h3>' +
	'<div class="box-body table-responsive">' +
	'<table id="classesDataTable" class="table table-bordered table-striped">';

	theTable = theTable + '<thead><tr>' +
	'<th>&nbsp;</th>' +
	'<th>Handle</th>' +
	'<th>Name</th>' +
	'<th>Module</th>' +
	'<th>Sharing</th>' +
	'</tr></thead><tbody>';

	for( var x = 0; x < classes.length; x++ ){
		var classe = classes[x];
		var moduleName = classe.moduleName;
		var className = classe.myName;
		var parentClassName = classe.myParentName;
		var handle = classe.handle;
		var sharing = classe.sharing;
		var attributes = classe.attributes;

		var showLink = '<button type="button" onclick="showClass(\''+ handle +'\');" class="btn btn-default btn-xs btn-flat"><i class="fa fa-mail-forward"></i></button>'; 

		theTable = theTable + '<tr>' +
		'<td>'+showLink+'</td>' +
		'<td>'+handle+'</td>' +
		'<td>'+className+'</td>' +
		'<td>'+moduleName+'</td>' +
		'<td>'+sharing+'</td>' +
		'</tr>';

	}

	theTable = theTable + '</tbody></table></div></div></div>';
	$("#mainContentData").append( theTable );
	$('#classesDataTable').DataTable();
}


function showClass( handle ){
	$.ajax({
		url:"/classes/byhandle/" + handle, 
		type: "GET", 
		success: function( classe ) {
			emptyPanels();			
			
			var moduleName = classe.moduleName;
			var className = classe.myName;
			var parentClassName = classe.myParentName;
			var sharing = classe.sharing;
			var attributes = classe.attributes;

			var theTable = '<div class="col-md-12 col-sm-12 col-xs-12"><div class="box box-primary"><div class="box-header"><h3 class="box-title">'+
            'Class '+className+'</h3></div>' +
			'<div class="box-body table-responsive">' +
			'<table id="classesDataTable" class="table table-bordered table-striped">';

			theTable = theTable + '<thead><tr>' +
			'<th>Handle</th>' +
			'<th>Module</th>' +
			'<th>Sharing</th>' +
			'</tr></thead><tbody>';
			
			theTable = theTable + '<tr>' +
			'<td>'+handle+'</td>' +
			'<td>'+moduleName+'</td>' +
			'<td>'+sharing+'</td>' +
			'</tr>';
			
			theTable = theTable + '</tbody></table></div></div></div>';
			$("#mainContentData").append( theTable );		

			
			var attributesCard = '<table class="tableModule">' +
			'<tr class="fieldsModuleHeader">' +
			'<th style="width:25%;border: 1px solid">Name</th>' +
			'<th style="width:25%;border: 1px solid">Data Type</th>' +
			'<th style="width:50%;border: 1px solid">Semantics</th>' +
			'</tr>'; 		
			
			for( var yy=0; yy<attributes.length; yy++ ) {
				attributesCard = attributesCard + '<tr class="fieldsModuleBody">' +
				'<td style="border: 1px solid">'+attributes[yy].name+'</td>' +
				'<td style="border: 1px solid">'+attributes[yy].dataType+'</td>' +
				'<td style="border: 1px solid">'+attributes[yy].semantics+'</td>' +
				'</tr>';		
			}
			attributesCard = attributesCard + '</table>';			
			
			
			
			var theCard = '<div class="col-md-12 col-sm-12 col-xs-12">' +
		    '<div class="box box-primary"><div class="box-header"><h3 class="box-title">Attributes</h3></div>' +
			    '<div class="box-body table-responsive">'+attributesCard+'</div>' +
		    '</div></div>';
			
			
			$("#mainContentDataLine2").append( theCard );			
		},		
	    error: function(xhr, textStatus) {
	    	//
	    }, 		
	});		

}