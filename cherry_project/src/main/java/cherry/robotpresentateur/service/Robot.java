package cherry.robotpresentateur.service;

public class Robot {
	
	private String name;
	private String ip;
	
	public Robot(){
		
		name = "no_name";
		ip ="0";
		
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = "http://" + ip + ":8080";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
