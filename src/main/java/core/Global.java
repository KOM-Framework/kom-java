package core;

public class Global {

	public static final String WORKSPACE_LOCATION = System.getProperty("user.dir");
	
	//Supported Browsers
	public static final String FIREFOX = "Firefox";
	public static final String CHROME = "Chrome";
	public static final String OPERA = "Opera";
	public static final String SAFARI = "Safari";
	public static final String INTERNET_EXPLORER = "IntenetExplorer";
	public static final String EDGE = "Edge";
	
	//Configurations
	public static final String BROWSER = CHROME;
	public static final boolean REMOTE_EXECUTION = System.getenv("REMOTE_EXECUTION") != null && Boolean.parseBoolean(System.getenv("REMOTE_EXECUTION"));
	public static final String SELENIUM_HUB = "http://10.0.0.237:5555/wd/hub";

	//Timers
	public static final int DEFAULT_EXPLICIT_WAIT = 5;
	public static final int DEFAULT_IMPLICIT_WAIT = 30;
	public static final int DEFAULT_AJAX_WAIT = 10;
	public static final int DEFAULT_PAGE_LOAD_TIME = 60;
	

}
