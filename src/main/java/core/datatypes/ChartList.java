package core.datatypes;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.openqa.selenium.By;

import core.Log;
import core.web.WebItem;
import core.web.WebItemList;

public class ChartList extends WebItemList {
	
	public ChartList(By itemId) {
		super(itemId);
	}
	
	@SuppressWarnings("unchecked")
	public <C> ArrayList<C> getItems(C type){
		Log.info("Getting items from the '"+this.locator+"' chart list");
		ArrayList<C> elementsList = new ArrayList<C>();
		Field[] fields = type.getClass().getDeclaredFields();
		int size = this.getSize();
		for (int i=1;i<=size;i++){
			Object item = null;
			try {
				item = type.getClass().newInstance();
			} catch (InstantiationException | IllegalAccessException e1) {
				e1.printStackTrace();
			}
			for(Field field : fields){
				try {
					Object fieldsObject = field.get(item);
					String fieldsLocator = fieldsObject.getClass().getField("locator").get(fieldsObject).toString();
					String xpath = this.locator + "[" + i + "]" + fieldsLocator;
					Field byIDField = fieldsObject.getClass().getField("byId");
					byIDField.set(fieldsObject, By.xpath(xpath));
					field.set(item, fieldsObject);
				}  catch (IllegalArgumentException | NoSuchFieldException | SecurityException|IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			elementsList.add(i-1, (C) item);
		}
		return elementsList;
	}
	
	public <C> C getItem(int index, C type){
		Log.info("Trying to find an chart index-'"+index+"' in '"+this.locator+"' chart list.");
		C foundItem=null;

		ArrayList<C> itemList = this.getItems(type);
		
		if(index<itemList.size()&&index>=0)
		{
			foundItem = itemList.get(index);
		}
		else
		{
			Log.info("Index-'"+index+"' in '"+this.locator+"' chart list is NOT valid.");
		}
		
		return foundItem;
	}
	
	public <C> WebItem getPropertyOfChart(int index, String fieldBy, C type)
	{
		WebItem propertyItem  = null;
		Log.info("Trying to change property for  chart index-'"+index+"' in '"+this.locator+"' chart list.");
		C foundItem=this.getItem(index,type);
		
		if(foundItem!=null)
		{
			Field itemField = FieldUtils.getDeclaredField(type.getClass(), fieldBy);
			
			
			Object fieldObject = null;
			try {
				fieldObject = FieldUtils.readField(itemField, foundItem);
			} catch (IllegalAccessException e2) {
				e2.printStackTrace();
			}
			
			propertyItem = (WebItem) fieldObject;
			
		}
		
		return propertyItem;
	
	}
	
	
	public <C> C getItemByParaName(String itemName, String fieldBy, C type){
		Log.info("Trying to find an '"+itemName+"' item in '"+this.locator+"' grid view");
		C foundItem=null;

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
				e.printStackTrace();
			}
			if(fieldValue.equals(itemName)){
				foundItem=item;
				break;
			}
		}

		return foundItem;
	}
}
