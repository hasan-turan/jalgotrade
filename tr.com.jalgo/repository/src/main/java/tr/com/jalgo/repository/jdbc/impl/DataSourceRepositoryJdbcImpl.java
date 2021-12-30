package tr.com.jalgo.repository.jdbc.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tr.com.jalgo.repository.BaseRepository;
import tr.com.jalgo.repository.DataSourceRepository;
import tr.com.jalgo.repository.jdbc.Query;
import tr.com.jalgo.repository.jdbc.mapper.DataSourceMapper;
import tr.com.jalgo.repository.jdbc.types.TableType;

@Repository("DataSourceRepositoryJdbcImpl")
public class DataSourceRepositoryJdbcImpl extends BaseRepository implements DataSourceRepository {

 

	public DataSourceRepositoryJdbcImpl() {
	 
	}

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
	 
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public long insert(tr.com.jalgo.model.DataSource param) {
		//@formatter:off
		String SQL = "INSERT INTO "
				  + TableType.DATASOURCES
				  + "("
				  + "Id,"
				  + "Name,"
				  + "Url"	
				  + ")" 
				  +" VALUES (?,?,?)";
		return jdbcTemplate.update(SQL, 
				param.getId(),  
				param ,
				param.getUrl());
		//@formatter:on
	}

	@Override
	public void update(tr.com.jalgo.model.DataSource param) {
		//@formatter:off
		String SQL = "UPDATE "
		+ TableType.DATASOURCES
		+ " Set "
		+ "Name=?,"
		+ "Url=?"
		+" )" 
		+ " WHERE Id=?";

		jdbcTemplate.update(SQL, 
				param, 
				param.getUrl(), 
				param.getId());
		//@formatter:on
	}

	@Override
	public List<tr.com.jalgo.model.DataSource> findAll(tr.com.jalgo.model.DataSource param) {
		Query query = generateSelectQuery(param);
		List<tr.com.jalgo.model.DataSource> dataSources = jdbcTemplate.query(query.getSql(), new DataSourceMapper(),
				query.getParameters());

//		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.getSql());
//		List<Ohlc> ohlcs = new ArrayList<Ohlc>();
//		for (Map<?, ?> row : rows) {
//			Ohlc ohlc = new Ohlc();
//			ohlc.setDate((Date) row.get("Date"));
//			ohlc.setOpen((Long) row.get("Open"));
//			ohlcs.add(ohlc);
//		}

		return dataSources;
	}

	@Override
	public tr.com.jalgo.model.DataSource find(tr.com.jalgo.model.DataSource param) {
		Query query = generateSelectQuery(param);
		tr.com.jalgo.model.DataSource ohlc = jdbcTemplate.queryForObject(query.getSql(), new DataSourceMapper(),
				query.getParameters());
		return ohlc;
	}

	@Override
	public tr.com.jalgo.model.DataSource getById(long id) {
		Query query = generateSelectQuery(new tr.com.jalgo.model.DataSource(id));
		tr.com.jalgo.model.DataSource dataSource = jdbcTemplate.queryForObject(query.getSql(), new DataSourceMapper(),
				query.getParameters());
		return dataSource;
	}

	private Query generateSelectQuery(tr.com.jalgo.model.DataSource param) {

		String SQL = "SELECT * FROM " + TableType.DATASOURCES + " WHERE 1=1";
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
	public List<tr.com.jalgo.model.DataSource> getAll() {
		String SQL = "SELECT * FROM " + TableType.DATASOURCES + " WHERE 1=1";
		return jdbcTemplate.query(SQL, new DataSourceMapper());
	}
}
