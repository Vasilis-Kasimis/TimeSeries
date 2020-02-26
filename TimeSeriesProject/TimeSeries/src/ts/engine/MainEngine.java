package ts.engine;

import ts.commons.*;
import ts.parsing.IParser;
import ts.parsing.ParserFactory;
import ts.segmentation.ISegmentator;
import ts.segmentation.SegmentatorFactory;
import ts.segmentation.TopDownSegmentator;
import ts.visual.IVisualizer;
import ts.visual.VisualizerFactory;
import ts.writer.IWriter;
import ts.writer.WriterFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class MainEngine implements IEngine{
	private IParser parser;
	private IVisualizer visualizer;
	private VisualizerFactory visualizerFactory;
	private ParserFactory parserFactory;
	private ArrayList<TimeLine> timelines;
	private ISegmentator segmentator;
	private SegmentatorFactory segmentatorFactory;
	private IWriter writer;
	private WriterFactory writerFactory;
	
	public MainEngine(){
		parserFactory = new ParserFactory();
		visualizerFactory = new VisualizerFactory();
		segmentatorFactory = new SegmentatorFactory();		
	}


	public ArrayList<TimeLine> getTimelines() {
		return timelines;
	}
	
	public void setTimeLine(String aFile,String format) throws FileNotFoundException, IOException {
		if(format.equals("tsv")){
			parser = parserFactory.createParser("TSVParser");
		}
		else if(format.equals("csv")){
			parser = parserFactory.createParser("CSVParser");
		}
		else if(format.equals("xlsx")){
			parser = parserFactory.createParser("ExcelParser");
		}
		
		timelines = parser.parse(aFile);
	}
	public void writeTimeLine(String aFile,String format) throws FileNotFoundException, IOException {
		if(format.equals("tsv")){
			writer = writerFactory.createWriter("TSVParser");
		}
		else if(format.equals("csv")){
			writer = writerFactory.createWriter("CSVParser");
		}
		
		writer.write(timelines, aFile);
	}
	
	public void setVisualizer(int pos) throws ParseException {
		visualizer = visualizerFactory.createVisualizer("TimeSeriesVisualizer", timelines.get(pos), null);
		visualizer.visualize("real");
	}
	
	public void setSegmentator(String name) {
		segmentator = segmentatorFactory.createSegmentator(name);
	}
	public TimeLine segmentTimeline(int pos, double maxError) throws ParseException {		
		return segmentator.segmentationAlgorithm(timelines.get(pos), maxError);
	}
	
	public void setVisualizer(int pos, TimeLine segmentedTimeLine) throws ParseException {
		visualizer = visualizerFactory.createVisualizer("TimeSeriesVisualizer", timelines.get(pos), segmentedTimeLine);
		if(segmentator instanceof TopDownSegmentator)
			visualizer.visualize(" TopDown");
		else
			visualizer.visualize(" BottomUp");
	}
}
