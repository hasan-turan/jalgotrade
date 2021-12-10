package tr.com.jalgo.repository.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tr.com.jalgo.model.Exchange;
import tr.com.jalgo.model.User;
import tr.com.jalgo.model.Account;

public class AccountMapper implements RowMapper<Account> {

	@Override
	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
		Account userAccount = new Account();
		userAccount.setId(rs.getInt("Id"));
		userAccount.setUser(new User(rs.getLong("UserId")));
		userAccount.setExchange(new Exchange(rs.getString("ExchangeId")));
		userAccount.setApiKey(rs.getString("ApiKey"));
		userAccount.setSecretKey(rs.getString("SecretKey"));
		return userAccount;
	}

}
