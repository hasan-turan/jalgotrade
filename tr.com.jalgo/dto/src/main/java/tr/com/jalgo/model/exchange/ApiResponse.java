package tr.com.jalgo.model.exchange;

import lombok.Data;

@Data
public class ApiResponse {
	private String data;
	private String message;
	private int statusCode;
	private String status;
}
