package core.web;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.Annotations;

import core.Custom;

public abstract class WebDynamicInit {

	private static Object object;
	private static Class<?> cls;
	
	private static void init(Field f) {
		try {
			
			Annotation[] annotations = f.getAnnotations();
			for (Annotation annotation : annotations) {
				switch (annotation.annotationType().getName()) {
				case "org.openqa.selenium.support.FindBy":
					Object id = new Annotations(f).buildBy();
					Constructor<?>[] constr = f.getType().getConstructors();
					for (Constructor<?> constructor : constr) {
						if (constructor.getParameterCount() == 1) {
							f.set(object, constructor.newInstance(id));
							break;
						}
					}
					break;
				case "org.openqa.selenium.support.FindBys":
					id = new Annotations(f).buildBy();
					constr = f.getType().getConstructors();
					for (Constructor<?> constructor : constr) {
						ArrayList<By> byList = Custom.byChainedToByList(id);
						if (constructor.getParameterCount() == byList.size()) {
							f.set(object, constructor.newInstance(byList.toArray()));
							break;
						}
					}
					break;
				}
			}
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException | InstantiationException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static void initElements(Object obj) {
		object = obj;
		cls = obj.getClass();
		do {
			Field[] fields = cls.getDeclaredFields();
			for (Field f : fields) {
				if (f.getModifiers() == 1) {
					init(f);
				}
			}
			cls = cls.getSuperclass();
		} while (!cls.getName().equals("core.web.WebPage") && !cls.getName().equals("core.web.WebDynamicInit"));
	}
	
	public WebDynamicInit() {
		initElements(this);
	}
}
