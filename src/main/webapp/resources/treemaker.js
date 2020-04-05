
var classesTree = [];


function existClassNode( fullName ){
	for ( var x=0; x < classesTree.length; x++  ) {
		if( classesTree[x].fullName === fullName ) {
			return true;
		} 
	}
	return false;
}

function getClassNode( fullName ){
	for ( var x=0; x < classesTree.length; x++  ) {
		if( classesTree[x].fullName === fullName ) {
			return classesTree[x];
		} 
	}
	return null;
}


function createClassNode( name, parentName, classes ){
	var theNode = {};
	var theNodeText = {};
	theNode.stackChildren = false;
	theNode.image = "/resources/img/diagram.png";
	//theNode.link = { href: "#" };

	var myFullName = name;
	if( parentName ) myFullName = parentName + "." + name; 
	var classe = getCompleteClass( myFullName, classes );

	theNodeText.name = name;
	if( classe != null ) {
		theNodeText.title = classe.sharing;	
		theNodeText.contact = classe.moduleName; 
		//theNodeText.desc = classe.handle; 
	}
	
	theNode.text = theNodeText;
	return theNode;
}


function getCompleteClass( classFullName, classes ) {
	for( var x=0; x < classes.length; x++ ) {
		var classe = classes[x];
		if( classe.myName === classFullName ) return classe;
	}
	return null;
}


function createClassTree( classes ){
	
	var config = {
			container: "#classDiagram",
			rootOrientation:  'WEST',
			siblingSeparation: 10,
	        subTeeSeparation: 10,			
			connectors: {
				type: 'step'
			},
			node: {
				HTMLclass: 'classDiagramTree'
			}
	};
	
	var chart_config = [ config ];
	classesTree = [];
	
	
	for( var x=0; x < classes.length; x++ ) {
		var classe = classes[x];
		var parentPath = classe.myName.split(".");
		var myName = parentPath[ parentPath.length - 1 ];
		
		var separator = "";
		var parentName = "";
		for( var y=0; y < parentPath.length -1; y++ ) {
			var grandParent = parentName;
			parentName = parentName + separator + parentPath[y];
			separator = ".";
			
			if( !existClassNode( parentName )  ){
				var classNode = {};
				classNode.name = parentPath[y];
				classNode.fullName = parentName; 
				classNode.theClass = createClassNode( classNode.name, grandParent, classes );
				
				var parentClassNode = getClassNode( grandParent );
				if( parentClassNode != null ){
					classNode.theClass.parent = parentClassNode.theClass; 	
				} else {
					//
				}
				
				classesTree.push( classNode );
				chart_config.push( classNode.theClass );

			} else {
				//console.log( " > " + parentPath[y] + " (" + parentName + ") already exists");
			}
			
		}
					
		var classNode = {};
		classNode.name = myName;
		classNode.fullName = classe.myName; 
		classNode.theClass = createClassNode( myName, parentName, classes );
		
		var parentClassNode = getClassNode( parentName );
		if( parentClassNode != null ){
			classNode.theClass.parent = parentClassNode.theClass; 	
		} else {
			//
		}
		
		classesTree.push( classNode );
		chart_config.push( classNode.theClass );
		
	}
	
	
	console.log( chart_config );
	new Treant( chart_config );
	
}

