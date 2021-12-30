package tr.com.jalgo.historical;

import org.springframework.stereotype.Component;

import lombok.Getter;
import tr.com.jalgo.model.ApiResponse;
import tr.com.jalgo.model.Parity;
import tr.com.jalgo.model.types.IntervalType;

@Component
public abstract class RemoteData {
	
	@Getter
	private String url;
	
	@Getter
	private String apiKey;
	
	public RemoteData(String url,String apiKey) {
		this.url=url;
		this.apiKey=apiKey;
				
	}
 
	public abstract ApiResponse getData(Parity symbol, IntervalType interval);

	 
}
