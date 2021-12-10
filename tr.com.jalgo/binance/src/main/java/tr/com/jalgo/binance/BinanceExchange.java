package tr.com.jalgo.binance;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.websocket.Session;

import org.apache.commons.codec.binary.Hex;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import tr.com.jalgo.model.Candle;
import tr.com.jalgo.model.Exchange;
import tr.com.jalgo.model.IExchange;
import tr.com.jalgo.model.Symbol;
import tr.com.jalgo.model.exceptions.ExchangeException;
import tr.com.jalgo.model.exchange.ApiResponse;
import tr.com.jalgo.model.strategies.Strategy;
import tr.com.jalgo.model.types.HttpMethodType;
import tr.com.jalgo.model.types.IntervalType;
import tr.com.jalgo.model.types.StatusType;
import tr.com.jalgo.model.types.StrategyResultType;
import tr.com.jalgo.model.ws.MessageHandler;
import tr.com.jalgo.model.ws.WebSocketClient;

@SuppressWarnings("serial")
public class BinanceExchange extends Exchange implements IExchange   {

	private final static String LIVE_API_URL = "https://api.binance.com";
	private final static String TESTNET_API_URL = "https://testnet.binance.vision";
	private final static String WEBSOCKET_URL = "wss://stream.binance.com:9443/ws/";

	private WebSocketClient wsClient = null;
	//private Session session = null;

	public BinanceExchange(String apiKey, String secretKey, boolean isTestNet) {
		super(apiKey, secretKey, isTestNet ? TESTNET_API_URL : LIVE_API_URL, WEBSOCKET_URL);

	}

	private String generateSignature(String queryStringParams, String bodyParams)
			throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
		String allParams = queryStringParams + bodyParams;
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");

		SecretKeySpec secret_key = new SecretKeySpec(allParams.getBytes("UTF-8"), "HmacSHA256");
		sha256_HMAC.init(secret_key);
		byte[] hash = sha256_HMAC.doFinal();
		return new String(Hex.encodeHex(hash));
	}

	@Override
	public ApiResponse ping() throws ExchangeException {
		var requestUrl = "/api/v3/ping";
		return sendRequest(requestUrl, HttpMethodType.GET, null, null, null, null);

	}

	@Override
	public ApiResponse time() throws ExchangeException {
		var requestUrl = "/api/v3/time";
		return sendRequest(requestUrl, HttpMethodType.GET, null, null, null, null);

	}

	@Override
	public ApiResponse exchangeInfo(String[] symbols) throws ExchangeException {
		var requestUrl = "/api/v3/exchangeInfo";
		Map<String, Object> requestParams = null;
		if (symbols != null) {
			requestParams = new HashMap<String, Object>();
			requestParams.put("symbols", symbols);
		}

		return sendRequest(requestUrl, HttpMethodType.GET, requestParams, null, null, null);

	}

	@Override
	public ApiResponse dept(Symbol currency, int limit) throws ExchangeException {
		checkCurrency(currency);
		return sendRequest("/api/v3/depth", HttpMethodType.GET, getParameters(currency, limit), null, null, null);

	}

	@Override
	public ApiResponse trades(Symbol currency, int limit) throws ExchangeException {
		checkCurrency(currency);
		return sendRequest("/api/v3/trades", HttpMethodType.GET, getParameters(currency, limit), null, null, null);
	}

	@Override
	public ApiResponse historicalTrades(Symbol currency, int limit, long formId) throws ExchangeException {
		checkCurrency(currency);
		return sendRequest("/api/v3/trades", HttpMethodType.GET, getParameters(currency, limit, formId), null, null, null);
	}

	@Override
	public ApiResponse aggregateTrades(Symbol currency, int limit, Date startDate, Date endDate, long formId)
			throws ExchangeException {
		checkCurrency(currency);
		return sendRequest("/api/v3/aggTrades", HttpMethodType.GET,
				getParameters(currency, limit, startDate, endDate, formId), null, null, null);
	}

	@Override
	public ApiResponse klines(Symbol currency, IntervalType interval, Date startDate, Date endDate, int limit)
			throws ExchangeException {
		checkCurrency(currency);
		checkInterval(interval);
		return sendRequest("/api/v3/klines", HttpMethodType.GET,
				getParameters(currency, interval, startDate, endDate, limit), null, null, null);
	}

	@Override
	public void trade(Symbol symbol, IntervalType interval, List<Strategy> strategies) throws ExchangeException {

		if (symbol == null)
			throw new ExchangeException("Please specify a currency!");

		String path = symbol.getBaseSymbol().toLowerCase() + symbol.getCounterSymbol().toLowerCase() + "@kline_"
				+ interval.getValue();

		wsClient = new WebSocketClient(this.getWsUrl() + path);

		wsClient.addMessageHandler(new MessageHandler() {
			@Override
			public void handleMessage(String message) {
				System.out.println(message);
				ObjectMapper mapper = new ObjectMapper();
				Candle candle = new Candle();
				ExecutorService executorService = null;
				try {
					JsonNode kNode = mapper.readTree(message).get("k");

//					ohlc.setStartTime(kNode.get("t").asLong());
//					ohlc.setCloseTime(kNode.get("T").asLong());
//					ohlc.setSymbol(kNode.get("s").asText());
//					ohlc.setInterval(kNode.get("i").asText());
					
					candle.setOpen(kNode.get("o").asDouble());
					candle.setHigh(kNode.get("h").asDouble());
					candle.setLow(kNode.get("l").asDouble());
					candle.setClose(kNode.get("c").asDouble());
					candle.setVolume(kNode.get("v").asDouble());

					List<Callable<StrategyResultType>> callables = new ArrayList<Callable<StrategyResultType>>();
					strategies.forEach(s -> {
						Callable<StrategyResultType> callable = new Callable<StrategyResultType>() {

							@Override
							public StrategyResultType call() throws Exception {

								return s.test(candle, interval);
							}
						};
						callables.add(callable);
					});

					executorService = Executors.newCachedThreadPool();
					List<Future<StrategyResultType>> strategyResults = executorService.invokeAll(callables);

					
					System.out.println("----------Results------------");
					strategyResults.forEach(sr -> {
						if (sr.isDone())
							try {
								System.out.println(sr.get().getValue());
							} catch (InterruptedException | ExecutionException e) {
								throw new ExchangeException(e.getMessage());
							}

					});
					System.out.println("--------------------------------");

				} catch (InterruptedException e) {
					throw new ExchangeException(e.getMessage());
				} catch (JsonProcessingException e) {
					throw new ExchangeException(e.getMessage());
				} finally {
					if (executorService != null)
						executorService.shutdown();
				}
			}

			@Override
			public void handleOnOpen(Session session) throws ExchangeException {
				startStream();
			}

			@Override
			public void handleKeepAlive() throws ExchangeException {
				keepAlive();
			}

		});

	}

