package tr.com.jalgo.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import tr.com.jalgo.model.BaseModel;

public class ReflectionUtils {

	public static <T extends BaseModel> Annotation getClassAnnotation(T clazz, Class<?> annotation) {
		return getAnnotation(clazz.getClass().getAnnotations(), annotation);
	}

	private static Annotation getAnnotation(Annotation[] annotations, Class<?> ofType) {
		for (Annotation a : annotations) {
			if (a.getClass().equals(ofType.getClass()))
				return a;
		}
		return null;
	}

	public static <T extends BaseModel> Annotation getFieldAnnotation(T clazz, Class<?> annotation) {
		Field[] fields = clazz.getClass().getFields();
		for (Field f : fields) {
			Annotation anno = getAnnotation(f.getAnnotations(), annotation);
			if (anno != null)
				return anno;
		}
		return null;
	}
	
	public static <T extends BaseModel> Annotation getMethodAnnotation(T clazz, Class<?> annotation) {
		Method[] methods = clazz.getClass().getMethods();
		for (Method m : methods) {
			Annotation anno = getAnnotation(m.getAnnotations(), annotation);
			if (anno != null)
				return anno;
		}
		return null;
	}
}
