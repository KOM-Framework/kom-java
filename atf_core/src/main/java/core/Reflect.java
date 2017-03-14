package core;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflect {

	public static Object invokeMethodFromField(Object object, String fieldName, String methodName, Object... args) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		Object obj = object.getClass().getField(fieldName).get(object);
        Method method =null;
		if (args.length >= 1) {
            Class<?>[] argClasses = new Class<?>[args.length];
		    for(int i=0; i<args.length; i++){
                argClasses[i]=args[i].getClass();
            }
            method = obj.getClass().getMethod(methodName, argClasses);
        }else{
            method = obj.getClass().getMethod(methodName);
        }
        return method.invoke(obj, args);
	}

	public static Object invokeMethod(Object object, String methodName, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method =null;
        if (args.length >= 1) {
            Class<?>[] argClasses = new Class<?>[args.length];
            for(int i=0; i<args.length; i++){
                argClasses[i]=args[i].getClass();
            }
            method = object.getClass().getMethod(methodName, argClasses);
        }else{
            method = object.getClass().getMethod(methodName);
        }
		return method.invoke(object, args);
	}

	public static Object getFieldValueFromField(Object object, String baseFieldName, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Object obj = object.getClass().getField(baseFieldName).get(object);
		return obj.getClass().getField(fieldName).get(obj);
	}

	public static Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
		return object.getClass().getField(fieldName).get(object);
	}

	public static Annotation getMethodAnnotation(String className, String methodName, String annotationName) {
		Annotation myAnnotation = null;
		try {
			Method method = Class.forName(className).getMethod(methodName);
			Annotation[] annotations = method.getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation.annotationType().getName().equals(annotationName)) {

					myAnnotation = annotation;
				}
			}
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return myAnnotation;
	}
}
