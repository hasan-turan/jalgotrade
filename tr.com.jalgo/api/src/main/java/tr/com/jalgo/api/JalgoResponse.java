package tr.com.jalgo.api;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.csrf.CsrfToken;

import lombok.Getter;
import lombok.Setter;

 

public class JalgoResponse {

	private @Getter Object data;

	private @Getter @Setter HttpStatus status;

	private @Getter @Setter String message;

	private @Getter @Setter String url;

	private  @Getter @Setter CsrfToken csrfToken;

	public JalgoResponse() {

	}

	public void setError(String error) {
		this.status=HttpStatus.INTERNAL_SERVER_ERROR;
		this.setMessage(error);
	}
	
	public void setInfo(String info) {
		this.status=HttpStatus.OK;
		this.setMessage(info);
	}
	
	public void setData(Object data) {
		this.status=HttpStatus.OK;
		this.data=data;
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
