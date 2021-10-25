package tr.com.jalgo.model.exchange;

import lombok.Data;


@Data
public class CurrencyPair   {
	private String baseSymbol;
	private String counterSymbol;
}
