package cherry.gamehandlers.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cherry.robothandlers.service.LaunchPresentation;
import cherry.robothandlers.service.Poppy;


import org.json.JSONObject;
//LOG
import org.apache.log4j.Logger;

@RestController
public class JsonRetriever {
	 
	private static Logger logger = Logger.getLogger(JsonRetriever.class);
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/{jsonreader}")
	public Poppy jsonRetriever(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		// GET Ip adress
		/*String ip = request.getHeader("X-Forwarded-For");  
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
				    
	    System.out.println("\n Hello Client: " + ip+ " !");
	    
	    if( ip.contentEquals("0:0:0:0:0:0:0:1")){
	    	ip = "localhost";
	    }*/
	    
		  StringBuffer jb = new StringBuffer();
		  String line = null;
		  String info = new String();
		
		  try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		  } catch (Exception e) { 
			  logger.error("Can't read the request", e);
			  info  = "Can't read request";
		  }
		  
		  //System.out.println("\n1		" + jb.toString());
		  
		  JSONObject myJson = new JSONObject();
		  
		  try{
			  myJson =new JSONObject((jb.toString()));
		  }
		  catch(Exception e){
			  logger.error("It's not a JSON", e);
			  info  = "Not a Json";
			  return new Poppy(info);
		  }
		  
		  // extraire le JSON ici
		  // s'assurer de la dispo des robots concerne.
		  // cree une instance de presentation
		  // la lancer
		  
		  
		  if(!LaunchPresentation.isPresentationRunning)
		  {
			  try{
				  logger.info("Play presentation from Website");
				  info  = "Play presentation";
				  //SetupController.urlToWebsite = "http://" + ip + ":80/";
				  LaunchPresentation.playFromJson(myJson);
			  }
			  catch(Exception e){
				  info  = "Exception raised when attempting to play presentation";
				  logger.error("Exception raised when attempting to play presentation from WebSite", e);
			  }
		  }
		  else
		  { 
			  logger.warn("Presentation already running!");
			  info =  "Presentation already running! Please wait until the end";
			  return new Poppy(info);
		  }
		  
	
		return new Poppy(info);
	}
		
		/*@CrossOrigin
		@RequestMapping(method = RequestMethod.POST, value = "/{setRobotParameters}")
		public Poppy setParameters(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			
			StringBuffer jb = new StringBuffer();
			String line = null;
			String info = new String();
			
			try {
				BufferedReader reader = request.getReader();
				while ((line = reader.readLine()) != null)
			      
					
				jb.append(line);
			} catch (Exception e) { 
				logger.error("Can't read the request", e);
				info  = "Can't read request";
			}
			  
			  //System.out.println("\n1		" + jb.toString());
			  
		    JSONObject myJson = new JSONObject();
			  
		    try{
			    myJson =new JSONObject((jb.toString()));
		    }
		    catch(Exception e){
		    	logger.error("It's not a JSON", e);
				info  = "Not a Json";
				return new Poppy(info);
			}
				
				
				
			return new Poppy(info);
		}*/
}

	
		

