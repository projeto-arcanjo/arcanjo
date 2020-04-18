
function displayClass( rootClass ){
	
	var detailsCard = '<table class="tableModule">' +
	'<tr class="fieldsModuleBody"><td style="width:10%; border: 1px solid">Módulo:</td><td style="border: 1px solid">'+rootClass.moduleName+'</td></tr>' +
	'<tr class="fieldsModuleBody"><td style="border: 1px solid">Descrição:</td><td style="border: 1px solid">'+rootClass.semantics+'</td></tr></table>';
	
	$("#detailsCard").html( detailsCard );
	$("#currentRootClass").text( getLastName( rootClass.myName ) );
	displayAttributes( rootClass.attributes );
	loadChildren( rootClass.myName );
}


function getLastName( className ){
	var tempArray = className.split(".");
	return tempArray[ tempArray.length -1 ];
}


function displayChildren( obj ){
	var childrenListContainer = "<ul id='childrenClasses' class='nav nav-pills nav-stacked'>";
	
	if( !obj ) return;
	for( x=0; x < obj.length; x++ ) {
		var classData = obj[x];
		var handle = classData.handle; 
		var liData = "<li><a onclick='loadClass(\""+ classData.myName + "\")' href='#'>" + getLastName( classData.myName ) + "<span class='label label-primary pull-right'>"+handle+"</span></a></li>";
		childrenListContainer = childrenListContainer + liData;
	}
	
	childrenListContainer = childrenListContainer + "</ul>";
	$("#chihldrenClassContainer").append( childrenListContainer );
}

function loadChildren( parentName ){
	var targetUrl = "/classes/byparentname/" + parentName.replaceAll(".","");
	$.ajax({
		url: targetUrl, 
		type: "GET", 
		success: function( obj ) {
			displayChildren( obj );
		},
	    error: function(xhr, textStatus) {
	    	//
	    }, 		
    });
}

function displayAttributes( attributes ){
	var attributesCard = '<table class="tableModule">' +
	'<tr class="fieldsModuleHeader">' +
	//'<th style="width:25%;border: 1px solid">Handle</th>' +
	'<th style="width:25%;border: 1px solid">Nome</th>' +
	'<th style="width:25%;border: 1px solid">Tipo</th>' +
	'<th style="width:50%;border: 1px solid">Descrição</th>' +
	'</tr>'; 		
	for( var yy=0; yy<attributes.length; yy++ ) {
		attributesCard = attributesCard + '<tr class="fieldsModuleBody">' +
		//'<td style="border: 1px solid">'+attributes[yy].handle+'</td>' +
		'<td style="border: 1px solid">'+attributes[yy].name+'</td>' +
		'<td style="border: 1px solid">'+attributes[yy].dataType+'</td>' +
		'<td style="border: 1px solid">'+attributes[yy].semantics+'</td>' +
		'</tr>';		
	}
	attributesCard = attributesCard + '</table>';			

	$("#attributesCard").append( attributesCard );			
}


function drawBreadCrumbs( className ){
	var breadCrumbNames = className.split(".");
	$("#classBreadCrumb").empty();
	var linkName = "";
	var separator = "";
	for( x=0; x < breadCrumbNames.length; x++  ) {
		if( x == breadCrumbNames.length-1 ) {
			$("#classBreadCrumb").append("<li class='active'>"+breadCrumbNames[x]+"</li>");
		} else {
			linkName = linkName + separator + breadCrumbNames[x];
			separator = ".";
			$("#classBreadCrumb").append("<li><a onclick='loadClass(\""+ linkName + "\")' href='#'>"+breadCrumbNames[x]+"</a></li>");
		}
	}
}

function loadClass( className ){
	drawBreadCrumbs( className );
	
	$("#chihldrenClassContainer").empty();
	$("#attributesCard").empty();
	
	var targetUrl = "/classes/byname/" + className.replaceAll(".","");
	$.ajax({
		url: targetUrl, 
		type: "GET", 
		success: function( obj ) {
			displayClass( obj );
		},
	    error: function(xhr, textStatus) {
	    	//
	    }, 		
    });
}

$( document ).ready(function() {
	
	// Prepara o scroll da caixa de classes
    $('#chihldrenClassContainer').slimScroll({
        height: '500px',
        wheelStep : 5,
    });
    
	// inicialmente chama a classe raiz da HLA
	loadClass("HLAobjectRoot");
	
});
