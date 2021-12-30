package tr.com.jalgo.common.utils;

public class StringUtils {

	public static boolean isNullOrEmptyWhiteSpace(String value) {
		return value == null || value.isBlank();
	}

	public static boolean equals(String value1, String value2, boolean caseSensitive) {
		if (value1 == null || value2 == null)
			return false;
		if (caseSensitive == true)
			return value1.equals(value2);
		
		return value1.toLowerCase().equals(value2.toLowerCase());
	}

	public static boolean isNotBlank(String value) {
		 return !isNullOrEmptyWhiteSpace(value);
	}
	
	public static boolean isBlank(String value) {
		 return isNullOrEmptyWhiteSpace(value);
	}
	
	public static String format(String template, Object... params) {
		if (null == template) {
			return null;
		}
		if (ArrayUtils.isEmpty(params) || isBlank(template)) {
			return template ;
		}
		return String.format(template, params);
	}

}
