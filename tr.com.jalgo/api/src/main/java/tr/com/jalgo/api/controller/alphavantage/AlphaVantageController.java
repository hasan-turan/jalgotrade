package tr.com.jalgo.api.controller.alphavantage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tr.com.jalgo.historical.HistoricalData;
import tr.com.jalgo.historical.alphavantage.types.FunctionType;
import tr.com.jalgo.model.Symbol;
import tr.com.jalgo.model.exchange.ApiResponse;
import tr.com.jalgo.model.types.IntervalType;

@RestController
@RequestMapping(value = "/alphavantage")
public class AlphaVantageController {

	@Autowired
	@Qualifier("alphaVantageData")
	HistoricalData alphaVantage;

	@GetMapping(value="/intraday")
	public ApiResponse intraday() {
		return alphaVantage.intraday( new Symbol("BTC", "USD"), IntervalType.HISTORICAL_5min);
	}
	
	@GetMapping(value="/daily")
	public ApiResponse daily() {
		return alphaVantage.daily( new Symbol("BTC", "USD"));
	}
	
	@GetMapping(value="/weekly")
	public ApiResponse weekly() {
		return alphaVantage.weekly( new Symbol("BTC", "USD"));
	}
	
	@GetMapping(value="/monthly")
	public ApiResponse monthly() {
		return alphaVantage.monthly( new Symbol("BTC", "USD"));
	}
}
