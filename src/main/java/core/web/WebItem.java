package core.web;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Optional;

import core.Global;
import core.Log;

public class WebItem implements WebElement{

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

	public WebElement getPassiveItem() {
		return getItemWithCondition(Global.DEFAULT_EXPLICIT_WAIT, ExpectedConditions.presenceOfElementLocated(byId));
	}

	public WebItem(By byID) {
		String idString = byID.toString();
		locator = idString.substring(idString.indexOf(":") + 1, idString.length()).trim();
		byId = byID;
	}

	public boolean exists(@Optional int... waitTime) {
		int waitValue = waitTime.length == 0 ? 0 : waitTime[0];
		boolean result = false;
		try {
			result = getItemWithCondition(waitValue, ExpectedConditions.presenceOfElementLocated(byId)) != null;
		} catch (Exception e) {
		}
		Log.info("'" + locator + "' existance verification. Exists = " + result);
		return result;
	}

	public void click() {
		Log.info("Clicking on '" + locator + "'");
		getActiveItem().click();
		Browser.waitForJQueryExecution(Global.DEFAULT_AJAX_WAIT);
	}

	public void movetoclick() {
		Log.info("Moving to and clicking on '" + locator + "'");
		Actions action = new Actions(Browser.getDriver());
		action.moveToElement(getActiveItem()).click();
		action.perform();
		Browser.waitForJQueryExecution(Global.DEFAULT_AJAX_WAIT);
	}

	public boolean isEnabled() {
		boolean out = this.getPassiveItem().isEnabled();
		Log.info("'" + locator + "' isEnabled verification: Enabled = " + out);
		return out;
	}

	public boolean isDisplayed() {
		boolean out = this.getPassiveItem().isDisplayed();
		Log.info("'" + locator + "' isDisplayed verification: Displayed = " + out);
		return out;
	}

	public void clear() {
		Log.info("Clearing '" + locator + "'");
		this.getActiveItem().clear();
	}

	public String getText() {
		String textOut = this.getActiveItem().getText();
		Log.info("Getting text from '" + locator + "'. ActualText = " + textOut);
		return textOut;
	}

	public boolean isChecked() {
		boolean out = this.getActiveItem().isSelected();
		Log.info("'" + locator + "' isChecked verification: Checked = " + out);
		return out;
	}

	public void changeattribute(String attrName, String value) {
		getPassiveItem();
		JavascriptExecutor js = (JavascriptExecutor) Browser.getDriver();
		js.executeScript("arguments[0].setAttribute('" + attrName + "', '" + value + "')", item);
		Log.info("Changing attibute for '" + locator + "'. AttributeName= '" + attrName + "'. NewValue= '" + value
				+ "'.");
	}

	public void changeinnerHTML(String value) {
		getPassiveItem();
		JavascriptExecutor js = (JavascriptExecutor) Browser.getDriver();
		js.executeScript("arguments[0].innerHTML ='" + value + "'", item);
		Log.info("Changing innerHTML for '" + locator + "'. NewValue= '" + value + "'.");
	}

	public String getAttribute(String attributeName) {
		return this.getPassiveItem().getAttribute(attributeName);
	}

	public boolean isSelected() {
		return this.getActiveItem().isSelected();
	}

	public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
		return this.getActiveItem().getScreenshotAs(target);
	}

	public void submit() {
		this.getActiveItem().submit();
	}

	public void sendKeys(CharSequence... keysToSend) {
		this.getActiveItem().sendKeys(keysToSend);
	}

	public String getTagName() {
		return this.getActiveItem().getTagName();
	}

	public List<WebElement> findElements(By by) {
		return this.getActiveItem().findElements(by);
	}

	public WebElement findElement(By by) {
		return this.getActiveItem().findElement(by);
	}

	public Point getLocation() {
		return this.getActiveItem().getLocation();
	}

	public Dimension getSize() {
		return this.getActiveItem().getSize();
	}

	public Rectangle getRect() {
		return this.getActiveItem().getRect();
	}

	public String getCssValue(String propertyName) {
		return this.getActiveItem().getCssValue(propertyName);
	}
}
