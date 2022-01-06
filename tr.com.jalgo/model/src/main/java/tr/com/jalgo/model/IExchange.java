package tr.com.jalgo.model;

import java.util.Date;
import java.util.List;

import tr.com.jalgo.model.exceptions.ExchangeException;
import tr.com.jalgo.model.strategies.Strategy;
import tr.com.jalgo.model.types.EnvironmentType;
import tr.com.jalgo.model.types.IntervalType;

public interface IExchange {
	
	public void start(EnvironmentType environment) throws ExchangeException;
	
	public Candle convertJsonStringToCandle(String message) throws ExchangeException;
	
	public ApiResponse ping() throws ExchangeException;

	public ApiResponse getTime() throws ExchangeException;

	public ApiResponse getExchangeInfo(List<Parity> parities) throws ExchangeException;

	/*
	 * @parity: mandatory
	 * 
	 * @limit: Default 100; max 5000. Valid limits:[5, 10, 20, 50, 100, 500, 1000,
	 * 5000]
	 */
	public ApiResponse getDept(Parity parity, int limit) throws ExchangeException;

	/*
	 * @parity: Mandatory
	 * 
	 * @limit: limit:Optional, Default 500; max 1000.
	 */
	public ApiResponse getTrades(Parity parity, int limit) throws ExchangeException;

	/*
	 * @parity: Mandatory
	 * 
	 * @limit: Optional,Default 500; max 1000.
	 * 
	 * @startDate: Timestamp in ms to get aggregate trades from INCLUSIVE.
	 * 
	 * @endDate: Timestamp in ms to get aggregate trades until INCLUSIVE.
	 * 
	 * @formId: Trade id to fetch from. Default gets most recent trades.
	 */
	public ApiResponse getAggregateTrades(Parity parity, int limit, Date startDate, Date endDate, long formId)
			throws ExchangeException;

	/*
	 * @parity: Mandatory
	 * 
	 * @limit: Optional,Default 500; max 1000.
	 * 
	 * @formId: Trade id to fetch from. Default gets most recent trades.
	 */
	public ApiResponse getHistoricalTrades(Parity parity, int limit, long formId) throws ExchangeException;

	public ApiResponse getKlines(Parity parity, IntervalType interval, Date startDate, Date endDate, int limit)
			throws ExchangeException;

	public void trade(Parity parity, IntervalType interval, List<Strategy> strategies) throws ExchangeException;

	 

	/**
	 * Start a new user data stream. The stream will close after 60 minutes unless a
	 * keepalive is sent. If the account has an active listenKey, that listenKey
	 * will be returned and its validity will be extended for 60 minutes.
	 */
	public ApiResponse startStream() throws ExchangeException;

	/**
	 * Keepalive a user data stream to prevent a time out. User data streams will
	 * close after 60 minutes. It's recommended to send a ping about every 30
	 * minutes.
	 */
	public void keepAlive() throws ExchangeException;
}
