package ts.segmentation;

import ts.commons.Points;
import ts.commons.TimeLine;

public class TopDownSegmentator implements ISegmentator {

	public TimeLine segmentationAlgorithm(TimeLine T, int start, int finish, double maxError) {
		double bestSoFar = Double.POSITIVE_INFINITY;
		System.out.println("Start = "+start+" finish = "+ finish);
		int breakPoint = -1;
		for(int i = start + 1; i < finish - 1; i++) {
			double improvementInApproximation = improvementSplittingHere(T, i, start, finish);
			System.out.println("For breakpoint "+ i +" improvement = "+improvementInApproximation);
			if(improvementInApproximation < bestSoFar) {
				breakPoint = i;
				bestSoFar = improvementInApproximation;
			}
		}
		//breakPoint = -1;
		System.out.println("breakPoint = " + breakPoint);
		TimeLine SegTsLeft = new TimeLine();
		TimeLine SegTsRight = new TimeLine();
		
		if(breakPoint != -1 && finish - start + 1 > 3 && calculateError(T, start, breakPoint) > maxError) {
			System.out.println("ErrorLeft = " + calculateError(T, start, breakPoint));
			System.out.println("Calling left");
			SegTsLeft = segmentationAlgorithm(T, start, breakPoint, maxError);
		}
		else {
			System.out.println("Start = "+start+" finish = "+ finish+" No breaking left");
			SegTsLeft = new TimeLine();
			Points startPoint = new Points(T.getPoint(start));
			Points endPoint = null;
			if(breakPoint == -1) {
				endPoint = new Points(T.getPoint(start+1));
			}
			else {
				endPoint = new Points(T.getPoint(breakPoint));
			}
			SegTsLeft.addPoint(startPoint);
			SegTsLeft.addPoint(endPoint);
				
			
		}
		if(breakPoint != -1 && finish - start + 1 > 3 && calculateError(T, breakPoint + 1, finish) > maxError) {
			System.out.println("ErrorRight = " + calculateError(T, start, breakPoint));
			System.out.println("Calling right");
			SegTsRight = segmentationAlgorithm(T, breakPoint + 1, finish, maxError);
		}
		else {
			System.out.println("Start = "+start+" finish = "+ finish+" No breaking right");
			SegTsRight = new TimeLine();
			if(breakPoint != -1) {

				Points startPoint = new Points(T.getPoint(breakPoint+1));
				SegTsRight.addPoint(startPoint);
			}
			Points endPoint = new Points(T.getPoint(finish));
			
			SegTsRight.addPoint(endPoint);
		}
		
		return SegTsLeft.append(SegTsRight);
	}
	@Override
	public TimeLine segmentationAlgorithm(TimeLine T, double maxError) {
		
		TimeLine segmented = null;
		if(calculateError(T, 0 , T.getSize()-1) <= maxError) {
			segmented = new TimeLine();
			Points startPoint = new Points(T.getPoint(0));
			Points endPoint = new Points(T.getPoint(T.getSize()-1));

			segmented.addPoint(startPoint);
			segmented.addPoint(endPoint);
		}
		else
			segmented = segmentationAlgorithm(T, 0 , T.getSize()-1, maxError);
		
		segmented.setLabel(T.getLabel());
		segmented.setTitle(T.getTitle());
		return segmented;
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

	private double improvementSplittingHere(TimeLine t, int i, int start, int finish) {
		double errorLeft = calculateError(t, start, i);
		double errorRight = calculateError(t, i+1, finish);
		
		return errorLeft + errorRight;
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
