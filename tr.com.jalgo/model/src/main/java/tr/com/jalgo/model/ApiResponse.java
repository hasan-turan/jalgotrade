package tr.com.jalgo.model;

 

 

import org.apache.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

public class ApiResponse {
	@Getter
	@Setter
	private Object data;

	@Getter
	private String message;

	@Getter
	@Setter
	private int statusCode;

	 

	public ApiResponse() {

	}

	public ApiResponse(int statusCode) {
		this.statusCode = statusCode;

	}

	public ApiResponse(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}
	
	public ApiResponse(Object data,int statusCode,String message) {
		this.data=data;
		this.statusCode=statusCode;
		this.message=message;
	}

	public void setError(String error) {
		this.statusCode = HttpStatus.SC_INTERNAL_SERVER_ERROR;
		this.message += error + "\r\n";
	}

	public void setInfo(String info) {
		this.statusCode = HttpStatus.SC_ACCEPTED;
		this.message += info + "\r\n";
	}
}
