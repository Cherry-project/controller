package cherry.chatterbot.web;
import cherry.chatterbot.service.Chatterbot;
import cherry.robothandlers.service.LaunchPresentation;
import cherry.robothandlers.service.LaunchPrimitive;
import cherry.robothandlers.web.SetupController;

import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatterbotController{
	
	
	@CrossOrigin
	@RequestMapping("/chatterbot")
	public static void testChatterbot(@RequestParam(value="msg") String msg) throws IOException {
		
		System.out.println("\nRobot utilise : " + SetupController.robotList.get(0).getName());
		
		//LaunchPresentation.robotsUsed = new HashSet<String>();
		// Get the first robot connected
		try{
			LaunchPresentation.robotsUsed.add(SetupController.robotList.get(0));		
		}
		catch(Exception e)
		{
			System.out.println("\n No robot available");
		}
		
		// Dialog
		// User
		System.out.println("\nUtilisateur : " + msg);
		
		String response = new String();
		//Get response from bot
		response = Chatterbot.sendToChatterbot(msg);

		// Bot
		System.out.println("\nBot : " + response);
	
		// Make the robot speak
		LaunchPrimitive.startSpeakPrimitive(response);
		
	}
}
