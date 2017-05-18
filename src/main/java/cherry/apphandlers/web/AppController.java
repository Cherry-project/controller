package cherry.apphandlers.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cherry.robothandlers.service.LaunchPresentation;
import cherry.robothandlers.service.LaunchPrimitive;
import cherry.robothandlers.service.Poppy;
import cherry.robothandlers.service.Robot;
import cherry.robothandlers.web.SetupController;
import cherry.robothandlers.web.TestController;

@RestController
@RequestMapping("/app")
public class AppController {
private static Logger logger = Logger.getLogger(TestController.class);
	
	/***********************************************************************************************/
	/*Function to retrieve all the primitive of the robot by sending him a request*/
	/*and sending to the client the result*/
	/***********************************************************************************************/
	@CrossOrigin
	@RequestMapping(value = "/primitives", method = RequestMethod.GET, produces = "application/json")
	public void appPrimitives(@RequestParam(value="id", required = true) String name, HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException
	{
			String info = new String();
			Robot robot = SetupController.getRobot(name);
			
			if(!robot.getName().equals("no_name")){
				logger.info("Get all primitives");
				info = LaunchPrimitive.getPrimitiveList(robot.getIp());
			}else{
				info = "{'response':'There is no robot available.'}";
			}
			res.setContentType(MediaType.APPLICATION_JSON);
			PrintWriter out = res.getWriter();
			out.write(info);
	}
	
	/***********************************************************************************************/
	/*Function that checks if the robot is moving and if it isn't, then we send a behave request*/
	/***********************************************************************************************/
	@CrossOrigin
	@RequestMapping("/behave")
	public Poppy appBehave(@RequestParam(value="name") String behaveStr,@RequestParam(value="id", required = true) String name) 
	{
			String info = "I played the following behave : " + behaveStr;
			Robot robot = SetupController.getRobot(name);

			if(!LaunchPresentation.isPresentationRunning && !robot.getIsMoving() && robot.getName() != "no_name")
			{
				logger.info("Play behavior :" + behaveStr);
				if(LaunchPrimitive.startBehaviorPrimitive(behaveStr,robot.getIp()) == 0)
					robot.setIsMoving(true);
				else
					robot.setIsMoving(false);
			}
			else {
				logger.warn("A presentation or a behave is running or no robot found Please retry later");
			}
		    return new Poppy(info);  
    }
	
	/***********************************************************************************************/
	/*Function that chain a list of behave*/ 
	/***********************************************************************************************/
	@CrossOrigin
	@RequestMapping(value = "/chore", method = RequestMethod.POST, consumes = "application/json")
	public Poppy appChore(HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException 
	{		
			 /*Retrieve post data to JSONObject*/
			 StringBuffer jb = new StringBuffer();
			  String line = null;
			  try {
			    BufferedReader reader = req.getReader();
			    while ((line = reader.readLine()) != null)
			      jb.append(line);
			  } catch (Exception e) { /*report an error*/ }

			JSONObject jsonObject = new JSONObject(jb.toString());
			String name = (String) jsonObject.get("id");

			/*Retrieve robot in robotList*/
			String info = "\n I a set of behave. ";
			Robot robot = SetupController.getRobot(name);
			
			/*If robot is not moving set a list of primitives and launch the first one*/
			if(!LaunchPresentation.isPresentationRunning && !robot.getIsMoving() && robot.getName() != "no_name")
			{
				JSONArray arr = jsonObject.getJSONArray("list");
				robot.setPrimList(new ArrayList<String>());
				for(int i = 0; i < arr.length(); i++){
				    robot.getPrimList().add(arr.getString(i));
				}
				logger.info("Begin of a choregraphy with a set of " + robot.getPrimList().size() + " primitives.");
				Iterator<String> primIt = robot.getPrimList().iterator();
				if(primIt.hasNext()){
					String primitive = primIt.next();
					if(LaunchPrimitive.startBehaviorPrimitive(primitive,robot.getIp()) == 0)
						robot.setIsMoving(true);
					else
						robot.setIsMoving(false);
					logger.info("I played the following behave : " + primitive);
					primIt.remove();
				}else{
					robot.setIsMoving(false);
					logger.info("List of primitives is empty.");
				}
			}
			else {
				logger.warn("A presentation or a behave is running or no robot found. Please retry later");
			}
		    return new Poppy(info);  
    }
	
	/***********************************************************************************************/
	/*Function that test if the robot is moving*/ 
	/***********************************************************************************************/
	@CrossOrigin
	@RequestMapping(value = "/ismoving", method = RequestMethod.GET)
	public void appIsMoving(@RequestParam(value="id", required = true) String name, HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException 
	{
			
			Robot robot = SetupController.getRobot(name);

			JSONObject json = new JSONObject();
			if(robot.getName() != "no_name"){
				if(!LaunchPresentation.isPresentationRunning && !robot.getIsMoving() )
				{
					json.append("isMoving", false);
				}
				else {
					logger.warn("A presentation or a behave is running or no robot found. Please retry later");
					json.append("isMoving", true);
				}
			}else{
				logger.warn("No robot found. Please retry later");
			}
			res.setContentType(MediaType.APPLICATION_JSON);
			PrintWriter out = res.getWriter();
			out.write(json.toString());  
    }

	/***********************************************************************************************/
	/*Function that chain a list of phrases called speech*/ 
	/***********************************************************************************************/
	@CrossOrigin
	@RequestMapping(value = "/speech", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
	public Poppy appSpeech(HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException 
	{		
			System.out.println(req.getCharacterEncoding());
			 /*Retrieve post data to JSONObject*/
			 StringBuffer jb = new StringBuffer();
			  String line = null;
			  try {
			    BufferedReader reader = req.getReader();
			    while ((line = reader.readLine()) != null)
			      jb.append(line);
			  } catch (Exception e) { /*report an error*/ }
			
			  System.out.println(jb);
			JSONObject jsonObject = new JSONObject(jb.toString());
			String name = (String) jsonObject.get("id");

			/*Retrieve robot in robotList*/
			String info = "\n I a set of behave. ";
			Robot robot = SetupController.getRobot(name);

			
			/*If robot is not moving set a list of primitives and launch the first one*/
			if(!LaunchPresentation.isPresentationRunning && !robot.getIsSpeaking() && robot.getName() != "no_name")
			{
				JSONArray arr = jsonObject.getJSONArray("list");
				robot.setSpeechList(new ArrayList<String>());
				for(int i = 0; i < arr.length(); i++){
				    robot.getSpeechList().add(arr.getString(i));
				}
				logger.info("Begin of a speech with a set of " + robot.getSpeechList().size() + " phrases.");
				Iterator<String> speechIt = robot.getSpeechList().iterator();
				if(speechIt.hasNext()){
					String speech = speechIt.next();
					if(LaunchPrimitive.startSpeakPrimitive(speech,robot.getIp()) == 0){
						robot.setIsSpeaking(true);
						logger.info("I speak the following text : " + speech);
					}else
						robot.setIsSpeaking(false);
					speechIt.remove();
				}else{
					robot.setIsSpeaking(false);
					logger.info("List of phrases is empty.");
				}
			}
			else {
				logger.warn("A presentation or a behave is running or no robot found. Please retry later");
			}
		    return new Poppy(info);  
    }
	
	/***********************************************************************************************/
	/*Function that test if the robot is speaking*/ 
	/***********************************************************************************************/
	@CrossOrigin
	@RequestMapping(value = "/isspeaking", method = RequestMethod.GET)
	public void appIsSpeaking(@RequestParam(value="id", required = true) String name, HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException 
	{
			Robot robot = SetupController.getRobot(name);

			JSONObject json = new JSONObject();
			if(robot.getName() != "no_name"){
				if(!LaunchPresentation.isPresentationRunning && !robot.getIsSpeaking() && robot.getName() != "no_name")
				{
					json.append("isSpeaking", false);
				}
				else {
					logger.warn("A presentation or a behave is running. Please retry later");
					json.append("isSpeaking", true);
				}
			}else{
				logger.warn("No robot found. Please retry later");
			}
			res.setContentType(MediaType.APPLICATION_JSON);
			PrintWriter out = res.getWriter();
			out.write(json.toString());  
    }
	
	/***********************************************************************************************/
	/*Function that test if a robot is taken by another child using a CherryApp*/ 
	/***********************************************************************************************/
	@CrossOrigin
	@RequestMapping(value = "/istaken", method = RequestMethod.GET)
	public void appIsTaken(@RequestParam(value="id", required = true) String name, HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException 
	{
			Robot robot = SetupController.getRobot(name);

			JSONObject json = new JSONObject();
			if(robot.getName() != "no_name"){
	
				if(!LaunchPresentation.isPresentationRunning && !robot.isTaken())
				{
					json.append("isTaken", false);
				}
				else {
					logger.warn("A presentation or a behave is running. Please retry later");
					json.append("isTaken", true);
				}
			}else{
				logger.warn("No robot found. Please retry later");
			}
			res.setContentType(MediaType.APPLICATION_JSON);
			PrintWriter out = res.getWriter();
			out.write(json.toString());  
    }
	
	/***********************************************************************************************/
	/*Function that checks if the robot is moving and if it isn't, then we send a behave request*/
	/***********************************************************************************************/
	@CrossOrigin
	@RequestMapping("/stop")
	public Poppy appStop(@RequestParam(value="id", required = true) String name) 
	{
			String info = "I stop the current behave of " + name;
			Robot robot = SetupController.getRobot(name);
			if(robot.getName() != "no_name"){

				logger.info("Stop behavior");
				robot.setPrimList(new ArrayList<String>());
				LaunchPrimitive.stopPrimitive(robot.getIp());
			}else{
				logger.warn("No robot found. Please retry later");
			}
		    return new Poppy(info);  
    }

	
	
	/***********************************************************************************************/
	/*Function that checks if the robot is moving and if it isn't, then we send a behave request*/
	/***********************************************************************************************/
	@CrossOrigin
	@RequestMapping("/presentation")
	public Poppy appPresentation(@RequestParam(value="id", required = true) String name) 
	{
			String info = "I played app presentation";
			Robot robot = SetupController.getRobot(name);
			String prim = LaunchPrimitive.getPrimitiveList(robot.getIp());
			//JSONObject json = new JSONObject(prim);
			logger.info(prim);
			
			//HttpConnection.sendGet(robot.getIp() + "/primitive/" + behavior + "/stop.json");

			//status = HttpConnection.sendPostJson(robot.getIp() + "/primitive/say_fr/method/start/args.json",json);

		    return new Poppy(info);  
    }

	
	/***********************************************************************************************/
	/*Non used methods*/ 
	/***********************************************************************************************/
	@CrossOrigin
	@RequestMapping("/add/user")
	public String appAddUser(
			@RequestParam(value="type") String type,
			@RequestParam(value="email") String email,
			@RequestParam(value="pw") String pw,
			@RequestParam(value="last") String last,
			@RequestParam(value="first") String first) 
	{
		 	/*ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("file:spring-conf.xml");
		    UserCollection userCollection = context.getBean(UserCollection.class);

		    Iterable<User> userList = userCollection.findAll();
			Long id = null;
            for (User u : userList){
	                id = u.getUserId();
	        }
		    User user = null;
		    if(type.equals("child")){
				user = new Child(id+1,email,pw,last,first);
		    }else if(type.equals("doctor")){
				user = new Doctor(id+1,email,pw,last,first);
		    }else if(type.equals("family")){
				user = new Family(id+1,email,pw,last,first);
		    }else if(type.equals("teacher")){
				user = new Teacher(id+1,email,pw,last,first);
		    }else{
		    	context.close();
		    	return "Please enter a valid user : \n /user?type=<<child,doctor,family,teacher>>&email=<>&pw=<>&last=<>&first=<>";
		    }
			
			userCollection.save(user);
			
		    Iterable<User> userlist = userCollection.findAll();
	        System.out.println("Person List : ");
            for (User u : userlist){
	                System.out.println(u);
	        }
            //System.out.println("User Record with name jon is "+ userCollection.searchByLastName("jon"));
            context.close();

		    return user.toString();  */return "";
    }
	
	@CrossOrigin
	@RequestMapping("/get/user")
	public String appGetUser() 
	{
		 	/*ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("file:spring-conf.xml");
		    UserCollection userCollection = context.getBean(UserCollection.class);
						
			Iterable<User> userList = userCollection.findAll();
			String out = "Users List :<br>\n";
            for (User u : userList){
	                out = out + u + "<br>\n";
	        }
            context.close();
            System.out.println(out);
		    return out; */ return "";
    }
	
	@CrossOrigin
	@RequestMapping("/get/robot")
	public String appGetRobot() 
	{
		 	/*ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("file:spring-conf.xml");
		    RobotCollection robotCollection = context.getBean(RobotCollection.class);
					
			Iterable<Robot> robotList = robotCollection.findAll();
			String out = "robot List :<br>\n";
            for (Robot u : robotList){
	                out = out + u + "<br>\n";
	        }
            context.close();
            System.out.println(out);
		    return out; */ return "";
    }
	
}
