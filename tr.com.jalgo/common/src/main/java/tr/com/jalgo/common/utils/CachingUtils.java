package  tr.com.jalgo.common.utils;

import java.lang.reflect.Method;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import tr.com.jalgo.model.exceptions.CachingException;

@Component
public class CachingUtils {

	@Autowired
	CacheManager cacheManager;

	private static CachingUtils instance;

	@PostConstruct
	public void registerInstance() {
		instance = this;
	}

	public static Cache getCache(String cacheName) {
		return instance.cacheManager.getCache(cacheName);
	}

	public static Cache.ValueWrapper getObject(Class<?> clazz, String key) {
		Cache cashe = getClassCash(clazz);
		if (cashe != null)
			return cashe.get(key);
		return null;
	}

	public static Cache getClassCash(Class<?> clazz) {
		// get all cache names in ehcache.xml
		Collection<String> cacheNames = instance.cacheManager.getCacheNames();
		for (String cacheName : cacheNames) {
			if (cacheName.startsWith(clazz.getSimpleName())) {
				return instance.cacheManager.getCache(cacheName);

			}
		}
		return null;
	}

	public static boolean clearClassCash(Class<?> clazz) throws CachingException {

		try {
			Cache cache = getClassCash(clazz);
			cache.clear();
			return true;
		} catch (Exception e) {
			throw new CachingException(e.getMessage());
		}

	}

	public static void addClassCash(Class<?> clazz, Method method, Object value, Object... args) throws CachingException {

		try {

			Cache cache = getClassCash(clazz);

			// generate cache key
			String cachKey = CachingUtils.generateKey(clazz, method, args);

			// if value is not null save to cache
			if (value != null) {
				cache.put(cachKey, value);
			}

		} catch (Exception e) {
			throw new CachingException(e.getMessage());
		}

	}

	public static String generateKey(Class<?> clazz, Method method, Object... args) {
		return getClassSimpleName(clazz) + "_" + method.getName() + "_" + StringUtils.arrayToDelimitedString(args, "_");
	}

	public static String getClassSimpleName(Class<?> clazz) {
		return clazz.getSimpleName();
	}
}
