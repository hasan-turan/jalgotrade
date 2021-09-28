package tr.com.jalgo.api.controller;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tr.com.jalgo.api.JalgoResponse;

@RestController
@RequestMapping("/binance")
public class BinanceController {

	@RequestMapping(value = "/ticker", method = RequestMethod.GET, produces = "application/json")
	public JalgoResponse ticker(@RequestBody String symbol) throws IOException {
		JalgoResponse response = new JalgoResponse();

		Exchange binance = ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class);
		MarketDataService marketDataService = binance.getMarketDataService();
		Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);

		System.out.println(ticker.toString());

		return response;
	}

}
