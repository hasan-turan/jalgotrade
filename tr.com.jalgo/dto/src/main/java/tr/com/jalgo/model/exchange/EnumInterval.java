package tr.com.jalgo.model.exchange;

public enum EnumInterval {
	m1("1m"), m5("5m"), h1("1h"), h4("4h"), d1("1d"), w1("1w"), M1("M1"), Y1("1y");

	private String value;

	EnumInterval(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
