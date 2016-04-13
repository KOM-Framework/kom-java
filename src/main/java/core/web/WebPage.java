package core.web;

import java.util.ArrayList;
import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Optional;

import core.AssertLogger;
import core.Log;

public abstract class WebPage extends WebDynamicInit{
	
	private String pageName = this.getClass().getName();
	protected ArrayList<String> invokeArgs;
	protected abstract By webPageId();
	protected abstract void invoke();
	
	public WebPage(@Optional("") String... invokeArgs){
		this.invokeArgs=new ArrayList<String>(Arrays.asList(invokeArgs));
		if (!exists(0)){
			Log.info("Invoking '"+pageName+"' page");
			invoke();
			AssertLogger.assertTrue(exists(10), pageName + " does not exists after invoke attempt in 10 seconds");
			initElements(this);	
		}
	}
	
	public boolean exists(int waitTime){
		boolean result=false;
		WebDriverWait wait = new WebDriverWait(Browser.getDriver(), waitTime);
		try{
			WebElement item = wait.until(ExpectedConditions.elementToBeClickable(webPageId()));
			result=item!=null;
		}catch(Exception e){
		}
		Log.info("'" + pageName + "' page existance verification. Exists = " + result);
		return result;
	}
	
}
 