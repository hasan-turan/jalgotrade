package tr.com.jalgo.api.controller.binance;

import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import info.bitrich.xchangestream.binance.BinanceStreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import tr.com.jalgo.api.JalgoResponse;
import tr.com.jalgo.dto.Greeting;
import tr.com.jalgo.dto.HelloMessage;
import tr.com.jalgo.dto.SymbolPair;

@RestController
public class BinanceWsController {
	
	StreamingExchange binanceStream = null;
	Disposable tickerSubscription = null;
	
	public BinanceWsController() {
		binanceStream = StreamingExchangeFactory.INSTANCE.createExchange(BinanceStreamingExchange.class);
	}
	
	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception {
		Thread.sleep(1000); // simulated delay
		return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
	}

	@MessageMapping("/stream")
	@SendTo("/topic/streaming")
	public JalgoResponse stream(SymbolPair symbol) throws Exception {
		Thread.sleep(1000); // simulated delay

		JalgoResponse response = new JalgoResponse();
		CurrencyPair currencyPair = new CurrencyPair(symbol.getBaseSymbol(), symbol.getCounterSymbol());
		// Connect to the Exchange WebSocket API. Here we use a blocking wait.

		binanceStream.connect().blockingAwait();

		// Subscribe to live trades update.
		// @formatter:off
		tickerSubscription = binanceStream
				.getStreamingMarketDataService()
				.getTicker(currencyPair)
				.subscribe(
					ticker ->  {
						response.setData(ticker);
					},
					throwable -> {
						
						response.setError(throwable.getMessage());
					} 
				);
		
		// @formatter:on
		return response;

	}
}
