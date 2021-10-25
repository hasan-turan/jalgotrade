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

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

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

import tr.com.jalgo.model.enums.EnumHttpMethod;
import tr.com.jalgo.model.exchange.ApiResponse;
import tr.com.jalgo.model.exchange.CurrencyPair;
import tr.com.jalgo.model.exchange.EnumInterval;
import tr.com.jalgo.model.exchange.Exchange;
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
	public ApiResponse ping() {
		var requestUrl = this.getUrl() + "/api/v3/userDataStream";
		var listenKey = getListenKey();
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("listenKey", listenKey.getData());
		return sendRequest(requestUrl, EnumHttpMethod.PUT, requestParams, null, null, null);

	}

	@Override
	public ApiResponse ticker(CurrencyPair pair) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponse klines(CurrencyPair symbolPair, Date startDate, Date endDate, EnumInterval interval) {
		// TODO Auto-generated method stub
		return null;
	}

	private ApiResponse sendRequest(String path, EnumHttpMethod method, Map<String, String> requestParams,
			Map<String, String> headerParams, Optional<Boolean> addApikeyToHeader, Optional<Boolean> secure) {

		ApiResponse apiResponse = new ApiResponse();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			var requestURL = this.getUrl() + path;
			StringBuilder queryStringParams = new StringBuilder();
			if (requestParams != null) {
				if (requestParams.size() > 0) {
					requestParams.forEach((key, value) -> {
						queryStringParams.append(key + "=" + value);
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

}
