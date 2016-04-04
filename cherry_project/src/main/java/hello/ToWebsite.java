package hello;

public class ToWebsite {
	
	private static String url_to_website = PoppyController.url_to_website;
	
	
	public static void displayPicture(String picture_name){
		
		try {
			HttpURLConnectionExample.sendGet(url_to_website + "/WS_video.php?name=" + picture_name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void deletePicture(String picture_name){
		
		try {
			HttpURLConnectionExample.sendGet(url_to_website + "/WS_video.php?name=" + picture_name + "&owner=admin_off");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void setListeningSignal(String cmd){
		
		try {
			HttpURLConnectionExample.sendGet(url_to_website + "/PhpProject_test/WS_ecoute.php?ecoute=" + cmd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
