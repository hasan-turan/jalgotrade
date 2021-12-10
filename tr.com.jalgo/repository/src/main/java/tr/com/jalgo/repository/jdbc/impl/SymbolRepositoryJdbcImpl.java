package tr.com.jalgo.repository.jdbc.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tr.com.jalgo.model.Symbol;
import tr.com.jalgo.repository.SymbolRepository;
import tr.com.jalgo.repository.jdbc.Query;
import tr.com.jalgo.repository.jdbc.mapper.SymbolMapper;

@Repository("SymbolRepositoryJdbcImpl")
public class SymbolRepositoryJdbcImpl implements SymbolRepository {

	private static String TABLE_NAME = "Symbols";

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public long Add(Symbol param) {
		//@formatter:off
		String SQL = "INSERT INTO "
				  + TABLE_NAME
				  + "("
				  + "Id,"
				  + "BaseSymbol,"
				  + "CounterSymbol"
				  + ")" 
				  +" VALUES (?,?,?)";
		return jdbcTemplate.update(SQL, 
				param.getId(),  
				param.getBaseSymbol(),
				param.getCounterSymbol()
			);
		//@formatter:on
	}

	@Override
	public void update(Symbol param) {
		//@formatter:off
		String SQL = "UPDATE "
		+ TABLE_NAME
		+ " Set "
		+ "BaseSymbol=?,"
		+ "CounterSymbol=?"
		+" )" 
		+ " WHERE Id=?";

		jdbcTemplate.update(SQL, 
				param.getBaseSymbol(),
				param.getCounterSymbol(),
				param.getId());
		//@formatter:on
	}

	@Override
	public List<Symbol> findAll(Symbol param) {
		Query query = generateSelectQuery(param);
		List<Symbol> ohlcs = jdbcTemplate.query(query.getSql(), new SymbolMapper(), query.getParameters());

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
	public Symbol find(Symbol param) {
		Query query = generateSelectQuery(param);
		Symbol ohlc = jdbcTemplate.queryForObject(query.getSql(), new SymbolMapper(), query.getParameters());
		return ohlc;
	}

	@Override
	public Symbol getById(long id) {
		Query query = generateSelectQuery(new Symbol(id));
		Symbol ohlc = jdbcTemplate.queryForObject(query.getSql(), new SymbolMapper(), query.getParameters());
		return ohlc;
	}

	private Query generateSelectQuery(Symbol param) {

		String SQL = "SELECT * FROM" + TABLE_NAME + " WHERE 1=1";
		List<Object> params = new ArrayList<Object>();

		if (param.getId() > 0) {
			SQL += " AND Id=?";
			params.add(param.getId());
		}

		if (param.getBaseSymbol() != null) {
			SQL += " AND BaseSymbol=?";
			params.add(param.getBaseSymbol());
		}
		
		if (param.getCounterSymbol() != null) {
			SQL += " AND CounterSymbol=?";
			params.add(param.getCounterSymbol());
		}

		return new Query(SQL, params.toArray());
	}
}
