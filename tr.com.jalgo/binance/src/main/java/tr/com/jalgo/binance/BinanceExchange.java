package tr.com.jalgo.binance;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;
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

import tr.com.jalgo.model.enums.EnumHttpMethod;
import tr.com.jalgo.model.exchange.ApiResponse;
import tr.com.jalgo.model.exchange.Currency;
import tr.com.jalgo.model.exchange.EnumInterval;
import tr.com.jalgo.model.exchange.Exchange;
import tr.com.jalgo.model.exchange.ExchangeException;
import tr.com.jalgo.model.exchange.IExchange;

public class BinanceExchange extends Exchange implements IExchange {

	private final static String LIVE_API_URL = "https://api.binance.com";
	private final static String TESTNET_API_URL = "https://testnet.binance.vision";

	public BinanceExchange(String apiKey, String secretKey, boolean isTestNet) {
		super(apiKey, secretKey, isTestNet ? TESTNET_API_URL : LIVE_API_URL);

	}

	private ApiResponse getListenKey() {
		return sendRequest("/api/v3/userDataStream", EnumHttpMethod.POST, null, null, Optional.of(true),
				Optional.of(false));
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
		return sendRequest(requestUrl, EnumHttpMethod.GET, null, null, null, null);

	}

	@Override
	public ApiResponse time() throws ExchangeException {
		var requestUrl = "/api/v3/time";
		return sendRequest(requestUrl, EnumHttpMethod.GET, null, null, null, null);

	}

	@Override
	public ApiResponse exchangeInfo(String[] symbols) throws ExchangeException {
		var requestUrl = "/api/v3/exchangeInfo";
		Map<String, Object> requestParams = null;
		if (symbols != null) {
			requestParams = new HashMap<String, Object>();
			requestParams.put("symbols", symbols);
		}

		return sendRequest(requestUrl, EnumHttpMethod.GET, requestParams, null, null, null);

	}

	@Override
	public ApiResponse dept(Currency currency, int limit) throws ExchangeException {
		checkCurrency(currency);
		return sendRequest("/api/v3/depth", EnumHttpMethod.GET, getParameters(currency, limit), null, null, null);

	}

	@Override
	public ApiResponse trades(Currency currency, int limit) throws ExchangeException {
		checkCurrency(currency);
		return sendRequest("/api/v3/trades", EnumHttpMethod.GET, getParameters(currency, limit), null, null, null);
	}

	@Override
	public ApiResponse historicalTrades(Currency currency, int limit, long formId) throws ExchangeException {
		checkCurrency(currency);
		return sendRequest("/api/v3/trades", EnumHttpMethod.GET, getParameters(currency, limit, formId), null, null,
				null);
	}

	@Override
	public ApiResponse aggregateTrades(Currency currency, int limit, Date startDate, Date endDate, long formId)
			throws ExchangeException {
		checkCurrency(currency);
		return sendRequest("/api/v3/aggTrades", EnumHttpMethod.GET,
				getParameters(currency, limit, startDate, endDate, formId), null, null, null);
	}

	@Override
	public ApiResponse klines(Currency currency, EnumInterval interval, Date startDate, Date endDate, int limit)
			throws ExchangeException {
		checkCurrency(currency);
		checkInterval(interval);
		return sendRequest("/api/v3/klines", EnumHttpMethod.GET,
				getParameters(currency, interval, startDate, endDate, limit), null, null, null);
	}

	@Override
	public ApiResponse ticker(Currency pair) throws ExchangeException {
		// TODO Auto-generated method stub
		return null;
	}

	private ApiResponse sendRequest(String path, EnumHttpMethod method, Map<String, Object> requestParams,
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
			if (method == EnumHttpMethod.POST)
				httpUriRequest = new HttpPost(requestURL);
			if (method == EnumHttpMethod.PUT)
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
				httpUriRequest.addHeader("X-MBX-APIKEY", this.getApiKey());

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

	private void checkCurrency(Currency currency) throws ExchangeException {
		if (currency == null)
			throw new ExchangeException("Please specify symbol");
	}

	private void checkInterval(EnumInterval interval) throws ExchangeException {
		if (interval == null)
			throw new ExchangeException("Please specify interval");
	}
	
	private Map<String, Object> getParameters(Object... parameters) {

		Map<String, Object> requestParams = new HashMap<String, Object>();

		if (parameters == null)
			return requestParams;

		for (Object parameter : parameters) {
			Class<? extends Object> clazz = parameter.getClass();

			if (clazz.equals(Currency.class))
				requestParams.put("symbol",
						((Currency) parameter).getBaseSymbol() + ((Currency) parameter).getCounterSymbol());

			if (clazz.equals(Date.class) && parameter.toString() == "startDate")
				requestParams.put("startTime", ((Date) parameter).getTime());

			if (clazz.equals(Date.class) && parameter.toString() == "endDate")
				requestParams.put("endTime", ((Date) parameter).getTime());

			if (clazz.equals(EnumInterval.class) && parameter.toString() == "interval")
				requestParams.put("interval", EnumInterval.valueOf(parameter.toString()));

			if (parameter.toString() == "formId")
				requestParams.put("formId", parameter);

			if (parameter.toString() == "limit")
				requestParams.put("limit", parameter);

		}

		return requestParams;
	}
}