//	@Override
//	public ApiResponse unSubscribe(List<Currency> currencies) throws ExchangeException {
//		wsClient.onClose(wsClient.getUserSession(), null);
//		return null;
//	}

	@Override
	public ApiResponse startStream() throws ExchangeException {

		ApiResponse apiResponse = sendRequest("/api/v3/userDataStream", HttpMethodType.POST, null, null, Optional.of(true),
				Optional.of(false));

		if (apiResponse.getStatus() == StatusType.OK)
			this.getAccount().setListenKey(apiResponse.getData().toString());

		return apiResponse;

	}

	@Override
	public void keepAlive() throws ExchangeException {
		String listenKey = this.getAccount().getListenKey();
		sendRequest("/api/v3/userDataStream", HttpMethodType.PUT, getParameters(listenKey), null, null, null);
	}

	private void checkCurrency(Symbol currency) throws ExchangeException {
		if (currency == null)
			throw new ExchangeException("Please specify symbol");
	}

	private void checkInterval(IntervalType interval) throws ExchangeException {
		if (interval == null)
			throw new ExchangeException("Please specify interval");
	}

	private Map<String, Object> getParameters(Object... parameters) {

		Map<String, Object> requestParams = new HashMap<String, Object>();

		if (parameters == null)
			return requestParams;

		for (Object parameter : parameters) {
			Class<? extends Object> clazz = parameter.getClass();

			if (clazz.equals(Symbol.class))
				requestParams.put("symbol",
						((Symbol) parameter).getBaseSymbol() + ((Symbol) parameter).getCounterSymbol());

			else if (clazz.equals(Date.class) && parameter.toString() == "startDate")
				requestParams.put("startTime", ((Date) parameter).getTime());

			else if (clazz.equals(Date.class) && parameter.toString() == "endDate")
				requestParams.put("endTime", ((Date) parameter).getTime());

			else if (clazz.equals(IntervalType.class) && parameter.toString() == "interval")
				requestParams.put("interval", IntervalType.valueOf(parameter.toString()));

			else if (parameter.toString() == "formId")
				requestParams.put("formId", parameter);

			else if (parameter.toString() == "limit")
				requestParams.put("limit", parameter);

			else
				requestParams.put(parameter.toString(), parameter);

		}

		return requestParams;
	}

	private ApiResponse sendRequest(String path, HttpMethodType method, Map<String, Object> requestParams,
			Map<String, String> headerParams, Optional<Boolean> addApikeyToHeader, Optional<Boolean> secure) {

		ApiResponse apiResponse = new ApiResponse();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			var requestURL = this.getUrl() + path;
			StringBuilder queryStringParams = new StringBuilder();
			if (requestParams != null) {
				if (requestParams.size() > 0) {
					requestParams.forEach((key, value) -> {
						queryStringParams.append(key + "=" + value.toString());
					});
					requestURL += "?" + queryStringParams.toString();
					if (secure != null ? secure.isPresent() : false) {
						var signature = generateSignature(queryStringParams.toString(), "");
						requestURL += "&signature=" + signature;
					}
				}
			}

			HttpUriRequest httpUriRequest;
			if (method == HttpMethodType.POST)
				httpUriRequest = new HttpPost(requestURL);
			if (method == HttpMethodType.PUT)
				httpUriRequest = new HttpPut(requestURL);
			else
				httpUriRequest = new HttpGet(requestURL);

			if (headerParams != null) {
				List<NameValuePair> header = new ArrayList<NameValuePair>();

				if (headerParams.size() > 0)
					headerParams.forEach((key, value) -> {
						header.add(new BasicNameValuePair(key, value));
						// httpUriRequest.addHeader(key, value);
					});

				httpUriRequest.setHeaders((Header[]) header.toArray());
			}

			if (addApikeyToHeader != null ? addApikeyToHeader.isPresent() : false)
				httpUriRequest.addHeader("X-MBX-APIKEY", this.getAccount().getApiKey());

			CloseableHttpResponse response = httpClient.execute(httpUriRequest);
			apiResponse.setCode(response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				apiResponse.setError(response.getStatusLine().getReasonPhrase());
			} else {
				apiResponse.setInfo(response.getStatusLine().getReasonPhrase());
			}

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// return it as a String
				apiResponse.setData(EntityUtils.toString(entity));
			}
			response.close();

		} catch (Exception ex) {
			apiResponse.setError(ex.getMessage());

		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				apiResponse.setError(e.getMessage());
			}
		}
		return apiResponse;
	}

}
