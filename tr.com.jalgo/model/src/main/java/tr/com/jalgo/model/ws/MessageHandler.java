package tr.com.jalgo.model.ws;

import javax.websocket.Session;

import tr.com.jalgo.model.exceptions.ExchangeException;

public interface MessageHandler {
	  public void handleMessage(String message);
	  public void handleOnOpen(Session session) throws ExchangeException;
	  public void handleKeepAlive()  throws ExchangeException  ;
}
