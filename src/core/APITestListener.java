package core;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class APITestListener implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		Log.info("==============================================");
		Log.info("Started test: " + result.getName());
		Log.info("==============================================");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		Log.info("==============================================");
		Log.info("Finished test: " + result.getName() + " with status: SUCCESS");
		Log.info("==============================================");
		Log.info("");
		Log.info("");
		Log.writeLog(result.getName());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		Log.info("==============================================");
		Log.info("Finished test: " + result.getName() + " with status: FAILED");
		Log.info("==============================================");
		Log.info("");
		Log.info("");
		Log.writeLog(result.getName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		Log.info("==============================================");
		Log.info("Finished test: " + result.getName() + " with status: SKIPPED");
		Log.info("==============================================");
		Log.info("");
		Log.info("");
		Log.sessionLog.clear();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
		// Log.info("Started tests from the class -
		// "+context.getClass().getName());
	}

	@Override
	public void onFinish(ITestContext context) {

	}

}
