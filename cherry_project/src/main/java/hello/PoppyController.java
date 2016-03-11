package hello;

import java.util.ArrayList;
import java.io.IOException;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PoppyController {
	
	public static String url = "http://127.0.0.2:8888";
    
	@RequestMapping("/poppy")
	public Poppy poppy(@RequestParam(value="name", defaultValue="World") String name) {
        
		String info = new String();
		System.out.println("\n name: " + name);
		
		// PLay presentation
		if( name.toLowerCase().indexOf("sogeti")!= -1){
			info = "Play Sogeti";
			
			
			String excelFilePath = "D:/utilisateurs/jguichar/cherry_project_2/Resources/Sogeti.xlsx";
			try{
			LaunchPresentation.start(excelFilePath);
			}
			catch(IOException e){
				System.out.println("\n Erreur" + e);
			}
			catch(InterruptedException e){
			System.out.println("\n Erreur" + e);
			}
			
		}
		
		else if( name.toLowerCase().indexOf("mairie") != -1){
			info = "Play Mairie";
			
   			String excelFilePath = "D:/utilisateurs/jguichar/cherry_project_2/Resources/Prima.xlsx";
   			try{
   				LaunchPresentation.start(excelFilePath);
   				}
   				catch(IOException e){
   					System.out.println("\n Erreur" + e);
   				}
   				catch(InterruptedException e){
   				System.out.println("\n Erreur" + e);
   				}
			
		}
		else if(name.toLowerCase().indexOf("prima") != -1 ){
			info = "Play Prima";

			String excelFilePath = "D:/utilisateurs/jguichar/cherry_project_2/Resources/Prima.xlsx";
			try{
				LaunchPresentation.start(excelFilePath);
				}
				catch(IOException e){
					System.out.println("\n Erreur" + e);
				}
				catch(InterruptedException e){
				System.out.println("\n Erreur" + e);
				}
		}
		else if( name.toLowerCase().indexOf("bonjour") != -1 ){
			info = "Play Bonjour";
			LaunchPrimitive.playBehaviorPrimitive("double_me_behave");
			
			LaunchPrimitive.playSpeakPrimitive("Bonjour, je m'appelle Cherry.");
			
			
		}
		else{
			info = "Nom inconnu";
			LaunchPrimitive.playBehaviorPrimitive("hunkers_behave");

			LaunchPrimitive.playSpeakPrimitive("Je n'ai pas compris ce que vous avez dit. Veuillez répéter.");
			

		}
			
    		
        return new Poppy(info);
    }
}
