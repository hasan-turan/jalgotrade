package tr.com.jalgo.model.enums;

import lombok.Getter;

public enum EnumHttpMethod {
	
	GET("GET"), POST("POST"),PUT("PUT"),DELETE("DELETE");
	
	@Getter
	private String value;
	
	private EnumHttpMethod(String value) {
		this.value=value;
	}
	
	
}
