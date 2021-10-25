package tr.com.jalgo.model.enums;

import lombok.Getter;

public enum EnumStatus {
	
	UNDEFINED("Undefined"),OK("Ok"), ERROR("Error");
	
	@Getter
	private String value;
	
	private EnumStatus(String value) {
		this.value=value;
	}
	
	
}
