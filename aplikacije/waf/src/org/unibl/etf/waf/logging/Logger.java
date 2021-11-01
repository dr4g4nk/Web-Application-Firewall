package org.unibl.etf.waf.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class Logger {

	private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger("WAFLogger");
	private static FileHandler fileHandler;
	static {
		
		try {
			fileHandler = new FileHandler("ShopApp.log", true);
	       	fileHandler.setFormatter(new SimpleFormatter());
	       	logger.addHandler(fileHandler);
	       	//logger.setUseParentHandlers(false);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized static void log(String msg, Throwable thrown){
		logger.log(Level.INFO, msg, thrown);
	}
}
