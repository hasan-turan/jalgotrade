package tr.com.jalgo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Candles")
public class Candle extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DataSource dataSource;
	private Exchange exchange;
	private Parity parity;
	private Interval interval;
	private long id;
	private long startTime;
	private long closeTime;
	private Date date;
	private double open;
	private double high;
	private double low;
	private double close;
	private double baseAssetVolume;
	private double counterAssetVolume;
	 
	public Candle(long id) {
		this.id=id;
	}
}
