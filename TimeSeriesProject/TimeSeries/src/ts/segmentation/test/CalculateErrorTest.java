package ts.segmentation.test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import ts.commons.TimeLine;
import ts.segmentation.TopDownSegmentator;

public class CalculateErrorTest {

	
	@Test
	public void testCalculateError() {
		TopDownSegmentator topDown = new TopDownSegmentator();
		TimeLine t = new TimeLine();
		
		t.addPoint(2000, 10);
		t.addPoint(2001, 12);
		t.addPoint(2002, 10);
		t.addPoint(2003, 14);
		t.addPoint(2004, 10);
		
		double error = topDown.calculateError(t, 0, 2);
		double error2 = topDown.calculateError(t, 0, 4);
		
		Assert.assertEquals(4.0, error, 0.0);
		Assert.assertEquals(20.0, error2, 0.0);
	}

}
