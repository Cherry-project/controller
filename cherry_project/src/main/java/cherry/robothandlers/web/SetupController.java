package cherry.robothandlers.web;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cherry.robothandlers.service.Poppy;
import cherry.robothandlers.service.Robot;
import cherry.robothandlers.service.UpdateRobotListThread;

import org.apache.log4j.Logger;
import org.json.JSONArray;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class SetupController {
	
	private static Logger logger = Logger.getLogger(SetupController.class);
	private static boolean startUpdateThread = false;
	
	public static String urlToRobot;
	public static String urlToWebsite = "http://localhost:80";
	
	public static ArrayList<Robot> robotList = new ArrayList<Robot>();
	
	
	@RequestMapping("/setup")
	public Poppy setupRobot(@RequestParam(value="id", required = false, defaultValue = "null") String name, HttpServletRequest request, HttpServletResponse response) 
	{		
			
			// GET Ip adress
			String ip = request.getHeader("X-Forwarded-For");  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("Proxy-Client-IP");  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("WL-Proxy-Client-IP");  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("HTTP_X_FORWARDED");  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("HTTP_CLIENT_IP");  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("HTTP_FORWARDED_FOR");  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("HTTP_FORWARDED");  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("HTTP_VIA");  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("REMOTE_ADDR");  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getRemoteAddr();  
		    }  
		    
		    logger.info("New Robot: Name: " + name + " Ip adress: " + ip);
		    
		    // Create a new Robot instance
			Robot robot = new Robot();	
		    robot.setIp(ip);
		    robot.setName(name);
			    
		    boolean robotKnown = false;
		    
		    //Check whether the new robot is already in the list
		    for(Robot robotIdx : robotList)
		    {
		    	// if true, Update IP_adress
		    	if(name.equals(robotIdx.getName()))
		    	{
		    		robotKnown = true;
		    		logger.warn("Robot " + name + " already exists");
		    		robotIdx.setIp(ip);
		    		break;	
		    	} 	
		    }
		    
		    // If false add it
		    if (!robotKnown)
		    {
			    robotList.add(robot);   
			    System.out.println("\n Hello robot: " + name + " !");
			    
			    // Set current IP
			    urlToRobot = robot.getIp();
		    } 
		    
		    // start RobotList update thread
		    if (!startUpdateThread)
		    {
		    	UpdateRobotListThread update = new UpdateRobotListThread();
		    	update.start();
		    	startUpdateThread = true;
		    }
		    
		       
		    return new Poppy("Id: " + ip + " HTTP REQ: " + request);
		    
	    }
	
	@CrossOrigin
	@RequestMapping("/robots")
	public static void availableRobots(@RequestParam(value="id", required = false, defaultValue = "null") String name, HttpServletRequest request, HttpServletResponse response) 
	{	
		// Parse the current robot_list
		JSONArray mJSONArray = new JSONArray(Arrays.asList(robotList));
		
		// Set response parameters
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");//cross domain request/CORS
		
		
		// Send a JSON containing the robots
		try {
			response.getWriter().write(mJSONArray.toString());
			//mJSONArray.write(response.getWriter());
		} catch (IOException e) {
			System.out.println("\n Error: " + e);
		}
		
	}

		
}
