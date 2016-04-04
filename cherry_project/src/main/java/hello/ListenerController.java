package hello;

import java.util.ArrayList;
import java.io.IOException;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Random;

@RestController
@RequestMapping("/robot")
public class ListenerController {
	
	private ArrayList<String> wait_behave_list = new ArrayList<String>();
	private ArrayList<String> wait_text_list = new ArrayList<String>();
    
	@RequestMapping("/wait")
	public Poppy poppy(@RequestParam(value="state", defaultValue="off") String str) {

		String info = new String();

		if( str.equals("on")){
			
			ToWebsite.setListeningSignal("off");
			
			String current_primitive = LaunchPrimitive.getRunningPrimitiveList();
    		
			int index_torso = current_primitive.indexOf("torso_idle_motion");
    		int index_head = current_primitive.indexOf("head_idle_motion");
    		
    		if(index_torso == -1){
    			LaunchPrimitive.playBehaviorPrimitive("torso_idle_motion");
    		}
    		if(index_head == -1){
    			LaunchPrimitive.playBehaviorPrimitive("head_idle_motion");
    		}
			//protéger le lancement des primitives de mouvement!
			
			System.out.println("\n Play waiting behaviors");
			
			System.out.println("\n This is not a string");
		
			//LaunchPrimitive.playSpeakPrimitive("Je suis \u00e0 votre \u00e9coute");
			/*try{
				Thread.sleep(1000);
			}
			catch(InterruptedException e){
				System.out.println("\n " +e);
			}*/
			
			LaunchPrimitive.ListenPrimitive();
			
			info = "Waiting state";
		}
		else{
			info = "Not in a Waiting state";
		}
		
		return new Poppy(info);
	}
	@RequestMapping("/listen")
	public Poppy poppy1(@RequestParam(value="state", defaultValue="off") String str) {

		String info = new String();

		if( str.equals("on")){
			
			String current_primitive = LaunchPrimitive.getRunningPrimitiveList();
    		
    		int index_head = current_primitive.indexOf("head_idle_motion");
    		
    		if(index_head != -1){
    			LaunchPrimitive.stopPrimitive("head_idle_motion");
    		}
			
			System.out.println("\n I don't understand");
		
			//LaunchPrimitive.playSpeakPrimitive("pas");
			try{
				Thread.sleep(1000);
			}
			catch(InterruptedException e){
				System.out.println("\n " +e);
			}
			
			LaunchPrimitive.ListenPrimitive();
			info = "Listening state";
		}
		else{
			info = "Not in a Listening state";
		}
		
		return new Poppy(info);
	}
	
	@RequestMapping("/wait_behave")
	public Poppy poppy2(@RequestParam(value="state", defaultValue="off") String str) {

		String info = new String();
		Random rand = new Random();

		if( str.equals("on")){
			
			String current_primitive = LaunchPrimitive.getRunningPrimitiveList();
    		
			int index_torso = current_primitive.indexOf("torso_idle_motion");
    		int index_head = current_primitive.indexOf("head_idle_motion");
    		
    		if(index_torso == -1){
    			LaunchPrimitive.playBehaviorPrimitive("torso_idle_motion");
    		}
    		if(index_head == -1){
    			LaunchPrimitive.playBehaviorPrimitive("head_idle_motion");
    		}
			
			System.out.println("\n Je joue un wait au pif");
		
			if( wait_behave_list.isEmpty() && wait_text_list.isEmpty()){
				
				// Get list of primitive into Excel file
				try {
					wait_behave_list = SimpleExcelReaderExample.getExcelField("./Resources/Attente.xlsx","Behave");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//list.add(0,"Start");
				System.out.println("\nList of " + "Behave: " + wait_behave_list );
				
				// Get list of TTS into Excel file
				try {
					wait_text_list = SimpleExcelReaderExample.getExcelField("./Resources/Attente.xlsx","Text");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//list_text.add(0,"Start");
				System.out.println("\nList of " + "Text: " + wait_text_list );
				
			}
			
			LaunchPrimitive.playBehaviorPrimitive(wait_behave_list.get(rand.nextInt(((wait_behave_list.size()-1)+ 1))));
			LaunchPrimitive.playSpeakPrimitive(wait_text_list.get(rand.nextInt(((wait_behave_list.size()-1)+ 1))));
			
			LaunchPrimitive.ListenPrimitive();
			
			info = "Listening state";
		}
		else{
			info = "Not in a Listening state";
		}
		
		return new Poppy(info);
	}
}
