package cherry.robothandlers.service;

import java.util.Iterator;

import org.apache.log4j.Logger;

import cherry.robothandlers.web.SetupController;

public class UpdateRobotListThread extends Thread{
    
	private static Logger logger = Logger.getLogger(UpdateRobotListThread.class);
	
	public void run() {
        
		logger.info("Start of Thread");
    
        while(true){
        	
        	if(!LaunchPresentation.isPresentationRunning)
        	{
	
	        	Iterator<Robot> robotIdx = SetupController.robotList.iterator();
	        	while (robotIdx.hasNext()) {
	        	    Robot currentRobot = robotIdx.next();
	        	    
	        	    // Ping
					String status = new String();
					try {
						status = HttpConnection.sendGet(currentRobot.getIp());
						logger.info("Robot " + currentRobot.getName() + " still alive!");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						logger.info("Robot " + currentRobot.getName() + " is unreachable!");
					}
					
					// If not reachable, robot deleted
					if(status.isEmpty())
					{
						logger.info("Robot " + currentRobot.getName() + " no longer available. Robot removed from list");
						//System.out.println("\nRobot " + currentRobot.getName() + " no longer anymore. Robot removed from list");
						robotIdx.remove(); 
					}	        	                
	        	}
        	}
		    

        	logger.info("Update Thread still running");
			
			try {
				Thread.sleep(300000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
      
}


