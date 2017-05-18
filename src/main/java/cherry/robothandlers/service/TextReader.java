package cherry.robothandlers.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

public class TextReader {
	
	public static ArrayList<String> getLines(String filePath) throws FileNotFoundException, IOException {
	
		ArrayList<String> lines = new ArrayList<String>();
		
		try
		{
		    File f = new File (filePath);
		    FileReader fr = new FileReader (f);
		    BufferedReader br = new BufferedReader (fr);
		 
		    try
		    {
		        String line = br.readLine();

		        
		        while (line != null)
		        {
		        	lines.add(line);
		            System.out.println (line);
		            line = br.readLine();     
		        }

		        br.close();
		        fr.close();
		    }
		    catch (IOException exception)
		    {
		        System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		    }
		}
		catch (FileNotFoundException exception)
		{
		    System.out.println ("File not found ");
		}
		
		return lines;
	}
}
