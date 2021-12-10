package tr.com.jalgo.model.types;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum StrategyResultType {
	UNDEFINED("Undefined"), BUY("Buy"), SELL("Sell"), STOP_LOSS("Stop_Loss");

	@Getter
	@JsonValue
	private String value;

	private StrategyResultType(String value) {
		this.value = value;
	}

}
