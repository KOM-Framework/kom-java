package core.web;

import core.Global;
import core.browsers.*;
import core.utilities.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;
public class Browser {

	private static Browser browser;
	private static WebDriver driver;
	
	public static Browser getBrowser() {
		return browser;
	}
	public static void setBrowser(Browser browser) {
		Browser.browser = browser;
	}
	private void setDriver(WebDriver driver) {
		Browser.driver = driver;
        setPageLoadTime(Global.DEFAULT_IMPLICIT_WAIT);
		Browser.driver.manage().window().maximize();
	}

	public static void quitDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}
	
	public static WebDriver getDriver() {
		if (driver == null) {
			setBrowser(new Browser(Global.BROWSER));
		}
		return driver;
	}

	Browser(String browserType) {
		Log.info("Creating an instance of a "+browserType+" browser");
		if (Global.REMOTE_EXECUTION){
			this.setDriver(new RemoteBrowsers());
		}else{
			switch (browserType) {
				case Global.CHROME:
					this.setDriver( new KOMChrome().getDriver());
					break;
				case Global.INTERNET_EXPLORER:
					this.setDriver(new KOMInternetExplorer().getDriver());
					break;
				case Global.OPERA:
					this.setDriver(new KOMOpera().getDriver());
					break;
				case Global.SAFARI:
					this.setDriver(new KOMSafari().getDriver());
					break;
				case Global.FIREFOX:
					this.setDriver(new KOMFireFox().getDriver());
					break;
				case Global.EDGE:
					this.setDriver(new KOMEdge().getDriver());
					break;
			}
		}
	}
	
	public static boolean textExists(By by, int seconds){
        Log.info(String.format("Checking if '%s' text locator exists on the page withing %s seconds", by.toString(), seconds));
		try{
			(new WebDriverWait(getDriver(), seconds)).until(ExpectedConditions.visibilityOfElementLocated(by));
			return true;
		}catch (TimeoutException ignored){
		}
		return false;
	}
	
	public static boolean textExists(String text, int seconds){
	    Log.info(String.format("Checking if '%s' text exists on the page withing %s seconds", text, seconds));
		return textExists(By.xpath("//*[contains(text(),'" + text + "')]"), seconds);
	}
	
	public static void waitForJQueryExecution(int time) {
		(new WebDriverWait(getDriver(), time)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				JavascriptExecutor js = (JavascriptExecutor) d;
				return (Boolean) js.executeScript("return !!window.jQuery?window.jQuery.active == 0:true");
			}
		});
		Browser.sleep(100);
	}

	public  static void sleep(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void switchToIFrame(By locator, int waitTime) {
	    Log.info(String.format("Switching to %s iFrame with %s seconds wait", locator.toString(), waitTime));
		WebDriverWait wait = new WebDriverWait(Browser.getDriver(), waitTime);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}

    public static void acceptAlert(){
	    Log.info("Accepting alert on the page");
        getDriver().switchTo().alert().accept();
    }

    public static void dismissAlert(){
        Log.info("Dismissing alert on the page");
        getDriver().switchTo().alert().dismiss();
    }

    public static void setPageLoadTime(int pageLoadWaitTime){
        Log.info(String.format("Setting page load time to %s seconds", pageLoadWaitTime));
        driver.manage().timeouts().pageLoadTimeout(pageLoadWaitTime, TimeUnit.SECONDS);
    }

    public static void open(String url, int currentPageLoadWaitTime){
        setPageLoadTime(currentPageLoadWaitTime);
        try{
            Log.info(String.format("Opening %s url", url));
            getDriver().get(url);
        }catch (TimeoutException ignored){
        }
        setPageLoadTime(Global.DEFAULT_IMPLICIT_WAIT);
    }

    public static void open(String url){
        try{
            Log.info(String.format("Opening %s url", url));
            getDriver().get(url);
        }catch (TimeoutException ignored){
        }
    }

    public static void refresh(){
		getDriver().navigate().refresh();
	}

	public static void scrollToBottom() {
		((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public static void scrollByHeight(int height) {
		((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0,'"+height+"')", "");
	}

    public static boolean waitForApplicationToLoad(int... waitTime) {
        int waitValue = waitTime.length == 0 ? 0 : waitTime[0];
        WebDriverWait wait = new WebDriverWait(Browser.getDriver(), waitValue);
        boolean result = false;
        try {
            ExpectedCondition<Boolean> appLoadCondition = new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return ((JavascriptExecutor)Browser.getDriver()).executeScript("return document.readyState").equals("complete");
                }
            };
            result = wait.until(appLoadCondition) != null;
        } catch (Exception ignored) {
        }
        Log.info("Application loading. Ready status = " + result);
        return result;
    }

}
