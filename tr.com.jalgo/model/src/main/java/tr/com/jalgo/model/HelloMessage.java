package tr.com.jalgo.model;

import java.io.Serializable;

 
public class HelloMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 
	private String name;

	public HelloMessage() {
	}

	public HelloMessage(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
