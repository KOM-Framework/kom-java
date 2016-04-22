package core.datatypes;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import core.Global;
import core.Log;
import core.web.Browser;
import core.web.WebItem;

public class DropDownList extends WebItem {

	public DropDownList(By by) {
		super(by);
	}
	
	public void select(String visibleText){
		Log.info("Selecting '"+visibleText+"' in the '"+this.locator+"' drop down list");
		Select userTypeDropdown = new Select(this.getActiveItem());
		userTypeDropdown.selectByVisibleText(visibleText);
		Browser.waitForJQueryExecution(Global.DEFAULT_AJAX_WAIT);
	}
	
	public void select(int index){
		Log.info("Selcting index-'"+index+"' in the '"+this.locator+"' drop down list");
		Select userTypeDropdown = new Select(this.getActiveItem());
		userTypeDropdown.selectByIndex(index);
		Browser.waitForJQueryExecution(Global.DEFAULT_AJAX_WAIT);
	}
	
	public String getSelectedOptionName()
	{
		Select userTypeDropdown = new Select(this.getActiveItem());
		String out=userTypeDropdown.getFirstSelectedOption().getText();
		Log.info("Getting the selected option of "+this.locator+"' drop down list: "+out+".");
		return out;
	}
	
	public ArrayList<String> getOptionNames(){
		ArrayList<String> out=new ArrayList<String>();
		Select userTypeDropdown = new Select(this.getActiveItem());
		List<WebElement> options = userTypeDropdown.getOptions();
		for(WebElement option:options){
			out.add(option.getText());
		}
		Log.info("Getting option names from the '"+this.locator+"' list");
		Log.info("Actual list: "+out);
		return out;
	}

}
