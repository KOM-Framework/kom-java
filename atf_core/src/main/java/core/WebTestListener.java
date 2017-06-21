package core;

import core.web.Browser;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import ru.yandex.qatools.allure.annotations.Attachment;


public class WebTestListener implements ITestListener {

    @Attachment
    private static String attachLog(){
        String attach = String.join("\n", Log.sessionLog);
        Log.sessionLog.clear();
        return attach;
    }

    private static String containerName;

	@Override
	public void onTestStart(ITestResult result) {
        Log.info("==============================================");
        Log.info("Started test: "+result.getName());
        Log.info("==============================================");
	}

    @Attachment(value = "Screenshot", type = "image/png")
    private byte[] attachScreenshot() {
        return ((TakesScreenshot) Browser.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

	@Override
	public void onTestSuccess(ITestResult result) {
        attachLog();
	}

	@Override
	public void onTestFailure(ITestResult result) {
        attachScreenshot();
        attachLog();
	}

	@Override
	public void onTestSkipped(ITestResult result) {
        attachLog();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onStart(ITestContext context) {
        String groupToTest = System.getenv("GROUP_TO_TEST");
        if (groupToTest!= null){
            System.out.println("+========= Executing Group =========");
            System.out.println(String.format("             %s           ", groupToTest));
            System.out.println("+===================================");
        }
	}

	@Override
	public void onFinish(ITestContext context) {
        Browser.quitDriver();
    }

}
