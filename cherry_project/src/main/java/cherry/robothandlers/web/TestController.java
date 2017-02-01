package cherry.robothandlers.web;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import cherry.gamehandlers.web.JsonRetriever;
import cherry.robothandlers.service.LaunchPresentation;
import cherry.robothandlers.service.LaunchPrimitive;
import cherry.robothandlers.service.Poppy;


@RestController
@RequestMapping("/test")
public class TestController {
    
	private static Logger logger = Logger.getLogger(TestController.class);
	
	@CrossOrigin
	@RequestMapping("/behave")
	public Poppy testBehave(@RequestParam(value="name") String behaveStr) 
	{
			String info = "\n I played the following behave: " + behaveStr;
			
			if(!LaunchPresentation.isPresentationRunning)
			{
				logger.info("Play behavior :" + behaveStr);
				LaunchPrimitive.startBehaviorPrimitive(behaveStr);
			}
			else {
				logger.warn("A presentation is running. Please retry later");
			}
		    return new Poppy(info);  
    }
	

	@CrossOrigin
	@RequestMapping("/speak")
	public Poppy testSpeak(@RequestParam (value="text", required = true) String textStr, @RequestParam (value="tts_engine", required = false, defaultValue = "null")String ttsName) 
	{
			String info = "\n I said " + textStr;
			
			if(!LaunchPresentation.isPresentationRunning)
			{
				if( ttsName != null)
				{
					LaunchPrimitive.setPrimitiveParameter("speak", "tts_engine", ttsName);
				}
				
				logger.info("Play behavior :" + textStr);
				LaunchPrimitive.startSpeakPrimitive(textStr);
			
			}
			else {
				logger.warn("A presentation is running. Please retry later");
			}
		    return new Poppy(info);  
    }

	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/{json}")
	public Poppy jsonReader(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		StringBuffer jb = new StringBuffer();
		String line = null;
		String info = new String();
		info = "Json";
		try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		  } catch (Exception e) { 
			  logger.error("Can't read the request", e);
			  info  = "Can't read request";
		  }
		  
		  
		  JSONObject myJson = new JSONObject();
			
		    // Check if a proper Json
		    try{
			    myJson =new JSONObject((jb.toString()));
		    }
		    catch(Exception e){
		    	logger.error("It's not a JSON", e);
				info  = "Not a Json";
				return new Poppy(info);
			}
		  
		    //LaunchPrimitive.sendMovementToRobot(myJson.toString());
		    //LaunchPrimitive.playMovement();
		  
		return new Poppy(info); 
	}
	
}