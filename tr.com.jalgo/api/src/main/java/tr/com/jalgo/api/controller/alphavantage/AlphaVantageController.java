package tr.com.jalgo.api.controller.alphavantage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.com.jalgo.historical.RemoteData;
import tr.com.jalgo.model.ApiResponse;
import tr.com.jalgo.model.Asset;
import tr.com.jalgo.model.Parity;
import tr.com.jalgo.model.types.IntervalType;

@RestController
@RequestMapping(value = "/alphavantage")
public class AlphaVantageController {

	@Autowired
	@Qualifier("alphaVantageData")
	RemoteData alphaVantage;

	@GetMapping(value="/intraday")
	public ApiResponse intraday() {
		return alphaVantage.getData( new Parity( new Asset("BTC"), new Asset("USD")), IntervalType.HISTORICAL_5min);
	}
	
	@GetMapping(value="/daily")
	public ApiResponse daily() {
		return alphaVantage.getData( new Parity(new Asset("BTC"), new Asset("USD")),IntervalType.D1);
	}
	
	@GetMapping(value="/weekly")
	public ApiResponse weekly() {
		return alphaVantage.getData( new Parity(new Asset("BTC"), new Asset("USD")),IntervalType.W1);
	}
	
	@GetMapping(value="/monthly")
	public ApiResponse monthly() {
		return alphaVantage.getData( new Parity(new Asset("BTC"), new Asset("USD")),IntervalType.M1);
	}
}
