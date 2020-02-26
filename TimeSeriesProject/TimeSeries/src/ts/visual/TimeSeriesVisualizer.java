package ts.visual;

import java.awt.BasicStroke;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Month;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.Vector;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

import com.orsoncharts.util.json.parser.ParseException;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import ts.commons.TimeLine;


public class TimeSeriesVisualizer implements IVisualizer {
	
	private JFrame frame;
	private TimeLine aTimeLine;
	private TimeLine bTimeLine;
	
	public TimeSeriesVisualizer(TimeLine aTimeLine) {
		
		this.aTimeLine = aTimeLine;
		
	}
	public TimeSeriesVisualizer(TimeLine aTimeLine, TimeLine bTimeLine) {
		
		this.aTimeLine = aTimeLine;
		this.bTimeLine = bTimeLine;
		
	}
	public  void visualize(String type) throws java.text.ParseException  {
		if(type.equals("real")) {
			visualize();
			return;
		}
			
		frame = new JFrame();
		frame.setBounds(100, 100, 699, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final XYDataset dataset = createDataset("Real", aTimeLine);
		final XYDataset dataset2 = createDataset("Segmented", bTimeLine);
        final JFreeChart chart = createChart(dataset, dataset2, type);
		
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        chartPanel.setMouseZoomable(true, true);
        chartPanel.setVisible(true);
        frame.add(chartPanel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        
	}
	private XYDataset createDataset(String title, TimeLine aTimeLine) {
		TimeSeries series = new TimeSeries(title);
		
		for (int i = 0; i < aTimeLine.getSize(); i++) {
				series.addOrUpdate(new Year((int) aTimeLine.getPoint(i).getDate()),(double) aTimeLine.getPoint(i).getValue());
		}

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(series);

		return dataset;
	}
	public  void visualize() throws java.text.ParseException  {
		frame = new JFrame();
		frame.setBounds(100, 100, 699, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final XYDataset dataset = createDataset(aTimeLine.getTitle());
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        chartPanel.setMouseZoomable(true, true);
        chartPanel.setVisible(true);
        frame.add(chartPanel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
	}
	
	
	
	private  XYDataset createDataset(String name ,RegularTimePeriod start) throws java.text.ParseException {
			TimeSeries series = new TimeSeries(name, start.getClass());
			RegularTimePeriod period = start;
		
			for (int i = 0; i < aTimeLine.getSize(); i++) {
					series.add(period, aTimeLine.getPoint(i).getValue());
					period = period.next();
			}

			TimeSeriesCollection dataset = new TimeSeriesCollection();
			dataset.addSeries(series);

			return dataset;

	}
	private  XYDataset createDataset(String name ) throws java.text.ParseException {
		TimeSeries series = new TimeSeries(name);
		
		for (int i = 0; i < aTimeLine.getSize(); i++) {
				series.addOrUpdate(new Year((int) aTimeLine.getPoint(i).getDate()),(double) aTimeLine.getPoint(i).getValue());				
		}

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(series);

		return dataset;

	}
	private JFreeChart createChart(final XYDataset dataset) {

        final JFreeChart chart = ChartFactory.createTimeSeriesChart(
        		aTimeLine.getLabel(),
            "Date", 
            "Value",
            dataset,
            true,
            true,
            false
        );

        chart.setBackgroundPaint(Color.white);
  
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(false);
        
        final XYItemRenderer renderer = plot.getRenderer();
        if (renderer instanceof StandardXYItemRenderer) {
	        final StandardXYItemRenderer rr = (StandardXYItemRenderer) renderer;
	        rr.setShapesFilled(true);
	        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
	        renderer.setSeriesStroke(1, new BasicStroke(2.0f));
	    }
        
        final DateAxis axis = (DateAxis) plot.getDomainAxis();        
        return chart;
    }
	
	private JFreeChart createChart(final XYDataset dataset,final XYDataset dataset2, String type) {

        final JFreeChart chart = ChartFactory.createTimeSeriesChart(
        		aTimeLine.getLabel() + " - " + type,
            "Date", 
            "Value",
            dataset,
            true,
            true,
            false
        );

        chart.setBackgroundPaint(Color.white);
        XYPlot plot = chart.getXYPlot();
        plot.setOrientation(PlotOrientation.VERTICAL);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white); 
        plot.setDataset(1, dataset2);
        final XYItemRenderer renderer = plot.getRenderer();

        XYItemRenderer renderer2 = new StandardXYItemRenderer();
        renderer2.setSeriesPaint(0, Color.blue);
        plot.setRenderer(1, renderer2);
        return chart;
    }
}
	