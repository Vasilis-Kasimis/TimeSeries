package ts.parsing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import ts.commons.TimeLine;

public class CSVParser implements IParser{

		
	@Override
	public ArrayList<TimeLine> parse(String fileName) throws IOException {
	
		ArrayList<TimeLine> timeLines = new ArrayList<TimeLine>();
		
        FileInputStream fis = new FileInputStream(fileName);
        Scanner scanner = new Scanner(fis);
        
       
        ArrayList<Double> values = new ArrayList<Double>();
        ArrayList<Double> date = new ArrayList<Double>();

        ArrayList<Double> currentDate = new ArrayList<Double>();
        
        
        String title = "";
        String label = "";
        
        int j=0;
        int k=0;
        while (scanner.hasNext()) {
        	int start = 1;
            String row = scanner.nextLine();
            if(row.startsWith("\"")) {
            	String[] rowSplit = row.split("\"");
            	row = rowSplit[2];
            	label = rowSplit[1];
            	start = 0;
            }
            String[] cols = row.split(",");
            if(start == 1) {
            	label = cols[0];
            }
            
            if(k == 0) {
            	title = cols[0];
            	for(int i = 1; i < cols.length; i++) {
            		date.add(Double.valueOf(cols[i]));
                }
            	k++;
            }
            else {
            	System.out.println(row);
            	
            	for(int i = start; i < cols.length; i++) {
                	if(cols[i].equals("") == false) {
                		values.add(Double.valueOf(cols[i]));
                		currentDate.add(date.get(i-1));
                	}
                }
            	
            	TimeLine aTimeLine = new TimeLine();
                System.out.println("size="+currentDate.size());
                for(int n = 0; n < currentDate.size(); n++) {
                	System.out.println("Insert: "+ currentDate.get(n)+"-->"+values.get(n));
               		aTimeLine.addPoint(currentDate.get(n), values.get(n));
                }
                aTimeLine.setTitle(title);
                aTimeLine.setLabel(label);
                
                timeLines.add(aTimeLine);
                values.clear();
                currentDate.clear();
            }   
        }
        
        fis.close();

        return timeLines;
		
	}
	
}