package ts.segmentation;

import ts.commons.TimeLine;

public interface ISegmentator {
	public TimeLine segmentationAlgorithm(TimeLine timeline, double maxError);
	public double calculateError(TimeLine timeline, int start, int finish);
}
