package cherry.gamehandlers.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cherry.robothandlers.service.LaunchPresentation;
import cherry.robothandlers.service.Poppy;

import org.json.JSONObject;
//LOG
import org.apache.log4j.Logger;

@RestController
public class JsonRetriever {
	 
	private static Logger logger = Logger.getLogger(JsonRetriever.class);
	
	@RequestMapping("/jsonreader")
	public Poppy poppy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
		  
		  System.out.println("\n1		" + jb.toString());
		  
		  //Response to Website
		  /*response.setContentType("text/html");
		  response.setStatus(HttpServletResponse.SC_OK);
		  response.getOutputStream();*/
		  JSONObject my_json = new JSONObject();
		  
		  try{
			  my_json =new JSONObject((jb.toString()));
		  }
		  catch(Exception e){
			  logger.error("It's not a JSON", e);
			  info  = "Not a Json";
			  return new Poppy(info);
			  }
		  
		  if(!LaunchPresentation.is_presentation_running)
		  {
			  try{
				  logger.info("Play presentation from Website");
				  info  = "Play presentation";
				  LaunchPresentation.playFromJson(my_json);
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
}
	
		

