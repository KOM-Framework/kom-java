package core.datatypes;

import core.Log;
import core.Reflect;
import core.web.WebItemList;
import org.openqa.selenium.By;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class GridView extends WebItemList {

	public Pager pager;
	private Object structure;

	public GridView(String xpath, Class structure) throws IllegalAccessException, InstantiationException {
		super(By.xpath(xpath));
        this.structure = structure.newInstance();
	}

	public GridView(String xpath, Class structure, String pagerXpath, String currentPageXpath, Pager.PagerType pagerType) throws IllegalAccessException, InstantiationException {
		super(By.xpath(xpath));
		this.structure = structure.newInstance();
		this.pager = new Pager(By.xpath(pagerXpath), By.xpath(currentPageXpath));
        this.pager.setPagerType(pagerType);
	}

	public void waitForResults(int waitTime) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException {
		long startTime = System.currentTimeMillis();
		if (this.exists(waitTime)){
            int size = this.getItems().size();
            while(size==0 && (System.currentTimeMillis() - startTime<waitTime*1000)){
                size = this.getItems().size();
            }
        }

	}

	@SuppressWarnings("unchecked")
	public <C> ArrayList<C> getItems() throws NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
		Log.info("Getting items from the '" + this.byId.toString() + "' grid view");
		ArrayList<C> elementsList = new ArrayList<C>();
		Field[] fields = this.structure.getClass().getDeclaredFields();
		if (this.exists(0)){
            int size = this.getSize();
            for (int i = 1; i <= size; i++) {
                Object item = null;
                try {
                    item = this.structure.getClass().newInstance();
                } catch (InstantiationException | IllegalAccessException e1) {
                    e1.printStackTrace();
                }
                for (Field f : fields) {
                    if (f.getModifiers() == 1) {
                        try {
                            Object fieldsObject = f.get(item);
                            String fieldsLocator = null;
                            fieldsLocator = Reflect.invokeMethod(fieldsObject, "getLocator").toString();
                            String xpath = this.getLocator() + "[" + i + "]" + fieldsLocator;
                            Field byIDField = fieldsObject.getClass().getField("byId");
                            byIDField.set(fieldsObject, By.xpath(xpath));
                            f.set(item, fieldsObject);
                        } catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
                            e.printStackTrace();
                        }
                    }
                }

                elementsList.add(i - 1, (C) item);
            }
        }
		return elementsList;
	}

	/*
     * this method is used to get list's row by row's field text.
     */
	public <C> C getItem(String itemName, String fieldBy, int waitTime) throws NoSuchMethodException, InvocationTargetException, NoSuchFieldException, IllegalAccessException {
		Log.info("Trying to find an '" + itemName + "' item in '" + this.byId.toString()+ "' grid view");
		C foundItem = null;
		if (pager != null) {
			pager.firstPage();
		}

		boolean process = true;
		do {
		    this.waitForResults(waitTime);
			ArrayList<C> itemList = this.getItems();
			for (C item : itemList) {
				String fieldValue = null;
				Object fieldObject = null;
				fieldObject = Reflect.invokeMethodFromField(item, fieldBy,
							"getText");
				fieldValue = fieldObject == null ? "" : (String) fieldObject;
				if (fieldValue.equals(itemName)) {
					foundItem = item;
					break;
				}
			}
			if (foundItem != null) {
				process = false;
			} else if (pager != null && pager.nextPage()) {
				Log.info("Switching to the next page");
			} else {
				process = false;
			}
		} while (process);

		return foundItem;
	}

	public <C> C getItem(int index, int waitTime) throws NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
		Log.info("Trying to get an item by index " + index + "in '" + this.byId.toString() + "' grid view");
		C foundItem = null;
		if (this.pager != null) {
			this.pager.firstPage();
		}
        this.waitForResults(waitTime);
		ArrayList<C> itemList = this.getItems();
		int size = itemList.size();

		if (index >= 0 && index < size) {
			foundItem = itemList.get(index);
		}
		else if (this.pager != null){
			int page =  (int)(index / size)+1;//TODO: Delete +1 if pager starts from 0
			int indexOnPage = index % size;
			int pageCount = this.pager.getSize();
			if(page<=pageCount){
				this.pager.moveToPage(page);
				itemList = this.getItems();
				size = itemList.size();
				if (indexOnPage >= 0 && indexOnPage < size) {
					foundItem = itemList.get(indexOnPage);
				}
			}
		}
		if (foundItem==null)
			Log.info("Index-'" + index + 1 + "' in '" + this.byId.toString() + "' chart list is NOT valid.");
		return foundItem;
	}

	public <C> ArrayList<String> getAllItemNames(String fieldBy, int waitTime) throws NoSuchMethodException, InvocationTargetException, NoSuchFieldException, IllegalAccessException {
		Log.info("Getting item names from the '" + this.byId.toString() + "' grid view");
		ArrayList<String> outList = new ArrayList<String>();
		if (pager != null) {
			pager.firstPage();
		}
		boolean process = true;
		do {
            this.waitForResults(waitTime);
			ArrayList<C> itemList = this.getItems();
			for (C item : itemList) {
				String fieldValue = "";
				Object fieldObject = null;
				fieldObject = Reflect.invokeMethodFromField(item, fieldBy,
							"getText");
				fieldValue = fieldObject == null ? "" : (String) fieldObject;
				outList.add(fieldValue);
			}
			if (process){
				if (pager != null && pager.nextPage()) {
					Log.info("Switching to the next page");
				} else {
					process = false;
				}
			}
		} while (process);

		return outList;
	}

	public int getListLength() {
		Log.info("Trying to find list length in '" + this.byId.toString() + "' grid view");
		int outCount = 0;
		if (this.pager != null)
			this.pager.firstPage();
		do{
			outCount += this.getSize();
			Log.info("Switching to the next page");
		}while (pager != null && pager.nextPage());
		return outCount;
	}
}
