package core.datatypes;

import core.utilities.Log;
import core.web.KomElement;
import core.web.KomElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CheckBoxList extends KomElements {
	
	public KomElements labels;
 
	public CheckBoxList(By checkBox) {
		super(checkBox);
	}
	
	public CheckBoxList(By checkBoxId,By labelId) {
		super(checkBoxId);
		this.labels =new KomElements(labelId);
	}
	
	public ArrayList<String> getText(){
		ArrayList<String> out = new ArrayList<String>();
		List<KomElement> items = this.labels.getElement();
		for(WebElement lable:items){
			out.add(lable.getText());
		}
		Log.info("Getting text from the '"+this.byId.toString()+"' list. Text = '"+out+"'");
		return out;
	}
	
	public void check(boolean check, ArrayList<String> checkBoxLables){
		Log.info("Checking the '"+checkBoxLables+"'list in '"+this.byId.toString()+"'. Check = "+check);
		List<KomElement> lables = this.labels.getElement();
		List<KomElement> items = this.getElement();
		int size = this.labels.getElement().size();
		for (String s:checkBoxLables){
			for(int i=0;i<size;i++){
				if(lables.get(i).getText().equals(s)&&(items.get(i).isSelected()!=check)){
					items.get(i).click();}}}
	}
	
	public ArrayList<String> getUncheckedItems(){
		ArrayList<String> outList= new ArrayList<String>(); 
		int size = this.getElement().size();
		List<KomElement> items = this.getElement();
		List<KomElement> lables = this.labels.getElement();
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
		int size = this.getElement().size();
		List<KomElement> items = this.getElement();
		List<KomElement> labels = this.labels.getElement();
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
