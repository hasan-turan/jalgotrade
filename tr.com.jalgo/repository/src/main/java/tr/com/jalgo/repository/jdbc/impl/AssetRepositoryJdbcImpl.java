package tr.com.jalgo.repository.jdbc.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tr.com.jalgo.model.Asset;
import tr.com.jalgo.repository.AssetRepository;
import tr.com.jalgo.repository.BaseRepository;
import tr.com.jalgo.repository.jdbc.Query;
import tr.com.jalgo.repository.jdbc.mapper.AssetMapper;
import tr.com.jalgo.repository.jdbc.types.TableType;

@Repository("AssetRepositoryJdbcImpl")
public class AssetRepositoryJdbcImpl extends BaseRepository implements AssetRepository {

	public AssetRepositoryJdbcImpl() {

	}

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public long insert(Asset param) {
		//@formatter:off
		String SQL = "INSERT INTO "
				  + TableType.ASSETS
				  + "("
				  + "Id,"
				  + "Symbol,"
				  + "Description"
				  + ")" 
				  +" VALUES (?,?,?)";
		return jdbcTemplate.update(SQL, 
				param.getId(),  
				param.getSymbol(),
				param.getDescription()
			);
		//@formatter:on
	}

	@Override
	public void update(Asset param) {
		//@formatter:off
		String SQL = "UPDATE "
		+ TableType.ASSETS
		+ " Set "
		+ "Abbreviation=?,"
		+ "Description=?"
		+" )" 
		+ " WHERE Id=?";

		jdbcTemplate.update(SQL, 
				param.getSymbol(),
				param.getDescription(),
				param.getId());
		//@formatter:on
	}

	@Override
	public List<Asset> findAll(Asset param) {
		Query query = generateSelectQuery(param);
		List<Asset> Assets = jdbcTemplate.query(query.getSql(), new AssetMapper(), query.getParameters());

//		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.getSql());
//		List<Ohlc> ohlcs = new ArrayList<Ohlc>();
//		for (Map<?, ?> row : rows) {
//			Ohlc ohlc = new Ohlc();
//			ohlc.setDate((Date) row.get("Date"));
//			ohlc.setOpen((Long) row.get("Open"));
//			ohlcs.add(ohlc);
//		}

		return Assets;
	}

	@Override
	public Asset find(Asset param) {
		Query query = generateSelectQuery(param);
		Asset Asset = jdbcTemplate.queryForObject(query.getSql(), new AssetMapper(), query.getParameters());
		return Asset;
	}

	@Override
	public Asset getById(long id) {
		Query query = generateSelectQuery(new Asset(id));
		Asset Asset = jdbcTemplate.queryForObject(query.getSql(), new AssetMapper(), query.getParameters());
		return Asset;
	}

	private Query generateSelectQuery(Asset param) {

		String SQL = " SELECT * FROM " + TableType.ASSETS + " WHERE 1=1 ";

		List<Object> params = new ArrayList<Object>();

		if (param.getId() > 0) {
			SQL += " AND Id=?";
			params.add(param.getId());
		}

		if (param.getSymbol() != null) {
			SQL += " AND Symbol=?";
			params.add(param.getSymbol());
		}

		return new Query(SQL, params.toArray());
	}

	@Override
	public List<Asset> getAll() {
		String SQL = "SELECT * FROM " + TableType.ASSETS + " WHERE 1=1";
		return jdbcTemplate.query(SQL, new AssetMapper());
	}

}
