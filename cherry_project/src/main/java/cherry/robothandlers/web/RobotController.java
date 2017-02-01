package cherry.robothandlers.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cherry.robothandlers.service.Poppy;

@RestController
public class RobotController {
	
	
	@RequestMapping("/speakfinished")
	public Poppy speakFinished(@RequestParam(value="id", required = false, defaultValue = "null") String name, HttpServletRequest request, HttpServletResponse response) 
	{	
		// TODO
		// Detect when the robot has finished instead of polling the robot
		return new Poppy("");	
	}
	
	@RequestMapping("/behavefinished")
	public Poppy behaveFinished(@RequestParam(value="id", required = false, defaultValue = "null") String name, HttpServletRequest request, HttpServletResponse response) 
	{
		// TODO
		// Detect when the robot has finished instead of polling the robot
		return new Poppy("");	
	}

}
