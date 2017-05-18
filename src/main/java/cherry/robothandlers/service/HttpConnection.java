package cherry.robothandlers.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.io.OutputStreamWriter;

import org.apache.log4j.Logger;
import org.json.JSONObject;

public class HttpConnection {

	//public final String USER_AGENT = "Mozilla/5.0";
	private static Logger logger = Logger.getLogger(HttpConnection.class);

	// HTTP GET request
	public static String sendGet(String url) throws Exception {

		String USER_AGENT = "Mozilla/5.0";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		
		// optional default is GET
		con.setRequestMethod("GET");
		con.setConnectTimeout(1000*5);
		con.setReadTimeout(1000*5);
		
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		logger.info("Sending 'GET' request to URL : " + url);
		
		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'GET' request to URL : " + url);
		//System.out.println("Response Code : " + responseCode);
		
		logger.info("Response code from " + url + " : " + responseCode);
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}
	
	// HTTP POST request
	public static String sendPost(String url,String urlParameters) throws IOException {

		String USER_AGENT = "Mozilla/5.0";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		// Pour être compatible avec l'encoding python utiliser ceci
		//con.setRequestProperty("Accept-Language", "fr-FR,fr;q=0.8,en-US;q=0.6,en;q=0.4");
		//con.setRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.3");
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.setRequestProperty("Accept", "application/json");
		
		//
		byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
		int    postDataLength = postData.length;
		con.setRequestProperty( "charset", "utf-8");
		con.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
		//
		
		
		// Send post request
		con.setDoOutput(true);
		//logger.info("Response code from " + url + " : " + con.getContent().toString());
		
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.write(postData);
		wr.flush();
		wr.close();
	
		
		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + urlParameters);
		//System.out.println("Response Code : " + responseCode);
		
		logger.info("Sending POST to url: " + url  + " With parameters: " + urlParameters);
		logger.info("Response code from " + url + " : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		System.out.println(response.toString());
		return  response.toString();


	}

	// HTTP GET request
	public static int sendGetCode(String url) throws Exception {

		String USER_AGENT = "Mozilla/5.0";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		logger.info("Sending 'GET' request to URL : " + url);
		
		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'GET' request to URL : " + url);
		//System.out.println("Response Code : " + responseCode);
		
		logger.info("Response code from " + url + " : " + responseCode);
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return responseCode;
	}
	
	// HTTP POST request
	public static int sendPostJson(String url,JSONObject json) throws IOException {

		String USER_AGENT = "Mozilla/5.0";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		//con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Accept-Language", "fr-FR,fr;q=0.8,en-US;q=0.6,en;q=0.4");
		con.setRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.3");
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.setRequestProperty("Accept", "application/json");
		
		//
		//byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
		//int    postDataLength = postData.length;
		//con.setRequestProperty( "charset", "utf-8");
		//con.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
		//
		
		
		// Send post request
		con.setDoOutput(true);
		//logger.info("Response code from " + url + " : " + con.getContent().toString());
		
		OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
		wr.write(json.toString());/*Change Here*/
		wr.flush();
		wr.close();

		
		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + urlParameters);
		//System.out.println("Response Code : " + responseCode);
		
		logger.info("Sending POST to url: " + url  + " With parameters: " + json.toString());
		logger.info("Response code from " + url + " : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
			System.out.println("response : " +  inputLine);
		}
		in.close();
		
		//print result
		System.out.println(response.toString());
		return  responseCode;
	}
	
}
