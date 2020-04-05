package br.com.cmabreu.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.cmabreu.misc.JSONHelper;
import br.com.cmabreu.model.InteractionClassList;
import br.com.cmabreu.model.Module;
import br.com.cmabreu.model.ObjectClassList;
import br.com.cmabreu.rti1516e.Attribute;
import br.com.cmabreu.rti1516e.InteractionClass;
import br.com.cmabreu.rti1516e.ObjectClass;

@Service
public class ModuleProcessorService {
	private Logger logger = LoggerFactory.getLogger( ModuleProcessorService.class );
	private ObjectClassList objectList;
	private InteractionClassList interactionClassList;
	
	public ModuleProcessorService() {
		this.objectList = new ObjectClassList();
		this.interactionClassList = new InteractionClassList();
	}
	
	public ObjectClassList getObjectList() {
		return objectList;
	}
	
	public InteractionClassList getInteractionClassList() {
		return interactionClassList;
	}
	
	
	private void addToList( JSONObject objectClass, String parentName, String moduleName, String myFullQualifiedName ) throws Exception {
		// The last in the chain (leaf) must process this one.
		String sharing = JSONHelper.getString(objectClass,"sharing");
		String semantics = JSONHelper.getString(objectClass,"semantics");
		// Read Attributes
		List<Attribute> attributes = new ArrayList<Attribute>();
		if( objectClass.has("attribute") ) {
    		Object obj = objectClass.get("attribute");
    		
    		if ( obj instanceof JSONArray ) {
    			JSONArray objArray = (JSONArray)obj;
        		for( int x=0; x < objArray.length(); x++ ) {
        			attributes.add( new Attribute( objArray.getJSONObject( x ) ) );
        		}
    		} else {
    			attributes.add( new Attribute( (JSONObject)obj ) );
    		}
    		
		}
		objectList.add( new ObjectClass( parentName, myFullQualifiedName, attributes, sharing, moduleName, semantics) );
	}
	
	
    private Integer navigate( JSONObject objectClass, String parentName, String moduleName ) throws Exception{
    	Integer counter = 0;
    	String myName = JSONHelper.getString(objectClass,"name");
    	String separator = "";
    	if( !parentName.equals("") ) separator = ".";
    	String myFullQualifiedName = parentName + separator +  myName;
    	
    	if( !objectClass.has("objectClass") ) {
    		// add
    		addToList( objectClass, parentName, moduleName, myFullQualifiedName );
    		// have just me
    		counter = 1;
    	} else {
    		// add
    		addToList( objectClass, parentName, moduleName, myFullQualifiedName );
    		// Go deeper!
    		Object obj = objectClass.get("objectClass");
    		if ( obj instanceof JSONArray ) {
    			JSONArray objArray = (JSONArray)obj;
        		for( int x=0; x < objArray.length(); x++ ) {
        			JSONObject childObjectClass = objArray.getJSONObject( x );
        			counter = counter + navigate( childObjectClass, myFullQualifiedName, moduleName );
        		}
    		} else {
    			JSONObject childObjectClass = (JSONObject)obj;
    			counter = counter + navigate( childObjectClass, myFullQualifiedName, moduleName );
    		}
    	}
    	return counter;
    }	
    
    public void parseModules( List<Module> modules ) throws Exception {
    	this.objectList.getList().clear();
    	for( Module module : modules ) {
    		logger.info("Processing file " + module.getFileName() );
    		JSONObject jobj = getXMLFromModuleFile( module.getFile() ).getJSONObject("objectModel");
    		
    		if( jobj.has("dataTypes") ) {
    			Object obj = jobj.get("dataTypes");
    			if ( obj instanceof JSONObject ) module.processDataTypes( jobj.getJSONObject("dataTypes") );
    		}
    		
    		if( jobj.has("transportations") ) {
        		Object obj = jobj.get("transportations");
        		if ( obj instanceof JSONObject ) {
        			JSONObject transps = jobj.getJSONObject("transportations");
        			module.setTransportations( transps.get("transportation") );    			
        		}
    		}
    		
    		if( jobj.has("dimensions") ) {
        		if ( jobj.get("dimensions") instanceof JSONObject ) {
        			JSONObject dims = jobj.getJSONObject("dimensions");
        			module.setDimensions( dims.get("dimension") );
        		}
    		}
    		
    		if( jobj.has("interactions") ) {
    			if ( jobj.get("interactions") instanceof JSONObject ) {
	    			JSONObject interactions = jobj.getJSONObject("interactions");
	    			if( interactions.has("interactionClass") ) {
	    				Object obj = interactions.get("interactionClass");
	    				
	    		    	JSONArray interactionClassArr = null;
	    				if ( obj instanceof JSONObject ) {
	    					interactionClassArr = new JSONArray();
	    					interactionClassArr.put( interactions.getJSONObject("interactionClass") );
	    				}
	    				if ( obj instanceof JSONArray ) {
	    					interactionClassArr = interactions.getJSONArray("interactionClass"); 
	    				}
	    				
	    				walkThruInteractions( "", interactionClassArr, module.getFileName() );
	    			}
    			}
    		}
    		
    		try {
	    		JSONObject objects = jobj.getJSONObject("objects");
	    		JSONObject objectClass = objects.getJSONObject("objectClass");
	    		module.setIdentification( jobj.getJSONObject("modelIdentification") );
	    		navigate( objectClass, "", module.getFileName() );
    		} catch( Exception e ) {
    			logger.warn("Elemento 'OBJECTS' nao encontrado.");
    		}
    	}
    	objectList.normalize();
    }

    
    private void walkThruInteractions( String parentName, JSONArray interactionClasses, String moduleName ) {
    	
		for( int x=0; x < interactionClasses.length(); x++ ) {
			JSONObject interactionClass = interactionClasses.getJSONObject( x );
			try {
				String name = JSONHelper.getString(interactionClass,"name");
				if( interactionClass.has("interactionClass") ) {
					Object obj = interactionClass.get("interactionClass");
			    	JSONArray interactionClassArr = null;
					if ( obj instanceof JSONObject ) {
						interactionClassArr = new JSONArray();
						interactionClassArr.put( interactionClass.getJSONObject("interactionClass") );
					}
					if ( obj instanceof JSONArray ) {
						interactionClassArr = interactionClass.getJSONArray("interactionClass"); 
					}
					walkThruInteractions( parentName + name + ".", interactionClassArr, moduleName );
				} else {
					try {
						interactionClassList.getList().add( new InteractionClass( parentName + name, interactionClass, moduleName ) );
					} catch ( Exception e ) {
						System.out.println( interactionClass.toString() );
						e.printStackTrace();
					}
				}
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
    }
    
	private JSONObject getXMLFromModuleFile( File module ) throws Exception {
		StringBuilder sb = new StringBuilder();
		Scanner sc = new Scanner( module );
		while ( sc.hasNextLine() ) {
			sb.append( sc.nextLine() );
		}
		sc.close(); 
		String result = sb.toString();
		JSONObject obj = XML.toJSONObject( result );
		logger.info( "Loaded XML file " +  module.getName() + " ( " + obj.getJSONObject("objectModel").getJSONObject("modelIdentification").get("name").toString() + " )" );
		return obj;
	}    
	
	
}
