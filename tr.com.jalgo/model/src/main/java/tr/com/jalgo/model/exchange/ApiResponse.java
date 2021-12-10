package tr.com.jalgo.model.exchange;

import lombok.Getter;
import lombok.Setter;
import tr.com.jalgo.model.types.StatusType;

public class ApiResponse {
	@Getter
	@Setter
	private Object data;

	@Getter
	private String message;

	@Getter
	@Setter
	private StatusType status;

	@Getter
	@Setter
	private int code;

	public ApiResponse() {

	}

	public ApiResponse(StatusType status) {
		this.status = status;

	}

	public ApiResponse(StatusType status, String message) {
		this.status = status;
		this.message = message;
	}

	public void setError(String error) {
		this.status = StatusType.ERROR;
		this.message += error + "\r\n";
	}

	public void setInfo(String info) {
		this.status = StatusType.OK;
		this.message += info + "\r\n";
	}
}
