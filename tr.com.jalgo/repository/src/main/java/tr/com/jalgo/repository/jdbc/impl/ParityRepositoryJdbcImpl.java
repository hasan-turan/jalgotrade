package tr.com.jalgo.repository.jdbc.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tr.com.jalgo.model.Parity;
import tr.com.jalgo.repository.BaseRepository;
import tr.com.jalgo.repository.ParityRepository;
import tr.com.jalgo.repository.jdbc.Query;
import tr.com.jalgo.repository.jdbc.mapper.ParityMapper;
import tr.com.jalgo.repository.jdbc.types.TableType;

@Repository("ParityRepositoryJdbcImpl")
public class ParityRepositoryJdbcImpl extends BaseRepository implements ParityRepository {

	private JdbcTemplate jdbcTemplate;

	public ParityRepositoryJdbcImpl() {

	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public long insert(Parity param) {
		//@formatter:off
		String SQL = "INSERT INTO "
				  + TableType.PARITIES
				  + "("
				  + "Id,"
				  + "BaseAssetId,"
				  + "CounterAssetId"
				  + ")" 
				  +" VALUES (?,?,?)";
		return jdbcTemplate.update(SQL, 
				param.getId(),  
				param.getBaseAsset().getId(),
				param.getCounterAsset().getId()
			);
		//@formatter:on
	}

	@Override
	public void update(Parity param) {
		//@formatter:off
		String SQL = "UPDATE "
		+ TableType.PARITIES
		+ " Set "
		+ "BaseAssetId=?,"
		+ "CounterAssetId=?"
		+" )" 
		+ " WHERE Id=?";

		jdbcTemplate.update(SQL, 
				param.getBaseAsset().getId(),
				param.getCounterAsset().getId(),
				param.getId());
		//@formatter:on
	}

	@Override
	public List<Parity> findAll(Parity param) {
		Query query = generateSelectQuery(param);
		List<Parity> Paritys = jdbcTemplate.query(query.getSql(), new ParityMapper(), query.getParameters());

//		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.getSql());
//		List<Ohlc> ohlcs = new ArrayList<Ohlc>();
//		for (Map<?, ?> row : rows) {
//			Ohlc ohlc = new Ohlc();
//			ohlc.setDate((Date) row.get("Date"));
//			ohlc.setOpen((Long) row.get("Open"));
//			ohlcs.add(ohlc);
//		}

		return Paritys;
	}

	@Override
	public Parity find(Parity param) {
		Query query = generateSelectQuery(param);
		Parity Parity = jdbcTemplate.queryForObject(query.getSql(), new ParityMapper(), query.getParameters());
		return Parity;
	}

	@Override
	public Parity getById(long id) {
		Query query = generateSelectQuery(new Parity(id));
		Parity Parity = jdbcTemplate.queryForObject(query.getSql(), new ParityMapper(), query.getParameters());
		return Parity;
	}

	private String getSelectSQL() {
		//@formatter:off
		return " SELECT Parities.* , "+
						 " BaseAsset.Symbol BaseAssetSymbol,  "+
						 " CounterAsset.Symbol CounterAssetSymbol  "+
						 " FROM "  + TableType.PARITIES+  " Parities "+
								" INNER JOIN "+ TableType.ASSETS +" BaseAsset on Parities.BaseAssetId= BaseAsset.Id "+
								" INNER JOIN "+ TableType.ASSETS +" CounterAsset on Parities.counterAssetId= CounterAsset.Id "+
							" WHERE 1=1";
				//@formatter:on
	}

	private Query generateSelectQuery(Parity param) {

		String SQL = getSelectSQL();

		List<Object> params = new ArrayList<Object>();

		if (param.getId() > 0) {
			SQL += " AND Id=?";
			params.add(param.getId());
		}

		if (param.getBaseAsset() != null) {
			SQL += " AND BaseAssetId=?";
			params.add(param.getBaseAsset().getId());
		}

		if (param.getCounterAsset() != null) {
			SQL += " AND CounterAssetId=?";
			params.add(param.getCounterAsset().getId());
		}

		return new Query(SQL, params.toArray());
	}

	@Override
	public List<Parity> getAll() {
		String SQL = getSelectSQL();
		return jdbcTemplate.query(SQL, new ParityMapper());
	}

}
