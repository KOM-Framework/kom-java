package core.web;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import core.Global;
import core.Log;

public class WebItem {
	
	private WebElement item;
	public String locator;
	public By byId;
	
	public WebElement getItemWithCondition(int seconds, ExpectedCondition<WebElement> condition) {
		WebDriverWait wait = new WebDriverWait(Browser.getDriver(), seconds);
		item = wait.until(condition);
		return item;
	}
	
	public WebElement getActiveItem() {
		return getItemWithCondition(Global.DEFAULT_EXPLICIT_WAIT, ExpectedConditions.elementToBeClickable(byId));
	}
	
	public WebElement getPassiveItem(){
		return getItemWithCondition(Global.DEFAULT_EXPLICIT_WAIT, ExpectedConditions.presenceOfElementLocated(byId));
	}
	
	public WebItem(By byID){
		String idString = byID.toString();
		locator = idString.substring(idString.indexOf(":")+1, idString.length()).trim();
		byId=byID;
	}
	
	public boolean exists(int waitTime){
		boolean result = false;
		try{
			result=getItemWithCondition(waitTime,ExpectedConditions.presenceOfElementLocated(byId))!=null;
		}catch(Exception e){}
		Log.info("'"+locator+ "' existance verification. Exists = "+result);
		return result;
	}
	
	public void click(){
		Log.info("Clicking on '"+locator+"'");
		getActiveItem().click();
		Browser.waitForAjax(Global.DEFAULT_AJAX_WAIT);
	}
	
	//function added by weina
	public void movetoclick(){
		Log.info("Moving to and clicking on '"+locator+"'");
		Actions action = new Actions(Browser.getDriver());
		action.moveToElement(getActiveItem()).click();
		action.perform();
		Browser.waitForAjax(Global.DEFAULT_AJAX_WAIT);
	}
	
	public boolean isEnabled(){
		boolean out =this.getPassiveItem().isEnabled();
		Log.info("'"+locator + "' isEnabled verification: Enabled = "+out);
		return out;
	}
	
	public boolean isDisplayed(){
		boolean out =this.getPassiveItem().isDisplayed();
		Log.info("'"+locator + "' isDisplayed verification: Displayed = "+out);
		return out;
	}
	
	public void clear(){
		Log.info("Clearing '"+locator+"'");
		this.getActiveItem().clear();
	}
	
	public String getText(){
		String textOut=this.getActiveItem().getText();
		Log.info("Getting text from '"+locator +"'. ActualText = "+textOut);
		return textOut;
	}
	
	//function added by weina
	public boolean isChecked()
    {
		boolean out =this.getActiveItem().isSelected();
		Log.info("'"+locator + "' isChecked verification: Checked = "+out);
		return out;
    }
	
	//function added by weina
	public void changeattribute(String attrName, String value)
    {
		getActiveItem();
        JavascriptExecutor js = (JavascriptExecutor) Browser.getDriver();
        js.executeScript("arguments[0].setAttribute('"+attrName+"', '"+value+"')", item);
        Log.info("Changing attibute for '"+locator +"'. AttributeName= '"+attrName+"'. NewValue= '"+value+"'.");
    }
    
	//function added by weina
    public void changeinnerHTML(String value)
    {
    	   getActiveItem();
           JavascriptExecutor js = (JavascriptExecutor) Browser.getDriver();
           js.executeScript("arguments[0].innerHTML ='"+value+"'", item);
           Log.info("Changing innerHTML for '"+locator +"'. NewValue= '"+value+"'.");
    }
    
    public String getAttribute(String attributeName){
    	return this.getPassiveItem().getAttribute(attributeName);
    }

    public boolean isSelected(){
    	return this.getActiveItem().isSelected();
    }
}
