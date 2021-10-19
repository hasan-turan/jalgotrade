package tr.com.jalgo.dto;

import java.util.Date;

import lombok.Data;

@Data
public class KlineParameter {
	private SymbolPair symbolPair;
	private Date startDate;
	private Date endDate;
	private String interval;
}
