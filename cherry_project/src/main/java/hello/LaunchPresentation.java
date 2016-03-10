package hello;

import java.io.IOException;
import java.util.ArrayList;

public class LaunchPresentation {

	public static void start( String excelFilePath) throws InterruptedException, IOException {
		
		
		
		// Declare Arrays
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> list_text = new ArrayList<String>();
		ArrayList<String> list_img = new ArrayList<String>();
		
		// Get list of primitive into Excel file
		try {
			list = SimpleExcelReaderExample.getExcelField(excelFilePath,"Behave");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		list.add(0,"Start");
		System.out.println("\nList of " + "Behave: " + list );
		
		// Get list of TTS into Excel file
		try {
			list_text = SimpleExcelReaderExample.getExcelField(excelFilePath,"Text");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		list_text.add(0,"Start");
		System.out.println("\nList of " + "Text: " + list_text );
		
		//Get list of picture to display
		
		try {
			list_img = SimpleExcelReaderExample.getExcelField(excelFilePath,"Slide");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		list_img.add(0,"Start");
		System.out.println("\nList of " + "diapo: " + list_img );
	
		
		int index_behave = -1;
		int index_speak = -1;
		
		for(int i=1; i< list.size(); i++){
			
			// Check currently running primitive
			String current_primitive = LaunchPrimitive.getRunningPrimitiveList();
			System.out.println("\nStr " + current_primitive );
			
			index_behave = current_primitive.indexOf(list.get(i-1));
			index_speak = current_primitive.indexOf("speak");
			
			System.out.println("\nIndex: " + index_behave +"of :" + list.get(i-1) + " speak: " + index_speak);
			
			// Check wether speak or behave are still running
			while( index_speak != -1 && index_behave != -1)
			{
				Thread.sleep(1000);
				
				System.out.println("\n			Wainting for " + list.get(i-1) + " to stop" );
				
				current_primitive = LaunchPrimitive.getRunningPrimitiveList();
				System.out.println("\n			Str " + current_primitive );
				
				index_behave = current_primitive.indexOf(list.get(i-1));
    			index_speak = current_primitive.indexOf("speak");
				
    			System.out.println("\n			Index: " + index_behave +" of behave: " + list.get(i-1) + " of speak: " + index_speak);
    			
			}
			
			// Picture			
			if ( !list_img.get(i).equals(list_img.get(i-1)))
			{	
				System.out.println("\n Old: " + list_img.get(i-1) + " New: " + list_img.get(i));
				// Shut down previous img (exept "start)
  				if (list_img.get(i-1) != "Start"){
					
  					try {
						HttpURLConnectionExample.sendPost("http://52.50.54.27/WS_video.php","name=" + list_img.get(i-1) + "&owner=admin_off");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("\n Erreur" + e);
						e.printStackTrace();
					}
				}
				// set the new one 
				try {
					HttpURLConnectionExample.sendPost("http://52.50.54.27/WS_video.php","name=" + list_img.get(i));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("\n Erreur" + e);
					e.printStackTrace();
				}

			}
			// text
			LaunchPrimitive.playSpeakPrimitive(list_text.get(i));
			System.out.println("\n Speak: " + list_text.get(i));
			
			// behavior
			LaunchPrimitive.playBehaviorPrimitive(list.get(i));
			System.out.println("\n Play behavior: " + list.get(i));
				
			
		}
		// TODO Auto-generated method stub

	}

}
