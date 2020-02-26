package ts.visual;
import ts.commons.TimeLine;
import ts.visual.IVisualizer;

public class VisualizerFactory {

	public IVisualizer createVisualizer(String concreteClassName, TimeLine tl, TimeLine t2){
		if (concreteClassName.equals("TimeSeriesVisualizer")){
			return new TimeSeriesVisualizer(tl, t2);
		}
		System.out.println("If the code got up to here, you passed a wrong argument to AnalyserFactory");
		return null;
	}
}
