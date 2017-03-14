package core.datatypes;

import core.Global;
import core.Log;
import core.web.Browser;
import core.web.WebItem;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class TextField extends WebItem {

	public TextField(By itemId) {
		super(itemId);
		
	}
	
	public void sendKeys(String keys){
		Log.info("Sending '"+keys+"' keys to the '"+this.byId.toString()+"' text field");
		WebElement element = getActiveItem();
		element.sendKeys(keys);
	}
	
	public void sendKeys(Keys keys){
		Log.info("Sending '"+keys.name()+"' keys to the '"+this.byId.toString()+"' text field");
		getActiveItem().sendKeys(keys);
		Browser.waitForJQueryExecution(Global.DEFAULT_AJAX_WAIT);
	}

	public void clearAndSendKeys(String keys){
		Log.info("Clearing and sending '"+keys+"' keys to the '"+this.byId.toString()+"' text field");
		WebElement element = getActiveItem();
		element.clear();
		element.sendKeys(keys);
	}

	public void clickAndSendKeys(String keys){
		Log.info("Clicking and sending '"+keys+"' keys to the '"+this.byId.toString()+"' text field");
        getActiveItem().click();
        getActiveItem().sendKeys(keys);
	}

	public String getText(){
		return this.getActiveItem().getAttribute("value");
	}

	public void cleanWithDel(){
		String actualText = this.getActiveItem().getAttribute("value");
        getActiveItem().sendKeys(Keys.HOME);
		for (int i=0; i<actualText.length();i++){
			getActiveItem().sendKeys(Keys.DELETE);
		}
	}

	public void sendKeysIntoHiddenField(String keys){
		Log.info("Sending '"+keys+"' keys to the '"+this.byId.toString()+"' hidden text field");
		getPassiveItem().sendKeys(keys);
		Browser.waitForJQueryExecution(Global.DEFAULT_AJAX_WAIT);
	}
}
