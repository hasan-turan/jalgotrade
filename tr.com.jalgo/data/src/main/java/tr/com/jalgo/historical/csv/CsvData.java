package tr.com.jalgo.historical.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tr.com.jalgo.model.ApiResponse;
import tr.com.jalgo.historical.LocalData;
import tr.com.jalgo.model.Parity;
import tr.com.jalgo.model.Candle;
import tr.com.jalgo.model.DataSource;
import tr.com.jalgo.model.Exchange;
import tr.com.jalgo.model.Interval;
import tr.com.jalgo.model.exceptions.FileException;
import tr.com.jalgo.service.CandleService;

@Component(value = "CsvData")
public class CsvData extends LocalData {

	@Autowired
	private CandleService candleService;

	private final String COMMA_SEPERATOR = ",";

	public CsvData() {

	}

	@Override
	public ApiResponse saveData(DataSource dataSource, Exchange exchange, Parity parity, Interval interval,
			InputStream fileData, Optional<Integer> skipFirstNRows) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(fileData));
		String dataRow = "";
		String[] dataColumns = null;
		String dateFormat = "yyyy-MM-dd HH:mm:ss";
		int rowCount = 0;
		List<Candle> candles = new ArrayList<Candle>();
		long start = System.currentTimeMillis();
		System.out.println("***********Started to reading file***************");
		try {
			while (reader.ready()) {
				rowCount++;
				dataRow = reader.readLine();
				if (skipFirstNRows.isPresent() && rowCount <= skipFirstNRows.get())
					continue;

				dataColumns = dataRow.split(this.COMMA_SEPERATOR);

				Candle candle = new Candle();
				candle.setDataSource(dataSource);
				candle.setExchange(exchange);
				candle.setInterval(interval);
				candle.setStartTime(Long.parseLong(dataColumns[0]));
				candle.setDate(new SimpleDateFormat(dateFormat).parse(dataColumns[1]));

				candle.setParity(parity);

				candle.setOpen(Double.parseDouble(dataColumns[3]));
				candle.setHigh(Double.parseDouble(dataColumns[4]));
				candle.setLow(Double.parseDouble(dataColumns[5]));
				candle.setClose(Double.parseDouble(dataColumns[6]));
				candle.setBaseAssetVolume(Double.parseDouble(dataColumns[7]));
				candle.setCounterAssetVolume(Double.parseDouble(dataColumns[8]));
				candles.add(candle);
				System.out.println("Candle: " + rowCount);
			}
			candleService.batchInsert(candles);
			reader.close();

			long end = System.currentTimeMillis();
			long totalMs = (end - start);
			System.out.println("Total time taken = " + totalMs / 1000 + " s");
			System.out.println("***********Finished reading file***************");
		} catch (IOException | ParseException e) {
			throw new FileException(e.getMessage());
		}
		return null;
	}

}
