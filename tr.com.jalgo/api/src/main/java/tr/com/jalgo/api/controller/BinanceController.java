package tr.com.jalgo.api.controller;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import io.reactivex.disposables.Disposable;
import tr.com.jalgo.api.JalgoResponse;
import tr.com.jalgo.dto.Greeting;
import tr.com.jalgo.dto.HelloMessage;

//https://www.baeldung.com/rest-vs-websockets
//https://spring.io/guides/gs/messaging-stomp-websocket/

@Controller
public class BinanceController {
	Exchange binance = null;

	Disposable tickerSubscription = null;

	public BinanceController() {
		binance = ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class);

	}

	@RequestMapping(value = "/binance/ticker", method = RequestMethod.GET, produces = "application/json")
	public JalgoResponse ticker(@RequestParam String baseSymbol, @RequestParam String counterSymbol)
			throws IOException {
		JalgoResponse response = new JalgoResponse();

		CurrencyPair currencyPair = new CurrencyPair(baseSymbol, counterSymbol);

		MarketDataService marketDataService = binance.getMarketDataService();
		Ticker ticker = marketDataService.getTicker(currencyPair);

		System.out.println(ticker.toString());
		response.setStatus(HttpStatus.OK);
		response.setData(ticker);

		return response;
	}

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception {
		Thread.sleep(1000); // simulated delay
		return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");

//		JalgoResponse response = new JalgoResponse();
//		CurrencyPair currencyPair = new CurrencyPair(symbol.getBaseSymbol(), symbol.getCounterSymbol());
//		// Connect to the Exchange WebSocket API. Here we use a blocking wait.
//		StreamingExchange binanceStream  = StreamingExchangeFactory.INSTANCE.createExchange(BinanceStreamingExchange.class);
//		binanceStream.connect().blockingAwait();
//
//		// Subscribe to live trades update.
//		// @formatter:off
//		tickerSubscription = binanceStream
//				.getStreamingMarketDataService()
//				.getTicker(currencyPair)
//				.subscribe(
//					ticker ->  {
//						response.setData(ticker);
//					},
//					throwable -> {
//						
//						response.setError(throwable.getMessage());
//					} 
//				);
//		// @formatter:on
//		return response;
	}
}
