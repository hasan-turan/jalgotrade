package tr.com.jalgo.binance.ws;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import tr.com.jalgo.binance.BinanceExchange;
import tr.com.jalgo.binance.BinanceProperties;
import tr.com.jalgo.common.utils.StringUtils;
import tr.com.jalgo.model.exceptions.ExchangeException;
import tr.com.jalgo.ws.MessageDecoder;
import tr.com.jalgo.ws.MessageEncoder;
import tr.com.jalgo.ws.WebSocketServer;

@Component
//Generates a ws url 
//ws://127.0.0.1:8080/{contextpath}/binance/kline/{parity}/{interval}/{environment} -> ws://127.0.0.1:8080/jalgo/binance/kline/btcusdt/4h/live
@ServerEndpoint(value = "/binance/kline/{parity}/{interval}/{environment}", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class BinanceWebSocketServer extends WebSocketServer {
	BinanceExchange binanceExchange;

	/**
	 * All parities(symbol for binance) must be lowercase for streams
	 */
	//@formatter:off
	@OnOpen
	public void onOpen(Session session, EndpointConfig config, 
			@PathParam("parity") String parity,
			@PathParam("interval") String interval, 
			@PathParam("environment") String environment)
			throws IOException, EncodeException {
		//@formatter:on
		boolean isTestNet = StringUtils.equals(environment, "test", false);

		String apiKey = isTestNet ? BinanceProperties.TEST_API_KEY : BinanceProperties.LIVE_API_KEY;
		String secretKey = isTestNet ? BinanceProperties.TEST_SECRET_KEY : BinanceProperties.LIVE_SECRET_KEY;

		binanceExchange = new BinanceExchange(apiKey, secretKey, isTestNet);
		this.onOpen(session, binanceExchange.getWsUrl() + parity.toLowerCase() + "@kline_" + interval);
	}

	@Override
	public void handleOpen(Session session) throws ExchangeException {
		if (binanceExchange != null)
			binanceExchange.startStream();
	}

	@Override
	public void handleKeepAlive() throws ExchangeException {
		if (binanceExchange != null)
			binanceExchange.keepAlive();
	}

}
