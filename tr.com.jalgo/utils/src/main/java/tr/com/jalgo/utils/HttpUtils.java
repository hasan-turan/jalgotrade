package tr.com.jalgo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import tr.com.jalgo.model.exceptions.HttpRequestException;

public class HttpUtils {

	public static String getAsString(String url) {
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

	public static JSONObject getAsJson(String url) {
		return new JSONObject(getAsString(url));
	}

}
