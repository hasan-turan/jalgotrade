package tr.com.jalgo.repository.jdbc.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tr.com.jalgo.model.Candle;
import tr.com.jalgo.model.Interval;
import tr.com.jalgo.model.Symbol;

public class CandleMapper  implements RowMapper<Candle> {

	@Override
	public Candle mapRow(ResultSet rs, int rowNum) throws SQLException {
		 Candle ohlc= new Candle();
		 ohlc.setSymbol(new Symbol(rs.getString("BaseSymbol"),rs.getString("CounterSymbol")));
		 ohlc.setInterval(new Interval(rs.getString("Interval")));
		 ohlc.setDate(rs.getDate("Date"));
		 ohlc.setTime(rs.getLong("Time"));
		 ohlc.setOpen(rs.getDouble("Open"));
		 ohlc.setHigh(rs.getDouble("High"));
		 ohlc.setLow(rs.getDouble("Low"));
		 ohlc.setClose(rs.getDouble("Close"));
		 ohlc.setVolume(rs.getDouble("Volume"));
		 return ohlc;
	}

}
