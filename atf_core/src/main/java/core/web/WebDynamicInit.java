package core.web;

import core.utility.General;
import core.datatypes.Pager;
import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.Annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public abstract class WebDynamicInit {

	private static void init(Field f, Object object) throws IllegalAccessException, InvocationTargetException, InstantiationException {
		Annotation[] annotations = f.getAnnotations();
		for (Annotation annotation : annotations) {
			String annotationNameWithPath = annotation.annotationType().getName();
            String annotationName = annotationNameWithPath.substring(annotationNameWithPath.lastIndexOf(".")+1);
			switch (annotationName) {
			case "FindBy":
				Object id = new Annotations(f).buildBy();
				Constructor<?>[] constructors = f.getType().getConstructors();
				for (Constructor<?> constructor : constructors) {
					if (constructor.getParameterCount() == 1) {
						f.set(object, constructor.newInstance(id));
						break;
					}
				}
				break;
			case "FindBys":
				id = new Annotations(f).buildBy();
                constructors = f.getType().getConstructors();
				for (Constructor<?> constructor : constructors) {
					ArrayList<By> byList = General.byChainedToByList(id);
					if (constructor.getParameterCount() == byList.size()) {
						f.set(object, constructor.newInstance(byList.toArray()));
						break;
					}
				}
				break;
			case "Grid":
				Class<? extends Annotation> type = annotation.annotationType();
				try {
					String xpathValue = String.valueOf(type.getMethod("xpath").invoke(annotation, (Object[])null));
					Class structureValue = (Class) type.getMethod("structure").invoke(annotation, (Object[])null);
					String xpathPager = String.valueOf(type.getMethod("pagerXpath").invoke(annotation, (Object[])null));
					String currentPageXpath = String.valueOf(type.getMethod("currentPageXpath").invoke(annotation, (Object[])null));
                    Pager.PagerType pagerType = (Pager.PagerType) type.getMethod("pagerType").invoke(annotation, (Object[])null);
                    constructors = f.getType().getConstructors();
					for (Constructor<?> constructor : constructors) {
						if (constructor.getParameterCount() == 2 && xpathPager.equals("")) {
							f.set(object, constructor.newInstance(xpathValue, structureValue));
							break;
						}else if (constructor.getParameterCount() == 5){
							f.set(object, constructor.newInstance(xpathValue, structureValue, xpathPager, currentPageXpath, pagerType));
							break;
						}
					}
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
	
	static void initElements(Object obj) {
		Class<?> cls = obj.getClass();
		do {
			Field[] fields = cls.getDeclaredFields();
			for (Field f : fields) {
				if (f.getModifiers() == 1) {
					try {
						init(f, obj);
					} catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
						e.printStackTrace();
					}
				}
			}
			cls = cls.getSuperclass();
		} while (!cls.getName().endsWith("WebPage") && !cls.getName().endsWith("WebDynamicInit"));
	}
	
	public WebDynamicInit(){
		initElements(this);
	}
}
