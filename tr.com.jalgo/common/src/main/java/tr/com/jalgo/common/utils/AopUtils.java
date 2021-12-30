package tr.com.jalgo.common.utils;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

public class AopUtils {

	private static MethodSignature getSignature(JoinPoint joinPoint) {
		return (MethodSignature) joinPoint.getSignature();
	}

	public static Method getMethod(JoinPoint joinPoint) {
		MethodSignature signature = getSignature(joinPoint);
		return signature.getMethod();
	}

	public static Class<?> getClass(JoinPoint joinPoint) {
		MethodSignature signature = getSignature(joinPoint);
		return signature.getDeclaringType();
	}

	public static Object[] getArgs(JoinPoint joinPoint) {
		return joinPoint.getArgs();
	}

}
