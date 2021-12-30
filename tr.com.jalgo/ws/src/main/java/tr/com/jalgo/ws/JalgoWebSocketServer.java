package tr.com.jalgo.ws;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import tr.com.jalgo.model.exceptions.ExchangeException;
@ServerEndpoint(value = "/jalgo" , encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class JalgoWebSocketServer extends WebSocketServer {

	@Override
	public void onOpen(Session session, EndpointConfig config, String parity, String interval, String environment)
			throws IOException, EncodeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleOpen(Session session) throws ExchangeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleKeepAlive() throws ExchangeException {
		// TODO Auto-generated method stub
		
	}

}
