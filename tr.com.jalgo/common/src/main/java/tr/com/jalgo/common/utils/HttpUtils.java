package tr.com.jalgo.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import tr.com.jalgo.model.exceptions.HttpRequestException;

public class HttpUtils {

	public static String sendRequest(String url) {
		String result="";

		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			HttpGet request = new HttpGet(url);
			try (CloseableHttpResponse response = client.execute(request)) {
				if (response.getStatusLine().getStatusCode() != 200)
					throw new HttpRequestException(response.getStatusLine().getReasonPhrase());

				HttpEntity entity = response.getEntity();
				if (entity != null) {
					 result=StreamUtils.getAsString(entity.getContent());
				}

			}

		} catch (UnsupportedOperationException | IOException e) {
			throw new HttpRequestException(e.getMessage());
		}
		return result;

	}

	public static String decode(String content, String charsetStr) {
		if (StringUtils.isBlank(content)) return content;
		String encodeContnt = null;
		try {
			encodeContnt = URLDecoder.decode(content, charsetStr);
		} catch (UnsupportedEncodingException  e) {
			throw new RuntimeException(StringUtils.format("Unsupported encoding: [{}]", charsetStr), e);
		}
		return encodeContnt;
	}

}
