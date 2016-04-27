package cherry.robothandlers.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cherry.robotpresentateur.service.Robot;
import cherry.robothandlers.service.Poppy;

import org.apache.log4j.Logger;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class SetupController {
	
	private static Logger logger = Logger.getLogger(SetupController.class);
	
	public static String url_to_robot = "http://192.168.1.103";
	public static String url_to_website = "http://192.168.1.200:80";
	
	public static ArrayList<Robot> robot_list = new ArrayList<Robot>();

			
	@RequestMapping("/setup")
	public Poppy setupRobot(@RequestParam(value="id", required = false, defaultValue = "null") String name, HttpServletRequest request, HttpServletResponse response) 
	{		
			
		
			
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
		    //System.out.println("\n Ip Adress: " + ip + " name: " + name);
		    
		    logger.info("New Robot: Name: " + name + " Ip adress: " + ip);
		    
		    //if (robot_list.size()<2)
		    //{
				Robot robot = new Robot();	
			    robot.setIp(ip);
			    robot.setName(name);
			    
			    robot_list.add(robot);
			    
			    System.out.println("\n Hello robot: " + name + " !");
			    
			    // Set IP to robot
			    url_to_robot = robot.getIp();
			    
			/*}
		    else{
		    	
		    	System.out.println("\n Already 2 robots defined!");
		    }*/
		    
		    // A la main
		    robot_list.get(0).setIp("192.168.1.103");
		    try{
		    	robot_list.get(1).setIp("192.168.1.104");
		    }
		    catch(Exception e){}
		    
		    return new Poppy("Id: " + ip + " HTTP REQ: " + request);
		    
	    }
	@RequestMapping("/robots")
	public Poppy availableRobots(@RequestParam(value="id", required = false, defaultValue = "null") String name, HttpServletRequest request, HttpServletResponse response) 
	{
		
		return new Poppy("Available robots" + robot_list.toString());
		
	}

		
}
