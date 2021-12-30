package tr.com.jalgo.repository.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tr.com.jalgo.model.Candle;
import tr.com.jalgo.model.DataSource;
import tr.com.jalgo.model.Exchange;
import tr.com.jalgo.model.Interval;
import tr.com.jalgo.model.Parity;

public class CandleMapper implements RowMapper<Candle> {

	@Override
	public Candle mapRow(ResultSet rs, int rowNum) throws SQLException {
		Candle candle = new Candle();
		candle.setDataSource(new DataSource(rs.getLong("DataSourceId")));
		candle.setExchange(new Exchange(rs.getLong("ExchangeId")));
		candle.setParity(new Parity(rs.getLong("ParityId")));
		candle.setInterval(new Interval(rs.getString("IntervalId")));
		candle.setDate(rs.getDate("Date"));
		candle.setStartTime(rs.getLong("StartTime"));
		candle.setCloseTime(rs.getLong("CloseTime"));
		candle.setOpen(rs.getDouble("Open"));
		candle.setHigh(rs.getDouble("High"));
		candle.setLow(rs.getDouble("Low"));
		candle.setClose(rs.getDouble("Close"));
		candle.setBaseAssetVolume(rs.getDouble("BaseAssetVolume"));
		candle.setCounterAssetVolume(rs.getDouble("CounterAssetVolume"));
		return candle;
	}

}
