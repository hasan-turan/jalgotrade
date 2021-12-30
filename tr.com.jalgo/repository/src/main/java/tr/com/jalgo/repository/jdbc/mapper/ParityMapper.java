package tr.com.jalgo.repository.jdbc.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tr.com.jalgo.model.Asset;
import tr.com.jalgo.model.Parity;

public class ParityMapper  implements RowMapper<Parity> {

	@Override
	public Parity mapRow(ResultSet rs, int rowNum) throws SQLException {
		 Parity parity= new Parity();
		 parity.setId(rs.getInt("Id"));
		 parity.setBaseAsset( new Asset( rs.getInt("BaseAssetId"),rs.getString("BaseAssetSymbol")));
		 parity.setCounterAsset(new Asset(rs.getInt("CounterAssetId"),rs.getString("CounterAssetSymbol")));
		 return parity;
	}

}
