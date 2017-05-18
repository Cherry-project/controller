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
	public Poppy setMultiplicationCoefficient(@RequestParam(value="param", required = false, defaultValue = "null") String aStr) {
       
		String info = new String();
		
		// Stop head mvt if activated
		String currentPrimitive = LaunchPrimitive.getRunningPrimitiveList();		
		int indexHead = currentPrimitive.indexOf("head_idle_motion");
		
		if(indexHead != -1){
			LaunchPrimitive.stopPrimitive("head_idle_motion");
		}
				
		// Set listen_state parameter "Multi" 
		//LaunchPrimitive.setListenStateParameter("multi");
		
		// trigger a random multi
		if( aStr.equals("null")){
			multi.playRandomMultiplication(multi);
			info = "Play Random multiplication";

		}
		// trigger a multi 
		else if ( Integer.parseInt(aStr) > 0 || Integer.parseInt(aStr) <= 10){
			int b = 8;

			multi.setNumber_a(Integer.parseInt(aStr));
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
			LaunchPrimitive.startSpeakPrimitive("Je ne peux pas faire cette multiplication.");
			return new Poppy("impossible de faire cette multiplication");
		}
		
		// Back to listen state
		//LaunchPrimitive.ListenPrimitive();
		return new Poppy(info);
    }
	
	// used to compute the answer
	@RequestMapping("/answer")
	public Poppy answerComputation(@RequestParam(value="param", defaultValue="Null") int response) {
        
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
			LaunchPrimitive.startSpeakPrimitive("Tu as donn\u00E9 la bonne r\u00E9ponse!");
			LaunchPrimitive.startBehaviorPrimitive("rest_open_behave");
			//System.out.println("\n Tu as bien r\u00E9pondu gamin!");
			
			// Reset to 0
			multi.setAlready_Done(0);
			
			// Back to normal listen state
			//LaunchPrimitive.setListenStateParameter("normal");
		}
		else if ( response !=  multi.getResult() && multi.getAlready_Done() == 1)
		{
			LaunchPrimitive.startSpeakPrimitive("A\u00efe a\u00efe a\u00efe;  tu n'as pas donn\u00E9 la bonne r\u00E9ponse!");
			//System.out.println("\n Ce n'est pas la bonne r\u00E9ponse, on l'a refait");
			multi.playMultiplication(multi);

			
		}
		else if ( response !=  multi.getResult() && multi.getAlready_Done() == 2)
		{
			LaunchPrimitive.startSpeakPrimitive("Ce n'est toujours pas la bonne r\u00E9ponse, la bonne r\u00E9ponse est " + multi.getResult() );
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
		
		LaunchPrimitive.startSpeakPrimitive("Tu as donn\u00E9 la bonne r\u00E9ponse!");
		LaunchPrimitive.startBehaviorPrimitive("rest_open_behave");
		//System.out.println("\n Tu as bien r\u00E9pondu gamin!");
		
		// Reset to 0
		multi.setAlready_Done(0);
		return new Poppy("Manual answer");
	
	}
	@RequestMapping("/false_answer")
	public Poppy falseAnswer(@RequestParam(value="param", defaultValue="Null") int response) {
	
		if ( multi.getAlready_Done() == 1)
		{
			LaunchPrimitive.startSpeakPrimitive("Aie aie aie;  tu n'as pas donn\u00E9 la bonne r\u00E9ponse!");
			//System.out.println("\n Ce n'est pas la bonne r\u00E9ponse, on l'a refait");
			multi.playMultiplication(multi);

			
		}
		else if ( multi.getAlready_Done() == 2)
		{
			LaunchPrimitive.startSpeakPrimitive("Ce n'est toujours pas la bonne r\u00E9ponse, la bonne r\u00E9ponse est " + multi.getResult() );
			LaunchPrimitive.startBehaviorPrimitive("rest_open_behave");
			//System.out.println("\n Deux fois faux, ca fait beaucoup gamin! La r\u00E9ponse est " + multi.getResult());
			
			// Reset to 0
			multi.setAlready_Done(0);
			
			// Back to normal listen state
			//LaunchPrimitive.setListenStateParameter("normal");
		}
		return new Poppy("Manual answer");
	
	}
	@RequestMapping("/learn_all_multiplications")
	public Poppy learnQuestion(@RequestParam(value="param", defaultValue="Null") int response) {
		
		multi.learnAllMultiplications();
		
		
		return new Poppy("Learn all questions");
	
	}
}


