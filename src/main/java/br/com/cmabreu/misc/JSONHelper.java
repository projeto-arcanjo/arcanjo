package br.com.cmabreu.misc;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONHelper {
	private static Logger logger = LoggerFactory.getLogger( JSONHelper.class );

	public static String getString( JSONObject jsonObj, String key) {
		try {
			//String value = jsonObj.getString( key );
			if( jsonObj.has(key) ) {
				String value = String.valueOf( jsonObj.get( key ) );
				return value;
			} else {
				//logger.error("Key '" + key + "' not found");
			}
		} catch ( Exception e ) {
			logger.error( e.getMessage() );
			System.out.println("--------------------------------------------");
			System.out.println( jsonObj.toString() );
			System.out.println("--------------------------------------------");
			
		}
		return "N/E";
	}

	
	
	public static Integer getInt( JSONObject jsonObj, String key) throws Exception {
		try {
			Integer value = jsonObj.getInt( key );
			return value;
		} catch ( Exception e ) {
			System.out.println( jsonObj.toString() );
			throw e;
		}
	}
	
	
	public static JSONArray getFlexible( JSONObject owner, String objectName ) {
		JSONArray result = new JSONArray();
		try {
			Object obj = owner.get( objectName );
			if ( obj instanceof JSONArray ) {
				result = (JSONArray)obj;
			} else {
				result.put( (JSONObject)obj );
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			System.out.println( owner.toString() );			
		}
		return result;
	}
	
	
}
