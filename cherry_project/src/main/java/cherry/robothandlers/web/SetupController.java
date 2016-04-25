package cherry.robothandlers.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cherry.robothandlers.web.PoppyController;
import cherry.robothandlers.service.Poppy;

import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/setup")
public class SetupController {
	
	private static Logger logger = Logger.getLogger(SetupController.class);
	
	@RequestMapping("/ip")
	public Poppy poppy(@RequestParam(value="param", required = false, defaultValue = "null") String a_str, HttpServletRequest request, HttpServletResponse response) 
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
		    System.out.println("\n Ip Adress: " + ip);
		    
		    PoppyController.url_to_robot = "http://" + ip + ":8080";
		    
		    logger.info("Get robot IP Adress :" + PoppyController.url_to_robot);
		    
		    try{
		    	Thread.sleep(5000);
		    }
		    catch(InterruptedException e)
		    {
		    	logger.warn("Can't wait with Thread.sleep", e);	
		    }
		    
		    //logger.info("Logger root: " + LogManager.getLoggerRepository());
		    
		    //LaunchPrimitive.playSpeakPrimitive("Je suis reveiller!");
		    
			
		    //Session sess = 

		    
		    //session.setAttribute("success" , "successfully accessed");
		    //System.out.println("\n Attribut: " +  session.getAttribute("success"));
		    
		    //System.out.println("\n Id: " +  session.getId());
		    //System.out.println("\n Servlet Context: " +  session.getServletContext());
		    //System.out.println("\n New session ? " + session.isNew());
		    //System.out.println("\n Remotehost" + request.getRemoteHost());
		    
		    return new Poppy("Id: " + ip + " HTTP REQ: " + request);
		    
	    }
	
	/*@RequestMapping(value = "/test", method = RequestMethod.POST)
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON)
	public Poppy poppy1
	         (@RequestBody String jsonReqString ,HttpServletRequest request) 
	{
	  //HttpSession session = request.getSession();
	  //session.setAttribute("success" , "successfully accessed");
	  
	  System.out.println("\n Json received" + jsonReqString);
	  
	  //session.getAttribute("success");
	  
	  return new Poppy("JSON REQ: " + jsonReqString );
	 
	}*/
		
}
