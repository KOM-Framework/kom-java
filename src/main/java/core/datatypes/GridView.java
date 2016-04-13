package core.datatypes;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.openqa.selenium.By;

import core.Log;
import core.web.Browser;
import core.web.WebItem;
import core.web.WebItemList;

public class GridView extends WebItemList{
	
	public WebItemList pager;
	public WebItem nextPageButton;
	
	public GridView(By by) {
		super(by);
	}
	
	public GridView(By by, By pager, By nextPageButton) {
		super(by);
		this.pager = 			new WebItemList(pager);
		this.nextPageButton = 	new WebItem(nextPageButton);
	}
	
	//weina added for RG GridView
	public GridView(By by, By pager) {
		super(by);
		this.pager = 			new WebItemList(pager);
	}

	@SuppressWarnings("unchecked")
	public <C> ArrayList<C> getItems(C type){
		Log.info("Getting items from the '" + this.locator + "' grid view");
		ArrayList<C> elementsList = new ArrayList<C>();
		Field[] fields = type.getClass().getDeclaredFields();
		int size = this.getSize();
		for (int i = 1; i <= size; i++) {
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
						String fieldsLocator = fieldsObject.getClass().getField("locator").get(fieldsObject).toString();
						String xpath = this.locator + "[" + i + "]" + fieldsLocator;
						Field byIDField = fieldsObject.getClass().getField("byId");
						byIDField.set(fieldsObject, By.xpath(xpath));
						f.set(item, fieldsObject);
					} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
							| SecurityException e) {
						e.printStackTrace();
					}
				}
			}

			elementsList.add(i - 1, (C) item);
		}
		return elementsList;
	}
	
	public <C> C getItem(String itemName, String fieldBy, C type){
		Log.info("Trying to find an '"+itemName+"' item in '"+this.locator+"' grid view");
		C foundItem=null;
		if(this.nextPageButton!=null)
		{
			boolean process = true;
			do{
				ArrayList<C> itemList = this.getItems(type);
				for(C item:itemList){
					String fieldValue = null;
					try {
						Field itemField = type.getClass().getDeclaredField(fieldBy);
						Object fieldObject = itemField.get(item);
						fieldValue =(String)fieldObject.getClass().getMethod("getText").invoke(fieldObject);
					} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
						e.printStackTrace();
						break;
					}
					if(fieldValue.equals(itemName)){
						foundItem=item;
						break;
					}
				}
				if (foundItem!=null){
					break;
				}else{
					if (this.nextPageButton.exists(0)){
						Log.info("Switching to the next page");
						this.nextPageButton.click();
					}else{
						process=false;
					}
				}
			}
			while(process);
		}
		//weina added for RG gridview
		else
		{
			int pageCount =0;
			if(this.pager!=null&&this.pager.getSize()!=0)
			{
				pageCount = this.pager.getSize();
				for(int ii=-1; ii<pageCount;ii++)
				{
					if(ii!=-1)
					{
						this.pager.getListElement(ii).movetoclick();
						Browser.sleep(1000);
					}
					ArrayList<C> itemList = this.getItems(type);
					for(C item:itemList){
						String fieldValue = null;
						Field itemField = null;
						Object fieldObject = null;
						try {
							itemField = type.getClass().getDeclaredField(fieldBy);
							fieldObject = itemField.get(item);
						} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e2) {
							e2.printStackTrace();
						}
						try {
							fieldValue =(String)fieldObject.getClass().getMethod("getText").invoke(fieldObject);
						} catch (NoSuchMethodException | IllegalAccessException
								| InvocationTargetException e) {
							//e.printStackTrace();
							continue;
						}
						if(fieldValue.equals(itemName)){
							foundItem=item;
							break;
						}
					}
					if (foundItem!=null)
					{
						break;
					}else
					{
						Log.info("Switching to the next page");
					}
				}
			}
			else
			{
				ArrayList<C> itemList = this.getItems(type);
				for(C item:itemList){
					String fieldValue = null;
					Field itemField = null;
					Object fieldObject = null;
					try {
						itemField = type.getClass().getDeclaredField(fieldBy);
						fieldObject = itemField.get(item);
					} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e2) {
						e2.printStackTrace();
					}
					try {
						fieldValue =(String)fieldObject.getClass().getMethod("getText").invoke(fieldObject);
					} catch (NoSuchMethodException | IllegalAccessException
							| InvocationTargetException e) {
						//e.printStackTrace();
						continue;
					}
					if(fieldValue.equals(itemName)){
						foundItem=item;
						break;
					}
				}		
			}
		}
		return foundItem;
	}
	
	
	
	public <C> C getItem(int index, C type){
		Log.info("Trying to get an item by index "+index+"in '"+this.locator+"' grid view");
		C foundItem=null;
		if(this.pager!=null)
		{
		
			int pageCount = this.pager.getSize()-2;
			int itemCount = 0;
			do{
				ArrayList<C> itemList = this.getItems(type);
				int size = itemList.size();
				if(index<=itemCount+size){
					foundItem=itemList.get(index>size?index%itemCount-1:index-1);
				}
				if (foundItem!=null){
					break;
				}else{
					if (pageCount>0){
						itemCount+=size;
						Log.info("Switching to the next page");
						this.nextPageButton.click();
					}
				}
				pageCount--;
			}
			while((pageCount>=0));
		}
		else
		{
			ArrayList<C> itemList = this.getItems(type);
			
			if(index<itemList.size()&&index>=0)
			{
				foundItem = itemList.get(index);
			}
			else
			{
				Log.info("Index-'"+index+"' in '"+this.locator+"' chart list is NOT valid.");
			}
		}
		return foundItem;
	}

	public <C> ArrayList<String> getAllItemNames(String fieldBy, C type){
		Log.info("Getting item names from the '"+this.locator+"' grid view");
		ArrayList<String> outList = new ArrayList<String>();
		int pageCount = 0;
		if(this.nextPageButton!=null)
		{
			Boolean process = true;
			do{
				ArrayList<C> itemList = this.getItems(type);
				for(C item:itemList){
					String fieldValue = null;
					Field itemField = FieldUtils.getDeclaredField(type.getClass(), fieldBy);
					Object fieldObject = null;
					try {
						fieldObject = FieldUtils.readField(itemField, item);
					} catch (IllegalAccessException e2) {
						e2.printStackTrace();
					}
					try {
						fieldValue=(String)MethodUtils.invokeMethod(fieldObject, "getText");
					} catch (NoSuchMethodException | IllegalAccessException
							| InvocationTargetException e) {
						fieldValue=null;
					}
					outList.add(fieldValue);
				}
				if (this.nextPageButton.exists(0)){
					Log.info("Switching to the next page");
					this.nextPageButton.click();
				}else{
					process=false;
				}
			}
			while(process);
		}
		else if (this.pager != null && this.pager.getSize() != 0) 
		{
			pageCount = this.pager.getSize();

			for (int ii = -1; ii < pageCount; ii++) 
			{
				if (ii != -1) {
					this.pager.getListElement(ii).movetoclick();
					Browser.sleep(2000);
					Log.info("Switching to the next page");
				}
				
				ArrayList<C> itemList = this.getItems(type);
				
				for(C item:itemList){
					String fieldValue = null;
					Field itemField = FieldUtils.getDeclaredField(type.getClass(), fieldBy);
					Object fieldObject = null;
					try {
						fieldObject = FieldUtils.readField(itemField, item);
					} catch (IllegalAccessException e2) {
						e2.printStackTrace();
					}
					try {
						fieldValue=(String)MethodUtils.invokeMethod(fieldObject, "getText");
					} catch (NoSuchMethodException | IllegalAccessException
							| InvocationTargetException e) {
						fieldValue=null;
					}
					outList.add(fieldValue);
				}
				
			}
		} 
		else 
		{
			ArrayList<C> itemList = this.getItems(type);
			
			for(C item:itemList){
				String fieldValue = null;
				Field itemField = FieldUtils.getDeclaredField(type.getClass(), fieldBy);
				Object fieldObject = null;
				try {
					fieldObject = FieldUtils.readField(itemField, item);
				} catch (IllegalAccessException e2) {
					e2.printStackTrace();
				}
				try {
					fieldValue=(String)MethodUtils.invokeMethod(fieldObject, "getText");
				} catch (NoSuchMethodException | IllegalAccessException
						| InvocationTargetException e) {
					fieldValue=null;
				}
				outList.add(fieldValue);
			}
		}

		return outList;
	}
	
	
	public int getListLength() {
		Log.info("Trying to find list length in '" + this.locator + "' grid view");
		int outCount=0;
		if (this.nextPageButton != null) {
			Boolean process=true;
			do {
				outCount+= this.getSize();
				if (this.nextPageButton.exists(0)){
					Browser.sleep(1500);
					Log.info("Switching to the next page");
					this.nextPageButton.click();
					Browser.sleep(1500);
				}else{
					process=false;
				}
			} while (process);
		} else {
			int pageCount = 0;
			if (this.pager != null && this.pager.getSize() != 0) {
				pageCount = this.pager.getSize();
				for (int ii = -1; ii < pageCount; ii++) {
					if (ii != -1) {
						this.pager.getListElement(ii).movetoclick();
						Browser.sleep(2000);
					}
					outCount += this.getSize();
					Log.info("Switching to the next page");

				}
			} else {
				outCount+= this.getSize();
			}
		}
		return outCount;
	}
	

}