package ts.parsing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import ts.commons.TimeLine;

public interface IParser {
	
	public abstract ArrayList<TimeLine> parse(String aFile) throws FileNotFoundException, IOException;

}