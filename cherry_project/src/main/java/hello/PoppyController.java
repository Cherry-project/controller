package hello;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PoppyController {
	
	//public static String url_to_robot = "http://127.0.0.2:8888";
	public static String url_to_robot = "http://192.168.1.106:8080";
	//public static String url_to_website = "http://52.50.54.27";
	public static String url_to_website = "http://192.168.1.101";
    
	@RequestMapping("/poppy")
	public Poppy poppy(@RequestParam(value="name", defaultValue="World") String name) {
        
		String info = new String();
		System.out.println("\n name: " + name);
		
		// PLay presentation
		if( name.toLowerCase().indexOf("sogeti")!= -1){
			info = "Play Sogeti";
			
			String excelFilePath = "./Resources/Sogeti.xlsx";

			try{
			LaunchPresentation.start(excelFilePath);
			}
			catch(IOException e){
				System.out.println("\n Erreur" + e);
			}
			catch(InterruptedException e){
			System.out.println("\n Erreur" + e);
			}			
		}
		
		else if( name.toLowerCase().indexOf("cherry") != -1){
			info = "Play Cherry";
			
   			String excelFilePath = "./Resources/Cherry.xlsx";
   			try{
   				LaunchPresentation.start(excelFilePath);
   				}
   				catch(IOException e){
   					System.out.println("\n Erreur" + e);
   				}
   				catch(InterruptedException e){
   				System.out.println("\n Erreur" + e);
   				}			
		}
		else if(name.toLowerCase().indexOf("prima") != -1 ){
			info = "Play Prima";

			String excelFilePath = "./Resources/Prima.xlsx";
			try{
				LaunchPresentation.start(excelFilePath);
				}
				catch(IOException e){
					System.out.println("\n Erreur" + e);
				}
				catch(InterruptedException e){
				System.out.println("\n Erreur" + e);
				}
		}
		else if( name.toLowerCase().indexOf("bonjour") != -1 ){
			info = "Play Bonjour";
			ToWebsite.setListeningSignal("off");
			LaunchPrimitive.playBehaviorPrimitive("double_me_behave");
			LaunchPrimitive.playSpeakPrimitive("Bonjour, je m'appelle Tcherry.");
			
			
			
			LaunchPrimitive.playSpeakPrimitive("Bienvenu, \u00e0 la semaine digitale");
			LaunchPrimitive.playBehaviorPrimitive("rest_open_behave");
		
			
			LaunchPrimitive.ListenPrimitive();
		}
		
		else if( name.toLowerCase().indexOf("aurevoir") != -1 ){
			info = "Play Au revoir";
			ToWebsite.setListeningSignal("off");
			LaunchPrimitive.playBehaviorPrimitive("left_arm_up_behave");
			LaunchPrimitive.playSpeakPrimitive("Merci d'\u00eatre pass\u00E9 me voir");
			
			
			LaunchPrimitive.playSpeakPrimitive("Je te souhaite une bonne visite");
			LaunchPrimitive.playBehaviorPrimitive("rest_open_behave");

			
			LaunchPrimitive.ListenPrimitive();
		}
		
		else{
			info = "Nom inconnu";
			ToWebsite.setListeningSignal("off");
			LaunchPrimitive.playBehaviorPrimitive("hunkers_behave");
			LaunchPrimitive.playSpeakPrimitive("Je n'ai pas compris ce que vous avez dit.");
			
			//LaunchPrimitive.playBehaviorPrimitive("left_arm_up_behave");
			LaunchPrimitive.playSpeakPrimitive("Veuillez r\u00E9p\u00E9ter");
			LaunchPrimitive.playBehaviorPrimitive("rest_open_behave");
			
			LaunchPrimitive.ListenPrimitive();
		}
			
        return new Poppy(info);
    }
	
	@RequestMapping("/stop")
	public Poppy poppy1(@RequestParam(value="presentation") String param) {
		
		if (param.equals("off") == true){
			
			System.out.println("\n I stop the current presentation");
			
			try{
				LaunchPresentation.stop();
			}
			catch(IOException e){
				System.out.println("\n Erreur" + e);
			}
			catch(InterruptedException e){
			System.out.println("\n Erreur" + e);
			}
			
		}
		else{
			System.out.println("\n Param unknow");
		}
		return new Poppy("Stop presentation");
	}
}
