package tr.com.jalgo.repository.jdbc.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tr.com.jalgo.model.Candle;
import tr.com.jalgo.model.exceptions.RepositoryException;
import tr.com.jalgo.repository.BaseRepository;
import tr.com.jalgo.repository.CandleRepository;
import tr.com.jalgo.repository.jdbc.Query;
import tr.com.jalgo.repository.jdbc.mapper.CandleMapper;
import tr.com.jalgo.repository.jdbc.types.TableType;

@Repository("CandleRepositoryJdbcImpl")

public class CandleRepositoryJdbcImpl extends BaseRepository implements CandleRepository {

	public CandleRepositoryJdbcImpl() {

	}

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public long insert(Candle param) {
		//@formatter:off
		String SQL = getInsertQuery();
		return jdbcTemplate.update(SQL, 
				param.getDataSource().getId(),
				param.getExchange().getId(),
				param.getParity().getId(),
				param.getInterval().getId(),
				param.getDate(), 
				param.getStartTime(),
				param.getCloseTime(),
				param.getOpen(), 
				param.getHigh(), 
				param.getLow(), 
				param.getClose(), 
				param.getBaseAssetVolume(),
				param.getCounterAssetVolume());
		//@formatter:on
	}

	@Override
	public void update(Candle param) {
		//@formatter:off
		String SQL = "UPDATE "
		+ TableType.CANDLES
		+ "DataSourceId=?,"
		+ "ExchangeId=?,"
		+ "ParityId=?,"
		+ "IntervalId=?,"
		+ "[Date]=?,"
		+ "StartTime=?,"
		+ "CloseTime=?,"
		+ "[Open]=?,"
		+ "[High]=?,"
		+ "[Low]=?,"
		+ "[Close]=?,"
		+ "BaseAssetVolume=?,"
		+ "CounterAssetVolume=?"
		+")"  
		+ " WHERE Id=?";

		jdbcTemplate.update(SQL, 
				param.getDataSource().getId(), 
				param.getExchange().getId(), 
				param.getParity().getId(), 
				param.getInterval().getId(), 
				param.getDate(), 
				param.getStartTime(), 
				param.getCloseTime(), 
				param.getOpen(),
				param.getHigh(), 
				param.getLow(), 
				param.getClose(), 
				param.getBaseAssetVolume(),
				param.getCounterAssetVolume(),
				param.getId());
		//@formatter:on
	}

	@Override
	public List<Candle> findAll(Candle param) {
		Query query = getSelectQuery(param);
		List<Candle> candles = jdbcTemplate.query(query.getSql(), new CandleMapper(), query.getParameters());

//		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.getSql());
//		List<result> results = new ArrayList<result>();
//		for (Map<?, ?> row : rows) {
//			result result = new result();
//			result.setDate((Date) row.get("Date"));
//			result.setOpen((Long) row.get("Open"));
//			results.add(result);
//		}

		return candles;
	}

	@Override
	public Candle find(Candle param) {
		Query query = getSelectQuery(param);
		Candle candle = jdbcTemplate.queryForObject(query.getSql(), new CandleMapper(), query.getParameters());
		return candle;
	}

	@Override
	public Candle getById(long id) {
		Query query = getSelectQuery(new Candle(id));
		Candle candle = jdbcTemplate.queryForObject(query.getSql(), new CandleMapper(), query.getParameters());
		return candle;
	}

	@Override
	public List<Candle> getAll() {
		String SQL = "SELECT * FROM " + TableType.CANDLES + " WHERE 1=1";
		return jdbcTemplate.query(SQL, new CandleMapper());
	}

