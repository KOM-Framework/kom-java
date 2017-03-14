package core.web;

import core.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class WebItemList {

	public By byId;

	public WebItemList(By itemId){
        byId=itemId;
    }

	public List<WebItem> getItems(int... waitTime) {
        int waitValue = waitTime.length == 0 ? 0 : waitTime[0];
        WebDriverWait wait = new WebDriverWait(Browser.getDriver(), waitValue);
        List<WebElement> weList= wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(byId));
        ArrayList<WebItem> outList = new ArrayList<WebItem>();
        int size = weList.size();
        for(int i=1;i<=size;i++){
            WebItem item = new WebItem(By.xpath("("+byId.toString().substring(byId.toString().indexOf(":") + 1, byId.toString().length()).trim()+")"+"["+i+"]"));
            outList.add(item);
        }
        return outList;
	}
	
	public String getLocator(){
		return byId.toString().substring(byId.toString().indexOf(":") + 1, byId.toString().length()).trim();
	}

	public boolean exists(int waitTime){
        Log.info("'"+byId.toString()+ "' existence verification.");
		boolean result = false;
        try{
            WebDriverWait wait = new WebDriverWait(Browser.getDriver(), waitTime);
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(byId));
            result = true;
        }
        catch (TimeoutException ignored){
        }
        Log.info(String.format("Exists = %s", result));
		return result;
	}
	
	public int getSize(){
		int out = this.getItems().size();
		Log.info("'"+byId.toString()+ "' list size verification. Size = "+out);
		return out;
	}

	public WebItem getListElement(int index){
		List<WebItem> tempItemList = getItems();
		WebItem outItem;
		if(tempItemList!=null&&tempItemList.size()>index){
			outItem=tempItemList.get(index);
			Log.info("Get element index-'"+index+"' from '"+byId.toString()+ "' list.");
			return outItem;
		}
		else{
			Log.info("Element index-'"+index+"' of '"+byId.toString()+ "' list is NOT found.");
			return null;
		}
	}

	public WebItem getListElement(String text)
	{
		List<WebItem> tempItemList = getItems();
		if(tempItemList!=null&&tempItemList.size()>0){
			for(WebItem outItem: tempItemList){
				if(outItem.getText().contains(text)){
					Log.info("Get element text-'"+text+"' from '"+byId.toString()+ "' list.");
					return outItem;
				}
			}
		}
		Log.info("Element text-'"+text+"' of '"+byId.toString()+ "' list is NOT found.");
		return null;
	}
	
	public WebItem getListElementExact(String text)
	{
		List<WebItem> tempItemList = getItems();
		if(tempItemList!=null&&tempItemList.size()>0){
			for(WebItem outItem: tempItemList){
				if(outItem.getText().equals(text)){
					Log.info("Get element text-'"+text+"' from '"+byId.toString()+ "' list.");
					return outItem;
				}
			}
		}
		Log.info("Element text-'"+text+"' of '"+byId.toString()+ "' list is NOT found.");
		return null;
	}

	public ArrayList<String> getAllItemsText() {
		Log.info("Getting item text from the '" + byId.toString() + "' item list");
		ArrayList<String> outList = new ArrayList<String>();
		
		List<WebItem> tempItemList = getItems();
		if(tempItemList!=null&&tempItemList.size()>0){
			for(WebItem item: tempItemList){
				outList.add(item.getText());
			}
		}
		return outList;
	}
	
	public boolean allRowContainText(String textToCheck)
	{
		List<WebItem> tempItemList = getItems();
		boolean allContains = true;
		for(int i=1; i<tempItemList.size(); i++)
		{
			if(!tempItemList.get(i).getText().contains(textToCheck))
			{
				allContains=false;
				Log.info("Row-"+(i-1)+" of '"+byId.toString()+ "' list does noe contains text-"+textToCheck+".");
				break;
			}
		}
		if(allContains)
			Log.info("All rows of '"+byId.toString()+ "' list contains text-"+textToCheck+".");
		return allContains;
	}
	
}
