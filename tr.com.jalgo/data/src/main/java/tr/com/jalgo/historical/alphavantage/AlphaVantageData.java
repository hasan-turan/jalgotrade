package tr.com.jalgo.historical.alphavantage;

import org.springframework.stereotype.Component;

import tr.com.jalgo.model.ApiResponse;
import tr.com.jalgo.common.utils.HttpUtils;
import tr.com.jalgo.historical.RemoteData;
import tr.com.jalgo.historical.alphavantage.types.FunctionType;
import tr.com.jalgo.model.Parity;
import tr.com.jalgo.model.types.IntervalType;

@Component("alphaVantageData")
public class AlphaVantageData extends RemoteData   {

	private static final String API_KEY = "BE0O5L3P9EXTSH5D";
	private static final String URL = "https://www.alphavantage.co/query";

	public AlphaVantageData() {
		super(URL, API_KEY);
		// TODO Auto-generated constructor stub
	}

 
	@Override
	public ApiResponse getData(Parity parity, IntervalType interval) {
		ApiResponse response = new ApiResponse();
		FunctionType function = getFunction(interval);

		//@formatter:off
		String url= this.getUrl()+ 
				"?function="+function.getValue()+
				"&symbol="+parity.getBaseAsset().getSymbol()+
				"&market="+parity.getCounterAsset().getSymbol()+
				"&apikey="+this.getApiKey();
		
		if(function==FunctionType.CRYPTO_INTRADAY)
			url+="&interval="+interval.getValue()+
					"&outputsize=full"+ //compact returns only the latest 100 data points in the intraday time series; full returns the full-length intraday time series. The "compact" option is recommended if you would like to reduce the data size of each API call.
					"&datatype=json";
		//@formatter:on

		var result = HttpUtils.sendRequest(url);
		response.setData(result);

		System.out.println(result);
		return response;
	}

	 

	private FunctionType getFunction(IntervalType interval) {
		switch (interval) {
		case W1:
			return FunctionType.DIGITAL_CURRENCY_WEEKLY;

		case M1:
			return FunctionType.DIGITAL_CURRENCY_MONTHLY;

		case D1:
			return FunctionType.DIGITAL_CURRENCY_DAILY;

		default:
			return FunctionType.CRYPTO_INTRADAY;
		}

	}

}
