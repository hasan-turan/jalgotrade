package tr.com.jalgo.ws;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.Session;

//from: https://www.oracle.com/webfolder/technetwork/tutorials/obe/java/HomeWebsocket/WebsocketHome.html

public class SessionHandler {

	private final Set<Session> sessions = new HashSet<>();
	 
 
	public void addSession(Session session) {
		sessions.add(session);

	}

	public void removeSession(Session session) {
		sessions.remove(session);
	}

	public void broadcast(Message message) {
		for (Session session : this.sessions) {
			unicast(session, message);
		}
	}

	public void multicast(List<Session> sessions, Message message) {
		for (Session session : sessions) {
			unicast(session, message);
		}
	}

	public void unicast(Session session, Message message) {
		try {
			// session.getBasicRemote().sendText(message.toString());
			//String jsonMessage = objectMapper.writeValueAsString(message);
			session.getBasicRemote().sendObject(message);
		} catch (IOException | EncodeException ex) {
			sessions.remove(session);
		}
	}
}
