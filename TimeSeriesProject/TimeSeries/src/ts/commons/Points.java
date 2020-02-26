package ts.commons;

public class Points {
	
	private double date = -1.0;
	private double value = -1.0;
	
	public Points(double date, double value){
		this.date = date;
		this.value = value;  
	}
	
	public Points(Points point) {
		this.date = point.date;
		this.value = point.value;
	}

	public double getDate(){
		return this.date;
	}
	
	public double getValue(){
		return this.value;
	}
	
}
