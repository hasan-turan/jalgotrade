package tr.com.jalgo.model;

import java.io.Serializable;

public class Greeting  implements Serializable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String content;

	  public Greeting() {
	  }

	  public Greeting(String content) {
	    this.content = content;
	  }

	  public String getContent() {
	    return content;
	  }
}
