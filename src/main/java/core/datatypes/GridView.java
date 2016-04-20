package core.datatypes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import org.openqa.selenium.By;
import core.Log;
import core.Reflect;
import core.web.WebItemList;

public class GridView extends WebItemList{
	
	public Button nextPageButton;
	
	public GridView(By by, By nextPageButton) {
		super(by);
		this.nextPageButton = 	new Button(nextPageButton);
	}
	
	@SuppressWarnings("unchecked")
	public <C> ArrayList<C> getItems(C type){
		Log.info("Getting items from the '" + this.locator + "' grid view");
		ArrayList<C> elementsList = new ArrayList<C>();
		Field[] fields = type.getClass().getDeclaredFields();
		for (int i = 1; i <= this.getSize(); i++) {
			Object item = null;
			try {
				item = type.getClass().newInstance();
			} catch (InstantiationException | IllegalAccessException e1) {
				e1.printStackTrace();
			}
			for (Field f : fields) {
				if (f.getModifiers() == 1) {
					try {
						Object fieldsObject = f.get(item);
						String fieldsLocator = (String) Reflect.getFieldValue(fieldsObject, "locator");
						String xpath = this.locator + "[" + i + "]" + fieldsLocator;
						Field byIDField = (Field) Reflect.getFieldValue(fieldsObject, "byId");
						byIDField.set(fieldsObject, By.xpath(xpath));
						f.set(item, fieldsObject);
					} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
						e.printStackTrace();
					}
				}
			}

			elementsList.add(i - 1, (C) item);
		}
		return elementsList;
	}
	
	public <C> C getItem(String itemName, String fieldBy, C type) {
		Log.info("Trying to find an '" + itemName + "' item in '" + this.locator + "' grid view");
		C foundItem = null;
		boolean process = true;
		do {
			ArrayList<C> itemList = this.getItems(type);
			for (C item : itemList) {
				String fieldValue = null;
				try {
					fieldValue = (String) Reflect.invokeMethod(type.getClass().getDeclaredField(fieldBy).get(item),"getText");
				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
					break;
				}
				if (fieldValue.equals(itemName)) {
					foundItem = item;
					break;
				}
			}
			if (foundItem != null) {
				break;
			} else {
				if (this.nextPageButton.exists(0)) {
					Log.info("Switching to the next page");
					this.nextPageButton.click();
				} else {
					process = false;
				}
			}
		} while (process);
		return foundItem;
	}
	
}