

function createInstanceTable( instances ){
	instancesMainCatalog = [];
	federatesMainCatalog = [];
	federationsMainCatalog = [];

	$("#menuInstances").empty();
	$("#menuFederates").empty();
	$("#menuFederations").empty();
	
	
	var theTable = '<div class="col-md-12 col-sm-12 col-xs-12"><div class="box"><div class="box-header"><h3 class="box-title">Object Instances</h3><br>Attributes are from the class definition not those published by this instance.</div>' +
		'<div class="box-body table-responsive">' +
		'<table id="instanceDataTable" class="table table-bordered table-striped">';

	theTable = theTable + '<thead><tr>' +
          '<th>Handle</th>' +
          '<th>Class</th>' +
          '<th>Module</th>' +
          '<th>Name</th>' +
          '<th style="width:40%">Class Attributes</th>' +
        '</tr></thead><tbody>';
	
	for( var x = 0; x < instances.length; x++ ){
		var instance = instances[x];
		
		addInstanceToIFace( instance );
		
		var handle = instance.objectInstanceHandle;
		var name = instance.objectName;
		var className = instance.objectClass.myName;
		var moduleName = instance.objectClass.moduleName;
		var attributes = instance.objectClass.attributes;

		if( !name.startsWith("MOM.") ) {
		
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
			
	
			if( attributes.length == 0 ) attributesCard = "&nbsp;";
			
			theTable = theTable + '<tr>' +
	        '<td>'+handle+'</td>' +
	        '<td>'+className+'</td>' +
	        '<td>'+moduleName+'</td>' +
	        '<td>'+name+'</td>' +
	        '<td>'+ attributesCard +'</td>' +
	      '</tr>';
		}
		
	}
	
	theTable = theTable + '</tbody></table></div></div></div>';
	
	$("#mainContentData").append( theTable );
	$('#instanceDataTable').DataTable();
	
}


