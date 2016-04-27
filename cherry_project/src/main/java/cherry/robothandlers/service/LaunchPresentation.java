package cherry.robothandlers.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

import cherry.gamehandlers.service.ToWebsite;
import cherry.robothandlers.web.SetupController;
import cherry.robotpresentateur.service.Robot;;

public class LaunchPresentation {
	
	// Status 
	public static boolean is_presentation_running = false;
	private static boolean stop = false;
	
	private static ArrayList<Robot> robot_list = SetupController.robot_list;
	public static  Set<String> robots_used; 
	
	public static void start( String excelFilePath) throws InterruptedException, IOException {
		
		// Listen signal "off"
		ToWebsite.setListeningSignal("off");
		is_presentation_running = true;
		
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
		ArrayList<String> list_behave = new ArrayList<String>();
		ArrayList<String> list_text = new ArrayList<String>();
		ArrayList<String> list_img = new ArrayList<String>();
		ArrayList<String> list_robot = new ArrayList<String>();
		
		// Get list of primitive into Excel file
		try {
			list_behave = SimpleExcelReaderExample.getExcelField(excelFilePath,"Behave");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		list_behave.add(0,"Start");
		System.out.println("\nList of " + "Behave: " + list_behave );
		
		// Get list of TTS into Excel file
		try {
			list_text = SimpleExcelReaderExample.getExcelField(excelFilePath,"Text");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		list_text.add(0,"Start");
		System.out.println("\nList of " + "Text: " + list_text );
		
		//Get list of picture to display
		try {
			list_img = SimpleExcelReaderExample.getExcelField(excelFilePath,"Slide");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		list_img.add(0,"Start");
		System.out.println("\nList of " + "diapo: " + list_img );
		
		//Get list of picture to display
		try {
			list_robot= SimpleExcelReaderExample.getExcelField(excelFilePath,"Robot");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		list_robot.add(0,"Start");
		System.out.println("\nList of " + "diapo: " + list_robot );
		
		play(list_behave,list_text,list_img, list_robot);
	}
	
	public static void playFromJson(JSONObject my_json) throws InterruptedException, IOException {
		// Set presentation bit
		is_presentation_running = true;
		
		int step_nb = my_json.length();
		System.out.println("\n Nombre d'etapes: " + step_nb);
		
		
		
		// Declare Arrays
		ArrayList<String> list_behave = new ArrayList<String>();
		list_behave.add(0,"Start");
		ArrayList<String> list_text = new ArrayList<String>();
		list_text.add(0,"Start");
		ArrayList<String> list_img = new ArrayList<String>();
		list_img.add(0,"Start");
		ArrayList<String> list_robot= new ArrayList<String>();
		list_robot.add(0,"Start");
		
		for(int i = 0; i < step_nb; i++ ) 
		{
			JSONObject loop_json = my_json.getJSONObject("_" + Integer.toString(i));
			System.out.println("\n" + loop_json.toString() );
			
			
			String behave = loop_json.getString("Behave"); 
			String text = loop_json.getString("Text"); 
			String slide = loop_json.getString("Slide");
			String robot= loop_json.getString("Robot"); 
			
			list_behave.add(behave.trim());
			list_text.add(text);
			list_img.add(slide.trim());
			list_robot.add(robot.trim());
			
		}
		
		System.out.println("\n Behave: " + list_behave);
		System.out.println("\n Text: " + list_text);
		System.out.println("\n Slide: " + list_img);
		System.out.println("\n Slide: " + list_robot);
	
		try{
			play(list_behave, list_text, list_img, list_robot);
		}
		catch(Exception e)
		{
			System.out.println("\n Erreur" + e);
		}
	
	
	}
		// Play presentation
	public static void play(ArrayList<String> list_behave, ArrayList<String> list_text, ArrayList<String> list_img, ArrayList<String> list_robot ) throws InterruptedException, IOException {
		
		// Robot to be used
		robots_used = new HashSet<String>(list_robot);
		
		// Make them move 
		for(Robot robot : robot_list){
			
			for(String robot_str : robots_used){
				
			
				if (robot.getName().equals(robot_str))
				{
					SetupController.url_to_robot= robot.getIp().toString();
					
					String current_primitive = LaunchPrimitive.getRunningPrimitiveList();
					
					int index_torso = current_primitive.indexOf("torso_idle_motion");
					int index_head = current_primitive.indexOf("head_idle_motion");
					
					if(index_torso == -1){
						LaunchPrimitive.playBehaviorPrimitive("torso_idle_motion");
					}
					if(index_head != -1){
						LaunchPrimitive.stopPrimitive("head_idle_motion");
					}
				}
			
			}
		}

		
		// Set indexes
		int index_behave = -1;
		int index_speak = -1;
		int index_img = -1;
		
		
		// Stop head, start torso if not done
		String current_primitive = LaunchPrimitive.getRunningPrimitiveList();
		
		/*int index_torso = current_primitive.indexOf("torso_idle_motion");
		int index_head = current_primitive.indexOf("head_idle_motion");
		
		if(index_torso == -1){
			LaunchPrimitive.playBehaviorPrimitive("torso_idle_motion");
		}
		if(index_head != -1){
			LaunchPrimitive.stopPrimitive("head_idle_motion");
		}*/
		
		//Play presentation
		for(int i=1; i< list_behave.size(); i++){
			
			// In case of stop
			if (stop){
				LaunchPrimitive.playSpeakPrimitive("D'accord, j'arr\u00eate la pr\u00e9sentation");
				//LaunchPrimitive.playBehaviorPrimitive("rest_open_behave");
				
				stop = false;
				break;
			}
			// Check currently running primitive
			 
			current_primitive = LaunchPrimitive.getRunningPrimitiveList();
			System.out.println("\nStr " + current_primitive );
			
			index_behave = current_primitive.indexOf(list_behave.get(i-1));
			index_speak = current_primitive.indexOf("speak");
			
			System.out.println("\nIndex: " + index_behave +"of :" + list_behave.get(i-1) + " speak: " + index_speak);
			
			// Check wether speak or behave are still running
			//while( index_speak != -1 && index_behave != -1)
			while(index_behave != -1)
			{
				Thread.sleep(1000);
				
				System.out.println("\n			Wainting for " + list_behave.get(i-1) + " to stop" );
				
				current_primitive = LaunchPrimitive.getRunningPrimitiveList();
				System.out.println("\n			Str " + current_primitive );
				
				index_behave = current_primitive.indexOf(list_behave.get(i-1));
    			index_speak = current_primitive.indexOf("speak");
				
    			System.out.println("\n			Index: " + index_behave +" of behave: " + list_behave.get(i-1) + " of speak: " + index_speak);
    			
			}
			
			// Picture			
			if ( !list_img.get(i).equals(list_img.get(i-1)))
			{	
				System.out.println("\n Old: " + list_img.get(i-1) + " New: " + list_img.get(i));
				// Shut down previous img (except "start)
  				if (list_img.get(i-1) != "Start"){
					
  					ToWebsite.deletePicture(list_img.get(i-1));

				}
				// set the new one
  				index_img = i;
  				ToWebsite.displayPicture(list_img.get(index_img));
  				
				Thread.sleep(1000);
			}
			
			// Wait for current robot to stop speaking
			//LaunchPrimitive.waitForSpeakToStop();
			
			// Get appropriate IP
			for(Robot robot : robot_list){
				
				System.out.println("\n Current Name"+ robot.getName() + " : " + robot.getIp() + "Name in JSON: " + list_robot.get(i).toString());
				
				if (robot.getName().equals(list_robot.get(i).toString()))
				{
					SetupController.url_to_robot= robot.getIp().toString();
					System.out.println("\n Set New IP to robot "+ robot.getName() + " : " + robot.getIp());
					break;
				}
			}
			// Speak
			LaunchPrimitive.playSpeakPrimitive(list_text.get(i));
			System.out.println("\n Speak: " + list_text.get(i));
			
			// Behavior if not wait
			if (list_behave.get(i).indexOf("wait") == -1)
			{
				LaunchPrimitive.playBehaviorPrimitive(list_behave.get(i));
				System.out.println("\n Play behavior: " + list_behave.get(i));
			}
			//if wait, get time to wait
			else 
			{	
				int time_to_wait = 0 ;
				
				try{
					time_to_wait = Integer.parseInt(list_behave.get(i).substring(list_behave.get(i).indexOf("(")+1, list_behave.get(i).indexOf(")")));
				} catch (NumberFormatException nfe) {
					System.out.println("Not an Integer!" );
				}
				
				System.out.println("I wait " + Integer.toString(time_to_wait) + "s");
				Thread.sleep(time_to_wait*1000);

			}
			
		}
		
		Thread.sleep(4000);
		// Kill the last diapo
		
		ToWebsite.deletePicture(list_img.get(index_img));
		
		// Stop all the robots engaged
		for(Robot robot : robot_list){
			
			for(String robot_str : robots_used){
				
			
				if (robot.getName().equals(robot_str))
				{
					SetupController.url_to_robot= robot.getIp().toString();
					System.out.println("\n I stop "+ robot.getName());
					
					LaunchPrimitive.playBehaviorPrimitive("rest_open_behave");
					LaunchPrimitive.stopPrimitive("torso_idle_motion");
				}
			
			}
		}
		
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
		is_presentation_running = false;
		
		// Back to listen
		//LaunchPrimitive.ListenPrimitive();*/

	}
	public static void stop() throws InterruptedException, IOException {
		
		if(is_presentation_running){
			stop = true;
			System.out.println("\n Set stop to 1");
		}
		else
		{
			System.out.println("\n No presentation running");
		}
		//LaunchPrimitive.setListenStateParameter("normal");
		
	}
}
