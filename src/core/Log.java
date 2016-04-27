package core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import core.web.Browser;

public class Log extends Logger {

	protected Log(String name) {
		super(name);
	}

	public static ArrayList<String> sessionLog = new ArrayList<String>();
	
	public static String logFolderLocation(){
		return  Global.REPORT_LOCATION + "/logs/" + Global.logFolderPath;
	}
	
	public static String getFinalFileName(String folderLocation, String fileName){
		File directory = new File(folderLocation);
		directory.mkdirs();
		long fileCount = FileUtils.listFiles(directory, null, false).stream()
				.filter(file -> file.getName().startsWith(fileName)).count();
		String finalFileName = fileCount > 0 ? fileName + "[" + fileCount + "]" : fileName;
		return finalFileName;
	}
	
	public static void writeLog(String testCaseName){
		File logFile = new File(logFolderLocation() + "/" + testCaseName + ".txt");
		try {
			FileUtils.writeLines(logFile, Log.sessionLog);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.sessionLog.clear();
	}
	
	public static void captureScreenshot(String fileName){
		File scrFile = ((TakesScreenshot)Browser.getDriver()).getScreenshotAs(OutputType.FILE);
		String folderLocation = logFolderLocation() + "/screenshots";
		try {
			FileUtils.copyFile(scrFile, new File(folderLocation+"/"+fileName+"_Screenshot.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
		Logger logg = Logger.getLogger(name);
		logg.info(message);
	}
	
	public static void error(String message) {
		sessionLog.add(message);
		String name = getCallerClassName();
		Logger logg = Logger.getLogger(name);
		logg.error(message);
	}
}
