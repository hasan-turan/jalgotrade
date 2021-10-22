package tr.com.jalgo.model.exchange;

import lombok.Getter;
import lombok.Setter;

public class Exchange {
	@Getter
	private final String apiKey;
	@Getter
	private final String secretKey;
	
	@Getter
	@Setter
	private String url;
	
	protected Exchange(String apiKey,String secretKey){
		this.apiKey=apiKey;
		this.secretKey=secretKey;
	}
	
	protected Exchange(String apiKey,String secretKey,String url){
		this.apiKey=apiKey;
		this.secretKey=secretKey;
		this.url=url;
	}
}
