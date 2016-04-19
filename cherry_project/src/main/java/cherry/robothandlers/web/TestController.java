package cherry.robothandlers.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cherry.robothandlers.service.LaunchPrimitive;
import cherry.robothandlers.service.Poppy;


@RestController
@RequestMapping("/test")
public class TestController {
    
	@RequestMapping("/behave")
	public Poppy poppy(@RequestParam(value="name") String a_str) 
	{
			String info = "\n Je joue le comportement suivant: " + a_str;
			
		    LaunchPrimitive.playBehaviorPrimitive(a_str);
		    
		    return new Poppy(info);  
	    }
	
}
