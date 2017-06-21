package core.web;

import core.Global;
import core.Log;
import core.Reflect;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class WebItem implements WebElement{

	public By byId;
	private WebElement element;

	private WebElement getItem(int seconds, ExpectedCondition<WebElement> condition) {
		WebDriverWait wait = new WebDriverWait(Browser.getDriver(), seconds);
        element = wait.until(condition);
		return element;
	}

	public String getElementId() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
	    if (element == null){
            element = getPassiveItem();
        }
	    return (String) Reflect.invokeMethod(element, "getId");
	}


	protected WebElement getActiveItem() {
		return getItem(Global.DEFAULT_EXPLICIT_WAIT, ExpectedConditions.elementToBeClickable(byId));
	}

    protected WebElement getPassiveItem() {
		return getItem(Global.DEFAULT_EXPLICIT_WAIT, ExpectedConditions.presenceOfElementLocated(byId));
	}

	public String getLocator(){
		return byId.toString().substring(byId.toString().indexOf(":") + 1, byId.toString().length()).trim();
	}

	public WebItem(By byID) {
		byId = byID;
	}

	public boolean exists(int... waitTime) {
		int waitValue = waitTime.length == 0 ? 0 : waitTime[0];
		boolean result = false;
		try {
			result = getItem(waitValue, ExpectedConditions.presenceOfElementLocated(byId)) != null;
		} catch (Exception ignored) {
		}
		Log.info("'" + byId.toString() + "' existence verification. Exists = " + result);
		return result;
	}

	public void click() {
		Log.info("Clicking on '" + byId.toString() + "'");
		getActiveItem().click();
		Browser.waitForJQueryExecution(Global.DEFAULT_AJAX_WAIT);
	}

    public void clickByJavaScript() {
        Log.info("Clicking by javascript on '" + byId.toString() + "'");
        ((JavascriptExecutor) Browser.getDriver()).executeScript("arguments[0].click();", getActiveItem());
        Browser.waitForJQueryExecution(Global.DEFAULT_AJAX_WAIT);
    }

	public void moveToAndClick() {
		Log.info("Moving to and clicking on '" + byId.toString() + "'");
		Actions action = new Actions(Browser.getDriver());
		action.moveToElement(getActiveItem()).click();
		action.perform();
		Browser.waitForJQueryExecution(Global.DEFAULT_AJAX_WAIT);
	}
	
	public void moveTo() {
		Log.info("Moving to and clicking on '" + byId.toString() + "'");
		Actions action = new Actions(Browser.getDriver());
		action.moveToElement(getActiveItem()).build().perform();
		Browser.waitForJQueryExecution(Global.DEFAULT_AJAX_WAIT);
	}

	public boolean isEnabled() {
		boolean out = this.getPassiveItem().isEnabled();
		Log.info("'" + byId.toString() + "' isEnabled verification: Enabled = " + out);
		return out;
	}

	public boolean isDisplayed() {
		boolean out = this.getPassiveItem().isDisplayed();
		Log.info("'" + byId.toString() + "' isDisplayed verification: Displayed = " + out);
		return out;
	}

	public void clear() {
		Log.info("Clearing '" + byId.toString() + "'");
		this.getActiveItem().clear();
	}

	public String getText() {
		String textOut = this.getPassiveItem().getText();
		Log.info("Getting text from '" + byId.toString() + "'. ActualText = " + textOut);
		return textOut;
	}

	public boolean isChecked() {
		boolean out = this.getActiveItem().isSelected();
		Log.info("'" + byId.toString() + "' isChecked verification: Checked = " + out);
		return out;
	}

	public void changeAttribute(String attrName, String value) {
        Log.info("Changing attibute for '" + byId.toString() + "'. AttributeName= '" + attrName + "'. NewValue= '" + value
                + "'.");
		JavascriptExecutor js = (JavascriptExecutor) Browser.getDriver();
		js.executeScript("arguments[0].setAttribute('" + attrName + "', '" + value + "')", getPassiveItem());

	}

	public void changeInnerHTML(String value) {
        Log.info("Changing innerHTML for '" + byId.toString() + "'. NewValue= '" + value + "'.");
		JavascriptExecutor js = (JavascriptExecutor) Browser.getDriver();
		js.executeScript("arguments[0].innerHTML ='" + value + "'", getPassiveItem());
	}
	
	public void createAttribute(String attrName) {
        Log.info("Creating attribute for '" + byId.toString() + "'. AttributeName= '" + attrName + "'.");
	    JavascriptExecutor js = (JavascriptExecutor) Browser.getDriver();
		js.executeScript("arguments[0].createAttribute('" + attrName + "')", getPassiveItem());
	}

	public void removeAttribute(String attrName) {
        Log.info("Removing attribute for '" + byId.toString() + "'. AttributeName= '" + attrName + "'.");
		JavascriptExecutor js = (JavascriptExecutor) Browser.getDriver();
		js.executeScript("arguments[0].removeAttribute('" + attrName + "')", getPassiveItem());
	}

	public void scrollIntoView() {
	    Log.info(String.format("Scrolling into view %s", byId.toString()));
		JavascriptExecutor js = (JavascriptExecutor) Browser.getDriver();
		js.executeScript("arguments[0].scrollIntoView(true);", getPassiveItem());
	}
	
	public void dragAndDropBy(int x, int y) {
        Log.info(String.format("Drag and drop element %s to x=%d and y=%d", byId.toString(), x, y));
		new Actions(Browser.getDriver()).dragAndDropBy(getActiveItem(), x, y).perform();
	}

	public String getAttribute(String attributeName) {
	    Log.info(String.format("Getting attribute %s from %s", attributeName, byId.toString()));
		return this.getPassiveItem().getAttribute(attributeName);
	}

	public boolean isSelected() {
	    Log.info(String.format("Check if %s is selected", byId.toString()));
		return this.getActiveItem().isSelected();
	}

	public void submit() {
	    Log.info(String.format("Submitting %s", byId.toString()));
		this.getActiveItem().submit();		
	}

	public void sendKeys(CharSequence... keysToSend) {
	    Log.info(String.format("Sending %s to the %s", keysToSend.toString(), byId.toString()));
		this.getActiveItem().sendKeys(keysToSend);		
	}

	public String getTagName() {
	    Log.info(String.format("Getting tag name from %s", byId.toString()));
		return this.getActiveItem().getTagName();
	}

	public List<WebElement> findElements(By by) {
		return this.getActiveItem().findElements(by);
	}

	public WebElement findElement(By by) {
		return this.getActiveItem().findElement(by);
	}

	public Point getLocation() {
        Log.info(String.format("Getting location of %s", byId.toString()));
		return this.getActiveItem().getLocation();
	}

	public Dimension getSize() {
        Log.info(String.format("Getting size of %s", byId.toString()));
		return this.getActiveItem().getSize();
	}

	public Rectangle getRect() {
        Log.info(String.format("Getting rectangle of %s", byId.toString()));
		return this.getActiveItem().getRect();
	}

	public String getCssValue(String propertyName) {
        Log.info(String.format("Getting css value of %s from %s", propertyName, byId.toString()));
		return this.getActiveItem().getCssValue(propertyName);
	}

    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
	    Log.info("Getting screenshot");
        return this.getActiveItem().getScreenshotAs(target);
    }

	public boolean waitForElementToAppear(int... waitTime) {
		return isVisible(waitTime);
	}

	public boolean waitForElementToDisappear(int... waitTime) {
		long waitValue = waitTime.length == 0 ? 0 : waitTime[0];
		boolean result = false;
        WebDriverWait wait = new WebDriverWait(Browser.getDriver(), waitValue);
		try {
            result = wait.until(ExpectedConditions.invisibilityOfElementLocated(byId));
		} catch (Exception ignored) {
		}
		Log.info("'" + byId.toString() + "' invisibility verification. Invisible = " + result);
		return result;
	}

	public boolean isVisible(int... waitTime) {
        int waitValue = waitTime.length == 0 ? 0 : waitTime[0];
		boolean result = false;
		try {
			result = this.getItem(waitValue, ExpectedConditions.visibilityOfElementLocated(byId)) != null;
		}catch (Exception ignored){}
		Log.info("'" + byId.toString() + "' visibility verification. Visible = " + result);
		return result;
	}

	public boolean waitForElementToBeEnabled(int... waitTime){
        int waitValue = waitTime.length == 0 ? 0 : waitTime[0];
		boolean result = false;
		try {
			result = this.getItem(waitValue, ExpectedConditions.elementToBeClickable(byId)) != null;
		}catch (Exception ignored){}
        Log.info(String.format("Waiting for the %s element to be Enabled. Enabled = %s", this.byId.toString(), result));
        return result;
    }

}
