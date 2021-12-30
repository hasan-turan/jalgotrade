package tr.com.jalgo.repository.jdbc.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tr.com.jalgo.model.Indicator;

public class IndicatorMapper  implements RowMapper<Indicator> {

	@Override
	public Indicator mapRow(ResultSet rs, int rowNum) throws SQLException {
		 Indicator indicator= new Indicator();
		 indicator.setId(rs.getInt("Id"));
		 indicator.setTitle(rs.getString("Title"));
		 indicator.setDescription(rs.getString("Description"));
		 indicator.setFormula(rs.getString("Formula"));
		 return indicator;
	}

}
