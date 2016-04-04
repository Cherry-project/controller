package hello;

import java.util.ArrayList;

import java.io.File;
import java.io.IOException;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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
