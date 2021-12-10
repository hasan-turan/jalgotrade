package tr.com.jalgo.model.strategies;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Getter;
import lombok.Setter;
import tr.com.jalgo.model.Candle;
import tr.com.jalgo.model.types.IntervalType;
import tr.com.jalgo.model.types.StrategyResultType;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "name")
@JsonSubTypes({ @Type(value = Strategy1.class, name = "Strategy1"), @Type(value = Strategy2.class, name = "Strategy2"),
		@Type(value = Strategy3.class, name = "Strategy3") })
public abstract class Strategy {

	public abstract StrategyResultType test(Candle ohlc, IntervalType interval);

	@Getter
	@Setter
	private String name;

	public Strategy() {

	}

	public Strategy(String name) {

		this.name = name;
	}

	public void log(String message) {
	 
		System.out.println(message);
	 
	}

}
