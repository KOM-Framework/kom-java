package core;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Log extends Logger {

	protected Log(String name) {
		super(name);
	}

	public static ArrayList<String> sessionLog = new ArrayList<String>();
	
	public static String getCallerClassName() {
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		for (int i = 1; i < stElements.length; i++) {
			StackTraceElement ste = stElements[i];
			if (!ste.getClassName().equals(Log.class.getName())
					&& ste.getClassName().indexOf("java.lang.Thread") != 0) {
				return ste.getClassName();
			}
		}
		return null;
	}

	public static void info(String message) {
		sessionLog.add(message);
		String name = getCallerClassName();
		Logger log = Logger.getLogger(name);
		log.info(message);
	}
	
	public static void error(String message) {
		sessionLog.add(message);
		String name = getCallerClassName();
		Logger log = Logger.getLogger(name);
		log.error(message);
	}
}
