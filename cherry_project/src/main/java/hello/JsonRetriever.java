package hello;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
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
		  
		  try{
			  logger.info("Play presentation from Website");
			  info  = "Play presentation";
			  LaunchPresentation.playFromJson(my_json);
		  }
		  catch(Exception e){
			  info  = "Exception raised when attempting to play presentation";
			  logger.error("Exception raised when attempting to play presentation from WebSite", e);
		  }
		  //} catch (Exception e) {
		    // crash and burn
			//  System.out.println("\n ERREUR");
		  //}
		  

			/*int step_nb = my_json.length();
			System.out.println("\n Nombre d'etapes: " + step_nb);
			
			// Declare Arrays
			ArrayList<String> list = new ArrayList<String>();
			list.add(0,"Start");
			ArrayList<String> list_text = new ArrayList<String>();
			list_text.add(0,"Start");
			ArrayList<String> list_img = new ArrayList<String>();
			list_img.add(0,"Start");
			
			for(int i = 0; i < step_nb; i++ ) 
			{
				JSONObject loop_json = my_json.getJSONObject("_" + Integer.toString(i));
				System.out.println("\n" + loop_json.toString() );
				
				
				String behave = loop_json.getString("Behave"); 
				String text = loop_json.getString("Text"); 
				String slide = loop_json.getString("Slide"); 
				
				list.add(behave.trim());
				list_text.add(text);
				list_img.add(slide.trim());
				
			}
			
			System.out.println("\n Behave: " + list);
			System.out.println("\n Text: " + list_text);
			System.out.println("\n Slide: " + list_img);
		
			try{
				LaunchPresentation.play(list, list_text, list_img);
			}
			catch(Exception e)
			{
				System.out.println("\n Erreur" + e);
			}*/
		
		
		
		/*System.out.println("\n" + request.toString());
		//String queryStr = URLDecoder.decode(request.getQueryString(), "UTF-8");
		//System.out.println("\n" + queryStr);
		
		// Get the request
		//request.setCharacterEncoding("UTF-8");
		
		System.out.println("\n1		" + request.toString());
		System.out.println("\n2		" + request.getCharacterEncoding());
		System.out.println("\n2		" + request.getContentLength());
		System.out.println("\n2		" + request.getContentType());
		System.out.println("\n2		" + request.getQueryString());
		
		System.out.println("\n3 	" + request.getInputStream().toString());
		Map<String, String[]> received_str = request.getParameterMap();
		System.out.println("\n2		" + received_str.toString());
		
		
		String my_json_str = received_str.keySet().toString();
		System.out.println("\n" + my_json_str);
		

		// Make it parsable
		my_json_str = my_json_str.substring(1, (my_json_str.length() -1));
		//System.out.println("\n" + my_json_str);
		
		// Parsing
		JSONObject json = new JSONObject(my_json_str);
		
		// Make the string parsable into JSON Object
		System.out.println("\n taille du json" + json.length());
		
		
		int step_nb = json.length();
		System.out.println("\n Nombre d'etapes: " + step_nb);
		System.out.println("\n" + json.toString() );*/
		
		// Declare Arrays
		/*ArrayList<String> list = new ArrayList<String>();
		list.add(0,"Start");
		ArrayList<String> list_text = new ArrayList<String>();
		list_text.add(0,"Start");
		ArrayList<String> list_img = new ArrayList<String>();
		list_img.add(0,"Start");
		
		for(int i = 0; i < step_nb; i++ ) 
		{
			JSONObject loop_json = JSON.getJSONObject(Integer.toString(i));
			System.out.println("\n" + loop_json.toString() );
			
			
			String behave = loop_json.getString("Behave"); 
			String text = loop_json.getString("Text"); 
			String slide = loop_json.getString("Slide"); 
			
			list.add(behave);
			list_text.add(text);
			list_img.add(slide);
			
		}
		
		System.out.println("\n Behave: " + list);
		System.out.println("\n Text: " + list_text);
		System.out.println("\n Slide: " + list_img);*/
		
		//JSONArray array = new JSONArray(my_json); 
		
		//JSONParser parser = new JSONParser();
		//JSONObject json = (JSONObject) parser.parse(my_json);
		
		
		
		//JSONObject json = readJsonFromUrl(requestURL.toString());
	    //System.out.println(json.toString());
	    //System.out.println(json.get("id"));
		
		
		//String inputString = param;
		

		return new Poppy(info);
	}
}
	
		

