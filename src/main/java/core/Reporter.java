package core;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import com.google.common.base.Joiner;

import core.filemanagement.MDir;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reporter {

	public static final String RESULT_HEADER = "<th>Result</th>\n";
	public static final String CUSTOM_HEADER = "<th>Console Output</th>\n<th>Screenshot</th>\n";

	public static final String MAIN_BODY_REGEX = "<h2>Tests</h2>\\s*<table>[\\s\\S]+</table>";
	public static final String TEST_CASE_REGEX = "<tr>\\s*<td.*>(.*)</td>\\s*.*\\s*.*";

	public static final String TEST_CASE_LOG_PREFIX = "\n<td><a href=\"";
	public static final String TEST_CASE_LOG_SUFFIX = "\" target=\"_blank\">Log File</a></td>";
	public static final String TEST_CASE_SCREENSHOT_PREFIX = "\n<td><a href=";
	public static final String TEST_CASE_SCREENSHOT_SUFFIX = " target=\"_blank\">Link to File</a></td>";

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		Arrays.asList(new File(Global.REPORT_HTML_CLASSES_LOCATION).listFiles()).forEach(htmlClass -> {
			String doc = null;
			try {
				doc = Joiner.on("\n").join(FileUtils.readLines(htmlClass));
			} catch (Exception e) {
				e.printStackTrace();
			}
			Matcher m = Pattern.compile(MAIN_BODY_REGEX).matcher(doc);
			String latestLogsFolder = MDir.getLatestDirectory(Global.REPORT_LOCATION + "/logs");
			String mainPart = "";
			while (m.find()) {
				mainPart = m.group();
			}
			String modifiedMainPart = mainPart.replace(RESULT_HEADER, RESULT_HEADER + CUSTOM_HEADER);
			m = Pattern.compile(TEST_CASE_REGEX).matcher(mainPart);
			while (m.find()) {
				String testCaseBody = m.group(0);
				String testCaseName = m.group(1);
				if (testCaseName.contains("(")) {
					testCaseName = StringUtils.left(testCaseName, testCaseName.indexOf("("));
					if (testCaseName.endsWith("[0]")) {
						testCaseName = StringUtils.left(testCaseName, testCaseName.indexOf("["));
					}
				}
				String logFile = "../logs/" + latestLogsFolder + "/" + testCaseName + ".txt";
				testCaseBody += TEST_CASE_LOG_PREFIX + logFile + TEST_CASE_LOG_SUFFIX;
				String screenShotPath = Global.REPORT_LOCATION + "/logs/" + latestLogsFolder + "/screenshots/"
						+ testCaseName + "_Screenshot.jpg";
				File screenShotFile = new File(screenShotPath);
				if (screenShotFile.exists()) {
					screenShotPath = "../logs/" + latestLogsFolder + "/screenshots/" + testCaseName + "_Screenshot.jpg";
					testCaseBody += TEST_CASE_SCREENSHOT_PREFIX + screenShotPath + TEST_CASE_SCREENSHOT_SUFFIX;
				}
				modifiedMainPart = modifiedMainPart.replace(m.group(0), testCaseBody);
			}
			doc = doc.replace(mainPart, modifiedMainPart);
			try {
				FileUtils.write(htmlClass, doc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
