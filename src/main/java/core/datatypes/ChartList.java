package core.datatypes;

import core.utilities.Log;
import core.utilities.Reflect;
import core.web.KomElement;
import core.web.KomElements;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.By;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ChartList extends KomElements {

	public ChartList(By itemId) {
		super(itemId);
	}

	@SuppressWarnings("unchecked")
	public <C> ArrayList<C> getItems(C type) {
		Log.info("Getting items from the '" + this.byId.toString() + "' chart list");
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
			for (Field field : fields) {
				try {
					Object fieldsObject = field.get(item);
					String fieldsLocator = null;
					try {
						fieldsLocator = Reflect.invokeMethod(fieldsObject, "getLocator").toString();
					} catch (NoSuchMethodException | InvocationTargetException e) {
						e.printStackTrace();
					}
					String xpath = this.getLocator() + "[" + i + "]" + fieldsLocator;
					Field byIDField = fieldsObject.getClass().getField("byId");
					byIDField.set(fieldsObject, By.xpath(xpath));
					field.set(item, fieldsObject);
				} catch (IllegalArgumentException | NoSuchFieldException | SecurityException
						| IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			elementsList.add(i - 1, (C) item);
		}
		return elementsList;
	}

	public <C> C getItem(int index, C type) {
		Log.info("Trying to find an chart index-'" + index + "' in '" + this.byId.toString() + "' chart list.");
		C foundItem = null;
		ArrayList<C> itemList = this.getItems(type);
		if (index < itemList.size() && index >= 0) {
			foundItem = itemList.get(index);
		} else {
			Log.info("Index-'" + index + "' in '" + this.byId.toString() + "' chart list is NOT valid.");
		}
		return foundItem;
	}

	public <C> KomElement getPropertyOfChart(int index, String fieldBy, C type) {
		KomElement propertyItem = null;
		Log.info("Trying to change property for  chart index-'" + index + "' in '" + this.byId.toString() + "' chart list.");
		C foundItem = this.getItem(index, type);
		if (foundItem != null) {
			Field itemField = FieldUtils.getDeclaredField(type.getClass(), fieldBy);
			Object fieldObject = null;
			try {
				fieldObject = FieldUtils.readField(itemField, foundItem);
			} catch (IllegalAccessException e2) {
				e2.printStackTrace();
			}
			propertyItem = (KomElement) fieldObject;
		}
		return propertyItem;
	}
}
