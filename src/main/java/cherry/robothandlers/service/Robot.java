package cherry.robothandlers.service;

import java.util.ArrayList;
import java.util.List;

public class Robot {

	private String name;
	private String ip;
	private int port;
	private boolean isSpeaking;
	private boolean isMoving;
	private boolean isTaken;
	private List<String> primList;
	private List<String> speechList;

	public Robot(){
		
		name = "no_name";
		ip ="0";
		port = 0;
		isSpeaking = false;
		isMoving = false;
		isTaken = false;
		setPrimList(new ArrayList<String>());
		setSpeechList(new ArrayList<String>());
		
		
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip, int port) {
		this.ip = "http://" + ip + ":" + port;
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
	
	public boolean isTaken() {
		return isTaken;
	}
	public void setTaken(boolean isTaken) {
		this.isTaken = isTaken;
	}
	
	public List<String> getSpeechList() {
		return speechList;
	}
	public void setSpeechList(List<String> speechList) {
		this.speechList = speechList;
	}
	
	public List<String> getPrimList() {
		return primList;
	}
	public void setPrimList(List<String> primList) {
		this.primList = primList;
	}  
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	
	@Override
    public String toString()
	{
            return "Robot [name = " + this.getName() + ", ip = " + this.getIp() + "]";
    }
}
