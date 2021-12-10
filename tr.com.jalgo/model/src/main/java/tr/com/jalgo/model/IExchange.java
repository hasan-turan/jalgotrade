package tr.com.jalgo.model;

import java.util.Date;
import java.util.List;

import tr.com.jalgo.model.exceptions.ExchangeException;
import tr.com.jalgo.model.exchange.ApiResponse;
import tr.com.jalgo.model.strategies.Strategy;
import tr.com.jalgo.model.types.IntervalType;

public interface IExchange {
	public abstract ApiResponse ping() throws ExchangeException;

	public abstract ApiResponse time() throws ExchangeException;

	public abstract ApiResponse exchangeInfo(String[] symbols) throws ExchangeException;

	/*
	 * @symbol: mandatory
	 * 
	 * @limit: Default 100; max 5000. Valid limits:[5, 10, 20, 50, 100, 500, 1000,
	 * 5000]
	 */
	public abstract ApiResponse dept(Symbol currency, int limit) throws ExchangeException;

	/*
	 * @symbol: Mandatory
	 * 
	 * @limit: limit:Optional, Default 500; max 1000.
	 */
	public abstract ApiResponse trades(Symbol currency, int limit) throws ExchangeException;

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
	public abstract ApiResponse aggregateTrades(Symbol currency, int limit, Date startDate, Date endDate, long formId)
			throws ExchangeException;

	/*
	 * @symbol: Mandatory
	 * 
	 * @limit: Optional,Default 500; max 1000.
	 * 
	 * @formId: Trade id to fetch from. Default gets most recent trades.
	 */
	public abstract ApiResponse historicalTrades(Symbol currency, int limit, long formId) throws ExchangeException;

	public abstract ApiResponse klines(Symbol currency, IntervalType interval, Date startDate, Date endDate, int limit)
			throws ExchangeException;

	public abstract void trade(Symbol currency, IntervalType interval, List<Strategy> strategies)
			throws ExchangeException;

	/**
	 * Start a new user data stream. The stream will close after 60 minutes unless a
	 * keepalive is sent. If the account has an active listenKey, that listenKey
	 * will be returned and its validity will be extended for 60 minutes.
	 */
	public abstract ApiResponse startStream() throws ExchangeException;

	/**
	 * Keepalive a user data stream to prevent a time out. User data streams will
	 * close after 60 minutes. It's recommended to send a ping about every 30
	 * minutes.
	 */
	public abstract void keepAlive() throws ExchangeException;
}
