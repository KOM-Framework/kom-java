package core.web;

import core.Global;
import core.Log;
import core.Reflect;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class WebPage extends WebDynamicInit {

	private String pageName = this.getClass().getName();
	protected ArrayList<String> invokeArgs;

	protected abstract void invokeActions() throws Exception;

	public WebPage(String... invokeArgs) {
	    super();
		this.invokeArgs = new ArrayList<>(Arrays.asList(invokeArgs));
	}

	public final void invoke() throws Exception {
		if (!exists()) {
			Log.info("Invoking '" + pageName + "' page");
			invokeActions();
			Assert.assertTrue(exists(Global.DEFAULT_PAGE_LOAD_TIME), String.format("%s does not exists after invoke attempt in %s seconds", pageName, Global.DEFAULT_PAGE_LOAD_TIME));
		}
	}
    
	public boolean exists(int... waitTime) throws Exception {
		int waitValue = waitTime.length == 0 ? 0 : waitTime[0];
		boolean result = false;
		WebDriverWait wait = new WebDriverWait(Browser.getDriver(), waitValue);
		try {
			WebElement item = wait.until(ExpectedConditions.visibilityOfElementLocated((By) Reflect.getFieldValueFromField(this, "webPageId", "byId")));
            result = item != null;
		} catch (TimeoutException ignored) {
		}
		Log.info("'" + pageName + "' page existence verification. Exists = " + result);
		return result;
	}
	
	public void pageRefresh()
	{
		Browser.getDriver().navigate().refresh();
	}

}
