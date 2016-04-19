package hello;

import java.util.ArrayList;

import java.io.File;
import java.io.IOException;
import java.lang.Object;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.PoppyController;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import org.apache.log4j.Logger;
import org.apache.log4j.Category;
import org.apache.log4j.LogManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;

@RestController
@RequestMapping("/setup")
public class SetupController {
	
	private static Logger logger = Logger.getLogger(SetupController.class);
	
	@RequestMapping("/ip")
	public Poppy poppy(@RequestParam(value="param", required = false, defaultValue = "null") String a_str, HttpServletRequest request, HttpServletResponse response) 
	{
			String info = a_str;
			/*String ip_adress = request.getHeader("X-FORWARDED-FOR");
			
			if (ip_adress == null) {  
				   ip_adress = request.getRemoteAddr();  
			   }
	        System.out.println(ip_adress);*/
			
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
		    
		    HttpSession session = request.getSession();
		    
		    PoppyController.url_to_robot = "http://" + ip + ":8080";
		    
		    logger.info("Get robot IP Adress :" + PoppyController.url_to_robot);
		    
		    /*String str = "été";
		    
		    byte[] bytes = str.getBytes(Charset.forName("UTF-8"));
		    System.out.println("\n Encode: " + bytes.toString());
		    
		    String str_2 = new String(bytes,StandardCharsets.UTF_8);
		    System.out.println("\n Decode: " + str_2);
		    LaunchPrimitive.playSpeakPrimitive(str_2);	
		    
		    String str_3 = new String(bytes,StandardCharsets.US_ASCII);
		    System.out.println("\n Decode: " + str_3);
		    LaunchPrimitive.playSpeakPrimitive(str_3);*/	
		    
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
