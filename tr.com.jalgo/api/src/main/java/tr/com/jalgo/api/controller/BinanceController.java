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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import info.bitrich.xchangestream.binance.BinanceStreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
 
import io.reactivex.disposables.Disposable;
import tr.com.jalgo.api.JalgoResponse;

//https://www.baeldung.com/rest-vs-websockets
//https://spring.io/guides/gs/messaging-stomp-websocket/

@RestController
@RequestMapping("/binance")
public class BinanceController {
	Exchange binance = null;
	 
	Disposable tickerSubscription = null;

	public BinanceController() {
		binance = ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class);
		
	}

	@RequestMapping(value = "/ticker", method = RequestMethod.GET, produces = "application/json")
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

	@MessageMapping("/message")
    @SendTo("/binance/stream")
	public JalgoResponse streamConnect(@RequestParam String baseSymbol, @RequestParam String counterSymbol)
			throws IOException, InterruptedException {
		JalgoResponse response = new JalgoResponse();

		CurrencyPair currencyPair = new CurrencyPair(baseSymbol, counterSymbol);
		// Connect to the Exchange WebSocket API. Here we use a blocking wait.
		StreamingExchange binanceStream  = StreamingExchangeFactory.INSTANCE.createExchange(BinanceStreamingExchange.class);
		binanceStream.connect().blockingAwait();

		// Subscribe to live trades update.
		tickerSubscription = binanceStream.getStreamingMarketDataService().getTicker(currencyPair).subscribe(
				ticker -> System.out.println(ticker.toString()),
				throwable -> System.out.println("Error in trade subscription" + throwable));

		return response;
	}

	 

}