package tr.com.jalgo.repository.jdbc.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tr.com.jalgo.model.Interval;
import tr.com.jalgo.repository.IntervalRepository;
import tr.com.jalgo.repository.jdbc.Query;
import tr.com.jalgo.repository.jdbc.mapper.IntervalMapper;

@Repository("IntervalRepositoryJdbcImpl")
public class IntervalRepositoryJdbcImpl implements IntervalRepository {

	private static String TABLE_NAME = "Intervals";

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public long Add(Interval param) {
		//@formatter:off
		String SQL = "INSERT INTO "
				  + TABLE_NAME
				  + "("
				  + "Id,"
				  + "Value"
				  + ")" 
				  +" VALUES (?,?)";
		return jdbcTemplate.update(SQL, 
				param.getId(),  
				param.getValue() 
			);
		//@formatter:on
	}

	@Override
	public void update(Interval param) {
		//@formatter:off
		String SQL = "UPDATE "
		+ TABLE_NAME
		+ " Set "
		+ "Value=?"
		+" )" 
		+ " WHERE Id=?";

		jdbcTemplate.update(SQL, 
				param.getValue(),
				param.getId());
		//@formatter:on
	}

	@Override
	public List<Interval> findAll(Interval param) {
		Query query = generateSelectQuery(param);
		List<Interval> ohlcs = jdbcTemplate.query(query.getSql(), new IntervalMapper(), query.getParameters());

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
	public Interval find(Interval param) {
		Query query = generateSelectQuery(param);
		Interval ohlc = jdbcTemplate.queryForObject(query.getSql(), new IntervalMapper(), query.getParameters());
		return ohlc;
	}

	@Override
	public Interval getById(long id) {
		Query query = generateSelectQuery(new Interval(id));
		Interval ohlc = jdbcTemplate.queryForObject(query.getSql(), new IntervalMapper(), query.getParameters());
		return ohlc;
	}

	private Query generateSelectQuery(Interval param) {

		String SQL = "SELECT * FROM" + TABLE_NAME + " WHERE 1=1";
		List<Object> params = new ArrayList<Object>();

		if (param.getId() > 0) {
			SQL += " AND Id=?";
			params.add(param.getId());
		}

		if (param.getValue() != null) {
			SQL += " AND Name=?";
			params.add(param.getValue());
		}

		return new Query(SQL, params.toArray());
	}
}
