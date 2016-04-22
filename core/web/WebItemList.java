package core.web;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import core.Log;

public class WebItemList {

	protected List<WebElement> itemList;
	public By byId;
	public String locator;

	public List<WebElement> getItems() {
		this.setInstance();
		return itemList;
	}

	public ArrayList<WebItem> convertToWebItems() {
		this.setInstance();
		ArrayList<WebItem> outList = new ArrayList<WebItem>();
		int size = itemList.size();
		for (int i = 1; i <= size; i++) {
			WebItem item = new WebItem(By.xpath("(" + locator + ")" + "[" + i + "]"));
			outList.add(item);
		}
		return outList;
	}

	private void setInstance() {
		itemList = Browser.getDriver().findElements(byId);
	}

	public WebItemList(By itemId) {
		locator = itemId.toString().substring(itemId.toString().indexOf(":") + 1, itemId.toString().length()).trim();
		byId = itemId;
	}

	public boolean exists(int waitTime) {
		boolean exists = false;
		long startTime = System.currentTimeMillis();
		do {
			try {
				this.setInstance();
				if (itemList != null) {
					exists = true;
					break;
				}
			} catch (NoSuchElementException e) {
			}
		} while (System.currentTimeMillis() - startTime <= waitTime * 1000);
		Log.info("'" + locator + "' existance verification. Exists = " + exists);
		return exists;
	}

	public int getSize() {
		int out = this.getItems().size();
		Log.info("'" + locator + "' list size verification. Size = " + out);
		return out;
	}

	public WebItem getListElement(int index) {
		List<WebItem> tempItemList = convertToWebItems();
		WebItem outItem;

		if (tempItemList != null && tempItemList.size() > index) {
			outItem = tempItemList.get(index);
			Log.info("Get element index-'" + index + "' from '" + locator + "' list.");
			return outItem;
		} else {
			Log.info("Element index-'" + index + "' of '" + locator + "' list is NOT found.");
			return null;
		}
	}

	public WebItem getListElement(String text) {
		List<WebItem> tempItemList = convertToWebItems();
		WebItem outItem;

		if (tempItemList != null && tempItemList.size() > 0) {
			for (int i = 0; i < tempItemList.size(); i++) {
				if (tempItemList.get(i).getText().contains(text)) {
					outItem = tempItemList.get(i);
					Log.info("Get element text-'" + text + "' from '" + locator + "' list.");
					return outItem;
				}
			}
		}

		Log.info("Element text-'" + text + "' of '" + locator + "' list is NOT found.");
		return null;
	}

	public boolean allRowContainText(String textToCheck) {
		List<WebItem> tempItemList = convertToWebItems();
		boolean allContains = true;
		for (int i = 1; i < tempItemList.size(); i++) {
			if (!tempItemList.get(i).getText().contains(textToCheck)) {
				allContains = false;
				Log.info("Row-" + (i - 1) + " of '" + locator + "' list does noe contains text-" + textToCheck + ".");
				break;
			}
		}

		if (allContains == true)
			Log.info("All rows of '" + locator + "' list contains text-" + textToCheck + ".");

		return allContains;
	}
	
}
