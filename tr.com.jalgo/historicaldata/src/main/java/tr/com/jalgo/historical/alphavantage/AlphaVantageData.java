package tr.com.jalgo.historical.alphavantage;

import org.springframework.stereotype.Component;

import tr.com.jalgo.historical.HistoricalData;
import tr.com.jalgo.historical.alphavantage.types.FunctionType;
import tr.com.jalgo.model.Symbol;
import tr.com.jalgo.model.exchange.ApiResponse;
import tr.com.jalgo.model.types.IntervalType;
import tr.com.jalgo.utils.HttpUtils;

@Component("alphaVantageData")
public class AlphaVantageData extends HistoricalData {

	private static final String API_KEY = "BE0O5L3P9EXTSH5D";
	private static final String URL = "https://www.alphavantage.co/query";

	public AlphaVantageData() {
		super(URL, API_KEY);
		// TODO Auto-generated constructor stub
	}

	public ApiResponse intraday(Symbol currency, IntervalType interval) {
		return getData(FunctionType.CRYPTO_INTRADAY, currency, interval);
	}

	public ApiResponse daily(Symbol currency) {
		return getData(FunctionType.DIGITAL_CURRENCY_DAILY, currency, IntervalType.NONE);
	}

	public ApiResponse weekly(Symbol currency) {
		return getData(FunctionType.DIGITAL_CURRENCY_WEEKLY, currency, IntervalType.NONE);
	}

	public ApiResponse monthly(Symbol currency) {
		return getData(FunctionType.DIGITAL_CURRENCY_MONTHLY, currency, IntervalType.NONE);
	}

	private ApiResponse getData(FunctionType function, Symbol currency, IntervalType interval) {
		ApiResponse response = new ApiResponse();

		//@formatter:off
		String url= this.getUrl()+ 
				"?function="+function.getValue()+
				"&symbol="+currency.getBaseSymbol()+
				"&market="+currency.getCounterSymbol()+
				"&apikey="+this.getApiKey();
		
		if(function==FunctionType.CRYPTO_INTRADAY)
			url+="&interval="+interval.getValue()+
					"&outputsize=full"+ //compact returns only the latest 100 data points in the intraday time series; full returns the full-length intraday time series. The "compact" option is recommended if you would like to reduce the data size of each API call.
					"&datatype=json";
		//@formatter:on

		var result = HttpUtils.getAsString(url);
		response.setData(result);

		System.out.println(result);
		return response;
	}

}
