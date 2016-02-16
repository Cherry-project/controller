package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class CirqueController {


    @RequestMapping("/cirque")
    public Cirque cirque() {
    	
    	String info = new String();

    	HttpURLConnectionExample test = new HttpURLConnectionExample();

    	try{

	    	info = test.sendGet();

    	} catch (Exception e){

    		info = "erreur";
    	}



        return new Cirque(info);
    }
}
