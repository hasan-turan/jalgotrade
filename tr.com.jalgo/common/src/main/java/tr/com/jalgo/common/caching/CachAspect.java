package tr.com.jalgo.common.caching;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
 
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import tr.com.jalgo.model.exceptions.CachingException;
import tr.com.jalgo.common.utils.AopUtils;
import tr.com.jalgo.common.utils.CachingUtils;
@Aspect
@Configuration
@Order(value = 1)
public class CachAspect {

	// within(*..service..*.*): 'service' is somewhere in the package name but not
	// at the start or end
	// within(service..*.*): 'service' is at the start of the package name (maybe
	// this can't happen in your scenario)
	// within(*..service.*)): 'service' is at the end of the package name
	@Pointcut("execution(* *(..))")
	public void all() {

	}

	@Pointcut("execution(* tr.com.jalgo.service..*.*(..))")
	public void allInBll() {

	}

	// All methods annotated with Cache
	@Around("all() && @annotation(cache)")
	public Object cacheByMethod(ProceedingJoinPoint joinPoint, Cache cache) throws Throwable {
		return proceedCache(joinPoint, cache);
	}

	// All classes annotated with Cache
	@Around("all() && @within(cache)")
	public Object cacheByClass(ProceedingJoinPoint joinPoint, Cache cache) throws Throwable {
		return proceedCache(joinPoint, cache);
	}

	public Object proceedCache(ProceedingJoinPoint joinPoint, Cache cache) throws Throwable {

		Method method = AopUtils.getMethod(joinPoint);
		Object[] args = AopUtils.getArgs(joinPoint);
		String methodName = method.getName();
		if (cache.clazz() == null)
			throw new CachingException("Please specify type of class to clazz parameter ");

		// if current method is one of {"find", "findAll"}  then
		if (Arrays.asList(cache.puts()).contains(methodName)) {

			// generate cache key from class , method and arguments
			String key = CachingUtils.generateKey(cache.clazz(), method, args);
			// get cached object related to key
			org.springframework.cache.Cache.ValueWrapper cachedObject = CachingUtils.getObject(cache.clazz(), key);
			// if there is no cached object then proceed method
			if (cachedObject == null) {
				Object returnedObject = joinPoint.proceed();
				CachingUtils.addClassCash(cache.clazz(), method, returnedObject, args);
				return returnedObject;
			}
			// if there exists cached object then return cached object
			else {
				return cachedObject.get();
			}
		}
		// if any save , update or delete
		// operations are being executed then remove all caches related to class
		else if (Arrays.asList(cache.evicts()).contains(methodName)) {
			CachingUtils.clearClassCash(cache.clazz());
		}
		// if method name different then *find*, *findAll*
		// proceed that methods
		return joinPoint.proceed();
	}

//	@AfterThrowing(value = "scope()", throwing = "error")
//	public void afterThrowing(JoinPoint joinPoint, Object error) throws Throwable {
//		System.out.println(error);
//	}

//	// Execute after @Cache annotated items in scope
//	@AfterReturning(value = "beanAnnotatedWithCache() ", returning = "returnValue")
//	public void afterReturning(JoinPoint joinPoint, Object returnValue) throws Throwable {
//
//		Method method = AopUtils.getMethod(joinPoint);
//		Class<?> clazz = method.getAnnotation(Cache.class).clazz();
//		String methodName = method.getName();
//		Object[] args = AopUtils.getArgs(joinPoint);
//
//		// if current method is list, listAll, getItems
//		if (methodName.contains("list") || methodName.contains("getItems")) {
//			CachingUtils.addClassCash(clazz, method, returnValue, args);
//			// if any save , update or delete
//			// operations are being executed then remove related caches
//		} else if (methodName.contains("save") || methodName.contains("update") || methodName.contains("delete")) {
//			CachingUtils.clearClassCash(clazz);
//		}
//
//	}

}
