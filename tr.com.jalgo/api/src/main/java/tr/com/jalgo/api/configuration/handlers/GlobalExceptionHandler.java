package tr.com.jalgo.api.configuration.handlers;

import org.apache.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import tr.com.jalgo.model.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler({ Exception.class })
	protected ResponseEntity<ApiResponse> exchangeException(final Exception ex) {
		ApiResponse response = new ApiResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		return new ResponseEntity<ApiResponse>(response, new HttpHeaders(),response.getStatusCode());
	}

}
