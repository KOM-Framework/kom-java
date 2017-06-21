package core;

import core.xml.Reader;

public class Global {

	public static final String WORKSPACE_LOCATION = System.getProperty("user.dir");
	
	//Supported Browsers
	public static final String FIREFOX = "Firefox";
	public static final String CHROME = "Chrome";
	public static final String OPERA = "Opera";
	public static final String SAFARI = "Safari";
	public static final String INTERNET_EXPLORER = "IntenetExplorer";
	
	//Configurations
	public static final String BROWSER = Reader.readParam("GBL_WEB_BROWSER")!=null?Reader.readParam("GBL_WEB_BROWSER"):CHROME;
	public static final boolean REMOTE_EXECUTION = System.getenv("REMOTE_EXECUTION") != null && Boolean.parseBoolean(System.getenv("REMOTE_EXECUTION"));
	public static final String SELENIUM_HUB = "http://10.0.0.237:5555/wd/hub";

	// Alphabetic | Numbers
	public static final String ENGLISH_ALPHABET = "abcdefghijklmnopqrstuvwxyz";
	public static final String ENGLISH_ALPHABET_UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String DIGITS = "1234567890";
	public static final String ENGLISH_ALPHABET_WITH_DIGITS = ENGLISH_ALPHABET+DIGITS;
	public static final String CHARSET = "[A-Za-z0-9\\.\\?\\:\\_\\=\\/\\s]";
	
	//Email configurations
	public static final String LOCAL_SMTP_SERVER = "10.0.0.25";
	
	//Timers
	public static final int DEFAULT_EXPLICIT_WAIT = 5;
	public static final int DEFAULT_IMPLICIT_WAIT = 30;
	public static final int DEFAULT_AJAX_WAIT = 10;
	public static final int DEFAULT_PAGE_LOAD_TIME = 60;
	
	//Logs and reports
	public static String logFolderPath;
	public static final String REPORT_LOCATION = "build/reports";
	public static final String REPORT_HTML_CLASSES_LOCATION = "build/reports/classes";
	
	//DataProviderPath
	public static final String DATA_PROVIDER_PATH="../src/main/resources/dataproviderfile/";
	public static final String DATA_JSON_EXTENSION=".json";
	public static final String DATA_PROVIDER_CLASS_TAG="org.testng.annotations.DataProvider";
	
	//WebItem Slider
	public static final String RULE_SCORES_TEST_HIDDEN_INPUT_ORG_LOWER_TAG = "data-orig-value";
	public static final String RULE_SCORES_TEST_HIDDEN_INPUT_ORG_UPPER_TAG = "data-orig-value-2";
	public static final String RULE_SCORES_TEST_HIDDEN_INPUT_AFTER_LOWER_TAG = "value";
	public static final String RULE_SCORES_TEST_HIDDEN_INPUT_AFTER_UPPER_TAG = "data-value-2";
	
	//Stlink defaults
	public static final int STLINK_VERSION = 3;
	public static final int STLINK_TIMEOUT = 10000;
	
	
}
