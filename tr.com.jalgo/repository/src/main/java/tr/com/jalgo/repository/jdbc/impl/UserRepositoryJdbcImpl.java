package tr.com.jalgo.repository.jdbc.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tr.com.jalgo.model.User;
import tr.com.jalgo.repository.BaseRepository;
import tr.com.jalgo.repository.UserRepository;
import tr.com.jalgo.repository.jdbc.Query;
import tr.com.jalgo.repository.jdbc.mapper.UserMapper;
import tr.com.jalgo.repository.jdbc.types.TableType;

@Repository("UserRepositoryJdbcImpl")
public class UserRepositoryJdbcImpl extends BaseRepository implements UserRepository {

	private JdbcTemplate jdbcTemplate;

	public UserRepositoryJdbcImpl() {
	 
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public long insert(User param) {
		//@formatter:off
		String SQL = "INSERT INTO "
				  + TableType.USERS
				  + "("
				  + "Id,"
				  + "Username,"
				  + "Password"
				  + ")" 
				  +" VALUES (?,?,?)";
		return jdbcTemplate.update(SQL, 
				param.getId(),  
				param.getUsername() ,
				param.getPassword());
		//@formatter:on
	}

	@Override
	public void update(User param) {
		//@formatter:off
		String SQL = "UPDATE "
		+ TableType.USERS
		+ " Set "
		+ "Username=?,"
		+ "Password=?"
		+" )" 
		+ " WHERE Id=?";

		jdbcTemplate.update(SQL, 
				param.getUsername(), 
				param.getPassword(),
				param.getId());
		//@formatter:on
	}

	@Override
	public List<User> findAll(User param) {
		Query query = generateSelectQuery(param);
		List<User> users = jdbcTemplate.query(query.getSql(), new UserMapper(), query.getParameters());

//		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.getSql());
//		List<Ohlc> ohlcs = new ArrayList<Ohlc>();
//		for (Map<?, ?> row : rows) {
//			Ohlc ohlc = new Ohlc();
//			ohlc.setDate((Date) row.get("Date"));
//			ohlc.setOpen((Long) row.get("Open"));
//			ohlcs.add(ohlc);
//		}

		return users;
	}

	@Override
	public User find(User param) {
		Query query = generateSelectQuery(param);
		User user = jdbcTemplate.queryForObject(query.getSql(), new UserMapper(), query.getParameters());
		return user;
	}

	@Override
	public User getById(long id) {
		Query query = generateSelectQuery(new User(id));
		User user = jdbcTemplate.queryForObject(query.getSql(), new UserMapper(), query.getParameters());
		return user;
	}

	private Query generateSelectQuery(User param) {

		String SQL = "SELECT * FROM " + TableType.USERS + " WHERE 1=1";
		List<Object> params = new ArrayList<Object>();

		if (param.getId() > 0) {
			SQL += " AND Id=?";
			params.add(param.getId());
		}

		if (param.getUsername() != null) {
			SQL += " AND Username=?";
			params.add(param.getUsername());
		}

		if (param.getPassword() != null) {
			SQL += " AND Password=?";
			params.add(param.getPassword());
		}

		return new Query(SQL, params.toArray());
	}

	@Override
	public List<User> getAll() {
		String SQL = "SELECT * FROM " + TableType.USERS + " WHERE 1=1";
		return jdbcTemplate.query(SQL, new UserMapper());
	}
}
