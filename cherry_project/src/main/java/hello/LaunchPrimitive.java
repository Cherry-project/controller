package hello;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class LaunchPrimitive {
	
	private static String url_to_robot = PoppyController.url_to_robot;
	
	
	public static void playBehaviorPrimitive(String behavior){
		
		try {
			HttpURLConnectionExample.sendGet(url_to_robot + "/primitive/" + behavior + "/start.json");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void stopPrimitive(String behavior){
		
		try {
			HttpURLConnectionExample.sendGet(url_to_robot + "/primitive/" + behavior + "/stop.json");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void playSpeakPrimitive(String txtString){
		
		
		String primitive = "speak";
    	String property = "sentence_to_speak";
    	
    	String str = "\"" + txtString + "\"";
    
    	// Start Primitive

		try {
			HttpURLConnectionExample.sendPost(url_to_robot + "/primitive/" + primitive + "/property/" + property +"/value.json", str);
			HttpURLConnectionExample.sendGet(url_to_robot + "/primitive/" + primitive + "/start.json");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
  	
	}
	
	public static void ListenPrimitive(){
		
    	String property = "listen";
    	
    	// check listen state
    	String listen_state = LaunchPrimitive.getListenStateParameter();
    	int is_state = listen_state.indexOf("presentation");
    	System.out.println("\n		Listen_state: " + listen_state + " Index: " + is_state  );
    	
    	
    	// wait until speak is running  	
    	int index_speak;
    	int index_listen;
		
    	// wait for end of speak if not in presentation state
		if(is_state == -1){
			do {
				try{
					Thread.sleep(1000);
				}
				catch(Exception e){
					System.out.println("\nErreur " +e);
				}
	    		String current_primitive = LaunchPrimitive.getRunningPrimitiveList();
	    		index_speak = current_primitive.indexOf("speak");
	    		System.out.println("\n			Primitive: " + current_primitive );
	    		System.out.println("\n			Wait for speak to stop... ");
	    	}
	    	while( index_speak != -1 );
		}
		// Start Primitive
		//LaunchPrimitive.playSpeakPrimitive("Maintenant tu peux me parler");
		System.out.println("\n Parle!");
		ToWebsite.setListeningSignal("on");
		//LaunchPrimitive.playBehaviorPrimitive("listen");
		
		try {
			HttpURLConnectionExample.sendGet(url_to_robot + "/primitive/" + property + "/start.json");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// wait until listen is finished
		do {
			try{
				Thread.sleep(1000);
			}
			catch(Exception e){
				System.out.println("\nErreur " +e);
			}
    		String current_primitive = LaunchPrimitive.getRunningPrimitiveList();
    		index_listen = current_primitive.indexOf("listen");
    		System.out.println("\n			Primitive: " + current_primitive );
    		System.out.println("\n			Wait for listen..." );
    	}
    	while( index_listen != -1 );
    	/*try{
			Process p = Runtime.getRuntime().exec("python ../primitiveWS-master/src/stt/examples/ecoute.py");
			System.out.println("\n Done " + p.toString());

			// attente compréhensions
			
			while (p.isAlive())
			{	
				continue;
			}
			p.destroy();
		}
		catch(Exception e){
			System.out.println("\n Erreur" + e);
		}*/
		
    	System.out.println("\n Listen finished");
	}
	
	public static String getRunningPrimitiveList(){
		

    	// Start Primitive
    	String current_primitive  = new String();
		try {
			current_primitive = HttpURLConnectionExample.sendGet(url_to_robot + "/primitive/running/list.json");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return current_primitive;
	}
	
	public static String getListenStateParameter(){
		
		
		String primitive = "listen";
    	String property = "listen_state";
    	String listen_state = new String();
    
    	// Start Primitive

		try {
			listen_state = HttpURLConnectionExample.sendGet(url_to_robot + "/primitive/" + primitive + "/property/" + property);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listen_state;
  	
	}
	
	public static void setListenStateParameter(String txtString){
		
		
		String primitive = "listen";
    	String property = "listen_state";
    	
    	String str = "\"" + txtString + "\"";
    
    	// Start Primitive

		try {
			HttpURLConnectionExample.sendPost(url_to_robot + "/primitive/" + primitive + "/property/" + property +"/value.json", str);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
  	
	}


}
