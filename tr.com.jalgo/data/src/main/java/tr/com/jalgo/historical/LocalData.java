package tr.com.jalgo.historical;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.stereotype.Component;

import tr.com.jalgo.model.ApiResponse;
import tr.com.jalgo.model.Parity;
import tr.com.jalgo.model.DataSource;
import tr.com.jalgo.model.Exchange;
import tr.com.jalgo.model.Interval;

@Component
public abstract class LocalData {
	
	public abstract ApiResponse saveData(DataSource dataSource,Exchange exchange,Parity symbol,Interval interval,InputStream fileData,Optional<Integer> skipFirstNRows);
}
