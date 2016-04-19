package cherry.robotpresentateur.web;

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
			LaunchPrimitive.playSpeakPrimitive("Oui!");
			LaunchPrimitive.playBehaviorPrimitive("swap_behave");
		}
		// NO
		if( name.toLowerCase().indexOf("no")!= -1){
			LaunchPrimitive.playSpeakPrimitive("Non!");
			LaunchPrimitive.playBehaviorPrimitive("swap_behave");
		}
		// DONT KNOW
		if( name.toLowerCase().indexOf("dontknw")!= -1){
			LaunchPrimitive.playSpeakPrimitive("A vrai dire je ne sais pas");
			LaunchPrimitive.playBehaviorPrimitive("question_behave");
		}
		// MAYBE
		if( name.toLowerCase().indexOf("maybe")!= -1){
			LaunchPrimitive.playSpeakPrimitive("Peut-\u00eatre!");
			LaunchPrimitive.playBehaviorPrimitive("swap_behave");
		}
		// JOKER
		if( name.toLowerCase().indexOf("joker")!= -1){
			LaunchPrimitive.playSpeakPrimitive("Joker");
			LaunchPrimitive.playBehaviorPrimitive("swap_behave");
		}
		//OK
		if( name.toLowerCase().indexOf("dac")!= -1){
			LaunchPrimitive.playSpeakPrimitive("Daccord!");
			LaunchPrimitive.playBehaviorPrimitive("swap_behave");
		}
		return new Poppy("Play generic behavior");
	}

	@RequestMapping("/dialog")
	public Poppy dialog(@RequestParam(value="name", defaultValue="Null") String name) {
		
		logger.info("Parameter received on-click" + name );
		String info = new String();
		
		// Step 1
		if( name.toLowerCase().indexOf("step1")!= -1){
			info = "Play step 1";
			LaunchPrimitive.playBehaviorPrimitive("double_me_behave");
			LaunchPrimitive.playSpeakPrimitive("Bonjour, je m'appelle Tcherry.");
			
			LaunchPrimitive.playSpeakPrimitive("Et toi, comment t'appelles tu?");
			LaunchPrimitive.playBehaviorPrimitive("left_arm_up_behave");
		}
		
		// Step 2
		if( name.toLowerCase().indexOf("step2")!= -1){
			info = "Play step 2";
			LaunchPrimitive.playSpeakPrimitive("Jacques, j'ai appris que tu \u00e9tais le patron de l'innovation Sogeti France");
			LaunchPrimitive.playBehaviorPrimitive("rest_open_behave");
			
			LaunchPrimitive.playSpeakPrimitive("c'est pas trop dur d'aller a plein de cocktails ?");
			LaunchPrimitive.playBehaviorPrimitive("question_behave");
		}
		
		// Step 3
		if( name.toLowerCase().indexOf("step3")!= -1){
			info = "Play step 3";
			LaunchPrimitive.playSpeakPrimitive("Sinon, il parait que tu es fan d'une \u00e9quipe de football qui est dans les bas fond du championnat");
			LaunchPrimitive.playBehaviorPrimitive("rest_open_behave");
			
			LaunchPrimitive.playSpeakPrimitive("Tu peux nous en dire plus a ce sujet?");
			LaunchPrimitive.playBehaviorPrimitive("hunkers_behave");
		}
		
		// Step 4
		if( name.toLowerCase().indexOf("step4")!= -1){
			info = "Play step 4";
			LaunchPrimitive.playSpeakPrimitive("Tu as dis dans une interview que Sogeti voulait sortir l'innovation de sa tour d'ivoire");
			LaunchPrimitive.playBehaviorPrimitive("question_behave");
			
			LaunchPrimitive.playSpeakPrimitive(", afin de la mettre au plus pr\u00e8s des unit\u00e9s op\u00e9rationnelles");
			LaunchPrimitive.playBehaviorPrimitive("rest_open_behave");
			
			LaunchPrimitive.playSpeakPrimitive("super! on va enfin me mettre des jambes!");
			LaunchPrimitive.playBehaviorPrimitive("little_arms_up_behave");
		}
		
		// Step 5
		if( name.toLowerCase().indexOf("step5")!= -1){
			info = "Play step 5";
			LaunchPrimitive.playSpeakPrimitive("Je pense que j'ai bien travaill\u00e9, je vais me d\u00e9connecter maintenant.");
			LaunchPrimitive.playBehaviorPrimitive("rest_open_behave");
			
			LaunchPrimitive.playSpeakPrimitive("Au revoir");		
			LaunchPrimitive.playBehaviorPrimitive("swap_behave");
		}
		
		return new Poppy(info);
	}


}
