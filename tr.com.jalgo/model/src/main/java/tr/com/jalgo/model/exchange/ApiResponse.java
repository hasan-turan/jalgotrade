package tr.com.jalgo.model.exchange;

import lombok.Getter;
import lombok.Setter;
import tr.com.jalgo.model.enums.EnumStatus;

public class ApiResponse {
	@Getter
	@Setter
	private String data;

	@Getter
	private String message;

	@Getter
	@Setter
	private EnumStatus status;
	
	@Getter
	@Setter
	private int code;

	public void setError(String error) {
		this.status = EnumStatus.ERROR;
		this.message += error + "\r\n";
	}

	public void setInfo(String info) {
		this.status = EnumStatus.OK;
		this.message += info + "\r\n";
	}
}
