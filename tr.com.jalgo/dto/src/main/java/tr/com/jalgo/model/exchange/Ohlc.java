package tr.com.jalgo.model.exchange;

import java.util.Date;

import lombok.Data;

@Data
public class Ohlc {
	private float open;
	private float high;
	private float low;
	private float close;
	private float volume;
	private Date time;
}
