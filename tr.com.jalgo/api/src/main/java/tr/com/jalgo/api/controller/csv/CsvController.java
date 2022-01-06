package tr.com.jalgo.api.controller.csv;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tr.com.jalgo.historical.LocalData;
import tr.com.jalgo.model.ApiResponse;
import tr.com.jalgo.model.DataSource;
import tr.com.jalgo.model.Exchange;
import tr.com.jalgo.model.Interval;
import tr.com.jalgo.model.Parity;
import tr.com.jalgo.model.exceptions.ExchangeException;
import tr.com.jalgo.model.types.StatusType;
import tr.com.jalgo.service.CandleService;
import tr.com.jalgo.service.DataSourceService;
import tr.com.jalgo.service.ExchangeService;
import tr.com.jalgo.service.IntervalService;
import tr.com.jalgo.service.ParityService;

@RestController
@CrossOrigin
@RequestMapping(value = "/csv")
public class CsvController {

	@Autowired
	CandleService candleService;

	@Autowired
	DataSourceService dataSourceService;

	@Autowired
	ExchangeService exchangeService;

	@Autowired
	ParityService parityService;

	@Autowired
	IntervalService intervalService;

	@Autowired
	@Qualifier("CsvData")
	LocalData csvData;

	private List<DataSource> dataSources;
	private List<Exchange> exchanges;
	private List<Parity> parities;
	private List<Interval> intervals;

	@PostConstruct
	public void initVariables() {
		this.dataSources = dataSourceService.getAll();
		this.exchanges = exchangeService.getAll();
		this.parities = parityService.getAll();
		this.intervals = intervalService.getAll();
	}

	@PostMapping(value = "/test")
	public ApiResponse test() throws IOException {
		return new ApiResponse(StatusType.ERROR, "OK");

	}

	@PostMapping(value = "/upload")
	public ApiResponse upload(@RequestParam("file") MultipartFile file) throws IOException {

		if (file.isEmpty())
			return new ApiResponse(StatusType.ERROR, "Please specify a file");

		String[] fileNameParts = FilenameUtils.removeExtension(file.getOriginalFilename()).split("_");

		/*
		 * filename= DataSource_Exchange_Parity_Year_Interval.csv
		 */
		if (fileNameParts.length < 5)
			return new ApiResponse(StatusType.ERROR,
					"File name should be in  [CryptoDataDownload_Bitstamp_BTC#USD_2017_1minute.csv] format");

		DataSource dataSource = getDataSource(fileNameParts[0]);
		Exchange exchange = getExchange(fileNameParts[1]);

		String[] parityParts = fileNameParts[2].split("#");
		Parity parity = getParity(parityParts[0], parityParts[1]);

		Interval interval = getInterval(fileNameParts[4]);

//		String fileName = file.getOriginalFilename();
//		byte[] bytes = file.getBytes();
		// //1-Bitstamp 1- CryptoDataDownload
		return csvData.saveData(dataSource, exchange, parity, interval, file.getInputStream(), Optional.of(2));

	}

	private DataSource getDataSource(String dataSourceName) {
		Optional<DataSource> result = this.dataSources.stream().filter(d -> d.getName().equals(dataSourceName))
				.findFirst();
		if (!result.isPresent())
			throw new ExchangeException("Datasource not found!");

		return result.get();
	}

	private Exchange getExchange(String exchangeName) {
		Optional<Exchange> result = this.exchanges.stream().filter(e -> e.getName().equals(exchangeName)).findFirst();
		if (!result.isPresent())
			throw new ExchangeException("Exchange not found!");

		return result.get();
	}

	private Parity getParity(String baseSymbol, String counterSymbol) {
		Optional<Parity> result = this.parities.stream()
				.filter(a -> a.getBaseAsset().getSymbol().equals(baseSymbol) && a.getCounterAsset().getSymbol().equals(counterSymbol))
				.findFirst();
		if (!result.isPresent())
			throw new ExchangeException("Parity not found!");

		return result.get();
	}

	private Interval getInterval(final String interval) {
		String calculatedInterval = getCalculatedInterval(interval);
		Optional<Interval> result = this.intervals.stream().filter(i -> {
			return i.getName().equals(calculatedInterval);
		}).findFirst();

		if (!result.isPresent())
			throw new ExchangeException("Interval not found!");

		return result.get();
	}

	private String getCalculatedInterval(String interval) {
		String calculatedInterval = "";
		var firstLetter = interval.substring(0, 1);
		var otherLetters = interval.substring(1, interval.length());
		calculatedInterval = firstLetter;
		if (otherLetters.equals("minute") || otherLetters.equals("m"))
			calculatedInterval += "m";
		else if (otherLetters.equals("hour") || otherLetters.equals("h"))
			calculatedInterval += "h";
		else if (otherLetters.equals("day") || otherLetters.equals("d"))
			calculatedInterval += "d";
		else if (otherLetters.equals("week") || otherLetters.equals("w"))
			calculatedInterval += "w";
		else if (otherLetters.equals("month") || otherLetters.equals("M"))
			calculatedInterval += "M";
		else if (otherLetters.equals("year") || otherLetters.equals("Y"))
			calculatedInterval += "Y";
		else
			throw new ExchangeException("Interval is not valid!");

		return calculatedInterval;
	}

}
