package core;

import core.web.Browser;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


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

	@Override
	public void onTestSuccess(ITestResult result) {
        attachLog();
	}

	@Override
	public void onTestFailure(ITestResult result) {
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
        if (Global.REMOTE_EXECUTION){
            WebTestListener.containerName = String.valueOf(Rand.timestamp());
            System.out.println(String.format("CONTAINER NAME = %s", WebTestListener.containerName));
            String browserContainer;
            switch (Global.BROWSER){
                case Global.FIREFOX:
                    browserContainer = "selenium/node-firefox-debug:3.0.1-germanium";
                    break;
                default:
                    browserContainer = "selenium/node-chrome-debug:3.0.1-germanium";
                    break;
            }
            System.out.println(String.format("BROWSER CONTAINER = %s", browserContainer));
            String startContainerCommand = String.format("sudo docker run -d --name %s --link selenium-hub:hub %s", WebTestListener.containerName, browserContainer);
            System.out.println(String.format("DOCKER COMMAND = %s", startContainerCommand));
            try {
                Process  p = Runtime.getRuntime().exec(startContainerCommand);
                BufferedReader stdoutReader = new BufferedReader(
                        new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = stdoutReader.readLine()) != null) {
                    System.out.println(line);
                }
                BufferedReader stderrReader = new BufferedReader(
                        new InputStreamReader(p.getErrorStream()));
                while ((line = stderrReader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                Log.error("Unable to start a new container for the execution");
                e.printStackTrace();
                System.exit(1);
            }
        }

	}

	@Override
	public void onFinish(ITestContext context) {
		Browser.quitDriver();
		if (Global.REMOTE_EXECUTION){
		    String stopContainerCmd = String.format("sudo docker rm -f %s", WebTestListener.containerName);
            try {
                Process  p = Runtime.getRuntime().exec(stopContainerCmd);
                BufferedReader stdoutReader = new BufferedReader(
                        new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = stdoutReader.readLine()) != null) {
                    System.out.println(line);
                }
                BufferedReader stderrReader = new BufferedReader(
                        new InputStreamReader(p.getErrorStream()));
                while ((line = stderrReader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                Log.error(String.format("Unable to stop %s container", WebTestListener.containerName));
                e.printStackTrace();
                System.exit(1);
            }
        }
	}

}
