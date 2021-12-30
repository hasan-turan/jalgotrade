package tr.com.jalgo.repository.jdbc.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tr.com.jalgo.model.Indicator;
import tr.com.jalgo.repository.BaseRepository;
import tr.com.jalgo.repository.IndicatorRepository;
import tr.com.jalgo.repository.jdbc.Query;
import tr.com.jalgo.repository.jdbc.mapper.IndicatorMapper;
import tr.com.jalgo.repository.jdbc.types.TableType;

@Repository("IndicatorRepositoryJdbcImpl")
public class IndicatorRepositoryJdbcImpl extends BaseRepository implements IndicatorRepository {

	private JdbcTemplate jdbcTemplate;

	public IndicatorRepositoryJdbcImpl() {
		 
	}



	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public long insert(Indicator param) {
		//@formatter:off
		String SQL = "INSERT INTO "
				  + TableType.INDICATORS
				  + "("
				  + "Id,"
				  + "Title,"
				  + "Description,"
				  + "Formula"			
				  + ")" 
				  +" VALUES (?,?,?,?)";
		return jdbcTemplate.update(SQL, 
				param.getId(),  
				param.getTitle() ,
				param.getDescription(), 
				param.getFormula());
		//@formatter:on
	}

	@Override
	public void update(Indicator param) {
		//@formatter:off
		String SQL = "UPDATE "
		+ TableType.INDICATORS
		+ " Set "
		+ "Title=?,"
		+ "Description=?,"
		+ "Formula=?"
		+" )" 
		+ " WHERE Id=?";

		jdbcTemplate.update(SQL, 
				param.getTitle(), 
				param.getDescription(),
				param.getFormula(),
				param.getId());
		//@formatter:on
	}

	@Override
	public List<Indicator> findAll(Indicator param) {
		Query query = generateSelectQuery(param);
		List<Indicator> result = jdbcTemplate.query(query.getSql(), new IndicatorMapper(), query.getParameters());
		return result;
	}

	@Override
	public Indicator find(Indicator param) {
		Query query = generateSelectQuery(param);
		Indicator Indicator = jdbcTemplate.queryForObject(query.getSql(), new IndicatorMapper(), query.getParameters());
		return Indicator;
	}

	@Override
	public Indicator getById(long id) {
		Query query =generateSelectQuery(new Indicator(id));
		Indicator Indicator = jdbcTemplate.queryForObject(query.getSql(), new IndicatorMapper(), query.getParameters());
		return Indicator;
	}

	private Query generateSelectQuery(Indicator param) {

		String SQL = "SELECT * FROM " + TableType.INDICATORS + " WHERE 1=1";
		List<Object> params = new ArrayList<Object>();

		if (param.getId() > 0) {
			SQL += " AND Id=?";
			params.add(param.getId());
		}

		if (param != null) {
			SQL += " AND Title=?";
			params.add(param.getTitle());
		}
 
		return new Query(SQL, params.toArray());
	}

	@Override
	public List<Indicator> getAll() {
		String SQL = "SELECT * FROM " + TableType.INDICATORS + " WHERE 1=1";
		return jdbcTemplate.query(SQL, new IndicatorMapper());
	}
}
