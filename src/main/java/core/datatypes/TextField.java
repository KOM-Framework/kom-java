package core.datatypes;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import core.Global;
import core.Log;
import core.web.Browser;
import core.web.WebItem;

public class TextField extends WebItem {

	public TextField(By itemId) {
		super(itemId);

	}

	public void sendKeys(String keys) {
		Log.info("Sending '" + keys + "' keys to the '" + this.locator + "' text field");
		WebElement element = getActiveItem();
		element.clear();
		element.sendKeys(keys);
	}

	public void sendKeys(Keys keys) {
		Log.info("Sending '" + keys.name() + "' keys to the '" + this.locator + "' text field");
		getActiveItem().sendKeys(keys);
		Browser.waitForJQueryExecution(Global.DEFAULT_AJAX_WAIT);
	}

	public String getText() {
		String actualText = this.getActiveItem().getAttribute("value");
		return actualText;
	}
	
}
