package tr.com.jalgo.api.types;

public enum EnumHttpAction {
	GET("GET"), POST("POST"), PUT("PUT");

	private String value;

	EnumHttpAction(String value) {
		this.value = value;
	}

	 
	public String getValue() {
		return value;
	}
}
