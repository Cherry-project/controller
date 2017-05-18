package cherry.onclick.web;

//LOG
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cherry.robothandlers.service.LaunchPrimitive;
import cherry.robothandlers.service.Poppy;

@RestController
@RequestMapping("/onclick")
public class ButtonClickController {
	
	private static Logger logger = Logger.getLogger(ButtonClickController.class);
	
	@RequestMapping("/generic")
	public Poppy generic(@RequestParam(value="name", defaultValue="Null") String name) {
		
		logger.info("Parameter received on-click" + name );
		// YES
		if( name.toLowerCase().indexOf("yes")!= -1){
			LaunchPrimitive.startSpeakPrimitive("Oui!");
			LaunchPrimitive.startBehaviorPrimitive("swap_behave");
		}
		// NO
		if( name.toLowerCase().indexOf("no")!= -1){
			LaunchPrimitive.startSpeakPrimitive("Non!");
			LaunchPrimitive.startBehaviorPrimitive("swap_behave");
		}
		// DONT KNOW
		if( name.toLowerCase().indexOf("dontknw")!= -1){
			LaunchPrimitive.startSpeakPrimitive("A vrai dire je ne sais pas");
			LaunchPrimitive.startBehaviorPrimitive("question_behave");
		}
		// MAYBE
		if( name.toLowerCase().indexOf("maybe")!= -1){
			LaunchPrimitive.startSpeakPrimitive("Peut-\u00eatre!");
			LaunchPrimitive.startBehaviorPrimitive("swap_behave");
		}
		// JOKER
		if( name.toLowerCase().indexOf("joker")!= -1){
			LaunchPrimitive.startSpeakPrimitive("Joker");
			LaunchPrimitive.startBehaviorPrimitive("swap_behave");
		}
		//OK
		if( name.toLowerCase().indexOf("dac")!= -1){
			LaunchPrimitive.startSpeakPrimitive("Daccord!");
			LaunchPrimitive.startBehaviorPrimitive("swap_behave");
		}
		return new Poppy("Played generic behavior");
	}

	@RequestMapping("/dialog")
	public Poppy dialog(@RequestParam(value="name", defaultValue="Null") String name) {
		
		logger.info("Parameter received on-click" + name );
		String info = new String();
		
		// Step 1
		if( name.toLowerCase().indexOf("step1")!= -1){
			info = "Played step 1";
			LaunchPrimitive.startBehaviorPrimitive("double_me_behave");
			LaunchPrimitive.startSpeakPrimitive("Bonjour, je m'appelle Tcherry.");
			
			LaunchPrimitive.startSpeakPrimitive("Et toi, comment t'appelles tu?");
			LaunchPrimitive.startBehaviorPrimitive("left_arm_up_behave");
		}
		
		return new Poppy(info);
	}


}
