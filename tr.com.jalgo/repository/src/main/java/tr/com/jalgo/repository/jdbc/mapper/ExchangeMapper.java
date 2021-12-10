package tr.com.jalgo.repository.jdbc.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tr.com.jalgo.model.Exchange;

public class ExchangeMapper  implements RowMapper<Exchange> {

	@Override
	public Exchange mapRow(ResultSet rs, int rowNum) throws SQLException {
		 Exchange exchange= new Exchange();
		 exchange.setId(rs.getInt("Id"));
		 exchange.setName(rs.getString("Name"));
		 exchange.setUrl(rs.getString("Url"));
		 exchange.setWsUrl(rs.getString("WsUrl"));
		 return exchange;
	}

}
