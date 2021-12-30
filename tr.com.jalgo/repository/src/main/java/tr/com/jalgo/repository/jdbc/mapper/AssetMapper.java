package tr.com.jalgo.repository.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tr.com.jalgo.model.Asset;
import tr.com.jalgo.model.Asset;

public class AssetMapper implements RowMapper<Asset> {

	@Override
	public Asset mapRow(ResultSet rs, int rowNum) throws SQLException {
		Asset asset = new Asset();
		asset.setId(rs.getInt("Id"));
		asset.setSymbol(rs.getString("Symbol"));
		asset.setDescription(rs.getString("Description"));
		return asset;
	}

}
