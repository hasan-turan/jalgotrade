package tr.com.jalgo.model.exchange;

import java.util.Date;

public interface IExchange {
	ApiResponse ping() throws ExchangeException;

	ApiResponse time() throws ExchangeException;

	ApiResponse exchangeInfo(String[] symbols) throws ExchangeException;

	/*
	 * @symbol: mandatory
	 * 
	 * @limit: Default 100; max 5000. Valid limits:[5, 10, 20, 50, 100, 500, 1000,
	 * 5000]
	 */
	ApiResponse dept(Currency currency, int limit) throws ExchangeException;

	/*
	 * @symbol: Mandatory
	 * 
	 * @limit: limit:Optional, Default 500; max 1000.
	 */
	ApiResponse trades(Currency currency, int limit) throws ExchangeException;

	/*
	 * @symbol: Mandatory
	 * 
	 * @limit: Optional,Default 500; max 1000.
	 * 
	 * @startDate: Timestamp in ms to get aggregate trades from INCLUSIVE.
	 * 
	 * @endDate: Timestamp in ms to get aggregate trades until INCLUSIVE.
	 * 
	 * @formId: Trade id to fetch from. Default gets most recent trades.
	 */
	ApiResponse aggregateTrades(Currency currency, int limit, Date startDate, Date endDate, long formId)
			throws ExchangeException;

	/*
	 * @symbol: Mandatory
	 * 
	 * @limit: Optional,Default 500; max 1000.
	 * 
	 * @formId: Trade id to fetch from. Default gets most recent trades.
	 */
	ApiResponse historicalTrades(Currency currency, int limit, long formId) throws ExchangeException;

	
	ApiResponse klines(Currency currency, EnumInterval interval, Date startDate, Date endDate,int limit)
			throws ExchangeException;
	
	ApiResponse ticker(Currency currenyPair) throws ExchangeException;

	
}
