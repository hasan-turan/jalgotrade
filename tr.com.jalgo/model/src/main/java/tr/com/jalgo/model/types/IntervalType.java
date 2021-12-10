package tr.com.jalgo.model.types;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum IntervalType {
	//@formatter:off
		NONE("None"), 
		m1("1m"), 
		m5("5m"), 
		H1("1h"), 
		H4("4h"), 
		D1("1d"), 
		W1("1w"), 
		M1("M1"), 
		Y1("1y") ,
		HISTORICAL_1min("1min"),
		HISTORICAL_5min("5min"),
		HISTORICAL_15min("15min"),
		HISTORICAL_30min("30min"),
		HISTORICAL_60min("60min");
	//@formatter:on
	private String value;

	IntervalType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
