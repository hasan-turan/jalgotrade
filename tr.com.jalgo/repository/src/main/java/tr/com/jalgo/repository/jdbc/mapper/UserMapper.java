package tr.com.jalgo.repository.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tr.com.jalgo.model.User;

public class UserMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("Id"));
		user.setUsername(rs.getString("Username"));
		user.setPassword(rs.getString("Password"));

		return user;
	}

}
