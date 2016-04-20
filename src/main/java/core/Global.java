package core;

public class Global {

	public static final String WORKSPACE_LOCATION = System.getProperty("user.dir");

	// Supported Browsers
	public static final String FIREFOX = "Firefox";
	public static final String CHROME = "Chrome";
	public static final String OPERA = "Opera";
	public static final String SAFARI = "Safari";
	public static final String INTERNET_EXPLORER = "IntenetExplorer";

	// Drivers
	public static final String IE_DRIVER_PATH = "src/main/resources/Drivers/IEDriverServer_x32.exe";
	public static final String CHRONE_DRIVER_PATH = "src/main/resources/Drivers/chromedriver.exe";
	public static final String OPERA_DRIVER_PATH = "src/main/resources/Drivers/operadriver.exe";

	// Configurations
	public static final String BROWSER = System.getenv("WEB_BROWSER") != null ? System.getenv("WEB_BROWSER") : FIREFOX;

	// Alphabetic | Numbers
	public static final String ENGLISH_ALPHABET = "abcdefghijklmnopqrstuvwxyz";
	public static final String ENGLISH_ALPHABET_APPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String DIGITS = "1234567890";
	public static final String ENGLISH_ALPHABET_WITH_DIGITS = ENGLISH_ALPHABET + DIGITS;

	// Email configurations
	public static final String LOCAL_SMTP_SERVER = "10.0.0.25";

	// Timers
	public static final int DEFAULT_EXPLICIT_WAIT = 2;
	public static final int DEFAULT_IMPLICIT_WAIT = 10;
	public static final int DEFAULT_AJAX_WAIT = 10000;

	// Logs and reports
	public static String logFolderPath;
	public static final String REPORT_LOCATION = "build/reports";
	public static final String REPORT_HTML_CLASSES_LOCATION = "build/reports/classes";
}
