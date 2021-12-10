package tr.com.jalgo.repository.jdbc.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tr.com.jalgo.model.Candle;
import tr.com.jalgo.repository.CandleRepository;
import tr.com.jalgo.repository.jdbc.Query;
import tr.com.jalgo.repository.jdbc.mapper.CandleMapper;

@Repository("CandleRepositoryJdbcImpl")
public class CandleRepositoryJdbcImpl implements CandleRepository {

	private static String TABLE_NAME = "Candles";

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public long Add(Candle param) {
		//@formatter:off
		String SQL = "INSERT INTO "
				  + TABLE_NAME
				  + "("
				  + "Id,"
				  + "ExchangeId,"
				  + "SymbolId,"
				  + "IntervalId,"
				  + "Date,"
				  + "Time,"
				  + "Open,"
				  + "High,"
				  + "Low,"
				  + "Close,"
				  + "Volume"
				  + ")" 
				  +" VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(SQL, 
				param.getId(),  
				param.getExchange().getId(),
				param.getSymbol().getId(),
				param.getInterval().getId(),
				param.getDate(), 
				param.getTime(),
				param.getOpen(), 
				param.getHigh(), 
				param.getLow(), 
				param.getLow(), 
				param.getClose(), 
				param.getVolume());
		//@formatter:on
	}

	@Override
	public void update(Candle param) {
		//@formatter:off
		String SQL = "UPDATE "
		+ TABLE_NAME
		+ "ExchangeId=?,"
		+ "SymbolId=?,"
		+ "IntervalId=?,"
		+ "Date=?,"
		+ "Time=?,"
		+ "Open=?,"
		+ "High=?,"
		+ "Low=?,"
		+ "Close=?,"
		+ "Volume=?)" 
		+ " WHERE Id=?";

		jdbcTemplate.update(SQL, 
				param.getExchange().getId(), 
				param.getSymbol().getId(), 
				param.getInterval().getId(), 
				param.getDate(), 
				param.getTime(), 
				param.getOpen(),
				param.getHigh(), 
				param.getLow(), 
				param.getClose(), 
				param.getVolume(),
				param.getId());
		//@formatter:on
	}

	@Override
	public List<Candle> findAll(Candle param) {
		Query query = generateSelectQuery(param);
		List<Candle> results = jdbcTemplate.query(query.getSql(), new CandleMapper(), query.getParameters());

//		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.getSql());
//		List<result> results = new ArrayList<result>();
//		for (Map<?, ?> row : rows) {
//			result result = new result();
//			result.setDate((Date) row.get("Date"));
//			result.setOpen((Long) row.get("Open"));
//			results.add(result);
//		}

		return results;
	}

	@Override
	public Candle find(Candle param) {
		Query query = generateSelectQuery(param);
		Candle result = jdbcTemplate.queryForObject(query.getSql(), new CandleMapper(), query.getParameters());
		return result;
	}

	@Override
	public Candle getById(long id) {
		Query query = generateSelectQuery(new Candle(id));
		Candle result = jdbcTemplate.queryForObject(query.getSql(), new CandleMapper(), query.getParameters());
		return result;
	}

	private Query generateSelectQuery(Candle param) {

		String SQL = "SELECT * FROM" + TABLE_NAME + " WHERE 1=1";
		List<Object> params = new ArrayList<Object>();

		if (param.getId() > 0) {
			SQL += " AND Id=?";
			params.add(param.getId());
		}

		if (param.getExchange() != null) {
			SQL += " AND ExchangeId=?";
			params.add(param.getExchange().getId());
		}

		if (param.getSymbol() != null) {
			SQL += " AND SymbolId=?";
			params.add(param.getSymbol().getId());
		}

		if (param.getInterval() != null) {
			SQL += " AND IntervalId=?";
			params.add(param.getInterval().getId());
		}

		if (param.getDate() != null) {
			SQL += " AND Date=?";
			params.add(param.getDate());
		}

		if (param.getTime() > 0) {
			SQL += " AND Time=?";
			params.add(param.getTime());
		}

		return new Query(SQL, params.toArray());
	}
}
