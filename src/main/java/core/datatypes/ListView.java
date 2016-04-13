package core.datatypes;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import core.Log;
import core.web.WebItemList;

public class ListView extends WebItemList{

	public ListView(By itemId) {
		super(itemId);
	}
	
	public ArrayList<String> getText(){
		ArrayList<String> out = new ArrayList<String>();
		for(WebElement item:this.getItems()){
			out.add(item.getText());
		}
		Log.info("Getting text from the '"+this.locator+"' list view");
		Log.info("Actual text: "+out);
		return out;
	}

}
