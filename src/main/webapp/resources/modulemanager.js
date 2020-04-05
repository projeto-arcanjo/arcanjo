

function createModuleDetails( module ){
	
	var classCount = module.classCount;
	var transportationsCount = module.transportationsCount;
	var dimensionsCount = module.dimensionsCount;
	var interactionsCount = module.interactionsCount;
	var moduleImage = 'data:image/png;base64,' + module.glyph;
	var fileName = module.fileName;
	var name = module.name;
	var description = module.description;
	var purpose = module.purpose;

	var simpleDataTypes = module.dataTypes.simpleDataTypes;
	var arrayDataTypes = module.dataTypes.arrayDataTypes;
	var basicDataRepresentations = module.dataTypes.basicDataRepresentations;
	var fixedRecordDataTypes = module.dataTypes.fixedRecordDataTypes;
	var enumeratedDataTypes = module.dataTypes.enumeratedDataTypes;
	
	var classes = module.classes;
	
    var classesCards = "";
	for( var y = 0; y < classes.length; y++ ) {
		var classe = classes[y];
		if( classe.moduleName == module.fileName ){
			classesCards = classesCards + '<p><span class="label label-success" style="font-family=\'Courier New\'">' + classe.myName + '</span></p>';
		}
	}
	
	
	// Left panel
	var theCard = '<div class="col-md-12 col-sm-12 col-xs-12">' +
	'<div class="box-body table-responsive no-padding">'; 
	theCard = theCard + '<div class="box box-widget widget-user-2"><div style="background-color:#3c8dbc !important;" class="widget-user-header"><div class="widget-user-image">' +
        		  '<img src="' + moduleImage + '" alt="Image">' +
                  '</div><h3 class="widget-user-username">' + fileName + '</h3><h5 class="widget-user-desc">' + name + '</h5>' +
                  '</div>' +
                  '<div class="box-footer no-padding"><ul class="nav nav-stacked">' + 
                  '<li><a style="padding:5px 5px !important;" href="#">' + description + '</a></li>' +
                  '<li><a style="padding:5px 5px !important;" href="#">' + purpose + '</li>' +
                  '</ul></div></div></div></div>';

	
	// From here I'll build the right panel with data types 
	var simpleDataCard = '<table class="table table-hover">' +
	'<tr>' +
      '<th>Name</th>' +
      '<th>Representation</th>' +
      '<th>Accuracy</th>' +
      '<th>Units</th>' +
      '<th>Resolution</th>' +
      '<th>Semantics</th>' +
    '</tr>'; 
	for( var x = 0; x < simpleDataTypes.length; x++ ) {
		var simpleDataType = simpleDataTypes[x];
		simpleDataCard = simpleDataCard + '<tr>' +
		'<td>'+simpleDataType.name+'</td>' +
        '<td>'+simpleDataType.representation+'</td>' +
        '<td>'+simpleDataType.accuracy+'</td>' +
        '<td>'+simpleDataType.units+'</td>' +
        '<td>'+simpleDataType.resolution+'</td>' +
        '<td>'+simpleDataType.semantics+'</td>' +
      '</tr>';		
	}
	simpleDataCard = simpleDataCard + '</table>';
	

	var fixedRecordDataTypeCard = '<table class="table table-hover">' +
	'<tr>' +
	'<th>Name</th>' +
	'<th>Encoding</th>' +
	'<th>Semantics</th>' +
	'<th style="width:50%">Fields</th>' +
	'</tr>'; 
	for( var x = 0; x < fixedRecordDataTypes.length; x++ ) {
		var fixedRecordDataType = fixedRecordDataTypes[x];
		var fields = fixedRecordDataType.fields;
		var fieldCard = '<table class="tableModule">' +
		'<tr class="fieldsModuleHeader">' +
		'<th style="width:25%">Name</th>' +
		'<th style="width:25%">Data Type</th>' +
		'<th style="width:50%">Semantics</th>' +
		'</tr>'; 
		
		for( var y = 0; y < fields.length; y++ ) {
			var field = fields[y];
			fieldCard = fieldCard + '<tr class="fieldsModuleBody">' +
			'<td>'+field.name+'</td>' +
			'<td>'+field.dataType+'</td>' +
			'<td>'+field.semantics+'</td>' +
			'</tr>';		
		}
		fieldCard = fieldCard + '</table>';
		
		fixedRecordDataTypeCard = fixedRecordDataTypeCard + '<tr>' +
		'<td>'+fixedRecordDataType.name+'</td>' +
		'<td>'+fixedRecordDataType.encoding+'</td>' +
		'<td>'+fixedRecordDataType.semantics+'</td>' +
		'<td>'+fieldCard+'</td>' +
		'</tr>';		
	}
	fixedRecordDataTypeCard = fixedRecordDataTypeCard + '</table>';
	
	
	
	var enumeratedDataTypeCard = '<table class="table table-hover">' +
	'<tr>' +
	'<th>Name</th>' +
	'<th>Representation</th>' +
	'<th>Semantics</th>' +
	'<th>Enumerators</th>' +
	'</tr>'; 
	for( var x = 0; x < enumeratedDataTypes.length; x++ ) {
		var enumeratedDataType = enumeratedDataTypes[x];
		var enumerators = enumeratedDataType.enumerators;
		var enumeratorCard = '<table class="tableModule">' +
		'<tr class="fieldsModuleHeader">' +
		'<th>Name</th>' +
		'<th style="width: 30%;">Value</th>' +
		'</tr>'; 
		
		for( var y = 0; y < enumerators.length; y++ ) {
			var enumerator = enumerators[y];
			enumeratorCard = enumeratorCard + '<tr class="fieldsModuleBody">' +
			'<td>'+enumerator.name+'</td>' +
			'<td>'+enumerator.value+'</td>' +
			'</tr>';		
		}
		enumeratorCard = enumeratorCard + '</table>';
		
		enumeratedDataTypeCard = enumeratedDataTypeCard + '<tr>' +
		'<td>'+enumeratedDataType.name+'</td>' +
		'<td>'+enumeratedDataType.representation+'</td>' +
		'<td>'+enumeratedDataType.semantics+'</td>' +
		'<td>'+enumeratorCard+'</td>' +
		'</tr>';		
	}
	enumeratedDataTypeCard = enumeratedDataTypeCard + '</table>';
	


	var basicDataRepresentationCard = '<table class="table table-hover">' +
	'<tr>' +
	'<th>Name</th>' +
	'<th>Size</th>' +
	'<th>Encoding</th>' +
	'<th>Endian</th>' +
	'<th>Interpretation</th>' +
	'</tr>'; 
	for( var x = 0; x < basicDataRepresentations.length; x++ ) {
		var basicDataRepresentation = basicDataRepresentations[x];
		basicDataRepresentationCard = basicDataRepresentationCard + '<tr>' +
		'<td>'+basicDataRepresentation.name+'</td>' +
		'<td>'+basicDataRepresentation.size+'</td>' +
		'<td>'+basicDataRepresentation.encoding+'</td>' +
		'<td>'+basicDataRepresentation.endian+'</td>' +
		'<td>'+basicDataRepresentation.interpretation+'</td>' +
		'</tr>';		
	}
	basicDataRepresentationCard = basicDataRepresentationCard + '</table>';
	
	
	var arrayDataCard = '<table class="table table-hover">' +
	'<tr>' +
      '<th>Name</th>' +
      '<th>Cardinality</th>' +
      '<th>Data Type</th>' +
      '<th>Encoding</th>' +
      '<th>Semantics</th>' +
    '</tr>'; 
	for( var x = 0; x < arrayDataTypes.length; x++ ) {
		var arrayDataType = arrayDataTypes[x];
		arrayDataCard = arrayDataCard + '<tr>' +
		'<td>'+arrayDataType.name+'</td>' +
	    '<td>'+arrayDataType.cardinality+'</td>' +
	    '<td>'+arrayDataType.dataType+'</td>' +
	    '<td>'+arrayDataType.encoding+'</td>' +
	    '<td>'+arrayDataType.semantics+'</td>' +
	  '</tr>';		
	}
	arrayDataCard = arrayDataCard + '</table>';
	
	
	
	// Right tab panel with data types
	theCard = theCard + '<div class="col-md-12 col-sm-12 col-xs-12">' +
			'<div class="nav-tabs-custom">' +
				'<ul class="nav nav-tabs">' +
					'<li class="active"><a href="#simpleDT" data-toggle="tab">Simple</a></li>' +
					'<li><a href="#arrayDT" data-toggle="tab">Array</a></li>' +
					'<li><a href="#basicRepresentationsDT" data-toggle="tab">Basic Representations</a></li>' +
					'<li><a href="#fixedRecordDT" data-toggle="tab">Fixed Record</a></li>' +
					'<li><a href="#enumeratedDT" data-toggle="tab">Enumerated</a></li>' +
				'</ul>' +
				'<div class="tab-content">' +
					'<div class="active tab-pane" id="simpleDT">' + simpleDataCard + '</div>' +
					'<div class="tab-pane" id="arrayDT">' + arrayDataCard + '</div>' +
					'<div class="tab-pane" id="basicRepresentationsDT">' + basicDataRepresentationCard + '</div>' +
					'<div class="tab-pane" id="fixedRecordDT">' + fixedRecordDataTypeCard + '</div>' +
					'<div class="tab-pane" id="enumeratedDT">' + enumeratedDataTypeCard + '</div>' +
				'</div>' +
			'</div>' +
		'</div>';	
	
	
	$("#mainContentData").append( theCard );
	
	createClassHierarchy();
	// In treemaker.js
	createClassTree( classes );
}

