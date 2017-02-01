package cherry.gamehandlers.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cherry.robothandlers.service.LaunchPrimitive;
import cherry.robothandlers.service.Poppy;
import cherry.robothandlers.service.Robot;
import cherry.robothandlers.web.SetupController;

@RestController
@RequestMapping("/settings")
public class RobotSettings {
	
	private static Logger logger = Logger.getLogger(JsonRetriever.class);
	private static ArrayList<Robot> robotList = SetupController.robotList;

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/{parameters}")
	public Poppy setParameters(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		StringBuffer jb = new StringBuffer();
		String line = null;
		String info = new String();

		String robotName = new String();
		
		// Check the request 
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
		      
				
			jb.append(line);
		} catch (Exception e) { 
			logger.error("Can't read the request", e);
			info  = "Can't read the request";
		}
	    
		System.out.println("\n " + jb.toString());
		
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
	    
	   
	    // Check if field robot
	    try{
	    	//Get robot  name
			 robotName = (String) myJson.get("robot");
			
			// Remove so that only primitives fields remain
			myJson.remove("robot");
			System.out.println("\n Robot: " + robotName);			
		}
		catch( JSONException e)
		{
			System.out.println("\n No robot selected !");
			info  = "Please select a robot!";
			return new Poppy(info);
		}
	    
	    info = "Robot not found!";
	    
	    // and get its IP adress and set all the parameters
	    for(Robot robot : robotList){
			
			if (robot.getName().equals(robotName.toLowerCase()))
			{
				SetupController.urlToRobot= robot.getIp().toString();
				System.out.println("\n Set New IP to robot "+ robot.getName() + " : " + robot.getIp());
				
				// Set parameters
				for(Object key : myJson.keySet()) 
					{	
				    	// Get the primitive name
				    	String primitive = (String)key;
				    	// Get the JSON corresponding to the primitive
				    	JSONObject subJson = myJson.getJSONObject(primitive);
				    	
				    	System.out.println("\nJson for primitive: " + primitive );
				    	
				    	for(Object subKey : subJson.keySet()) 
						{
				    		String property = (String)subKey;	    		
				    		String value = subJson.getString(property);
				    		
				    		System.out.println("\n	Property: " + property + " Value: " + value);
				    		
				    		// set robot's parameter
				    		LaunchPrimitive.setPrimitiveParameter(primitive, property, value);

						}	
						
				    	info = "Set all parameters for robot: " + robotName ;
					}
				
				break;
			}
		}
	    

			
		return new Poppy(info);
	
	}

	@CrossOrigin
	@RequestMapping("/reset_audio")
	public Poppy resetAudio(@RequestParam (value="robot", required = true) String robotName) 
	{		{
			String info = new String();
			
			info = "Robot not found";
			
			
			// and get its IP adress
		    for(Robot robot : robotList){
				
				if (robot.getName().equals(robotName.toLowerCase()))
				{
					SetupController.urlToRobot= robot.getIp().toString();
					System.out.println("\n Set New IP to robot "+ robot.getName() + " : " + robot.getIp());
					
					logger.info("Reset Audio System of robot: " + robotName);
	    		    
					LaunchPrimitive.resetAudioSystem();
					
					info = "Reset for robot : " + robotName + " done!";
					break;
				}
			}
			
			return new Poppy(info);
		}
		
	}
}
