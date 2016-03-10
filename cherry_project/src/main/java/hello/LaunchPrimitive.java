package hello;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class LaunchPrimitive {
	
	private static String url = PoppyController.url;
	
	
	public static void playBehaviorPrimitive(String behavior){
		
		//String url = "http://127.0.0.2:8888";
		//System.out.println("\nLaunch behavior :" + behavior);
		try {
			HttpURLConnectionExample.sendGet(url + "/primitive/" + behavior + "/start.json");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void playSpeakPrimitive(String txtString){
		
		//String url = "http://127.0.0.2:8888";
		
		String primitive = "speak";
    	String property = "sentence_to_speak";
    	
    	String str = "\"" + txtString + "\"";
    
    	// Start Primitive
    		    			;
		//System.out.println("\nText to speak :" + txtString);
		try {
			HttpURLConnectionExample.sendPost(url + "/primitive/" + primitive + "/property/" + property +"/value.json", str);
			//System.out.println("\nText_to_speak :" + txtString);
			HttpURLConnectionExample.sendGet(url + "/primitive/" + primitive + "/start.json");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
  	
	}
	
	public static String getRunningPrimitiveList(){
		
		//String url = "http://127.0.0.2:8888";
 
    	
    	// Start Primitive
    	String current_primitive  = new String();	    			;
		try {
			current_primitive = HttpURLConnectionExample.sendGet(url + "/primitive/running/list.json");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return current_primitive;
	}


}