	@Override
	public long[] batchInsert(List<Candle> params) {
		List<Long> results = new ArrayList<Long>();
		// int[] batchResult = null;
		Connection connection = null;
		PreparedStatement statement = null;
		int itemCount = 0;
		System.out.println("***********Batch insert started***************");

		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			statement = connection.prepareStatement(getInsertQuery());
			connection.setAutoCommit(false);

			for (Candle candle : params) {
				itemCount++;

				statement.setLong(1, candle.getDataSource().getId());
				statement.setLong(2, candle.getExchange().getId());
				statement.setLong(3, candle.getParity().getId());
				statement.setLong(4, candle.getInterval().getId());
				statement.setDate(5, new java.sql.Date(candle.getDate().getTime()));
				statement.setLong(6, candle.getStartTime());
				statement.setLong(7, candle.getCloseTime());
				statement.setDouble(8, candle.getOpen());
				statement.setDouble(9, candle.getHigh());
				statement.setDouble(10, candle.getLow());
				statement.setDouble(11, candle.getClose());
				statement.setDouble(12, candle.getBaseAssetVolume());
				statement.setDouble(13, candle.getCounterAssetVolume());
				statement.addBatch();

				System.out.println("Statement: " + itemCount);

			}
			statement.executeBatch();
			connection.commit();

		} catch (Exception e) {
			if (connection != null)
				try {
					connection.rollback();
				} catch (SQLException ex) {
					throw new RepositoryException(ex.getMessage());
				}
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (Exception ex) {
				throw new RepositoryException(ex.getMessage());
			}

		}

		System.out.println("***********Batch insert finished***************");

		return results.stream().mapToLong(l -> l).toArray();
	}

	private String getInsertQuery() {
		//@formatter:off
		return "INSERT INTO " + TableType.CANDLES 
				+ "(" 
				+ "DataSourceId," 
				+ "ExchangeId," 
				+ "ParityId," 
				+ "IntervalId,"
				+ "[Date]," 
				+ "StartTime," 
				+ "CloseTime," 
				+ "[Open]," 
				+ "[High]," 
				+ "[Low]," 
				+ "[Close]," 
				+ "BaseAssetVolume,"
				+ "CounterAssetVolume"
				+ ")" 
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		//@formatter:on
	}

	private Query getSelectQuery(Candle param) {
		//@formatter:off
		String SQL = "SELECT  "+
						"Candles.*,"+
						"Datasources.Name DataSource,"+
						"Exchanges.Name Exchange,"+
						"BaseAsset.Symbol BaseAssetSymbol,"+
						"CounterAsset.Symbol CounterAssetSymbol ,"+
						"Intervals.Value Interval"+
					"FROM " + TableType.CANDLES + " Candles "+
						" INNER JOIN " +TableType.DATASOURCES +" DataSources on Candles.DataSourceId= DataSources.Id"+
						" INNER JOIN "+TableType.EXCHANGES+ " Exchanges on Candles.ExchangeId= Exchanges.Id "+
						" INNER JOIN "+TableType.PARITIES+ " Parities on Candles.SymbolId= Parities.Id "+
						" INNER JOIN "+TableType.ASSETS+ "  BaseAsset on Parities.BaseAssetId= BaseAsset.Id "+
						" INNER JOIN "+TableType.ASSETS+ " CounterAsset on Parities.CounterAssetId= CounterAsset.Id "+
						" INNER JOIN "+TableType.INTERVALS+ " Intervals on Candles.IntervalId= Intervals.Id "+
					" WHERE 1=1";
		//@formatter:on
		List<Object> params = new ArrayList<Object>();

		if (param.getId() > 0) {
			SQL += " AND Candles.Id=?";
			params.add(param.getId());
		}

		if (param.getDataSource() != null) {
			SQL += " AND Candles.DataSourceId=?";
			params.add(param.getDataSource().getId());
		}

		if (param.getExchange() != null) {
			SQL += " AND Candles.ExchangeId=?";
			params.add(param.getExchange().getId());
		}

		if (param.getParity() != null) {
			SQL += " AND Candles.ParityId=?";
			params.add(param.getParity().getId());
		}

		if (param.getInterval() != null) {
			SQL += " AND Candles.IntervalId=?";
			params.add(param.getInterval().getId());
		}

		if (param.getDate() != null) {
			SQL += " AND Candles.Date=?";
			params.add(param.getDate());
		}

		if (param.getStartTime() > 0) {
			SQL += " AND Candles.StartTime=?";
			params.add(param.getStartTime());
		}
		
		if (param.getCloseTime() > 0) {
			SQL += " AND Candles.CloseTime=?";
			params.add(param.getCloseTime());
		}

		return new Query(SQL, params.toArray());
	}

}
