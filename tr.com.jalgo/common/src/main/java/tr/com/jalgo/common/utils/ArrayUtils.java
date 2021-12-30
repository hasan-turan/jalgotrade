package tr.com.jalgo.common.utils;

public class ArrayUtils {

	public static <T> boolean isEmpty(final T... array) {
		return array == null || array.length == 0;
	}

}
