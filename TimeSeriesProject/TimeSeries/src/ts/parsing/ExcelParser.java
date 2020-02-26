package ts.parsing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ts.commons.TimeLine;



public class ExcelParser implements IParser{

	@Override
	public ArrayList<TimeLine> parse(String fileName) throws IOException {
	
		ArrayList<TimeLine> timeLines = new ArrayList<TimeLine>();
		File excelFile = new File(fileName);
        FileInputStream fis = new FileInputStream(excelFile);
        
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        
        XSSFSheet sheet = workbook.getSheetAt(0);
        System.out.print(sheet.getSheetName());
       
        ArrayList<Double> values = new ArrayList<Double>();
        ArrayList<Double> date = new ArrayList<Double>();

        ArrayList<Double> currentDate = new ArrayList<Double>();
        
        Iterator<Row> rowIterator = sheet.iterator();
        String title = "";
        String label = "";
        int i=0;
        int j=0;
        int k=0;
        while (rowIterator.hasNext()) {
        	
            Row row = rowIterator.next();
        
            Iterator<Cell> cellIterator = row.cellIterator();
            
            Cell cell = cellIterator.next();
            if(cell.getCellType() == CellType.BLANK) {
            	break;
            }
            System.out.println("row="+cell.getRowIndex());
            if(cell.getRowIndex() == 0) {
            	if(cell.getCellType() == CellType.STRING) {
            		title = cell.getStringCellValue();
            	}
            }
            else {
            	label = cell.getStringCellValue();
            	System.out.println("label="+label);
            }
            
            while (cellIterator.hasNext()){            	
	    		cell = cellIterator.next();
	    		
				switch (cell.getCellType()) {
					
					case NUMERIC:
						if(cell.getRowIndex() == 0) {
							date.add(cell.getNumericCellValue());
						}
						else {
							
							values.add((double)cell.getNumericCellValue());
							int col = cell.getColumnIndex() -1;
							double curDate = date.get(col);
							currentDate.add(curDate);
						}
						break;
				}
            }
            
            if(cell.getRowIndex() >= 1) {
            	TimeLine aTimeLine = new TimeLine();
                for(int n = 0; n < currentDate.size(); n++) {
               		aTimeLine.addPoint(currentDate.get(n), values.get(n));
                }
                aTimeLine.setTitle(title);
                aTimeLine.setLabel(label);
                timeLines.add(aTimeLine);
                values.clear();
                currentDate.clear();
            }
            
        }
        
        workbook.close();
        fis.close();

        return timeLines;
		
	}
}
