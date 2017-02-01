package cherry.robothandlers.service;


import org.apache.log4j.Logger;

//import cherry.gamehandlers.service.ToWebsite;
import cherry.robothandlers.web.SetupController;

public class LaunchPrimitive {
	
	private static Logger logger = Logger.getLogger(LaunchPrimitive.class);
	
	// Method to start "behave" primitives
	public static void startBehaviorPrimitive(String behavior){
		
		try {
			logger.info("Play Behave Primitive: " + behavior);
			HttpConnection.sendGet(SetupController.urlToRobot + "/primitive/" + behavior + "/start.json");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	// Method to stop "behave" primitives
	public static void stopPrimitive(String behavior){
		
		try {
			logger.info("Stop Behave Primitive: " + behavior);
			HttpConnection.sendGet(SetupController.urlToRobot + "/primitive/" + behavior + "/stop.json");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	// Method to start "speak" primitive
	public static void startSpeakPrimitive(String txtString){
		
		
		String primitive = "speak";
    	String property = "sentence_to_speak";
    	String currentUrlToRobot = SetupController.urlToRobot;
    	boolean dontWait = false;
    	boolean dontPlay = false;
    	
    	/* Enable two robots to move speak simultaneously 
    	if(txtString.indexOf("(dontwait)") != -1)
    	{
    		dontWait = true;
    		txtString = txtString.substring(txtString.indexOf(")")+1);
    		
    	}*/
    	
    	if(txtString.trim().isEmpty()){
    		System.out.println("\nEmpty field! The robot wont play");
    		dontPlay =true;
    	}
    	//int index_speak;
    	
    	String str = "\"" + txtString + "\"";
    	
    	
		try{
			Thread.sleep(100);
		}
		catch(Exception e){
			System.out.println("\nErreur " + e);
		}
    
    	// Start Primitive
		try {
			
			// Set Parameter to current robot
			if(dontPlay!=true){
				logger.info("Play Speak Primitive with parameter" + str);
				HttpConnection.sendPost(currentUrlToRobot + "/primitive/" + primitive + "/property/" + property +"/value.json", str);
			}
			// Wait for all robots to stop speaking
			if(dontWait != true){
				waitForSpeakToStop();			
			}
			// Back to current robot
			SetupController.urlToRobot = currentUrlToRobot;
			
			// Start speak
			if(dontPlay!=true){
				HttpConnection.sendGet(currentUrlToRobot + "/primitive/" + primitive + "/start.json");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
  	
	}
	
	// Waiting for all robots to stop speaking
	public static void waitForSpeakToStop(){
		
		int index_speak;

		for(Robot robot : LaunchPresentation.robotsUsed){
			
				System.out.println("\nWaiting for robot " + robot.getName() + " to stop speaking");
				SetupController.urlToRobot= robot.getIp().toString();
				
				do {
					try{
						Thread.sleep(100);
					}
					catch(Exception e){
						System.out.println("\nErreur " + e);
					}

					/////////////////////////////////////////////			
					String current_primitive = getRunningPrimitiveList();
		    		////////////////////////////////////////////
		    		
		    		index_speak = current_primitive.indexOf("speak");

		    	}
		    	while( index_speak != -1 );
				System.out.println("\nRobot " + robot.getName() + " stopped");
			}
			
	}	
	
	// Start Listen primitive
	public static void startListenPrimitive(){
		
    	String property = "listen";
    	
    	// check listen state
    	String listen_state = LaunchPrimitive.getListenStateParameter();
    	int is_state = listen_state.indexOf("stop");
    	System.out.println("\n		Listen_state: " + listen_state + " Index: " + is_state  );
    	
    	
    	// wait until speak is running  	
    	int index_speak;
    	int index_listen;
		String current_primitive  = new String();
    	// wait for end of speak if not in presentation state
		if(is_state == -1){
			do {
				try{
					Thread.sleep(500);
				}
				catch(Exception e){
					System.out.println("\nErreur " +e);
				}	    		
				
				current_primitive = LaunchPrimitive.getRunningPrimitiveList();	    		
	    		
	    		index_speak = current_primitive.indexOf("speak");
	    		System.out.println("\n			Primitive: " + current_primitive );
	    		System.out.println("\n			Wait for speak to stop... ");
	    	}
	    	while( index_speak != -1 );
		
		// Start Primitive
		//LaunchPrimitive.playSpeakPrimitive("Maintenant tu peux me parler");
		System.out.println("\n Parle!");
		//ToWebsite.setListeningSignal("on");
		//LaunchPrimitive.playBehaviorPrimitive("listen");
		
		try {
			// Local IP for the moment -> to be changed 
			HttpConnection.sendGet("http://127.0.0.2:8888" + "/primitive/" + property + "/start.json");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// wait until listen is finished
		do {
			try{
				Thread.sleep(500);
			}
			catch(Exception e){
				System.out.println("\nErreur " +e);
			}
    		//String current_primitive = LaunchPrimitive.getRunningPrimitiveList();
    		
			index_listen = current_primitive.indexOf("listen");
    		System.out.println("\n			Primitive: " + current_primitive );
    		System.out.println("\n			Wait for listen..." );
    	}
    	while( index_listen != -1 );
		}
		
    	System.out.println("\n Listen finished");
    	//ToWebsite.setListeningSignal("off");
	}
	
	// Get all primitives running
	public static String getRunningPrimitiveList(){
		

    	// Start Primitive
    	String current_primitive  = new String();
		try {
			current_primitive = HttpConnection.sendGet(SetupController.urlToRobot + "/primitive/running/list.json");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return current_primitive;
	}
	
	// get Listen state of th robot
	public static String getListenStateParameter(){
		
		
		String primitive = "listen";
    	String property = "listen_state";
    	String listen_state = new String();
    
    	// Start Primitive

		try {
			// Local IP for the moment -> to be changed 
			listen_state = HttpConnection.sendGet("http://127.0.0.2:8888" + "/primitive/" + primitive + "/property/" + property);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listen_state;
  	
	}
	
	// Set Listen state of the robot
	public static void setListenStateParameter(String txtString){
		
		
		String primitive = "listen";
    	String property = "listen_state";
    	
    	String str = "\"" + txtString + "\"";
    
    	// Start Primitive

		try {
			// Local IP for the moment -> to be changed 
			HttpConnection.sendPost("http://127.0.0.2:8888" + "/primitive/" + primitive + "/property/" + property +"/value.json", str);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
  	
	}
	
	// Specific method to change language spoken by the robot
	public static void setSpeakLanguage(String langString){
		
		
		String primitive = "speak";
    	String property = "language";
    	
    	
    	String str = "\"" + langString.toLowerCase() + "\"";
    
    	// Start Primitive

		try {
			HttpConnection.sendPost(SetupController.urlToRobot + "/primitive/" + primitive + "/property/" + property +"/value.json", str);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
  	
	}
	
	// Generic method to set the property "property" of the primitive "primitive" with the value "value"
	public static void setPrimitiveParameter(String primitive, String property, String value){
		
    	  	
    	String str = "\"" + value.toLowerCase() + "\"";
    
		try {
			
			HttpConnection.sendPost(SetupController.urlToRobot + "/primitive/" + primitive + "/property/" + property +"/value.json", str);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
  	
	}
	
	// Reset the Audio file system on the robot -> all files are deleted
	public static void resetAudioSystem(){
		
		try {
			logger.info("Reset Audio System");
			HttpConnection.sendGet(SetupController.urlToRobot + "/primitive/" + "reset_audio" + "/start.json");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Specific method to send a full movement from server
	// Unused
	/*public static void sendMovementToRobot(String string){
		

		String primitive = "play_movement";
    	String property = "movement";
    	
    	String str = string ;
    
    	// Start Primitive

		try {
			HttpConnection.sendPost("http://127.0.0.2:8888" + "/primitive/" + primitive + "/property/" + property +"/value.json", str);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}*/
	
	// Specific method to start the mvt sent from th server
	// Unused
	/*public static void playMovement(){
		
	  	
		String primitive = "play_movement";
    	
    
    	// Start Primitive

		try {
			//logger.info("Set Language to : " + langString);
			HttpConnection.sendGet("http://127.0.0.2:8888" + "/primitive/" + primitive + "/start.json");
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
  	
	}*/
	

}
