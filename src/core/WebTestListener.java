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
	private static String videoFilePath;
	
	@Override
	public void onTestStart(ITestResult result) {
		try {
			String folder = Log.logFolderLocation()+"/videos";
			String file = Log.getFinalFileName(folder,result.getName());
			videoFilePath=folder+"/"+file +".avi";
			video.startRecording(folder,file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.info("==============================================");
		Log.info("Started test: "+result.getName());
		Log.info("==============================================");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String testCaseName = result.getName();
		Log.info("==============================================");
		Log.info("Finished test: " + testCaseName + " with status: SUCCESS");
		Log.info("==============================================");
		Log.info("");
		Log.writeLog(testCaseName);
		try {
			video.stopRecording();
		} catch (Exception e) {
			e.printStackTrace();
		}
		FileUtils.delete(new File(videoFilePath));
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testCaseName = result.getName();
		Log.info("==============================================");
		Log.info("Finished test: "+testCaseName+" with status: FAILED");
		Log.info("==============================================");
		Log.info("");
		Browser.sleep(1500);
		try {
			video.stopRecording();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String fileName = Log.writeLog(testCaseName);
		Log.captureScreenshot(fileName);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		Log.info("==============================================");
		Log.info("Finished test: "+result.getName()+" with status: SKIPPED");
		Log.info("==============================================");
		Log.info("");
		Log.sessionLog.clear();
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
