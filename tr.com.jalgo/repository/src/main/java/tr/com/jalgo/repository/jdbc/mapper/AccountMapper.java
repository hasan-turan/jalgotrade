package tr.com.jalgo.repository.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tr.com.jalgo.model.Exchange;
import tr.com.jalgo.model.User;
import tr.com.jalgo.model.types.EnvironmentType;
import tr.com.jalgo.model.ExchangeAccount;

public class AccountMapper implements RowMapper<ExchangeAccount> {

	@Override
	public ExchangeAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
		ExchangeAccount userAccount = new ExchangeAccount();
		userAccount.setId(rs.getInt("Id"));
		userAccount.setUser(new User(rs.getLong("UserId")));
		userAccount.setExchange(new Exchange(rs.getInt("ExchangeId")));
		userAccount.setApiKey(rs.getString("ApiKey"));
		userAccount.setSecretKey(rs.getString("SecretKey"));
		userAccount.setType(EnvironmentType.values()[rs.getInt("Type")]);
		return userAccount;
	}

}
