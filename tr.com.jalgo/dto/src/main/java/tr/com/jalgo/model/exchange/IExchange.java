package tr.com.jalgo.model.exchange;

import java.util.Date;

public interface IExchange {
	ApiResponse ping();
	ApiResponse ticker(Pair pair);
	ApiResponse klines(Pair symbolPair,Date startDate,Date endDate,EnumInterval interval);
}
