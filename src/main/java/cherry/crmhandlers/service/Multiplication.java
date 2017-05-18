package cherry.crmhandlers.service;

import java.util.Random;

import cherry.robothandlers.service.LaunchPrimitive;

public class Multiplication {

	private int number_a;
	private int number_b;
	private int result;
	private int already_done;
	
	
	public Multiplication() {
		// TODO Auto-generated method stub
		
		number_a = -1;
		number_b = -1;
		already_done = 0;
	}	

	public  int getNumber_a() {
		return number_a;
	}

	public void setNumber_a(int a) {
		number_a = a;
	}

	public  int getNumber_b() {
		return number_b;
	}

	public void setNumber_b(int b) {
		number_b = b;
	}

	public  int getAlready_Done() {
		return already_done;
	}

	public void setAlready_Done(int already) {
		already_done = already;
	}
	
	public  int getResult() {
		return result;
	}

	public void setResult(Multiplication m) {
		result = m.number_a*m.number_b;
	}
	

	public void playMultiplication(Multiplication m) {
		
		// text
		String str = "Combien font " + Integer.toString(m.number_a) + " fois " + Integer.toString(m.number_b) + " ?";
		LaunchPrimitive.startSpeakPrimitive(str);
		
		// behavior
		LaunchPrimitive.startBehaviorPrimitive("question_behave");
		
		//Set result
		m.setResult(m);
		//Mark as already done
		m.setAlready_Done(m.getAlready_Done()+1);
	}
	public void playRandomMultiplication(Multiplication m) {
		
		Random rand = new Random();
		
		m.setNumber_a(rand.nextInt(10 - 1 + 1) + 1);
		m.setNumber_b(rand.nextInt(10 - 1 + 1) + 1); 
				
		// text
		String str = "Combien font " + Integer.toString(m.number_a) + " fois " + Integer.toString(m.number_b) + " ?";
		LaunchPrimitive.startSpeakPrimitive(str);
		
		// behavior
		LaunchPrimitive.startBehaviorPrimitive("left_arm_up_behave");
		
		//Set result
		m.setResult(m);
		// Mark as done
		m.setAlready_Done(m.getAlready_Done()+1);	
	}
	public void learnAllMultiplications() {
		
			
		for(int i =1; i<=10; i++)
		{
			for (int j = 10 ; j<=10; j++)
			{
				// text
				String str = "Combien font " + Integer.toString(i) + " fois " + Integer.toString(j) + " ?";
				LaunchPrimitive.startSpeakPrimitive(str);
			}
		}
	}	
}
