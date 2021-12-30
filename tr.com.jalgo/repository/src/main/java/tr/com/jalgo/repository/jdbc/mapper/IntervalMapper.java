package tr.com.jalgo.repository.jdbc.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tr.com.jalgo.model.Interval;

public class IntervalMapper  implements RowMapper<Interval> {

	@Override
	public Interval mapRow(ResultSet rs, int rowNum) throws SQLException {
		 Interval interval= new Interval();
		 interval.setId(rs.getInt("Id"));
		 interval.setName(rs.getString("Name"));
		 return interval;
	}

}
