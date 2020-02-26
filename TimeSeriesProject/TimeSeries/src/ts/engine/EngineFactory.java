package ts.engine;

import ts.engine.IEngine;
import ts.engine.MainEngine;


public class EngineFactory {
	
	public IEngine createMainEngine(){
		return new MainEngine();
	}
}