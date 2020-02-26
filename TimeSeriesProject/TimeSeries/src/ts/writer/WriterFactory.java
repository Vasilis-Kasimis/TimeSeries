package ts.writer;


public class WriterFactory {
	public IWriter createWriter(String concreteClassName) {
		if(concreteClassName.equals("CSVWriter")) {
			return new CSVWriter();
		}
		if(concreteClassName.equals("TSVWriter")) {
			return new TSVWriter();
		}
		return null;
	}
}
