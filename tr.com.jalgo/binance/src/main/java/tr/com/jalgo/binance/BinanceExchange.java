package tr.com.jalgo.binance;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.websocket.DeploymentException;
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

import tr.com.jalgo.binance.helpers.BinanceExchangeInfoHelper;
import tr.com.jalgo.binance.models.Symbol;
import tr.com.jalgo.model.ApiResponse;
import tr.com.jalgo.model.Candle;
import tr.com.jalgo.model.IExchange;
import tr.com.jalgo.model.Parity;
import tr.com.jalgo.model.RemoteExchange;
import tr.com.jalgo.model.exceptions.ExchangeException;
import tr.com.jalgo.model.strategies.Strategy;
import tr.com.jalgo.model.types.EnvironmentType;
import tr.com.jalgo.model.types.HttpMethodType;
import tr.com.jalgo.model.types.IntervalType;
import tr.com.jalgo.model.types.StatusType;
import tr.com.jalgo.model.types.StrategyResultType;
import tr.com.jalgo.ws.MessageHandler;
import tr.com.jalgo.ws.WsClientEndpoint;
import tr.com.jalgo.ws.managers.ClientEndpointManager;

@SuppressWarnings("serial")

public class BinanceExchange extends RemoteExchange implements IExchange {

	private final static String LIVE_API_URL = "https://api.binance.com";
	private final static String TESTNET_API_URL = "https://testnet.binance.vision";

	private final static String LIVE_WEBSOCKET_URL = "wss://stream.binance.com:9443/ws/";
	private final static String TESTNET_WEBSOCKET_URL = "wss://testnet.binance.vision/ws";

	public BinanceExchange(String apiKey, String secretKey, EnvironmentType type) {
		super(apiKey, secretKey, type == EnvironmentType.LIVE ? LIVE_API_URL : TESTNET_API_URL,
				type == EnvironmentType.LIVE ? LIVE_WEBSOCKET_URL : TESTNET_WEBSOCKET_URL, type);

	}

