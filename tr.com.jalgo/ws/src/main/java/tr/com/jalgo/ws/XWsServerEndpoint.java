package tr.com.jalgo.ws;
//package tr.com.jalgo.ws;
//
//import java.io.IOException;
//import java.net.URISyntaxException;
//
//import javax.websocket.CloseReason;
//import javax.websocket.DeploymentException;
//import javax.websocket.EndpointConfig;
//import javax.websocket.OnClose;
//import javax.websocket.OnError;
//import javax.websocket.OnMessage;
//import javax.websocket.Session;
//
//import lombok.Getter;
//import tr.com.jalgo.model.exceptions.ExchangeException;
//
//public abstract class WsServerEndpoint {
//
//	@Getter
//	private SessionHandler sessionHandler;
//
//	public WsServerEndpoint() {
//		this.sessionHandler = new SessionHandler();
//	}
//
//	public abstract void handleOpen(Session session) throws ExchangeException;
//
//	public abstract void handleKeepAlive() throws ExchangeException;
//
//	public void onOpen(Session session, String websocketUrl) {
//
//		try {
//			this.sessionHandler.addSession(session);
//			WsClientEndpoint exchangeClient = new WsClientEndpoint(websocketUrl, new MessageHandler() {
//
//				@Override
//				public void handleMessage(String message) {
//					sessionHandler.unicast(session, new Message(message));
//				}
//
//				@Override
//				public void handleOnOpen(Session session) throws ExchangeException {
//					handleOpen(session);
//				}
//
//				@Override
//				public void handleKeepAlive() throws ExchangeException {
//					handleKeepAlive();
//				}
//
//			});
//
//		} catch (URISyntaxException | DeploymentException | IOException e) {
//			this.sessionHandler.removeSession(session);
//			sessionHandler.unicast(session, new Message(e.getMessage()));
//		}
//
//	}
//
//	@OnMessage
//	public void onMessage(Session session, String message) throws IOException {
//		sessionHandler.unicast(session, new Message(message));
//	}
//
//	@OnError
//	public void onError(Session session, Throwable error) {
//		sessionHandler.unicast(session, new Message(error));
//	}
//
//	@OnClose
//	public void onClose(Session session, CloseReason closeReason) {
//		sessionHandler.removeSession(session);
//	}
//
//}
