package tr.com.jalgo.repository.jdbc.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tr.com.jalgo.model.Exchange;
import tr.com.jalgo.repository.ExchangeRepository;
import tr.com.jalgo.repository.jdbc.Query;
import tr.com.jalgo.repository.jdbc.mapper.ExchangeMapper;

@Repository("ExchangeRepositoryJdbcImpl")
public class ExchangeRepositoryJdbcImpl implements ExchangeRepository {

	private static String TABLE_NAME = "Exchanges";

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public long Add(Exchange param) {
		//@formatter:off
		String SQL = "INSERT INTO "
				  + TABLE_NAME
				  + "("
				  + "Id,"
				  + "Name,"
				  + "Url,"
				  + "WsUrl"
				
				  + ")" 
				  +" VALUES (?,?,?,?)";
		return jdbcTemplate.update(SQL, 
				param.getId(),  
				param.getName() ,
				param.getUrl(), 
				param.getWsUrl());
		//@formatter:on
	}

	@Override
	public void update(Exchange param) {
		//@formatter:off
		String SQL = "UPDATE "
		+ TABLE_NAME
		+ " Set "
		+ "Name=?,"
		+ "Url=?,"
		+ "WsUrl=?"
		+" )" 
		+ " WHERE Id=?";

		jdbcTemplate.update(SQL, 
				param.getName(), 
				param.getUrl(), 
				param.getWsUrl(),
				param.getId());
		//@formatter:on
	}

	@Override
	public List<Exchange> findAll(Exchange param) {
		Query query = generateSelectQuery(param);
		List<Exchange> ohlcs = jdbcTemplate.query(query.getSql(), new ExchangeMapper(), query.getParameters());

//		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.getSql());
//		List<Ohlc> ohlcs = new ArrayList<Ohlc>();
//		for (Map<?, ?> row : rows) {
//			Ohlc ohlc = new Ohlc();
//			ohlc.setDate((Date) row.get("Date"));
//			ohlc.setOpen((Long) row.get("Open"));
//			ohlcs.add(ohlc);
//		}

		return ohlcs;
	}

	@Override
	public Exchange find(Exchange param) {
		Query query = generateSelectQuery(param);
		Exchange ohlc = jdbcTemplate.queryForObject(query.getSql(), new ExchangeMapper(), query.getParameters());
		return ohlc;
	}

	@Override
	public Exchange getById(long id) {
		Query query =generateSelectQuery(new Exchange(id));
		Exchange ohlc = jdbcTemplate.queryForObject(query.getSql(), new ExchangeMapper(), query.getParameters());
		return ohlc;
	}

	private Query generateSelectQuery(Exchange param) {

		String SQL = "SELECT * FROM" + TABLE_NAME + " WHERE 1=1";
		List<Object> params = new ArrayList<Object>();

		if (param.getId() > 0) {
			SQL += " AND Id=?";
			params.add(param.getId());
		}

		if (param.getName() != null) {
			SQL += " AND Name=?";
			params.add(param.getName());
		}
 
		return new Query(SQL, params.toArray());
	}
}
