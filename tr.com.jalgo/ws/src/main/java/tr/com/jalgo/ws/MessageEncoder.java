package tr.com.jalgo.ws;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageEncoder implements Encoder.Text<Message> {
	ObjectMapper objectMapper;

	@Override
	public void init(EndpointConfig config) {
		this.objectMapper = new ObjectMapper();
	}

	@Override
	public void destroy() {
		this.objectMapper = null;
	}

	@Override
	public String encode(Message object) throws EncodeException {
		try {
			return this.objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
