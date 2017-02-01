package cherry.robothandlers.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;
import java.io.FileNotFoundException;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
public class ExcelReader {

	public static ArrayList<ArrayList<String>> displayExcel(String excelFilePath) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        
        ArrayList<ArrayList<String>> listOfLists = new ArrayList<ArrayList<String>>();
        
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
        
        int row=0; 
         
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            ArrayList<String> myList = new ArrayList<String>();
            
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        System.out.print(cell.getStringCellValue());
                        myList.add(cell.getStringCellValue());
                        break;
                        
                    case Cell.CELL_TYPE_BOOLEAN:
                        System.out.print(cell.getBooleanCellValue());   
                        myList.add(String.valueOf(cell.getBooleanCellValue()));
                        break;
                        
                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.print(cell.getNumericCellValue());
                        myList.add(String.valueOf(cell.getNumericCellValue()));
                        break;
                }

                System.out.print(" - ");
            }
            
            listOfLists.add(row, myList);
            row = row+1;
            System.out.println();
        }
        
        workbook.close();
        inputStream.close();
        
        return  listOfLists;
        
        }

	public static ArrayList<String> getExcelField(String excelFilePath, String field) throws FileNotFoundException, IOException {
		
		ArrayList<ArrayList<String>> excelList = ExcelReader.displayExcel(excelFilePath);
		ArrayList<String> list = new ArrayList<String>();
		
		
		if (excelList.get(0).contains(field)) {
			
			int idx = excelList.get(0).indexOf(field);
			System.out.print("Specified Field exists at index: " + idx);
			
			for(int i=1; i <excelList.size(); i++){
				
				list.add(excelList.get(i).get(idx));
			}
		}
		else{
			 System.out.print("Specified Field doesn't exist ");
		}
		return list;
		
	}
}
