package cherry.chatterbot.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

//import org.apache.log4j.Logger;
import cherry.robothandlers.web.SetupController;


public class Chatterbot {

	private static String sauveInput = "";
	private static String var = "";
	private static String varTemp = "";
	private static String cerveau = "AR.txt";
	private static String vraiesvars= "";
	private static String nom = "";
	private static String noReut = "";
	private static String rappel = "";
	
	private static String url = SetupController.urlToWebsite + "/Genesis/chatterbot23.php";
	
	//private static Logger logger = Logger.getLogger(Chatterbot.class);
	
		
	public static String sendToChatterbot(String msg) throws IOException {
		
		String USER_AGENT = "Mozilla/5.0";
		String urlParameters ="vartemp=" + varTemp + "&cerveau=" + cerveau + "&vraiesvars=" + vraiesvars + "&nom=" + nom + "&noreut=" + noReut + "&var=" + var + "&usersay=" + msg + "&sauveinput=" + sauveInput + "&rappel=" +rappel;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("Accept", " text/html; charset=utf-8");
		
		//
		byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
		int    postDataLength = postData.length;
		con.setRequestProperty( "charset", "utf-8");
		con.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
		//
		
		
		// Send post request
		con.setDoOutput(true);
		
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.write(postData);
		wr.flush();
		wr.close();
	
		
		//int responseCode = con.getResponseCode();
		
		// logger.info("Sending POST to url: " + url  + " With parameters: " + urlParameters);
		// logger.info("Response code from " + url + " : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		// Get the result from chatterbot
		String responseFromChatterbot = getResponseFromChatterbot(response.toString()); 
		
		// Return response
		return responseFromChatterbot;
	}
	
	private static String getResponseFromChatterbot(String response){
		
		int start = response.indexOf("Bot : ");
		int end = 0;
		
		// Find right indexes
		if(response.contains("Bot : <IGNORER>") == true){
		    start = response.indexOf("Bot : <IGNORER>");
		    start = start +15;
		}
		if(response.contains("Bot : <IGNO")== false){
			start = start + 5;
		}
		
		if(response.indexOf("<NOREUT>") == 1){
			end = response.indexOf("<NOREUT>");
		}
		if(response.contains("<NOREUT>") == false){
			end = response.indexOf("<form method");
		}
		
		// Try to get response
		try{
			response = response.substring(start, end);
		}
		catch(Exception e)
		{
			response = "No response from chatterbot";
		}
		
		return response.trim();
		
	}
	
		
		
}
