package tr.com.jalgo.model.exchange;

import java.util.Date;

import lombok.Data;

@Data
public class KlineParameter {
	private Pair pair;
	private Date startDate;
	private Date endDate;
	private EnumInterval interval;
}
