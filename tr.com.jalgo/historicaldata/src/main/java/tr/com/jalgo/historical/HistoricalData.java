package tr.com.jalgo.historical;

import org.springframework.stereotype.Component;

import lombok.Getter;
import tr.com.jalgo.historical.alphavantage.types.FunctionType;
import tr.com.jalgo.historical.alphavantage.types.SliceType;
import tr.com.jalgo.model.Symbol;
import tr.com.jalgo.model.exchange.ApiResponse;
import tr.com.jalgo.model.types.IntervalType;

@Component
public abstract class HistoricalData {
	
	@Getter
	private String url;
	
	@Getter
	private String apiKey;
	
	public HistoricalData(String url,String apiKey) {
		this.url=url;
		this.apiKey=apiKey;
				
	}

	
	public abstract ApiResponse intraday(Symbol currency, IntervalType interval);
	
	public abstract ApiResponse daily(Symbol currency);
	
	public abstract ApiResponse weekly(Symbol currency);
	
	public abstract ApiResponse monthly(Symbol currency);
	
}
