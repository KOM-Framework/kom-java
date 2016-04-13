package core.datatypes;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import core.Log;
import core.web.WebItemList;

public class CheckBoxList extends WebItemList {
	
	public WebItemList lables;
 
	public CheckBoxList(By checkBox) {
		super(checkBox);
	}
	
	public CheckBoxList(By checkBoxId,By lableId) {
		super(checkBoxId);
		this.lables=new WebItemList(lableId);
	}
	
	public ArrayList<String> getText(){
		ArrayList<String> out = new ArrayList<String>();
		List<WebElement> items = this.lables.getItems(); 
		for(WebElement lable:items){
			out.add(lable.getText());
		}
		Log.info("Getting text from the '"+this.locator+"' list. Text = '"+out+"'");
		return out;
	}
	
	public void check(boolean check, ArrayList<String> checkBoxLables){
		Log.info("Checking the '"+checkBoxLables+"'list in '"+this.locator+"'. Check = "+check);
		List<WebElement> lables = this.lables.getItems();
		List<WebElement> items = this.getItems();
		int size = this.lables.getItems().size();
		for (String s:checkBoxLables){
			for(int i=0;i<size;i++){
				if(lables.get(i).getText().equals(s)&&(items.get(i).isSelected()!=check)){
					items.get(i).click();}}}
	}
	
	public ArrayList<String> getUncheckedItems(){
		ArrayList<String> outList= new ArrayList<String>(); 
		int size = this.getItems().size();
		List<WebElement> items = this.getItems();
		List<WebElement> lables = this.lables.getItems();
		for(int i=0;i<size;i++){
			if(!items.get(i).isSelected()){
				outList.add(lables.get(i).getText());
			}
		}
		Log.info("Getting unchecked elements from the '"+this.locator+"' list.");
		Log.info("Actual list: "+outList);
		return outList;
	}
	
	public ArrayList<String> getCheckedItems(){
		ArrayList<String> outList= new ArrayList<String>();
		int size = this.getItems().size();
		List<WebElement> items = this.getItems();
		List<WebElement> lables = this.lables.getItems();
		for(int i=0;i<size;i++){
			if(items.get(i).isSelected()){
				outList.add(lables.get(i).getText());
			}
		}
		Log.info("Getting checked elements from the '"+this.locator+"' list.");
		Log.info("Actual list: "+outList);
		return outList;
	}
}
