package tr.com.jalgo.historical.alphavantage.types;

import lombok.Getter;

public enum FunctionType {
	//@formatter:off
		TIME_SERIES_INTRADAY("TIME_SERIES_INTRADAY"), 
		TIME_SERIES_INTRADAY_EXTENDED("TIME_SERIES_INTRADAY_EXTENDED"),
		CRYPTO_INTRADAY("CRYPTO_INTRADAY"), //This API returns intraday time series (timestamp, open, high, low, close, volume) of the cryptocurrency specified, updated realtime.
		DIGITAL_CURRENCY_DAILY("DIGITAL_CURRENCY_DAILY"),
		DIGITAL_CURRENCY_WEEKLY("DIGITAL_CURRENCY_WEEKLY"),
		DIGITAL_CURRENCY_MONTHLY("DIGITAL_CURRENCY_MONTHLY");
	//@formatter:on
	@Getter
	private String value;

	FunctionType(String value) {
		this.value = value;
	}
}
