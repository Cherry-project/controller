package cherry.crmhandlers.web;

// REST
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cherry.crmhandlers.service.Users;
import cherry.gamehandlers.service.ToWebsite;
import cherry.robothandlers.service.Poppy;

import org.json.JSONObject;
// LOG
import org.apache.log4j.Logger;

import java.util.ArrayList;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	private static Logger logger = Logger.getLogger(UsersController.class);
	
	@RequestMapping("/define")
	public Poppy setNewUser(@RequestParam(value="adress", required = false, defaultValue = "null") String a_str) {
	
		Users current_user = new Users();
		System.out.println("\nBonjour " + a_str);
		String info = new String();
				

		current_user.setEmail(a_str);
		
		logger.info("Hello user " + current_user.getEmail());
		
		String content = ToWebsite.getAvailableContent(a_str);
		//System.out.println("\nContent1" + content);
		
		int start = content.indexOf("{");
		content = content.substring(start);
		
		System.out.println("\nContent2" + content);
		
		JSONObject my_json =new JSONObject(content);
		
		int step_nb = my_json.length();
		System.out.println("\nNombre de contenu dispo: " + step_nb);
		
		// Declare Arrays
		ArrayList<String> title_list = new ArrayList<String>();
		ArrayList<String> json_list = new ArrayList<String>();

		
		for(int i = 0; i < step_nb; i++ ) 
		{
			JSONObject loop_json = my_json.getJSONObject("_" + Integer.toString(i));
			//System.out.println("\n" + loop_json.toString() );
			
			
			String title= loop_json.getString("titre"); 
			JSONObject scenario = loop_json.getJSONObject("url"); 
			
			title_list.add(title.trim());
			json_list.add(scenario.toString());
			
		}
		System.out.println("\n " + title_list);
		System.out.println("\n " + json_list);
		
		current_user.setContent_title(title_list);
		current_user.setContent(json_list);
		
		info = "Content for user " + current_user.getEmail() + " retrieved";
		logger.info("Content for user " + current_user.getEmail() + " retrieved");
		
		return new Poppy(info);
	}

}
