package core.web;

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
	
	private void init(Field f) {
		try {
			Object id;
			id = new Annotations(cls.getDeclaredField(f.getName())).buildBy();
			Constructor<?>[] constr = f.getType().getConstructors();
			if (id.getClass().getSimpleName().equals("ByChained")) {
				ArrayList<By> byList = Custom.byChainedToByList(id);
				for (Constructor<?> constructor : constr) {
					if (constructor.getParameterCount() == byList.size()) {
						f.set(object, constructor.newInstance(byList.toArray()));
						break;
					}
				}
			} else {
				for (Constructor<?> constructor : constr) {
					if (constructor.getParameterCount() == 1) {
						f.set(object, constructor.newInstance(id));
						break;
					}
				}
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public void initElements(Object obj) {
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
