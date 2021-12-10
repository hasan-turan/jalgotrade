package tr.com.jalgo.model.types;

import lombok.Getter;

public enum StatusType {
	
	UNDEFINED("Undefined"),OK("Ok"), ERROR("Error");
	
	@Getter
	private String value;
	
	private StatusType(String value) {
		this.value=value;
	}
	
	
}
