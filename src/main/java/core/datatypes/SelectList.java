package core.datatypes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import core.web.Browser;
import core.web.WebItemList;

public class SelectList extends WebItemList {

	public SelectList(By itemId) {
		super(itemId);
	}
	
	public void selectOption(String option){
		WebDriver driver = Browser.getDriver();
		Actions action = new Actions(driver);
		for (WebElement element : getItems()) {
			if (element.getText().equals(option)) {
				action.click(element).perform();
				break;
			}
		}
	}

}
