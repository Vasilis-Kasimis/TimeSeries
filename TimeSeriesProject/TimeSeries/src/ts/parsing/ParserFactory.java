package ts.parsing;


import ts.parsing.IParser;

public class ParserFactory {
	
	public IParser createParser(String concreteClassName){
		if (concreteClassName.equals("ExcelParser")){
			return new ExcelParser();
		}
		if (concreteClassName.equals("CSVParser")){
			return new CSVParser();
		}
		if (concreteClassName.equals("TSVParser")){
			return new TSVParser();
		}

		System.out.println("If the code got up to here, you passed a wrong argument to ParserFactory");
		return null;
	}
}