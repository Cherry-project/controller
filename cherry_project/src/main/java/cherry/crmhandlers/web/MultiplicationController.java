package cherry.crmhandlers.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cherry.crmhandlers.service.Multiplication;
import cherry.robothandlers.service.LaunchPrimitive;
import cherry.robothandlers.service.Poppy;

@RestController
@RequestMapping("/multi")
public class MultiplicationController {
	
	public static Multiplication multi = new Multiplication();
    
	// used to set the a single value
	@RequestMapping("/set")
	public Poppy poppy(@RequestParam(value="param", required = false, defaultValue = "null") String a_str) {
       
		String info = new String();
		
		// Stop head mvt if activated
		String current_primitive = LaunchPrimitive.getRunningPrimitiveList();		
		int index_head = current_primitive.indexOf("head_idle_motion");
		
		if(index_head != -1){
			LaunchPrimitive.stopPrimitive("head_idle_motion");
		}
				
		// Set listen_state parameter "Multi" 
		//LaunchPrimitive.setListenStateParameter("multi");
		
		// trigger a random multi
		if( a_str.equals("null")){
			multi.playRandomMultiplication(multi);
			info = "Play Random multiplication";

		}
		// trigger a multi 
		else if ( Integer.parseInt(a_str) > 0 || Integer.parseInt(a_str) <= 10){
			int b = 8;

			multi.setNumber_a(Integer.parseInt(a_str));
			multi.setNumber_b(b);			
			multi.playMultiplication(multi);
			
			info = "Play multiplication";
			try{
				Thread.sleep(1);
			}
			catch (InterruptedException e){
				info = "Play Random multiplication";
			}
			
		}
		
		else{
			LaunchPrimitive.playSpeakPrimitive("Je ne peux pas faire cette multiplication.");
			return new Poppy("impossible de faire cette multiplication");
		}
		
		// Back to listen state
		//LaunchPrimitive.ListenPrimitive();
		return new Poppy(info);
    }
	
	// used to compute the answer
	@RequestMapping("/answer")
	public Poppy poppy1(@RequestParam(value="param", defaultValue="Null") int response) {
        
		System.out.println("\n Already done ? : " + multi.getAlready_Done());
		String info = new String();
		try{
			Thread.sleep(1);
		}
		catch (InterruptedException e){
			info = "Response";
		}
		
		if ( response ==  multi.getResult())
		{
			LaunchPrimitive.playSpeakPrimitive("Tu as donn\u00E9 la bonne r\u00E9ponse!");
			LaunchPrimitive.playBehaviorPrimitive("rest_open_behave");
			//System.out.println("\n Tu as bien r\u00E9pondu gamin!");
			
			// Reset to 0
			multi.setAlready_Done(0);
			
			// Back to normal listen state
			//LaunchPrimitive.setListenStateParameter("normal");
		}
		else if ( response !=  multi.getResult() && multi.getAlready_Done() == 1)
		{
			LaunchPrimitive.playSpeakPrimitive("Aie aie aie;  tu n'as pas donn\u00E9 la bonne r\u00E9ponse!");
			//System.out.println("\n Ce n'est pas la bonne r\u00E9ponse, on l'a refait");
			multi.playMultiplication(multi);

			
		}
		else if ( response !=  multi.getResult() && multi.getAlready_Done() == 2)
		{
			LaunchPrimitive.playSpeakPrimitive("Ce n'est toujours pas la bonne r\u00E9ponse, la bonne r\u00E9ponse est " + multi.getResult() );
			//System.out.println("\n Deux fois faux, ca fait beaucoup gamin! La r\u00E9ponse est " + multi.getResult());
			
			// Reset to 0
			multi.setAlready_Done(0);
			
			// Back to normal listen state
			//LaunchPrimitive.setListenStateParameter("normal");
		}
			

		System.out.println("\n Result de l'enfant: " + response);
		
		
		info = "R\u00E9ponse de l'enfant: " + response;
		
		// back to listen state
		//LaunchPrimitive.ListenPrimitive();
		return new Poppy(info);
    }
	@RequestMapping("/right_answer")
	public Poppy rightAnswer(@RequestParam(value="param", defaultValue="Null") int response) {
		
		LaunchPrimitive.playSpeakPrimitive("Tu as donn\u00E9 la bonne r\u00E9ponse!");
		LaunchPrimitive.playBehaviorPrimitive("rest_open_behave");
		//System.out.println("\n Tu as bien r\u00E9pondu gamin!");
		
		// Reset to 0
		multi.setAlready_Done(0);
		return new Poppy("Manual answer");
	
	}
	@RequestMapping("/false_answer")
	public Poppy falseAnswer(@RequestParam(value="param", defaultValue="Null") int response) {
	
		if ( multi.getAlready_Done() == 1)
		{
			LaunchPrimitive.playSpeakPrimitive("Aie aie aie;  tu n'as pas donn\u00E9 la bonne r\u00E9ponse!");
			//System.out.println("\n Ce n'est pas la bonne r\u00E9ponse, on l'a refait");
			multi.playMultiplication(multi);

			
		}
		else if ( multi.getAlready_Done() == 2)
		{
			LaunchPrimitive.playSpeakPrimitive("Ce n'est toujours pas la bonne r\u00E9ponse, la bonne r\u00E9ponse est " + multi.getResult() );
			LaunchPrimitive.playBehaviorPrimitive("rest_open_behave");
			//System.out.println("\n Deux fois faux, ca fait beaucoup gamin! La r\u00E9ponse est " + multi.getResult());
			
			// Reset to 0
			multi.setAlready_Done(0);
			
			// Back to normal listen state
			//LaunchPrimitive.setListenStateParameter("normal");
		}
		return new Poppy("Manual answer");
	
	}
}


