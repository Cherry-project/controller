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
public class PresentationController {
	
	private static Logger logger = Logger.getLogger(PresentationController.class);
	
	@RequestMapping("/presentation")
	public Poppy startPresentation(@RequestParam(value="name", defaultValue="World") String name) {
        
		String info = new String();
		//System.out.println("\n name: " + name);
		
		logger.info("Parameter received for presentation:" + name );
		
		// PLay presentation
		if( name.toLowerCase().indexOf("sogeti")!= -1){
			info = "Play Sogeti";
			
			String excelFilePath = "./Resources/Sogeti.xlsx";

			try{
			LaunchPresentation.playFromExcel(excelFilePath);
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
   				LaunchPresentation.playFromExcel(excelFilePath);
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
				LaunchPresentation.playFromExcel(excelFilePath);
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
			LaunchPrimitive.startBehaviorPrimitive("double_me_behave");
			LaunchPrimitive.startSpeakPrimitive("Bonjour, je m'appelle Tcherry.");
			
			
			
			//LaunchPrimitive.startSpeakPrimitive("Bienvenue, au colloque consacré a la Silveur Economie et a l'habitat!");
			//LaunchPrimitive.startBehaviorPrimitive("rest_open_behave");
		
			
			//LaunchPrimitive.ListenPrimitive();
		}
		
		else if( name.toLowerCase().indexOf("aurevoir") != -1 ){
			info = "Play Au revoir";
			//ToWebsite.setListeningSignal("off");
			LaunchPrimitive.startBehaviorPrimitive("left_arm_up_behave");
			LaunchPrimitive.startSpeakPrimitive("Je te souhaite une bonne visite");
			
			LaunchPrimitive.startSpeakPrimitive("Merci d'etre passer me voir");		
			LaunchPrimitive.startBehaviorPrimitive("rest_open_behave");

			
			//LaunchPrimitive.ListenPrimitive();
		}
		
		else{
			info = "Unknown Name";
			ToWebsite.setListeningSignal("off");
			LaunchPrimitive.startBehaviorPrimitive("hunkers_behave");
			LaunchPrimitive.startSpeakPrimitive("Je n'ai pas compris ce que vous avez dit.");
			
			//LaunchPrimitive.playBehaviorPrimitive("left_arm_up_behave");
			LaunchPrimitive.startSpeakPrimitive("Veuillez r\u00E9p\u00E9ter");
			LaunchPrimitive.startBehaviorPrimitive("rest_open_behave");
			
			//LaunchPrimitive.ListenPrimitive();
		}
			
        return new Poppy(info);
    }
	
	@RequestMapping("/stop")
	public Poppy stopPresentation(@RequestParam(value="presentation") String param) {
		
		String info = new String();
		
		if (param.equals("off") == true){
			
			System.out.println("\n I stop the current presentation");
			
			info = "Stop Presentation";
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
