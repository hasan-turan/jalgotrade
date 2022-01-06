package tr.com.jalgo.binance.ws;
//package tr.com.jalgo.binance.ws;
//
//import java.io.IOException;
//
//import javax.websocket.EncodeException;
//import javax.websocket.EndpointConfig;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//
//import org.springframework.stereotype.Component;
//
//import tr.com.jalgo.model.types.ExchangeAccountType;
//import tr.com.jalgo.ws.MessageDecoder;
//import tr.com.jalgo.ws.MessageEncoder;
//
//@Component
////Generates a ws url 
////ws://127.0.0.1:8080/{contextpath}/binance/kline/{parity}/{interval}/{environment} -> ws://127.0.0.1:8080/jalgo/binance/kline/btcusdt/4h/live
//@ServerEndpoint(value = "/binance/kline/{parity}/{interval}/{environment}", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
//public class BinanceKlineWsServerEndpoint extends BinanceWsServerEndpoint    {
//
//	/**
//	 * All parities(symbol for binance) must be lowercase for streams
//	 */
//
//	//@formatter:off
//	@OnOpen
//	public void onOpen(Session session, EndpointConfig config, 
//			@PathParam("parity") String parity,
//			@PathParam("interval") String interval, 
//			@PathParam("environment") String environment)
//			throws IOException, EncodeException {
//		//@formatter:on
//
//		ExchangeAccountType accountType = environment.equals("test") ? ExchangeAccountType.TEST
//				: ExchangeAccountType.LIVE;
//
//		this.initializeExchange(accountType);
//
//		this.onOpen(session, this.getExchange().getWsUrl(accountType) + parity.toLowerCase() + "@kline_" + interval);
//	}
//
// 
// 
//
//}
