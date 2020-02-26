package ts.segmentation;

import java.util.ArrayList;

import ts.commons.Points;
import ts.commons.TimeLine;

public class BottomUpSegmentator implements ISegmentator {

	@Override
	public TimeLine segmentationAlgorithm(TimeLine T, double maxError) {
		
		TimeLine segmented = new TimeLine();
		
		ArrayList<Segment> segments = new ArrayList<Segment>();
		
		for(int i = 0; i < T.getSize() -1; i+=2) {
			Segment segment = new Segment(i, i+1);
			segments.add(segment);
		}
		if(T.getSize() % 2 == 1) {
			int i = T.getSize() - 1;
			Segment segment = new Segment(i, i);
			segments.add(segment);
		}
		
		ArrayList<Double> mergeCost = new ArrayList<Double>();
		
		for(int i = 0; i < segments.size() -1; i++) {
			Segment left = segments.get(i);
			Segment right = segments.get(i+1);
			
			double cost = calculateError(T, left.getStart(), right.getFinish());
			mergeCost.add(cost);
			System.out.println(i+" mergeCost= "+cost);
		}
		int index = 0;
		if(segments.size() > 1) {
			index = minCost(mergeCost);
			double min = mergeCost.get(index);
			int count = 0;
			
			while( min < maxError) {
				System.out.println("index= "+index);
				//merge
				Segment segment = segments.get(index);
				Segment segment2 = segments.get(index+1);
				segment.setFinish(segment2.getFinish());
				//update
				segments.remove(index+1);
				mergeCost.remove(index);
				
				if(index + 1 < segments.size()) {
					Segment left = segments.get(index);
					Segment right = segments.get(index+1);
					
					double cost = calculateError(T, left.getStart(), right.getFinish());
					mergeCost.set(index, cost);
				}
				if(index - 1 >= 0) {
					Segment left = segments.get(index-1);
					Segment right = segments.get(index);
					
					double cost = calculateError(T, left.getStart(), right.getFinish());
					mergeCost.set(index-1, cost);
				}
				if(mergeCost.size() == 0)
					break;
				
				index = minCost(mergeCost);
				min = mergeCost.get(index);
				count++;
				System.out.println("count = "+count);
				
				for(int i = 0; i < mergeCost.size(); i++) {
					
					System.out.println(i+" mergeCost= "+mergeCost.get(i));
				}
			}
		}
		
		
		for(int i = 0; i < segments.size(); i++) {
			index = segments.get(i).getStart();
			
			if(segmented.hasPoint(T.getPoint(index).getDate()) == false) {
				Points p = new Points(T.getPoint(index));
				segmented.addPoint(p);
			}
			
			index = segments.get(i).getFinish();
			if(segmented.hasPoint(T.getPoint(index).getDate()) == false) {
				Points p = new Points(T.getPoint(index));
				segmented.addPoint(p);
			}
		}
		segmented.setLabel(T.getLabel());
		segmented.setTitle(T.getTitle());
		return segmented;
	}

	private int minCost(ArrayList<Double> mergeCost) {
		double min = mergeCost.get(0);
		int pos = 0;
		for(int i = 0; i < mergeCost.size(); i++) {
			if(min > mergeCost.get(i)) {
				min = mergeCost.get(i);
				pos = i;
			}
		}
		return pos;
	}
	@Override
	public double calculateError(TimeLine timeline, int start, int finish) {
		double error = 0;
		
		Points linePoint1 = timeline.getPoint(start);
		Points linePoint2 = timeline.getPoint(finish);
		
		for(int i = start; i < finish; i++) {
			error += distance(timeline.getPoint(i), linePoint1, linePoint2);
		}
		return error;
	}
	
	private double distance(Points point, Points linePoint1, Points linePoint2) {
		double y0 = point.getValue();
		double x0 = point.getDate();
		double y1 = linePoint1.getValue();
		double x1 = linePoint1.getDate();
		double y2 = linePoint2.getValue();
		double x2 = linePoint2.getDate();
		
		
		double a = Math.abs((y2-y1)*x0 - (x2 - x1)*y0 +x2*y1 -y2*x1);
		double b = Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
		
		return Math.pow(a/b, 2);
	}
}
