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
import core.Reflect;

public abstract class WebPage extends WebDynamicInit{
	
	private String pageName = this.getClass().getName();
	protected ArrayList<String> invokeArgs;

	protected abstract void invokeActions();

	public WebPage(@Optional("") String... invokeArgs) {
		this.invokeArgs = new ArrayList<String>(Arrays.asList(invokeArgs));
		initElements(this);
	}

	public final void invoke() {
		if (!exists()) {
			Log.info("Invoking '" + pageName + "' page");
			invokeActions();
			AssertLogger.assertTrue(exists(10), pageName + " does not exists after invoke attempt in 10 seconds");
		}
	}

	public boolean exists(@Optional int... waitTime) {
		int waitValue = waitTime.length == 0 ? 0 : waitTime[0];
		boolean result = false;
		WebDriverWait wait = new WebDriverWait(Browser.getDriver(), waitValue);
		try {
			WebElement item = wait.until(ExpectedConditions
					.presenceOfElementLocated((By) Reflect.getFieldValueFromField(this, "webPageId", "byId")));
			result = item != null;
		} catch (Exception e) {
		}
		Log.info("'" + pageName + "' page existance verification. Exists = " + result);
		return result;
	}
}
 