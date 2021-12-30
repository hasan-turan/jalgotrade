package tr.com.jalgo.repository.jdbc.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tr.com.jalgo.model.DataSource;

public class DataSourceMapper  implements RowMapper<DataSource> {

	@Override
	public DataSource mapRow(ResultSet rs, int rowNum) throws SQLException {
		 DataSource dataSource= new DataSource();
		 dataSource.setId(rs.getInt("Id"));
		 dataSource.setName(rs.getString("Name"));
		 dataSource.setUrl(rs.getString("Url"));
		 return dataSource;
	}

}
