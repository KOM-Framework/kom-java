package core;

import java.io.File;
import org.apache.tools.ant.util.FileUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import core.videorecording.VideoRecorder;
import core.web.Browser;


public class WebTestListener implements ITestListener {

	VideoRecorder video = new VideoRecorder();
	private static String resultFileName;
	private static String videoFilePath;
	
	@Override
	public void onTestStart(ITestResult result) {
		resultFileName = Log.getFinalFileName(Log.logFolderLocation(), result.getName());
		String folder = Log.logFolderLocation() + "/videos";
		videoFilePath = folder + "/" + resultFileName + ".avi";
		video.startRecording(folder, resultFileName);
		Log.info("==============================================");
		Log.info("Started test: " + resultFileName);
		Log.info("==============================================");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		Log.info("==============================================");
		Log.info("Finished test: " + resultFileName + " with status: SUCCESS");
		Log.info("==============================================");
		Log.info("");
		Log.writeLog(resultFileName);
		video.stopRecording();
		FileUtils.delete(new File(videoFilePath));
	}

	@Override
	public void onTestFailure(ITestResult result) {
		Log.info("==============================================");
		Log.info("Finished test: "+resultFileName+" with status: FAILED");
		Log.info("==============================================");
		Log.info("");
		Browser.sleep(1500);
		video.stopRecording();
		Log.writeLog(resultFileName);
		Log.captureScreenshot(resultFileName);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		Log.info("==============================================");
		Log.info("Finished test: "+resultFileName+" with status: SKIPPED");
		Log.info("==============================================");
		Log.info("");
		Log.sessionLog.clear();
		video.stopRecording();
		FileUtils.delete(new File(videoFilePath));
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onStart(ITestContext context) {
		Log.info("Started tests from the class - "+context.getClass().getName());
	}

	@Override
	public void onFinish(ITestContext context) {
	}

}
