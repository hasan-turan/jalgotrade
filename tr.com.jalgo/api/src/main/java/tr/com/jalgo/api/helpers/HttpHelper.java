package tr.com.jalgo.api.helpers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import tr.com.jalgo.api.types.EnumHttpAction;

public class HttpHelper {

	private String apiKey;
	private String secretKey;

	private HttpURLConnection connection;

	public HttpHelper(  String apiKey, String secretKey) throws IOException {

		this.apiKey = apiKey;
		this.secretKey = secretKey;
		
		
	}

	public String makeApiCall(String uri, EnumHttpAction httpAction, String jsonInputString) throws IOException {

		
		URL url = new URL(uri);
		connection = (HttpURLConnection) url.openConnection();
		
		connection.setRequestMethod(httpAction.getValue());
		connection.setRequestProperty("Content-Length", Integer.toString(jsonInputString.getBytes().length));
		connection.setRequestProperty("Content-Type", "application/json; utf-8");
		connection.setRequestProperty("Accept", "application/json");
		connection.setDoInput(true);
		// Ensure the Connection Will Be Used to Send Content
		// Otherwise, we won't be able to write content to the connection output stream:
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		
		Map<String, String> headers = new HashMap<>();
		headers.put("X-MBX-APIKEY",apiKey);
		
		for (String headerKey : headers.keySet()) {
			connection.setRequestProperty(headerKey, headers.get(headerKey));
		}
 
		// Send request
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(jsonInputString);
		wr.close();

		// Get Response
		InputStream is = connection.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
		String line;
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		rd.close();
		return response.toString();

	}

}
