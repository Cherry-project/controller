package cherry.robothandlers.web;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cherry.robothandlers.service.LaunchPrimitive;
import cherry.robothandlers.service.Robot;

@RestController
@RequestMapping("/robot")
public class RobotController {
	private static Logger logger = Logger.getLogger(TestController.class);

	@CrossOrigin
	@RequestMapping(value = "/speakfinished", method = RequestMethod.POST)
	public void speakFinished(@RequestParam(value="id", required = true) String name)
	{	
		Robot robot = SetupController.getRobot(name);

		if(!robot.getName().equals("no_name")){
			logger.info("Robot finished to Speak.");
			Iterator<String> speechIt = robot.getSpeechList().iterator();
			if(speechIt.hasNext()){
				String speech = speechIt.next();
				if(LaunchPrimitive.startSpeakPrimitive(speech,robot.getIp()) == 0){
					robot.setIsSpeaking(true);
					logger.info("I speak the following text : " + speech);
				}else
					robot.setIsSpeaking(false);
				speechIt.remove();
			}else{
				robot.setIsSpeaking(false);
				logger.info("End of the set of phrases.\n");
			}
		}else{
			logger.warn("No robot found. Please retry later");
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/behavefinished", method = RequestMethod.POST)
	public void behaveFinished(@RequestParam(value="id", required = true) String name)
	{
		Robot robot = SetupController.getRobot(name);
		if(!robot.getName().equals("no_name")){
			logger.info("Robot finished behave.");
			Iterator<String> primIt = robot.getPrimList().iterator();
			if(primIt.hasNext()){
				String primitive = primIt.next();
				if(LaunchPrimitive.startBehaviorPrimitive(primitive,robot.getIp()) == 0)
					robot.setIsMoving(true);
				else
					robot.setIsMoving(false);
				logger.info("I played the following behave : " + primitive);
				primIt.remove();
			}else{
				robot.setIsMoving(false);
				logger.info("End of the set of primitives.\n");
			}

		}else{
			logger.warn("No robot found. Please retry later");
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/take", method = RequestMethod.POST)
	public void robotTaken(@RequestParam(value="id", required = true) String name)
	{	
		Robot robot = SetupController.getRobot(name);

		if(!robot.getName().equals("no_name")){
			logger.info("Robot "+ robot.getName() +" taken.");
			robot.setTaken(true);
		}else{
			logger.warn("No robot found. Please retry later");
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/release", method = RequestMethod.POST)
	public void robotRelease(@RequestParam(value="id", required = true) String name)
	{	
		Robot robot = SetupController.getRobot(name);

		if(!robot.getName().equals("nuo_name")){
			logger.info("Robot "+ robot.getName() +" is released.");
			robot.setTaken(false);
		}else{
			logger.warn("No robot found. Please retry later");
		}
	}
}
