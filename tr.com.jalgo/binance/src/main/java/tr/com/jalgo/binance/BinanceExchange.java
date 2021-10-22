package tr.com.jalgo.binance;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import tr.com.jalgo.model.exchange.EnumInterval;
import tr.com.jalgo.model.exchange.Exchange;
import tr.com.jalgo.model.exchange.IExchange;
import tr.com.jalgo.model.exchange.Pair;
import tr.com.jalgo.model.exchange.ApiResponse;
import java.util.Map;
import java.util.Optional;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class BinanceExchange extends Exchange implements IExchange {

	private final String url = "https://api.binance.com";

	protected BinanceExchange(String privateKey, String secretKey) {
		super(privateKey, secretKey);
		// TODO Auto-generated constructor stub
	}

	private ApiResponse getListenKey() throws IOException {

		return makeCall("/api/v3/userDataStream", "POST", null, null, Optional.of(true), Optional.of(false));
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
	public ApiResponse ping() throws IOException {
		var requestUrl = this.url + "/api/v3/userDataStream";
		var listenKey = getListenKey();
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("listenKey", listenKey.getData());

		return makeCall(requestUrl, "PUT", requestParams, null, null, null);

	}

	@Override
	public ApiResponse ticker(Pair pair) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponse klines(Pair symbolPair, Date startDate, Date endDate, EnumInterval interval) {
		// TODO Auto-generated method stub
		return null;
	}

	private ApiResponse makeCall(String path, String method, Map<String, String> requestParams,
			Map<String, String> headerParams, Optional<Boolean> addApikeyToHeader, Optional<Boolean> secure)
			throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		ApiResponse apiResponse = new ApiResponse();
		try {
			var requestURL = this.url + path;
			StringBuilder queryStringParams = new StringBuilder();
			if (requestParams != null) {
				if (requestParams.size() > 0) {
					requestParams.forEach((key, value) -> {
						queryStringParams.append(key + "=" + value);
					});
					requestURL += "?" + queryStringParams.toString();
					if (secure.isPresent()) {
						var signature = generateSignature(queryStringParams.toString(), "");
						requestURL += "&signature=" + signature;
					}
				}
			}

			HttpGet request = new HttpGet(requestURL);
			if (headerParams != null) {
				if (headerParams.size() > 0)
					headerParams.forEach((key, value) -> {
						request.addHeader(key, value);
					});

				if (addApikeyToHeader.isPresent())
					request.addHeader("X-MBX-APIKEY", this.getApiKey());
			}

			CloseableHttpResponse response = httpClient.execute(request);
			apiResponse.setStatusCode(response.getStatusLine().getStatusCode());
			apiResponse.setStatus(response.getStatusLine().getReasonPhrase());

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// return it as a String
				apiResponse.setData(EntityUtils.toString(entity));
			}
			response.close();

		} catch (Exception ex) {

		} finally {
			httpClient.close();
		}
		return apiResponse;
	}

}
