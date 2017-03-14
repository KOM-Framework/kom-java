package core.datatypes;

import core.Log;
import core.Reflect;
import core.web.WebItemList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ListView extends WebItemList{

	public ListView(By itemId) {
		super(itemId);
	}
	
	public ArrayList<String> getText(){
		ArrayList<String> out = new ArrayList<String>();
		for(WebElement item:this.getItems()){
			out.add(item.getText());
		}
		Log.info("Getting text from the '"+this.byId.toString()+"' list view");
		Log.info("Actual text: "+out);
		return out;
	}

	public <C> C  getAllItems(C type) {
	   Object item = null;	 
	   Field[] fields = type.getClass().getDeclaredFields();
	   try {
			item = type.getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		int size = this.getSize();
		for (int i = 1; i <= size; i++) {
			for (Field f : fields) {
				if (f.getModifiers() == 1) {
					try {
						Object fieldsObject = f.get(item);
						String fieldsLocator = null;
						try {
							fieldsLocator = Reflect.invokeMethod(fieldsObject, "getLocator").toString();
						} catch (NoSuchMethodException | InvocationTargetException e) {
							e.printStackTrace();
						}
						String xpath = this.getLocator() + "[" + i + "]" + fieldsLocator;
						Field byIDField = fieldsObject.getClass().getField("byId");
						byIDField.set(fieldsObject, By.xpath(xpath));
						f.set(item, fieldsObject);
					} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
							| SecurityException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return (C)item ;	
		}
	

}
