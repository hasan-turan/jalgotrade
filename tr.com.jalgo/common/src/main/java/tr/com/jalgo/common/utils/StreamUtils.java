package tr.com.jalgo.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamUtils {

	public static String getAsString(InputStream stream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder stringBuilder = new StringBuilder();
		while (reader.ready()) {
			stringBuilder.append(reader.readLine());
			stringBuilder.append(System.lineSeparator());
		}
		return stringBuilder.toString();
	}
}
