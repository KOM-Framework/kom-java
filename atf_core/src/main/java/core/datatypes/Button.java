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
	
	public boolean isClickable(int seconds){
		try{
			WebDriverWait wait = new WebDriverWait(Browser.getDriver(), seconds);
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(this));
			if (!"none".equals(element.getCssValue("pointer-events")))
				return true;
			else 
				return false;
		}
		catch (WebDriverException e){
			return false;
		}
	}
}
