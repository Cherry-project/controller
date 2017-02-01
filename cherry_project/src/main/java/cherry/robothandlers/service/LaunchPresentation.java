package cherry.robothandlers.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import cherry.gamehandlers.service.ToWebsite;
import cherry.robothandlers.web.SetupController;;

public class LaunchPresentation {
	
	// Status 
	public static boolean isPresentationRunning = false;
	private static boolean stop = false;
	private static boolean enablePicture =false; 
	
	private static ArrayList<Robot> robotList = SetupController.robotList;
	public static  ArrayList<Robot> robotsUsed = new ArrayList<Robot>(); 
	
	public static void playFromExcel( String excelFilePath) throws InterruptedException, IOException {
		
		enablePicture =false;
		String pathToWS = new String();
		// Listen signal "off"
		//ToWebsite.setListeningSignal("off");
		//isPresentationRunning = true;
		
		// Go into presentation state => enable stop while speaking
		//LaunchPrimitive.setListenStateParameter("presentation");
		
		
		// Start Listen Primitive
		//LaunchPrimitive.ListenPrimitive();
		
		// Stop head, start torso if not done
		/*String current_primitive = LaunchPrimitive.getRunningPrimitiveList();
		
		int index_torso = current_primitive.indexOf("torso_idle_motion");
		int index_head = current_primitive.indexOf("head_idle_motion");
		
		if(index_torso == -1){
			LaunchPrimitive.playBehaviorPrimitive("torso_idle_motion");
		}
		if(index_head != -1){
			LaunchPrimitive.stopPrimitive("head_idle_motion");
		}*/
		
		
		// Declare Arrays
		ArrayList<String> listBehave = new ArrayList<String>();
		ArrayList<String> listText = new ArrayList<String>();
		ArrayList<String> listImg = new ArrayList<String>();
		ArrayList<String> listRobot = new ArrayList<String>();
		ArrayList<String> listLanguage = new ArrayList<String>();
		
		// Get list of primitive into Excel file
		try {
			listBehave = ExcelReader.getExcelField(excelFilePath,"Behave");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		listBehave.add(0,"Start");
		System.out.println("\nList of " + "Behave: " + listBehave );
		
		// Get list of TTS into Excel file
		try {
			listText = ExcelReader.getExcelField(excelFilePath,"Text");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		listText.add(0,"Start");
		System.out.println("\nList of " + "Text: " + listText );
		
		//Get list of picture to display
		try {
			listImg = ExcelReader.getExcelField(excelFilePath,"Slide");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		listImg.add(0,"Start");
		System.out.println("\nList of " + "diapo: " + listImg );
		
		//Get list of picture to display
		try {
			listRobot= ExcelReader.getExcelField(excelFilePath,"Robot");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		listRobot.add(0,"Start");
		System.out.println("\nList of " + "diapo: " + listRobot );
		
		//Get list of language to display
		try {
			listLanguage= ExcelReader.getExcelField(excelFilePath,"Language");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		listLanguage.add(0,"Start");
		System.out.println("\nList of " + "language: " + listLanguage );
		
		play(listBehave, listText, listImg, listRobot, listLanguage, pathToWS);
	}
	
	public static void playFromJson(JSONObject myJson) throws InterruptedException, IOException {

		// Disable picture display
		enablePicture =false;
		robotsUsed.clear();
		
		String pathToWS = new String();
			
		int stepNb = myJson.length();
		
				
		
		// Declare Arrays
		ArrayList<String> listBehave = new ArrayList<String>();
		listBehave.add(0,"Start");
		ArrayList<String> listText = new ArrayList<String>();
		listText.add(0,"Start");
		ArrayList<String> listImg = new ArrayList<String>();
		listImg.add(0,"Start");
		ArrayList<String> listRobot= new ArrayList<String>();
		listRobot.add(0,"Start");
		ArrayList<String> listLanguage= new ArrayList<String>();
		listLanguage.add(0,"Start");
		
		// try to retrieve the Path To WS for display picture
		try{
			pathToWS = (String) myJson.get("Path_to_WS");
			System.out.println("\n Picture displayed at " + pathToWS);
			
			stepNb = stepNb -1;
			enablePicture = true;
		}
		catch( JSONException e)
		{
			System.out.println("\n No image display");
		}
		
		System.out.println("\n Nombre d'etapes: " + (stepNb));
		
		for(int i = 0; i < stepNb; i++ ) 
		{
			JSONObject loopJson = myJson.getJSONObject("_" + Integer.toString(i));
			System.out.println("\n" + loopJson.toString() );
			
			
			String behave = loopJson.getString("Behave"); 
			String text = loopJson.getString("Text"); 
			String slide = loopJson.getString("Slide");
			String robot= loopJson.getString("Robot"); 
			String language= loopJson.getString("Language");
			
			listBehave.add(behave.trim());
			listText.add(text);
			listImg.add(slide.trim());
			listRobot.add(robot.trim());
			listLanguage.add(language.trim());
		}
		
		Set<String> uniqueRobotList = new HashSet<String>(listRobot);
		uniqueRobotList.remove("Start");
		
		System.out.println("\n Behave: " + listBehave);
		System.out.println("\n Text: " + listText);
		System.out.println("\n Slide: " + listImg);
		System.out.println("\n Robot: " + listRobot);
		System.out.println("\n Language: " + listLanguage);
		
		
		// Define Used Robot list from Available Robot list
		for( String element : uniqueRobotList)
		{	
			for (Robot robot : robotList)
			{
				if (robot.getName().equals(element))
				{
					robotsUsed.add(robot);
				}
			}
		}
		
		try{
			play(listBehave, listText, listImg, listRobot, listLanguage, pathToWS);
		}
		catch(Exception e)
		{
			System.out.println("\n Erreur" + e);
		}
	
	
	}
		// Play presentation
	public static void play(ArrayList<String> listBehave, ArrayList<String> listText, ArrayList<String> listImg, ArrayList<String> listRobot, ArrayList<String> listLanguage, String pathToWS ) throws InterruptedException, IOException {
		
		isPresentationRunning = true;
		
		
		// Make them move
		makeRobotMove();
		
		// Set indexes
		int indexBehave = -1;
		int indexSpeak = -1;
		int indexImg = -1;
		
		
		// Stop head, start torso if not done
		//String currentPrimitive = LaunchPrimitive.getRunningPrimitiveList();
		
		
		//Play presentation
		for(int i=1; i< listBehave.size(); i++){
			
			// In case of stop
			if (stop){
				LaunchPrimitive.startSpeakPrimitive("D'accord, j'arr\u00eate la pr\u00e9sentation");	
				stop = false;
				break;
			}
			
			// Check currently running primitive		 
			String currentPrimitive = LaunchPrimitive.getRunningPrimitiveList();
			System.out.println("\nStr " + currentPrimitive );
			
			indexBehave = currentPrimitive.indexOf(listBehave.get(i-1));
			indexSpeak = currentPrimitive.indexOf("speak");
			
			System.out.println("\nIndex: " + indexBehave +"of :" + listBehave.get(i-1) + " speak: " + indexSpeak);
			
			// Check wether speak or behave are still running
			while(indexBehave != -1 && listText.get(i).indexOf("(dontwait)") == -1)
			{
				Thread.sleep(500);
				
				System.out.println("\n			Wainting for " + listBehave.get(i-1) + " to stop" );
				
				currentPrimitive = LaunchPrimitive.getRunningPrimitiveList();
				System.out.println("\n			Str " + currentPrimitive );
				
				indexBehave = currentPrimitive.indexOf(listBehave.get(i-1));
    			indexSpeak = currentPrimitive.indexOf("speak");
				
    			System.out.println("\n			Index: " + indexBehave +" of behave: " + listBehave.get(i-1) + " of speak: " + indexSpeak);
    			
			}
			
			// Display picture if allowed		
			if ( !listImg.get(i).equals(listImg.get(i-1)) && enablePicture)
			{	
				System.out.println("\n Old: " + listImg.get(i-1) + " New: " + listImg.get(i));
				
				// Shut down previous img (except "start)
  				if (listImg.get(i-1) != "Start"){
					
  					ToWebsite.deletePicture(pathToWS, listImg.get(i-1));

				}
				// set the new one
  				indexImg = i;
  				ToWebsite.displayPicture(pathToWS, listImg.get(indexImg));
  				
				Thread.sleep(1000);
			}

			
			// Get appropriate IP
			for(Robot robot : robotsUsed){
				
				System.out.println("\n Current Name"+ robot.getName() + " : " + robot.getIp() + "Name in JSON: " + listRobot.get(i).toString());
				
				if (robot.getName().equals(listRobot.get(i).toString()))
				{
					SetupController.urlToRobot= robot.getIp().toString();
					System.out.println("\n Set New IP to robot "+ robot.getName() + " : " + robot.getIp());
					break;
				}
			}
			
			//Set language if defined
			if(!listLanguage.get(i).isEmpty()){
				
				LaunchPrimitive.setSpeakLanguage(listLanguage.get(i));
				System.out.println("\n Set language to " + listLanguage.get(i));
			}
			
			// Speak
			if(!listText.get(i).trim().isEmpty()){
				LaunchPrimitive.startSpeakPrimitive(listText.get(i));
				System.out.println("\n Speak: " + listText.get(i));
			}
			
			// Behavior if not wait
			if (listBehave.get(i).indexOf("wait") == -1)
			{
				LaunchPrimitive.startBehaviorPrimitive(listBehave.get(i));
				System.out.println("\n Play behavior: " + listBehave.get(i));
			}
			//if wait, get time to wait
			else 
			{	
				int timeToWait = 0 ;
				
				try{
					timeToWait = Integer.parseInt(listBehave.get(i).substring(listBehave.get(i).indexOf("(")+1, listBehave.get(i).indexOf(")")));
				} catch (NumberFormatException nfe) {
					System.out.println("Not an Integer!" );
				}
				
				System.out.println("I wait " + Integer.toString(timeToWait) + "s");
				Thread.sleep(timeToWait*1000);

			}
			
		}
		
		Thread.sleep(4000);
		// Kill the last diapo
		if(enablePicture)
		{
			ToWebsite.deletePicture(pathToWS, listImg.get(indexImg));
		}
		
		// Stop all the robots engaged
		makeRobotStop();
		
		
		// check listen state
    	//String listen_state = LaunchPrimitive.getListenStateParameter();
    	//int is_state = listen_state.indexOf("stop");
    	
    	// Si pas en stop
    	//if(is_state == -1){
		// Go into normal state => disable stop while speaking
    	//	LaunchPrimitive.setListenStateParameter("normal");
    	//}
		// Set stop to 0
		stop = false;	
		isPresentationRunning = false;
		
		// Back to listen
		//LaunchPrimitive.ListenPrimitive();*/

	}
	public static void makeRobotMove(){
	//MAKE ROBOT MOVE
		
	// for each robot of available robot list
	for(Robot robot : robotsUsed){
		
			SetupController.urlToRobot= robot.getIp().toString();
			
			String current_primitive = LaunchPrimitive.getRunningPrimitiveList();
			
			int indexTorso = current_primitive.indexOf("torso_idle_motion");
			int indexHead = current_primitive.indexOf("head_idle_motion");
			
			if(indexTorso == -1){
				LaunchPrimitive.startBehaviorPrimitive("torso_idle_motion");
			}
			if(indexHead != -1){
				LaunchPrimitive.stopPrimitive("head_idle_motion");
			}
		}
		
	}
	
	public static void makeRobotStop(){
	// STOP ALL ROBOTS
		
	for(Robot robot  : robotsUsed){
			
			SetupController.urlToRobot= robot.getIp().toString();
			System.out.println("\n I stop "+ robot.getName());
			
			LaunchPrimitive.startBehaviorPrimitive("rest_open_behave");
			LaunchPrimitive.stopPrimitive("torso_idle_motion");
		}
	
	}

	
	public static void stop() throws InterruptedException, IOException {
		
		if(isPresentationRunning){
			stop = true;
			System.out.println("\n Set stop to \"true\"");
		}
		else
		{
			System.out.println("\n No presentation running");
		}
		//LaunchPrimitive.setListenStateParameter("normal");
		
	}
}
