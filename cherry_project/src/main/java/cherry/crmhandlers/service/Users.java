package cherry.crmhandlers.service;


import java.util.ArrayList;

public class Users {
	
	private String email;
	private ArrayList<String> content_title = new ArrayList<String>();
	private ArrayList<String> content = new ArrayList<String>();
	
	public Users(){
		
		email = "_enfant@gmail.com";
	
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<String> getContent_title() {
		return content_title;
	}

	public void setContent_title(ArrayList<String> content_title) {
		this.content_title = content_title;
	}	
	public ArrayList<String> getContent() {
		return content;
	}

	public void setContent(ArrayList<String> content) {
		this.content = content;
	}
}
