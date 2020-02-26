package ts.segmentation;

public class SegmentatorFactory {
	public ISegmentator createSegmentator(String concreteClassName){
		if (concreteClassName.equals("TopDown")){
			return new TopDownSegmentator();
		}
		else if (concreteClassName.equals("BottomUp")){
			return new BottomUpSegmentator();
		}

		System.out.println("If the code got up to here, you passed a wrong argument to ParserFactory");
		return null;
	}
}
