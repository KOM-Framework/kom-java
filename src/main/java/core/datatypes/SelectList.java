package core.datatypes;

import core.web.Browser;
import core.web.KomElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class SelectList extends KomElements {

	public SelectList(By itemId) {
		super(itemId);
	}
	
	public void selectOption(String option){
		WebDriver driver = Browser.getDriver();
		Actions action = new Actions(driver);
		for (WebElement element : getElement()) {
			if (element.getText().equals(option)) {
				action.click(element).perform();
				break;
			}
		}
	}

}
