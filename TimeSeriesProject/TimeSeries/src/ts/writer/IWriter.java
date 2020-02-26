package ts.writer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import ts.commons.TimeLine;

public interface IWriter {
	public abstract void write(ArrayList<TimeLine> t, String filename) throws IOException;
}
