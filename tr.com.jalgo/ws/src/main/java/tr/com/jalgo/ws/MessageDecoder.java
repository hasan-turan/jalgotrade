package tr.com.jalgo.ws;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageDecoder implements Decoder.Text<Message> {
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
	public Message decode(String jsonString) throws DecodeException {

		try {
			return this.objectMapper.readValue(jsonString, Message.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean willDecode(String jsonString) {
		return (jsonString != null);
	}

}
