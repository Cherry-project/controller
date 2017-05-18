package cherry.robothandlers.web;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cherry.robothandlers.service.HttpConnection;
import cherry.robothandlers.service.Poppy;
import cherry.robothandlers.service.Robot;
import cherry.robothandlers.service.UpdateRobotListThread;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

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
	public Poppy setupRobot(@RequestParam(value="id", required = true) String name, @RequestParam(value="port", required = false, defaultValue = "8000") int port,@RequestParam(value="ip", required = false, defaultValue = "127.0.0.1") String ipp, HttpServletRequest request, HttpServletResponse response) 
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

		logger.info("New Robot: Name: " + name + " Ip adress: " + ip + ":8000");
		// Create a new Robot instance
		Robot robot = new Robot();	
		robot.setIp(ip,8000);
		robot.setPort(8000);
		robot.setName(name);

		boolean robotUnreachable = false;
		boolean robotKnown = false;

		// Ping
		try {
			HttpConnection.sendGet(robot.getIp());
			logger.info("Robot " + robot.getName() + " still alive!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.info("Robot " + robot.getName() + " is unreachable!");
			robot.setIp(ipp,port);
			robot.setPort(port);
			logger.info("New address ip: Name: " + name + " Ip adress: " + ipp + ":" + port);

			// Ping				
			try {
				HttpConnection.sendGet(robot.getIp());
				logger.info("Robot " + robot.getName() + " still alive!");
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				logger.info("Robot " + robot.getName() + " is still unreachable! Try to setup again!");
				robotUnreachable = true;
			}
		}

		//Check whether the new robot is already in the list
		for(Robot robotIdx : robotList)
		{
			// if true, Update IP_adress
			if(name.equals(robotIdx.getName()))
			{
				robotKnown = true;
				logger.warn("Robot " + name + " already exists");
				break;	
			} 	
		}

		// If false add it
		if (!robotKnown && !robotUnreachable && robot.getName() != "no_name")
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

	@RequestMapping("/setupssh")
	public Poppy setupSSHRobot(@RequestParam(value="id", required = true) String name, HttpServletRequest request, HttpServletResponse response) 
	{		

		// Create a new Robot instance
		int port = 0;
		try {
			port = getAvailablePort();
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			port = 0;
		}
		if(port != 0){
			String ip = "127.0.0.1";
			Robot robot = new Robot();	
			robot.setIp(ip,port);
			robot.setPort(port);
			robot.setName(name);
			logger.info("Setup reverse SSH");
			logger.info("New Robot: Name: " + name + " Ip adress: " + ip + ":" + port);

			boolean robotKnown = false;

			//Check whether the new robot is already in the list
			for(Robot robotIdx : robotList)
			{
				// if true, Update IP_adress
				if(name.equals(robotIdx.getName()))
				{
					robotKnown = true;
					logger.warn("Robot " + name + " already exists");
					port = robotIdx.getPort();
					break;	
				} 	
			}
			
			// If false add it
			if (!robotKnown && robot.getName() != "no_name")
			{
				robotList.add(robot);   
				System.out.println("\n Hello robot: " + name + " !");

				// Set current IP
				urlToRobot = robot.getIp();
			} 

			if(!robotKnown){
				JSONObject json = new JSONObject();
				json.accumulate("port", port);
				try {
					response.setContentType("application/json");
					response.getOutputStream().println(json.toString());
					logger.info("Reply to the robot : " + json.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(robotKnown){
				JSONObject json = new JSONObject();
				json.accumulate("port", 0);
				json.accumulate("error", "Name already use !");
				try {
					response.setContentType("application/json");
					response.getOutputStream().println(json.toString());
					logger.info("Reply to the robot : " + json.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.info("This name is already used by a robot try another name!");
			}else if(robot.getName() == "no_name"){
				JSONObject json = new JSONObject();
				json.accumulate("port", 0);
				json.accumulate("error", "Name has to be different from no_name !");
				try {
					response.setContentType("application/json");
					response.getOutputStream().println(json.toString());
					logger.info("Reply to the robot : " + json.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.info("A name is required !");
			}
			
			// start RobotList update thread
			if (!startUpdateThread)
			{
				UpdateRobotListThread update = new UpdateRobotListThread();
				update.start();
				startUpdateThread = true;
			}
			
			return new Poppy("Id: " + ip + " HTTP REQ: " + request);
		}else{
			JSONObject json = new JSONObject();
			json.accumulate("port", port);
			json.accumulate("error", "No available port !");
			try {
				response.setContentType("application/json");
				response.getOutputStream().println(json.toString());
				logger.info("Reply to robot : " + json.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("Setup reverse SSH failed! Try again!");
			return new Poppy("Setup reverse SSH failed TP REQ: " + request);
		}
	}
	
	@CrossOrigin
	@RequestMapping("/remove")
	public static void removeRobot(@RequestParam(value="id", required = true) String name, HttpServletRequest request, HttpServletResponse response) 
	{
		Iterator<Robot> robotIdx = robotList.iterator();
		Robot robot = new Robot();
		while (robotIdx.hasNext()) {
			Robot currentRobot = robotIdx.next();
			if(currentRobot.getName().equals(name)){
				robot = currentRobot;
				break;
			}
		}
		if(robot.getName() != "no_name"){
			logger.info("Robot " + robot.getName() + " no longer available. Robot removed from list");
			robotIdx.remove(); 
		}else{
			logger.info("No Robot removed from list");
		}
	}

	@CrossOrigin
	@RequestMapping("/robots")
	public static void availableRobots(HttpServletRequest request, HttpServletResponse response) 
	{	
		// Parse the current robot_list
		JSONArray mJSONArray = new JSONArray(Arrays.asList(robotList));

		// Set response parameters
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");//cross domain request/CORS
		
		// Send a JSON containing the robots
		try {
			response.getWriter().write(mJSONArray.getJSONArray(0).toString());
			//mJSONArray.write(response.getWriter());
		} catch (IOException e) {
			System.out.println("\n Error: " + e);
		}

	}

	/***********************************************************************************************/
	/*Go throw the list of robot to find the good one*/
	/***********************************************************************************************/
	public static Robot getRobot(String name){
		Iterator<Robot> robotIdx = robotList.iterator();
		Robot robot = new Robot();
		if(!name.equals("null")){
			while (robotIdx.hasNext()) {
				Robot currentRobot = robotIdx.next();
				if(currentRobot.getName().equals(name)){
					robot = currentRobot;
					break;
				}
			}
		}
		return robot;
	}
	/***********************************************************************************************/
	/*Go throw the list of robot to find the good one*/
	/***********************************************************************************************/
	public int getAvailablePort() throws IOException {
		for (int i = 1025; i <= 65535; i++) {
			ServerSocket s = null;
			try {
				//if(i != 8080 && i >= getMaxPort()){
				if(i != 8080){
					s = new ServerSocket(i);
					if(s != null && !s.isClosed())
						s.close();
					return i;
				}
			} catch (IOException ex) {
				if(s != null && !s.isClosed())
					s.close();
				continue; // try next port
			}
		}

		// if the program gets here, no port in the range was found
		throw new IOException("no free port found");
	}
}
