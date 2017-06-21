package core.web;

import core.Global;
import core.Log;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import io.github.bonigarcia.wdm.OperaDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Arrays;
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
		switch (browserType) {
		case Global.CHROME:
			if (Global.REMOTE_EXECUTION){
				DesiredCapabilities capability = DesiredCapabilities.chrome();
				capability.setCapability("chrome.switches", Arrays.asList("--ignore-certificate-errors"));
				try {
					this.setDriver(new RemoteWebDriver(new URL(Global.SELENIUM_HUB),capability));
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}else{
				ChromeDriverManager.getInstance().setup();
				this.setDriver(new ChromeDriver());
			}
			break;
		case Global.INTERNET_EXPLORER:
			InternetExplorerDriverManager.getInstance().setup();
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			this.setDriver(new InternetExplorerDriver(capabilities));
			break;
		case Global.OPERA:
			OperaDriverManager.getInstance().setup();
			this.setDriver(new OperaDriver());
			break;
		case Global.SAFARI:
			this.setDriver(new SafariDriver());
			break;
		case Global.FIREFOX:
			FirefoxDriverManager.getInstance().setup();
			FirefoxProfile fp = new FirefoxProfile();
			fp.setAcceptUntrustedCertificates(true);
			fp.setAssumeUntrustedCertificateIssuer(false);
			setDownloadWithoutAskConfirmationForfirefox(fp);
			this.setDriver(new FirefoxDriver(fp));
			break;
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
	
	private void setDownloadWithoutAskConfirmationForfirefox(FirefoxProfile profile){
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.download.dir", "C:\\Download\\");
		profile.setPreference("browser.helperApps.neverAsk.openFile",
		"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml,application/zip, application/octet-stream");
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
		"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml,application/zip, application/octet-stream");
		profile.setPreference("browser.helperApps.alwaysAsk.force", false);
		profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		profile.setPreference("browser.download.manager.focusWhenStarting", false);
		profile.setPreference("browser.download.manager.useWindow", false);
		profile.setPreference("browser.download.manager.showAlertOnComplete", false);
		profile.setPreference("browser.download.manager.closeWhenDone", false);
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
