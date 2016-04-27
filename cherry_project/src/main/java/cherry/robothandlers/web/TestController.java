package cherry.robothandlers.web;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cherry.robothandlers.service.LaunchPresentation;
import cherry.robothandlers.service.LaunchPrimitive;
import cherry.robothandlers.service.Poppy;


@RestController
@RequestMapping("/test")
public class TestController {
    
	private static Logger logger = Logger.getLogger(TestController.class);
	
	@RequestMapping("/behave")
	public Poppy poppy(@RequestParam(value="name") String a_str) 
	{
			String info = "\n Je joue le comportement suivant: " + a_str;
			
			if(!LaunchPresentation.is_presentation_running)
			{
				logger.info("Play behavior :" + a_str);
				LaunchPrimitive.playBehaviorPrimitive(a_str);
			}
			else {
				logger.warn("A presentation is running. Please retry later");
			}
		    return new Poppy(info);  
	    }
	
}
