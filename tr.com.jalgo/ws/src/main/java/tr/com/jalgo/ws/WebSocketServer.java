package tr.com.jalgo.ws;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;

import lombok.Getter;
import tr.com.jalgo.model.exceptions.ExchangeException;

//@ServerEndpoint(value = "/", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public abstract class WebSocketServer {

	@Getter
	private SessionHandler sessionHandler;

	public WebSocketServer() {
		this.sessionHandler = new SessionHandler();
	}

	//@formatter:off
	@OnOpen
	public abstract void onOpen(Session session, EndpointConfig config, 
			@PathParam("parity") String parity,
			@PathParam("interval") String interval,
			@PathParam("environment")  String environment) throws IOException, EncodeException;
	//@formatter:on

	public abstract void handleOpen(Session session) throws ExchangeException;

	public abstract void handleKeepAlive() throws ExchangeException;

	public void onOpen(Session session, String websocketUrl) {

		WebSocketClient exchangeClient;
		try {
			exchangeClient = new WebSocketClient(websocketUrl);
			exchangeClient.addMessageHandler(new MessageHandler() {

				@Override
				public void handleMessage(String message) {
					sessionHandler.unicast(session, new Message(message));
				}

				@Override
				public void handleOnOpen(Session session) throws ExchangeException {
					handleOpen(session);
				}

				@Override
				public void handleKeepAlive() throws ExchangeException {
					handleKeepAlive();
				}

			});
		} catch (URISyntaxException | DeploymentException | IOException e) {
			sessionHandler.unicast(session, new Message(e.getMessage()));
		}

	}

	@OnMessage
	public void onMessage(Session session, String message) throws IOException {
		sessionHandler.unicast(session, new Message(message));
	}

	@OnError
	public void onError(Session session, Throwable error) {
		sessionHandler.unicast(session, new Message(error));
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		sessionHandler.removeSession(session);
	}

}
