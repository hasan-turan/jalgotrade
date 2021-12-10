package tr.com.jalgo.model.types;

import lombok.Getter;

public enum HttpMethodType {
	
	GET("GET"), POST("POST"),PUT("PUT"),DELETE("DELETE");
	
	@Getter
	private String value;
	
	private HttpMethodType(String value) {
		this.value=value;
	}
	
	
}
