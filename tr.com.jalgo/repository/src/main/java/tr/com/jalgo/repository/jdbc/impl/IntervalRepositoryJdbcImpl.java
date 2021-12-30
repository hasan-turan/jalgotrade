package tr.com.jalgo.repository.jdbc.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tr.com.jalgo.model.Interval;
import tr.com.jalgo.repository.BaseRepository;
import tr.com.jalgo.repository.IntervalRepository;
import tr.com.jalgo.repository.jdbc.Query;
import tr.com.jalgo.repository.jdbc.mapper.IntervalMapper;
import tr.com.jalgo.repository.jdbc.types.TableType;

@Repository("IntervalRepositoryJdbcImpl")
public class IntervalRepositoryJdbcImpl extends BaseRepository implements IntervalRepository {

	private JdbcTemplate jdbcTemplate;
	
	public IntervalRepositoryJdbcImpl() {
	 
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public long insert(Interval param) {
		//@formatter:off
		String SQL = "INSERT INTO "
				  + TableType.INTERVALS
				  + "("
				  + "Id,"
				  + "Name"
				  + ")" 
				  +" VALUES (?,?)";
		return jdbcTemplate.update(SQL, 
				param.getId(),  
				param 
			);
		//@formatter:on
	}

	@Override
	public void update(Interval param) {
		//@formatter:off
		String SQL = "UPDATE "
		+ TableType.INTERVALS
		+ " Set "
		+ "Name=?"
		+" )" 
		+ " WHERE Id=?";

		jdbcTemplate.update(SQL, 
				param,
				param.getId());
		//@formatter:on
	}

	@Override
	public List<Interval> findAll(Interval param) {
		Query query = generateSelectQuery(param);
		List<Interval> intervals = jdbcTemplate.query(query.getSql(), new IntervalMapper(), query.getParameters());

//		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.getSql());
//		List<Ohlc> ohlcs = new ArrayList<Ohlc>();
//		for (Map<?, ?> row : rows) {
//			Ohlc ohlc = new Ohlc();
//			ohlc.setDate((Date) row.get("Date"));
//			ohlc.setOpen((Long) row.get("Open"));
//			ohlcs.add(ohlc);
//		}

		return intervals;
	}

	@Override
	public Interval find(Interval param) {
		Query query = generateSelectQuery(param);
		Interval interval = jdbcTemplate.queryForObject(query.getSql(), new IntervalMapper(), query.getParameters());
		return interval;
	}

	@Override
	public Interval getById(long id) {
		Query query = generateSelectQuery(new Interval(id));
		Interval interval = jdbcTemplate.queryForObject(query.getSql(), new IntervalMapper(), query.getParameters());
		return interval;
	}

	private Query generateSelectQuery(Interval param) {

		String SQL = "SELECT * FROM " + TableType.INTERVALS + " WHERE 1=1";
		List<Object> params = new ArrayList<Object>();

		if (param.getId() > 0) {
			SQL += " AND Id=?";
			params.add(param.getId());
		}

		if (param != null) {
			SQL += " AND Name=?";
			params.add(param);
		}

		return new Query(SQL, params.toArray());
	}

	@Override
	public List<Interval> getAll() {
		String SQL = "SELECT * FROM " + TableType.INTERVALS + " WHERE 1=1";
		return jdbcTemplate.query(SQL, new IntervalMapper());
	}
}
