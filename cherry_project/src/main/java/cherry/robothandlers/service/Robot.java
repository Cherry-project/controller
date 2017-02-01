package cherry.robothandlers.service;

public class Robot {
	
	private String name;
	private String ip;
	private boolean isSpeaking;
	private boolean isMoving;
	
	public Robot(){
		
		name = "no_name";
		ip ="0";
		isSpeaking = false;
		isMoving =false;
		
		
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

	public boolean getIsSpeaking() {
		return isSpeaking;
	}

	public void setIsSpeaking(boolean isSpeaking) {
		this.isSpeaking = isSpeaking;
	}

	public boolean getIsMoving() {
		return isMoving;
	}

	public void setIsMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
	

}
