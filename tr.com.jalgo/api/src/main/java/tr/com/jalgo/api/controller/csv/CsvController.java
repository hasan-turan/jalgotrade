package tr.com.jalgo.api.controller.csv;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tr.com.jalgo.model.exchange.ApiResponse;
import tr.com.jalgo.service.CandleService;

@RestController
@RequestMapping(value = "/csv")
public class CsvController {

	@Autowired
	CandleService candleService;

	@PostMapping(value = "/upload")
	public ApiResponse upload(@RequestParam("file") MultipartFile file) throws IOException {
		ApiResponse apiResponse = new ApiResponse();

		if (file.isEmpty()) {
			apiResponse.setError("Please specify a file");
			return apiResponse;
		}
		InputStream fileStream = file.getInputStream();
		BufferedReader reader= new BufferedReader(new InputStreamReader(fileStream));
		while( reader.ready()) {
			System.out.println(reader.readLine());
		}
		return apiResponse;

	}

}
