package tr.com.jalgo.ws;

import lombok.Getter;
import lombok.Setter;

public class Message {
	
	@Getter
	@Setter
	private int statusCode;
	
	@Getter
	@Setter
	private Object content;
	
	public Message(Object content) {
		this.content=content;
	}
	public Message(Object content,int statusCode) {
		this.content=content;
		this.statusCode=statusCode;
	}
}
