package core;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


public class WebTestListener implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
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
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testCaseName = result.getName();
		Log.info("==============================================");
		Log.info("Finished test: "+result.getName()+" with status: FAILED");
		Log.info("==============================================");
		Log.info("");
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		Log.captureScreenshot(testCaseName);
		Log.writeLog(testCaseName);
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
