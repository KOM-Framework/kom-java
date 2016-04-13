package core.web;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

import core.Global;
import core.Log;

public class Browser {

	private static Browser browser;
	private static WebDriver driver;
	
	public static Browser getBrowser() {
		return browser;
	}
	public static void setBrowser(Browser browser) {
		Browser.browser = browser;
	}
	public void setDriver(WebDriver driver) {
		Browser.driver = driver;
	}

	public static WebDriver getDriver() {
		if (driver == null) {
			setBrowser(new Browser(Global.BROWSER));
			driver.manage().timeouts().pageLoadTimeout(Global.DEFAULT_IMPLICIT_WAIT, TimeUnit.SECONDS);
		}
		return driver;
	}

	Browser(String browserType) {
		Log.info("Creating an instance of a "+browserType+" browser.");
		switch (browserType) {
		case Global.CHROME:
			System.setProperty("webdriver.chrome.driver", Global.CHRONE_DRIVER_PATH);
			this.setDriver(new ChromeDriver());
			break;
		case Global.INTERNET_EXPLORER:
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);      
			System.setProperty("webdriver.ie.driver", Global.IE_DRIVER_PATH);
			this.setDriver(new InternetExplorerDriver(capabilities));
			break;
		case Global.OPERA:
			System.setProperty("webdriver.opera.driver", Global.OPERA_DRIVER_PATH);
			this.setDriver(new OperaDriver());
			break;
		case Global.SAFARI:
			this.setDriver(new SafariDriver());
			break;
		default:
			FirefoxProfile fp = new FirefoxProfile();
			fp.setAcceptUntrustedCertificates(true);
			setDownloadWithoutAskConfirmationForfirefox(fp);
			this.setDriver(new FirefoxDriver(fp));
			break;
		}
	}
	
	public static boolean textExists(By by, int seconds){
		Log.info("'"+by.toString()+"' text existance verification");
		WebElement textItem = (new WebDriverWait(getDriver(), seconds)).until(ExpectedConditions.presenceOfElementLocated(by));
		return textItem!=null;
	}
	
	public static boolean textExists(String text, int seconds){
		return textExists(By.xpath("//*[contains(text(),'" + text + "')]"), seconds);
	}
	
	public static void waitForAjax(int time) {
		(new WebDriverWait(getDriver(), time)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				JavascriptExecutor js = (JavascriptExecutor) d;
				return (Boolean) js.executeScript("return !!window.jQuery && window.jQuery.active == 0");
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
	
	public static void pressKeys(String string) {
		try {
			Robot r = new Robot();
			for (int i = 0; i < string.length(); i++) {
				char c = string.charAt(i);
				if (Character.isUpperCase(c)) {
					r.keyPress(KeyEvent.VK_SHIFT);
				}
				r.keyPress(Character.toUpperCase(c));
				r.keyRelease(Character.toUpperCase(c));

				if (Character.isUpperCase(c)) {
					r.keyRelease(KeyEvent.VK_SHIFT);
				}
			}
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

}
