package ts.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import ts.commons.TimeLine;

public interface IEngine {


	public abstract void setTimeLine(String aFile,String format) throws FileNotFoundException, IOException;
	
	public abstract  void setVisualizer(int pos) throws ParseException ;
	public ArrayList<TimeLine> getTimelines();
	public void setSegmentator(String name);
	public TimeLine segmentTimeline(int pos, double maxError) throws ParseException ;

	public abstract void setVisualizer(int pos, TimeLine segmentedTimeLine) throws ParseException ;
}