	private WsClientEndpoint wsClient = null;
	// private Session session = null;

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
		return sendRequest(requestUrl, HttpMethodType.GET);

	}

	@Override
	public ApiResponse getTime() throws ExchangeException {
		var requestUrl = "/api/v3/time";
		return sendRequest(requestUrl, HttpMethodType.GET);

	}

	/*
	 * "https://api.binance.com/api/v3/exchangeInfo?symbols=BTCUSDT,ETHUSDT"
	 */
	@Override
	public ApiResponse getExchangeInfo(List<Parity> parities) throws ExchangeException {
		var requestUrl = "/api/v3/exchangeInfo";
		SimpleEntry<String, Object> requestParams = null;
		if (parities != null) {
			requestParams = new SimpleEntry<String, Object>("symbols",
					String.join(",", parities.stream().map(parity -> getParity(parity)).toArray(String[]::new)));
			return sendRequest(requestUrl, HttpMethodType.GET, requestParams);
		}

		return sendRequest(requestUrl, HttpMethodType.GET);

	}

	@Override
	public ApiResponse getDept(Parity parity, int limit) throws ExchangeException {
		checkParity(parity);

		return sendRequest("/api/v3/depth", HttpMethodType.GET,
				new SimpleEntry<String, Object>("symbol", getParity(parity)),
				new SimpleEntry<String, Object>("limit", limit));

	}

	@Override
	public ApiResponse getTrades(Parity parity, int limit) throws ExchangeException {
		checkParity(parity);

		return sendRequest("/api/v3/trades", HttpMethodType.GET,
				new SimpleEntry<String, Object>("symbol", getParity(parity)),
				new SimpleEntry<String, Object>("limit", limit));
	}

	@Override
	public ApiResponse getHistoricalTrades(Parity parity, int limit, long formId) throws ExchangeException {
		checkParity(parity);

		return sendRequest("/api/v3/trades", HttpMethodType.GET,
				new SimpleEntry<String, Object>("symbol", getParity(parity)),
				new SimpleEntry<String, Object>("limit", limit), new SimpleEntry<String, Object>("formId", formId));
	}

	@Override
	public ApiResponse getAggregateTrades(Parity parity, int limit, Date startDate, Date endDate, long formId)
			throws ExchangeException {
		checkParity(parity);

		return sendRequest("/api/v3/aggTrades", HttpMethodType.GET,
				new SimpleEntry<String, Object>("symbol", getParity(parity)),
				new SimpleEntry<String, Object>("limit", limit),
				new SimpleEntry<String, Object>("startTime", startDate.getTime()),
				new SimpleEntry<String, Object>("endTime", endDate.getTime()),
				new SimpleEntry<String, Object>("formId", formId));
	}

	/**
	 * Default value of limit is 500
	 */
	@Override
	public ApiResponse getKlines(Parity parity, IntervalType interval, Date startDate, Date endDate, int limit)
			throws ExchangeException {
		checkParity(parity);
		checkInterval(interval);

		return sendRequest("/api/v3/klines", HttpMethodType.GET,
				new SimpleEntry<String, Object>("symbol", getParity(parity)),
				new SimpleEntry<String, Object>("limit", limit),
				new SimpleEntry<String, Object>("interval", interval.getValue()),
				new SimpleEntry<String, Object>("startTime", startDate.getTime()),
				new SimpleEntry<String, Object>("endTime", endDate.getTime()));
	}

	@Override
	public void trade(Parity parity, IntervalType interval, List<Strategy> strategies) throws ExchangeException {

		if (parity == null)
			throw new ExchangeException("Please specify a parity!");

		String path = parity.getBaseAsset().getSymbol().toLowerCase()
				+ parity.getCounterAsset().getSymbol().toLowerCase() + "@kline_" + interval.getValue();

		try {
			String wsUrl = this.getAccount().getType() == EnvironmentType.TEST ? this.getTestWsUrl()
					: this.getLiveWsUrl();

			wsClient = new WsClientEndpoint(wsUrl + path, new MessageHandler() {
				@Override
				public void handleMessage(String message) {
					System.out.println(message);

					ExecutorService executorService = null;
					try {
						// all exchanges returns data in different format
						// therefore this part can not be a common method (or think about it, maybe it
						// can)
						Candle candle = convertJsonStringToCandle(message);

						List<Callable<StrategyResultType>> strategyCallables = new ArrayList<Callable<StrategyResultType>>();

						// iterate strategies and generate a callable (thread) for each strategy
						strategies.forEach(s -> {
							Callable<StrategyResultType> strategyCallable = new Callable<StrategyResultType>() {

								@Override
								public StrategyResultType call() throws Exception {

									return s.test(candle, interval);
								}
							};
							strategyCallables.add(strategyCallable);
						});

						// generate a thread pool
						executorService = Executors.newCachedThreadPool();

						// invoke(run/test) all strategies
						List<Future<StrategyResultType>> strategyResults = executorService.invokeAll(strategyCallables);

						System.out.println("----------Results------------");

						// iterate all strategy results
						strategyResults.forEach(strategyResult -> {

							// if strategy test/run is done
							if (strategyResult.isDone())
								try {

									// get strategy test/run result
									System.out.println(strategyResult.get().getValue());
								} catch (InterruptedException | ExecutionException e) {
									throw new ExchangeException(e.getMessage());
								}

						});
						System.out.println("--------------------------------");

					} catch (InterruptedException e) {
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

		} catch (URISyntaxException | DeploymentException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

//	@Override
//	public ApiResponse unSubscribe(List<parity> currencies) throws ExchangeException {
//		wsClient.onClose(wsClient.getUserSession(), null);
//		return null;
//	}

	@Override
	public ApiResponse startStream() throws ExchangeException {

		ApiResponse ApiResponse = sendRequest("/api/v3/userDataStream", HttpMethodType.POST, null, Optional.of(true),
				Optional.of(false));

		if (ApiResponse.getStatus() == StatusType.OK)
			this.getAccount().setListenKey(ApiResponse.getData().toString());

		return ApiResponse;

	}

	@Override
	public void keepAlive() throws ExchangeException {
		String listenKey = this.getAccount().getListenKey();

		sendRequest("/api/v3/userDataStream", HttpMethodType.PUT,
				new SimpleEntry<String, Object>("listenKey", listenKey));
	}

	private void checkParity(Parity parity) throws ExchangeException {
		if (parity == null)
			throw new ExchangeException("Please specify parity");
	}

	private void checkInterval(IntervalType interval) throws ExchangeException {
		if (interval == null)
			throw new ExchangeException("Please specify interval");
	}

	private String getParity(Parity parity) {
		return parity.getBaseAsset().getSymbol().toUpperCase() + parity.getCounterAsset().getSymbol().toUpperCase();
	}

	@SafeVarargs
	private ApiResponse sendRequest(String path, HttpMethodType method, SimpleEntry<String, Object>... requestParams) {
		return sendRequest(path, method, null, null, null, requestParams);
	}

	@SafeVarargs
	private ApiResponse sendRequest(String path, HttpMethodType method, Map<String, String> headerParams,
			Optional<Boolean> addApikeyToHeader, Optional<Boolean> secure,
			SimpleEntry<String, Object>... requestParams) {

		ApiResponse apiResponse = new ApiResponse();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			var requestURL = this.getLiveUrl() + path;
			String parameters = null;
			if (requestParams != null && requestParams.length > 0) {

				// converting all parameters to an array like-> [key1=value1, key2=value2,....]
				String[] parametersArray = Arrays.asList(requestParams).stream()
						.map(param -> param.getKey() + "=" + param.getValue().toString()).toArray(String[]::new);

				// converting parameter array to key1=value1&key2=value2... string
				parameters = String.join("&", parametersArray);

				requestURL += "?" + parameters;
				if (secure != null ? secure.isPresent() : false) {
					var signature = generateSignature(parameters, "");
					requestURL += "&signature=" + signature;
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

			CloseableHttpResponse httpResponse = httpClient.execute(httpUriRequest);

			if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				apiResponse.setError(httpResponse.getStatusLine().getReasonPhrase());
			} else {
				
				apiResponse.setStatus(StatusType.OK);
				
				if (!httpResponse.getStatusLine().getReasonPhrase().equals("OK"))
					apiResponse.setInfo(httpResponse.getStatusLine().getReasonPhrase());
				
				
			}

			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				// return it as a String
				apiResponse.setData(EntityUtils.toString(entity));
			}
			httpResponse.close();

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

	@Override
	public Candle convertJsonStringToCandle(String message) throws ExchangeException {

		Candle candle = null;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode kNode;
		try {
			kNode = mapper.readTree(message).get("k");
			candle = new Candle();
			candle.setOpen(kNode.get("o").asDouble());
			candle.setHigh(kNode.get("h").asDouble());
			candle.setLow(kNode.get("l").asDouble());
			candle.setClose(kNode.get("c").asDouble());
			candle.setBaseAssetVolume(kNode.get("v").asDouble());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new ExchangeException(e.getMessage());
		}

		return candle;
	}

	@Override
	public void start(EnvironmentType environment) throws ExchangeException {

		ApiResponse apiResponse = this.getExchangeInfo(null);
		if (apiResponse.getStatus() == StatusType.OK) {

			BinanceExchangeInfoHelper binanceExchangeInfoHelper = new BinanceExchangeInfoHelper(
					apiResponse.getData().toString());

			for (Symbol symbol : binanceExchangeInfoHelper.getExchangeInfo().getSymbols()) {

				String parity = symbol.getBaseAsset().toLowerCase() + "" + symbol.getQuoteAsset().toLowerCase();
				String wsUrl = "ws://localhost:8080/binance/kline?token=123456&parity=" + parity
						+ "&interval=4h&environment=" + environment.getKey();

//				WsClientManager.createWebSocketClient(wsUrl, new MessageHandler() {
//
//					@Override
//					public void handleMessage(String message) {
//						System.out.println(message);
//					}
//
//					@Override
//					public void handleOnOpen(Session session) throws ExchangeException {
//
//					}
//
//					@Override
//					public void handleKeepAlive() throws ExchangeException {
//					}
//
//				});

			}
		}
	}

}
