package cherry.robothandlers.web;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cherry.gamehandlers.service.ToWebsite;
import cherry.robothandlers.service.LaunchPresentation;
import cherry.robothandlers.service.LaunchPrimitive;
import cherry.robothandlers.service.Poppy;

import org.apache.log4j.Logger;

@RestController
public class PoppyController {
	
	//public static String url_to_robot = "http://127.0.0.2:8888";
	//public static String url_to_robot = "http://192.168.1.104:8080";
	//public static String url_to_website = "http://52.50.54.27";
	//public static String url_to_website = "http://192.168.1.103:80";
	
	private static Logger logger = Logger.getLogger(PoppyController.class);
	
	@RequestMapping("/presentation")
	public Poppy poppy(@RequestParam(value="name", defaultValue="World") String name) {
        
		String info = new String();
		//System.out.println("\n name: " + name);
		
		logger.info("Parameter received for presentation:" + name );
		
		// PLay presentation
		if( name.toLowerCase().indexOf("sogeti")!= -1){
			info = "Play Sogeti";
			
			String excelFilePath = "./Resources/Sogeti.xlsx";

			try{
			LaunchPresentation.start(excelFilePath);
			}
			catch(IOException e){
				System.out.println("\n Erreur" + e);
				info = "Error trying to play Sogeti";
			}
			catch(InterruptedException e){
				System.out.println("\n Erreur" + e);
				info = "Error trying to play Sogeti";
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
   					info = "Error trying to play Cherry";
   				}
   				catch(InterruptedException e){
   					System.out.println("\n Erreur" + e);
   					info = "Error trying to play Cherry";
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
					info = "Error trying to play Prima";
				}
				catch(InterruptedException e){
					System.out.println("\n Erreur" + e);
					info = "Error trying to play Prima";
				}
		}
		else if( name.toLowerCase().indexOf("bonjour") != -1 ){
			info = "Play Bonjour";
			//ToWebsite.setListeningSignal("off");
			LaunchPrimitive.playBehaviorPrimitive("double_me_behave");
			LaunchPrimitive.playSpeakPrimitive("Bonjour, je m'appelle Tcherry.");
			
			
			
			LaunchPrimitive.playSpeakPrimitive("Bienvenue, a la semaine digitale");
			LaunchPrimitive.playBehaviorPrimitive("rest_open_behave");
		
			
			//LaunchPrimitive.ListenPrimitive();
		}
		
		else if( name.toLowerCase().indexOf("aurevoir") != -1 ){
			info = "Play Au revoir";
			//ToWebsite.setListeningSignal("off");
			LaunchPrimitive.playBehaviorPrimitive("left_arm_up_behave");
			LaunchPrimitive.playSpeakPrimitive("Je te souhaite une bonne visite");
			
			LaunchPrimitive.playSpeakPrimitive("Merci d'etre passer me voir");		
			LaunchPrimitive.playBehaviorPrimitive("rest_open_behave");

			
			//LaunchPrimitive.ListenPrimitive();
		}
		
		else{
			info = "Nom inconnu";
			ToWebsite.setListeningSignal("off");
			LaunchPrimitive.playBehaviorPrimitive("hunkers_behave");
			LaunchPrimitive.playSpeakPrimitive("Je n'ai pas compris ce que vous avez dit.");
			
			//LaunchPrimitive.playBehaviorPrimitive("left_arm_up_behave");
			LaunchPrimitive.playSpeakPrimitive("Veuillez r\u00E9p\u00E9ter");
			LaunchPrimitive.playBehaviorPrimitive("rest_open_behave");
			
			//LaunchPrimitive.ListenPrimitive();
		}
			
        return new Poppy(info);
    }
	
	@RequestMapping("/stop")
	public Poppy poppy1(@RequestParam(value="presentation") String param) {
		
		String info = new String();
		
		if (param.equals("off") == true){
			
			System.out.println("\n I stop the current presentation");
			
			info = "Stop";
			try{
				LaunchPresentation.stop();
				logger.info("Stop presentation");
				
			}
			catch(IOException e){
				logger.error("Error trying to stop presentation", e);
			}
			catch(InterruptedException e){
				logger.error("Error trying to stop presentation", e);
			}
			
		}
		else{
			logger.warn("Unknow parameter to stop presentation");
			System.out.println("\n Param unknow");
			info = "Not stopped";
		}
		return new Poppy(info);
	}
}
