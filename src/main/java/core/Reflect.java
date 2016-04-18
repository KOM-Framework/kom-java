package core;

import java.lang.reflect.InvocationTargetException;

public class Reflect {

	public static Object invokeMethodFromField(Object object, String fieldName, String methodName, Object... args){
		Object obj = null;
		try {
			obj = object.getClass().getField(fieldName).get(object);
			return obj.getClass().getMethod(methodName).invoke(obj,args);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object getFieldValueFromField(Object object, String baseFieldName, String fieldName){
		Object obj = null;
		try {
			obj = object.getClass().getField(baseFieldName).get(object);
			return obj.getClass().getField(fieldName).get(obj);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException  e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object getFieldValue(Object object, String fieldName){
		try {
			return object.getClass().getField(fieldName).get(object);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException  e) {
			e.printStackTrace();
		}
		return null;
	}
}
