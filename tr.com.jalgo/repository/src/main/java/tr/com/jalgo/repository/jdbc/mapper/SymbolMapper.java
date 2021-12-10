package tr.com.jalgo.repository.jdbc.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tr.com.jalgo.model.Symbol;

public class SymbolMapper  implements RowMapper<Symbol> {

	@Override
	public Symbol mapRow(ResultSet rs, int rowNum) throws SQLException {
		 Symbol symbol= new Symbol();
		 symbol.setId(rs.getInt("Id"));
		 symbol.setBaseSymbol(rs.getString("BaseSymbol"));
		 symbol.setCounterSymbol(rs.getString("CounterSymbol"));
		 return symbol;
	}

}