function createClassHierarchy(){
	
	var theCard = '<div class="col-md-12 col-sm-12 col-xs-12">' +
	    '<div class="box box-primary">' +
		    '<div class="box-body"><div class="chart" id="classDiagram"></div></div>' +
	    '</div>' +
	'</div>';
	$("#mainContentDataLine2").append( theCard );
	
}

function createModuleTable( modules ){
	var theTable = '<div class="col-md-12 col-sm-12 col-xs-12"><div class="box"><div class="box-header"><h3 class="box-title">Modules</h3></div>' +
		'<div class="box-body table-responsive">' +
		'<table id="modulesDataTable" class="table table-bordered table-striped">';

	
	theTable = theTable + '<thead><tr>' +
          '<th>&nbsp;</th>' +
          '<th>File</th>' +
          '<th>Name</th>' +
          '<th>Description</th>' +
          '<th>Classes</th>' +
          //'<th>Interactions</th>' +
        '</tr></thead><tbody>';
	
	for( var x = 0; x < modules.length; x++ ){
		var module = modules[x];
		var classCount = module.classCount;
		var transportationsCount = module.transportationsCount;
		var dimensionsCount = module.dimensionsCount;
		var interactionsCount = module.interactionsCount;
		var moduleImage = 'data:image/png;base64,' + module.glyph;
		var fileName = module.fileName;
		var name = module.name;
		var description = module.description;
		var purpose = module.purpose;
		var classes = module.classes;

		$("#menuModules").append( '<li><a onclick="showModule(\'' + module.fileName +'\')" href="#"><i class="fa fa-file-code-o"></i> '+module.fileName+'</a></li>' );
		
	    var classesCards = "";
		for( var y = 0; y < classes.length; y++ ) {
			var classe = classes[y];
			if( classe.moduleName == module.fileName ){
				classesCards = classesCards + '<p><span class="label label-success" style="padding-bottom:3px;">' + classe.myName + '</span></p>';
			}
		}

		/*
	    var interactionsCards = "";
		for( var y = 0; y < interactions.length; y++ ) {
			var interaction = interactions[y];
			if( interaction.moduleName == module.fileName ){
				interactionsCards = interactionsCards + '<p><span class="label label-warning" style="padding-bottom:3px;">' + interaction.fullName + '</span></p>';
			}
		}
		*/
		
		var showLink = '<button type="button" onclick="showModule(\''+ fileName +'\');" class="btn btn-default btn-xs btn-flat"><i class="fa fa-mail-forward"></i></button>'; 
		
		theTable = theTable + '<tr>' +
		'<td>'+showLink+'</td>' +
        '<td>'+fileName+'</td>' +
        '<td>'+name+'</td>' +
        '<td>'+description+'</td>' +
        '<td>'+classesCards+'</td>' +
        //'<td>'+interactionsCards+'</td>' +
      '</tr>';
        
	}
	
	theTable = theTable + '</tbody></table></div></div></div>';
	$("#mainContentData").append( theTable );
	
	$('#modulesDataTable').DataTable();

	
}


