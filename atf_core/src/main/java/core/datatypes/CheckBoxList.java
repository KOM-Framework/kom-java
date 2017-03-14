package core.datatypes;

import core.Log;
import core.web.WebItem;
import core.web.WebItemList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CheckBoxList extends WebItemList {
	
	public WebItemList labels;
 
	public CheckBoxList(By checkBox) {
		super(checkBox);
	}
	
	public CheckBoxList(By checkBoxId,By lableId) {
		super(checkBoxId);
		this.labels =new WebItemList(lableId);
	}
	
	public ArrayList<String> getText(){
		ArrayList<String> out = new ArrayList<String>();
		List<WebItem> items = this.labels.getItems();
		for(WebElement lable:items){
			out.add(lable.getText());
		}
		Log.info("Getting text from the '"+this.byId.toString()+"' list. Text = '"+out+"'");
		return out;
	}
	
	public void check(boolean check, ArrayList<String> checkBoxLables){
		Log.info("Checking the '"+checkBoxLables+"'list in '"+this.byId.toString()+"'. Check = "+check);
		List<WebItem> lables = this.labels.getItems();
		List<WebItem> items = this.getItems();
		int size = this.labels.getItems().size();
		for (String s:checkBoxLables){
			for(int i=0;i<size;i++){
				if(lables.get(i).getText().equals(s)&&(items.get(i).isSelected()!=check)){
					items.get(i).click();}}}
	}
	
	public ArrayList<String> getUncheckedItems(){
		ArrayList<String> outList= new ArrayList<String>(); 
		int size = this.getItems().size();
		List<WebItem> items = this.getItems();
		List<WebItem> lables = this.labels.getItems();
		for(int i=0;i<size;i++){
			if(!items.get(i).isSelected()){
				outList.add(lables.get(i).getText());
			}
		}
		Log.info("Getting unchecked elements from the '"+this.byId.toString()+"' list.");
		Log.info("Actual list: "+outList);
		return outList;
	}
	
	public ArrayList<String> getCheckedItems(){
		ArrayList<String> outList= new ArrayList<String>();
		int size = this.getItems().size();
		List<WebItem> items = this.getItems();
		List<WebItem> labels = this.labels.getItems();
		for(int i=0;i<size;i++){
			if(items.get(i).isSelected()){
				outList.add(labels.get(i).getText());
			}
		}
		Log.info("Getting checked elements from the '"+this.byId.toString()+"' list.");
		Log.info("Actual list: "+outList);
		return outList;
	}
}
