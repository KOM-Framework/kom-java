package core.datatypes;

import core.Global;
import core.Log;
import core.web.Browser;
import core.web.WebItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DropDownList extends WebItem {

	public DropDownList(By by) {
		super(by);
	}
	
	public void select(String visibleText){
		Log.info("Selecting '"+visibleText+"' in the '"+this.byId.toString()+"' drop down list");
		Select userTypeDropdown = new Select(this.getActiveItem());
		userTypeDropdown.selectByVisibleText(visibleText);
		Browser.waitForJQueryExecution(Global.DEFAULT_AJAX_WAIT);
	}
	
	public void select(int index){
		Log.info("Selecting index-'"+index+"' in the '"+this.byId.toString()+"' drop down list");
		Select userTypeDropdown = new Select(this.getActiveItem());
		userTypeDropdown.selectByIndex(index);
		Browser.waitForJQueryExecution(Global.DEFAULT_AJAX_WAIT);
	}
	
	public String getSelectedOptionName()
	{
		String out = "";
		Select userTypeDropdown = new Select(this.getActiveItem());
		out=userTypeDropdown.getFirstSelectedOption().getText();
		Log.info("Getting the selected option of "+this.byId.toString()+"' drop down list: "+out+".");
		return out;
	}
	
	public ArrayList<String> getOptionNames(){
		ArrayList<String> out=new ArrayList<String>();
		Select userTypeDropdown = new Select(this.getActiveItem());
		List<WebElement> options = userTypeDropdown.getOptions();
		for(WebElement option:options){
			out.add(option.getText());
		}
		Log.info("Getting option names from the '"+this.byId.toString()+"' list");
		Log.info("Actual list: "+out);
		return out;
	}

	public ArrayList<ArrayList<String>> getOptionNamesByGroup(){
		List <WebElement> groups = this.getActiveItem().findElements(By.tagName("optgroup"));
		ArrayList<ArrayList<String>>out=new ArrayList<ArrayList<String>>(groups.size());
		for (WebElement group:groups){
			out.add(new ArrayList<String>(Arrays.asList( group.getText().split("\n"))));
		}
		return out;
	}
	
}
