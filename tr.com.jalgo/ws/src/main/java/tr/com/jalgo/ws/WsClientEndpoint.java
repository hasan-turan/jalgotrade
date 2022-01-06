package tr.com.jalgo.ws;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import lombok.Getter;
import tr.com.jalgo.model.exceptions.ExchangeException;

@ClientEndpoint
public class WsClientEndpoint {

//	private ClientEndpointConfig wsConfig;
//	private Configurator configurator;

	@Getter
	private Session session = null;

	private MessageHandler messageHandler;

	private WebSocketContainer container = null;

	@Getter
	private URI uri;

//	private Configurator createConfigurator(final Map<String, Map<String, String>> wsHeaders) {
//		return new Configurator() {
//			@Override
//			public void beforeRequest(Map<String, List<String>> headers) {
//				super.beforeRequest(headers);
//				if (wsHeaders == null)
//					return;
//
//				for (String header : wsHeaders.keySet()) {
//					Map<String, String> hmap = wsHeaders.get(header);
//					ArrayList<String> headerValue = new ArrayList<String>();
//					for (Entry<String, String> entry : hmap.entrySet()) {
//						StringBuilder nvp = new StringBuilder(entry.getKey());
//						nvp.append('=').append(entry.getValue());
//						headerValue.add(nvp.toString());
//					}
//					headers.put(header, headerValue);
//				}
//			}
//		};
//	}

//	public WsClientEndpoint(String url) throws URISyntaxException, DeploymentException, IOException {
//		 
//			this.uri = new URI(url);
//			this.container = ContainerProvider.getWebSocketContainer();
//			this.session = container.connectToServer(this, this.uri);
////			Map<String, Map<String, String>> wsHeaders = null;
////			this.configurator = createConfigurator(wsHeaders);
////			Builder builder = ClientEndpointConfig.Builder.create().configurator(this.configurator);
////			this.wsConfig = builder.build();
////			wsConfig.getUserProperties().put("params", new String[] { "btcusdt@aggTrade" });
//
//		 
//	}

	public WsClientEndpoint(String url, MessageHandler messageHandler)
			throws URISyntaxException, DeploymentException, IOException {

		this.uri = new URI(url);
		this.container = ContainerProvider.getWebSocketContainer();
		this.messageHandler = messageHandler;
		this.session = container.connectToServer(this, this.uri);

//		Map<String, Map<String, String>> wsHeaders = null;
//		this.configurator = createConfigurator(wsHeaders);
//		Builder builder = ClientEndpointConfig.Builder.create().configurator(this.configurator);
//		this.wsConfig = builder.build();
//		wsConfig.getUserProperties().put("params", new String[] { "btcusdt@aggTrade" });

	}

	/**
	 * Callback hook for Connection open events.
	 *
	 * @param session the userSession which is opened.
	 */
	@OnOpen
	public void onOpen(Session session) throws ExchangeException {
		System.out.println("opening websocket");
		this.session = session;
		if (this.messageHandler != null) {
			this.messageHandler.handleOnOpen(session);

			new Timer().scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					try {
						messageHandler.handleKeepAlive();
					} catch (ExchangeException e) {
						throw new ExchangeException(e.getMessage());
					}

				}
			}, 0, 30 * 60 * 1000); // send keep alive request after every 30 minutes

		}
	}

	/**
	 * Callback hook for Connection close events.
	 *
	 * @param session the userSession which is getting closed.
	 * @param reason  the reason for connection close
	 */
	@OnClose
	public void onClose(Session session, CloseReason reason) {
		System.out.println("closing websocket. Reason: " + reason.getReasonPhrase());
		this.session = null;
	}

	/**
	 * Callback hook for Message Events. This method will be invoked when a client
	 * send a message.
	 *
	 * @param message The text message
	 */
	@OnMessage
	public void onMessage(String message) {
		if (this.messageHandler != null) {
			this.messageHandler.handleMessage(message);
		}
	}

	/**
	 * register message handler
	 *
	 * @param msgHandler
	 */
	public void addMessageHandler(MessageHandler msgHandler) {
		this.messageHandler = msgHandler;
	}

	/**
	 * Send a message.
	 *
	 * @param message
	 */
	public void sendMessage(String message) {
		this.session.getAsyncRemote().sendText(message);
	}

}