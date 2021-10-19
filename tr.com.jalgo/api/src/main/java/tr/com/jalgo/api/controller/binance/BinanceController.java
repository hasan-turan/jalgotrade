package tr.com.jalgo.api.controller.binance;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.binance.BinanceAuthenticated;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.marketdata.BinanceKline;
import org.knowm.xchange.binance.dto.marketdata.KlineInterval;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.com.jalgo.api.JalgoResponse;
import tr.com.jalgo.dto.KlineParameter;
import tr.com.jalgo.dto.SymbolPair;

//https://www.baeldung.com/rest-vs-websockets
//https://spring.io/guides/gs/messaging-stomp-websocket/
///https://mkyong.com/webservices/jax-rs/restfull-java-client-with-java-net-url/
@RestController()
@RequestMapping(value = "/binance")
public class BinanceController {
	Exchange exchange = null;

	public BinanceController() {
		exchange = ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class);

	}

	@GetMapping(value = { "/", "" }, produces = "application/json")
	public JalgoResponse index() {
		JalgoResponse response = new JalgoResponse();
		response.setStatus(HttpStatus.OK);
		response.setData("Binance api is working!");

		return response;
	}

	@GetMapping(value = "/ticker", produces = "application/json")
	public JalgoResponse ticker(@RequestBody SymbolPair symbolPair) throws IOException, ParseException {
		JalgoResponse response = new JalgoResponse();

		CurrencyPair currencyPair = new CurrencyPair(symbolPair.getBaseSymbol(), symbolPair.getCounterSymbol());

		MarketDataService marketDataService = exchange.getMarketDataService();

		Ticker ticker = marketDataService.getTicker(currencyPair);

		response.setStatus(HttpStatus.OK);
		response.setData(ticker);

		return response;
	}

	@GetMapping(value = "/klines", produces = "application/json")
	public JalgoResponse klines(@RequestBody KlineParameter param) throws IOException, ParseException {
		JalgoResponse response = new JalgoResponse();

		CurrencyPair currencyPair = new CurrencyPair(param.getSymbolPair().getBaseSymbol(),
				param.getSymbolPair().getCounterSymbol());

		SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC"));

		Date date1 = iso8601Format.parse(iso8601Format.format(param.getStartDate())); // iso8601Format.parse("2013-04-28T18:47:21.000Z");
		Date date2 = iso8601Format.parse(iso8601Format.format(param.getEndDate())); // iso8601Format.parse("2021-08-28T16:49:13.000Z");

		BinanceExchange binanceExchange = (BinanceExchange) exchange;
		BinanceAuthenticated binance = ExchangeRestProxyBuilder
				.forInterface(BinanceAuthenticated.class, binanceExchange.getExchangeSpecification()).build();

		ResilienceRegistries resiRegistries = binanceExchange.getResilienceRegistries();
		BinanceMarketDataService binanceMarketDataService = new BinanceMarketDataService(binanceExchange, binance,
				resiRegistries);

		List<BinanceKline> klines = binanceMarketDataService.klines(currencyPair,
				KlineInterval.valueOf(param.getInterval()), null, date1.getTime(), date2.getTime());

		response.setStatus(HttpStatus.OK);
		response.setData(klines);

		return response;
	}

}
