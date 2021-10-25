package tr.com.jalgo.model.exchange;

import lombok.Getter;
import lombok.Setter;

public class Exchange {
	@Getter
	@Setter
	private String apiKey;

	@Getter
	@Setter
	private String secretKey;

	@Getter
	@Setter
	private String url;

	public Exchange(String apiKey, String secretKey, String url) {
		this.apiKey = apiKey;
		this.secretKey = secretKey;
		this.url = url;
	}
}
