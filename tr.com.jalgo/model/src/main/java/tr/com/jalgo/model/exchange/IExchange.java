package tr.com.jalgo.model.exchange;

import java.util.Date;

public interface IExchange {
	ApiResponse ping() ;
	ApiResponse ticker(CurrencyPair pair)  ;
	ApiResponse klines(CurrencyPair symbolPair,Date startDate,Date endDate,EnumInterval interval)  ;
}
