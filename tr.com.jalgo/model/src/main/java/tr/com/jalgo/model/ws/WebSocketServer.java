package tr.com.jalgo.model.ws;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

//import tr.com.jalgo.model.Message;
//https://www.baeldung.com/java-websockets

@ServerEndpoint(value = "/endpoint")
public class WebSocketServer {

	private Session session;
	private static Set<WebSocketServer> endPoints = new CopyOnWriteArraySet<>();
//	private static HashMap<String, String> symbols = new HashMap<>();

//	public WebSocketServer(String url) {
//		try {
//			URI endpointURI = new URI(url);
//			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
//			container.connectToServer(this, endpointURI);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

	@OnOpen
	public void open(Session session, EndpointConfig config )
			throws IOException, EncodeException {
		System.out.println("Yeni giriş: " + session.getId());

		this.session = session;
		endPoints.add(this);
		//symbols.put(session.getId(), symbol);

		session.getBasicRemote().sendText(session.getId().toString() + " connected!");
	}

	@OnMessage
	public void textMessage(Session session, String message ) throws IOException {
		System.out.println("String mesaj: " + message);
		session.getBasicRemote().sendText(message);
	}

	@OnMessage
	public void binaryMessage(Session session, ByteBuffer message )
			throws IOException {
		System.out.println("Binary message: " + message.toString());
		session.getBasicRemote().sendBinary(message);
	}

	@OnMessage
	public void pongMessage(Session session, PongMessage message ) {
		System.out.println("Pong message: " + message.getApplicationData().toString());
	}

	@OnError
	public void error(Session session, Throwable error) {
		System.err.println(session.getId());
		System.err.println(error);
	}

	@OnClose
	public void close(Session session, CloseReason closeReason) {
		System.out.println(session.getId());
		System.out.println(closeReason.getCloseCode().getCode());
		System.out.println(closeReason.getReasonPhrase());
	}

//	private static void broadcast(Message message) throws IOException, EncodeException {
//
//		endPoints.forEach(endpoint -> {
//			synchronized (endpoint) {
//				try {
//					endpoint.session.getBasicRemote().sendObject(message);
//				} catch (IOException | EncodeException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

}
