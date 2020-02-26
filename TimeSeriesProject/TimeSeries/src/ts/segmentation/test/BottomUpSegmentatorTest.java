package ts.segmentation.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import common.Assert;
import ts.commons.Points;
import ts.commons.TimeLine;
import ts.segmentation.BottomUpSegmentator;

public class BottomUpSegmentatorTest {
	
	private TimeLine t;
	private BottomUpSegmentator bottomUp;
	private ArrayList<Integer> tIndex;
	public BottomUpSegmentatorTest() {
		bottomUp = new BottomUpSegmentator();
		tIndex = new ArrayList<Integer>();
		
	}
	
	public void makeTests(int size, double maxError) {
		System.out.println("maxError="+maxError);
		System.out.println("size="+size);
		
		TimeLine segmented = bottomUp.segmentationAlgorithm(t, maxError);
		assertEquals(size, segmented.getSize());
		
		for(int i = 0; i < segmented.getSize() -1; i++) {
			int index1 = -1;
			int index2 = -1;
			Points p1 = segmented.getPoint(i);
			Points p2 = segmented.getPoint(i+1);
			
			for(int j = 0; j < t.getSize(); j++) {
				Points p3 = t.getPoint(j);
				if(index1 == -1) {
					if(p3.getDate() == p1.getDate()) {
						index1 = j;
					}
				}
				else {
					if(p3.getDate() == p2.getDate()) {
						index2 = j;
						break;
					}
				}
			}
			double error = bottomUp.calculateError(t, index1, index2);
			boolean b = false;
			if(error <= maxError) {
				b = true;
			}
			assertEquals(b, true);
		}
		
	}
	@Test
	public void testSegmentationAlgorithmGermany() {
		t = new TimeLine();
		t.addPoint(new Points(1800, 26.83753805));
		t.addPoint(new Points(1954, 23.925));
		t.addPoint(new Points(1975, 22.115));
		t.addPoint(new Points(1988, 24.31));
		t.addPoint(new Points(2005, 30.12885094));

		makeTests(2, 117);
		makeTests(4, 5);
		makeTests(5, 0.5);
	}

	@Test
	public void testSegmentationAlgorithmAustria() {
		t = new TimeLine();
		
		t.addPoint(new Points(1947, 25.9));
		t.addPoint(new Points(1950, 25.3));
		t.addPoint(new Points(1960, 24.01));
		t.addPoint(new Points(1970, 22.88));
		t.addPoint(new Points(1980, 23.15));
		t.addPoint(new Points(1990, 24.9));
		t.addPoint(new Points(2005, 28.93756866));

		makeTests(2, 58);
		makeTests(5, 0.1);
		makeTests(7, 0.01);
	}
	
	@Test
	public void testSegmentationAlgorithmRomania() {
		t = new TimeLine();
		
		t.addPoint(new Points(1957, 22.56));
		t.addPoint(new Points(1970, 21.81));
		t.addPoint(new Points(1976, 22.3));
		t.addPoint(new Points(1984, 22.25));
		t.addPoint(new Points(2005, 25.30126953));

		makeTests(2, 10);
		makeTests(3, 0.5);
		makeTests(5, 0.1);
	}
}
