package tr.com.jalgo.model.types;

import lombok.Getter;

public enum StatusType {

	UNDEFINED(0), OK(200), ERROR(500);

	@Getter
	private int value;

	private StatusType(int value) {
		this.value = value;
	}

	 
}
