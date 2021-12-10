package tr.com.jalgo.model.types;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MarketType {
	SPOT("Spot"), FEATURES("Features"),;

	private String value;

	MarketType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
