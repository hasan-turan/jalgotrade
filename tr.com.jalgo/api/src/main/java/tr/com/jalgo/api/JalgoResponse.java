package tr.com.jalgo.api;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.csrf.CsrfToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JalgoResponse {

	private Object data;

	private HttpStatus status;

	private String message;

	private String url;

	private CsrfToken csrfToken;

	public JalgoResponse() {

	}

	public JalgoResponse(HttpStatus status) {
		this.status = status;
	}

	public JalgoResponse(HttpStatus status, String message) {
		this(status);
		this.message = message;
	}

	public JalgoResponse(HttpStatus status, String message, String url) {
		this(status, message);
		this.url = url;
	}

	public JalgoResponse(HttpStatus status, String message, Object data) {
		this(status, message);
		this.data = data;

	}

	public JalgoResponse(HttpStatus status, String message, Object data, CsrfToken csrfToken) {
		this(status, message, data);
		// this.csrfToken = csrfToken;
	}

}
