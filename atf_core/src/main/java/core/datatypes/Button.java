package core.datatypes;

import core.web.Browser;
import core.web.WebItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Button extends WebItem{

	public Button(By byID) {
		super(byID);
	}
	
	public boolean isClickable(int... waitTime){
		try{
			int waitValue = waitTime.length == 0 ? 0 : waitTime[0];
			WebDriverWait wait = new WebDriverWait(Browser.getDriver(), waitValue);
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(this));
			return true;
		}
		catch (WebDriverException ignored){}
		return false;
	}
}
