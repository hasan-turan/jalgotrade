package tr.com.jalgo.repository.jdbc.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tr.com.jalgo.model.Exchange;
import tr.com.jalgo.repository.BaseRepository;
import tr.com.jalgo.repository.ExchangeRepository;
import tr.com.jalgo.repository.jdbc.Query;
import tr.com.jalgo.repository.jdbc.mapper.ExchangeMapper;
import tr.com.jalgo.repository.jdbc.types.TableType;

@Repository("ExchangeRepositoryJdbcImpl")
public class ExchangeRepositoryJdbcImpl extends BaseRepository implements ExchangeRepository {

	private JdbcTemplate jdbcTemplate;

	public ExchangeRepositoryJdbcImpl() {
		 
	}



	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public long insert(Exchange param) {
		//@formatter:off
		String SQL = "INSERT INTO "
				  + TableType.EXCHANGES
				  + "("
				  + "Id,"
				  + "Name,"
				  + "LiveUrl,"
				  + "TestUrl,"
				  + "WsUrl"
				
				  + ")" 
				  +" VALUES (?,?,?,?,?)";
		return jdbcTemplate.update(SQL, 
				param.getId(),  
				param.getName() ,
				param.getLiveUrl(), 
				param.getTestUrl(), 
				param.getWsUrl());
		//@formatter:on
	}

	@Override
	public void update(Exchange param) {
		//@formatter:off
		String SQL = "UPDATE "
		+ TableType.EXCHANGES
		+ " Set "
		+ "Name=?,"
		+ "LiveUrl=?,"
		+ "TestUrl=?,"
		+ "WsUrl=?"
		+" )" 
		+ " WHERE Id=?";

		jdbcTemplate.update(SQL, 
				param.getName(), 
				param.getLiveUrl(),
				param.getTestUrl(), 
				param.getWsUrl(),
				param.getId());
		//@formatter:on
	}

	@Override
	public List<Exchange> findAll(Exchange param) {
		Query query = generateSelectQuery(param);
		List<Exchange> exchanges = jdbcTemplate.query(query.getSql(), new ExchangeMapper(), query.getParameters());

//		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.getSql());
//		List<Ohlc> ohlcs = new ArrayList<Ohlc>();
//		for (Map<?, ?> row : rows) {
//			Ohlc ohlc = new Ohlc();
//			ohlc.setDate((Date) row.get("Date"));
//			ohlc.setOpen((Long) row.get("Open"));
//			ohlcs.add(ohlc);
//		}

		return exchanges;
	}

	@Override
	public Exchange find(Exchange param) {
		Query query = generateSelectQuery(param);
		Exchange exchange = jdbcTemplate.queryForObject(query.getSql(), new ExchangeMapper(), query.getParameters());
		return exchange;
	}

	@Override
	public Exchange getById(long id) {
		Query query =generateSelectQuery(new Exchange(id));
		Exchange exchange = jdbcTemplate.queryForObject(query.getSql(), new ExchangeMapper(), query.getParameters());
		return exchange;
	}

	private Query generateSelectQuery(Exchange param) {

		String SQL = "SELECT * FROM " + TableType.EXCHANGES + " WHERE 1=1";
		List<Object> params = new ArrayList<Object>();

		if (param.getId() > 0) {
			SQL += " AND Id=?";
			params.add(param.getId());
		}

		if (param != null) {
			SQL += " AND Name=?";
			params.add(param.getName());
		}
 
		return new Query(SQL, params.toArray());
	}

	@Override
	public List<Exchange> getAll() {
		String SQL = "SELECT * FROM " + TableType.EXCHANGES + " WHERE 1=1";
		return jdbcTemplate.query(SQL, new ExchangeMapper());
	}
}
