package tr.com.jalgo.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReflectionUtils {

	public static <T> Annotation getClassAnnotation(T clazz, Class<? extends Annotation> annotation) {
		return getAnnotation(clazz.getClass().getAnnotations(), annotation);
	}

	private static Annotation getAnnotation(Annotation[] annotations, Class<? extends Annotation> ofAnnotationType) {
		for (Annotation a : annotations) {
			if (a.annotationType().equals(ofAnnotationType))
				return a;
		}
		return null;
	}

	public static <T> Annotation getFieldAnnotation(T clazz, Field field, Class<? extends Annotation> annotation) {
		return getAnnotation(field.getAnnotations(), annotation);
	}

	public static <T> Annotation getMethodAnnotation(T clazz, Method method, Class<? extends Annotation> annotation) {
		return getAnnotation(method.getAnnotations(), annotation);
	}

	public static <T> List<Map<Method, Annotation>> getAllMethodsAnnotation(T clazz,
			Class<? extends Annotation> annotation) {
		Method[] methods = clazz.getClass().getMethods();
		List<Map<Method, Annotation>> annotations = new ArrayList<Map<Method, Annotation>>();
		for (Method m : methods) {
			Annotation anno = getAnnotation(m.getAnnotations(), annotation);
			if (anno != null) {
				Map<Method, Annotation> annox = new HashMap<Method, Annotation>();
				annox.put(m, anno);
				annotations.add(annox);
			}
		}
		return annotations;
	}

	public static <T> List<SimpleEntry<Field, Annotation>> getAllFieldsWithAnnotation(T clazz,
			Class<? extends Annotation> ofAnnotationType) {

		// iterate fields
		List<SimpleEntry<Field, Annotation>> fieldsWithAnnotation = Arrays.asList(clazz.getClass().getDeclaredFields())
				.stream().map(field -> {

					// iterate field annotations
					Optional<Annotation> anno = Arrays.asList(field.getAnnotations()).stream()
							.filter(a -> a.annotationType().equals(ofAnnotationType)).findFirst();

					if (anno.isPresent())
						return new SimpleEntry<Field, Annotation>(field, anno.get());

					return null;

				}).filter(x -> x != null).collect(Collectors.toList());

		return fieldsWithAnnotation;

	}

}
