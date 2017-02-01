package cherry.gamehandlers.service;
//LOG
import org.apache.log4j.Logger;

import cherry.robothandlers.service.HttpConnection;
import cherry.robothandlers.web.SetupController;

public class ToWebsite {
	
	private static String url_to_website = SetupController.urlToWebsite;
	private static Logger logger = Logger.getLogger(ToWebsite.class);
	
	public static void displayPicture(String pathToWS, String picture_name){
		
		int idx = picture_name.lastIndexOf("/") + 1;
		if( idx != 0)
		{
			picture_name = picture_name.substring(idx);
		}
		
		
		try {
			
			logger.info("Set picture: " + picture_name + " at:" + url_to_website);			
			//HttpURLConnectionExample.sendGet(url_to_website + "/PhpProject_test/WS_video.php?name=" + picture_name);
			HttpConnection.sendGet(pathToWS + "?name=" + picture_name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error("Unable to set a picture " + picture_name + " at: " + url_to_website);
		}

	}
	
	public static void deletePicture(String pathToWS, String picture_name){
		
		int idx = picture_name.lastIndexOf("/") + 1;
		
		if( idx != 0)
		{
			picture_name = picture_name.substring(idx);
		}
		
		try {
			
			logger.info("Delete picture: " + picture_name + " at:" + url_to_website);
			//HttpURLConnectionExample.sendGet(url_to_website + "/PhpProject_test/WS_video.php?name=" + picture_name + "&owner=admin_off");
			HttpConnection.sendGet(pathToWS+"?name=" + picture_name + "&owner=admin_off");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error("Unable to delete picture " + picture_name + " at: " + url_to_website);
		}

	}
	
	public static void setListeningSignal(String cmd){
		
		try {
			logger.info("Set listen icon at:" + url_to_website);
			HttpConnection.sendGet(url_to_website + "/PhpProject_test/WS_ecoute.php?ecoute=" + cmd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error("Unable to set listen icon at: " + url_to_website);
		}
	}


	
	public static String getAvailableContent(String user_adress){
		
		String content = new String();
		try {
			logger.info("Get content available for user" + user_adress);
			content = HttpConnection.sendGet(url_to_website + "/PhpProject_test/WS_contents.php?email=" + user_adress);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error("Unable to get user " + user_adress +  " content at: " + url_to_website);
		}
		return content;

	}

}
