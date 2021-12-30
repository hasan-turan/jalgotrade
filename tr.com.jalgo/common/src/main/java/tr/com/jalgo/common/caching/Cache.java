package tr.com.jalgo.common.caching;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import tr.com.jalgo.model.BaseModel;

@Target(value = { ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
/**
 * @clazz: Defines type of class to be cached
 * @puts: Defines methods whose values must be cached
 * @evits: Defines methods before which the @clazz caches must be cleared
 */
public @interface Cache {

	Class<? extends BaseModel> clazz() default BaseModel.class;

	//@formatter:off
	/**
	 * cache all values related to the @clazz when executing 
	 * default:{ "find", "findAll" }
	 */
	//@formatter:on
	String[] puts() default { "find", "findAll" };

	//@formatter:off
	/**
	 * clear all caches related to the @clazz when executing 
	 * default: {"add", "update", "delete", "deleteById" }
	 */
	//@formatter:on
	String[] evicts() default { "add", "update", "delete", "deleteById"  };
}
