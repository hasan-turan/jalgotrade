package tr.com.jalgo.repository.jdbc.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tr.com.jalgo.model.ExchangeAccount;
import tr.com.jalgo.repository.ExchangeAccountRepository;
import tr.com.jalgo.repository.BaseRepository;
import tr.com.jalgo.repository.jdbc.Query;
import tr.com.jalgo.repository.jdbc.mapper.AccountMapper;
import tr.com.jalgo.repository.jdbc.types.TableType;

@Repository("ExchangeAccountRepositoryJdbcImpl")
public class ExchangeAccountRepositoryJdbcImpl extends BaseRepository implements ExchangeAccountRepository {

	public ExchangeAccountRepositoryJdbcImpl() {

	}

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public long insert(ExchangeAccount param) {
		//@formatter:off
		String SQL = "INSERT INTO "
				  + TableType.ACCOUNTS
				  + "("
				  + "Id,"
				  + "UserId,"
				  + "ExchangeId,"
				  + "ApiKey,"
				  + "SecretKey,"
				  +	"Type"
				  + ")" 
				  +" VALUES (?,?,?,?,?,?)";
		return jdbcTemplate.update(SQL, 
				param.getId(),  
				param.getUser().getId() ,
				param.getExchange().getId() ,
				param.getApiKey() ,
				param.getSecretKey()
				);
		//@formatter:on
	}

	@Override
	public void update(ExchangeAccount param) {
		//@formatter:off
		String SQL = "UPDATE "
		+ TableType.ACCOUNTS
		+ " Set "
		+ "UserId=?,"
		+ "ExchangeId=?,"
		+ "ApiKey=?,"
		+ "SecretKey=?,"
		+ "Type=?"
		+" )" 
		+ " WHERE Id=?";

		jdbcTemplate.update(SQL, 
				param.getUser().getId(), 
				param.getExchange().getId(),
				param.getApiKey(),
				param.getSecretKey(),
				param.getType(),
				param.getId());
		//@formatter:on
	}

	@Override
	public List<ExchangeAccount> findAll(ExchangeAccount param) {
		Query query = generateSelectQuery(param);
		List<ExchangeAccount> accounts = jdbcTemplate.query(query.getSql(), new AccountMapper(), query.getParameters());

//		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.getSql());
//		List<Ohlc> ohlcs = new ArrayList<Ohlc>();
//		for (Map<?, ?> row : rows) {
//			Ohlc ohlc = new Ohlc();
//			ohlc.setDate((Date) row.get("Date"));
//			ohlc.setOpen((Long) row.get("Open"));
//			ohlcs.add(ohlc);
//		}

		return accounts;
	}

	@Override
	public ExchangeAccount find(ExchangeAccount param) {
		Query query = generateSelectQuery(param);
		ExchangeAccount account = jdbcTemplate.queryForObject(query.getSql(), new AccountMapper(), query.getParameters());
		return account;
	}

	@Override
	public ExchangeAccount getById(long id) {
		Query query = generateSelectQuery(new ExchangeAccount(id));
		ExchangeAccount account = jdbcTemplate.queryForObject(query.getSql(), new AccountMapper(), query.getParameters());
		return account;
	}

	private Query generateSelectQuery(ExchangeAccount param) {
		String SQL = "SELECT * FROM " + TableType.ACCOUNTS + " WHERE 1=1";
		List<Object> params = new ArrayList<Object>();

		if (param.getId() > 0) {
			SQL += " AND Id=?";
			params.add(param.getId());
		}

		if (param.getUser() != null) {
			SQL += " AND UserId=?";
			params.add(param.getUser().getId());
		}

		if (param.getExchange() != null) {
			SQL += " AND ExchangeId=?";
			params.add(param.getExchange().getId());
		}

		
		if (param.getType() != null) {
			SQL += " AND Type=?";
			params.add(param.getType().getValue());
		}

		return new Query(SQL, params.toArray());
	}

	@Override
	public List<ExchangeAccount> getAll() {
		String SQL = "SELECT * FROM " + TableType.ACCOUNTS + " WHERE 1=1";
		return jdbcTemplate.query(SQL, new AccountMapper());
	}

}
