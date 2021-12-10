package tr.com.jalgo.model.strategies;

import java.util.Random;
import java.util.logging.Level;

import tr.com.jalgo.model.Candle;
import tr.com.jalgo.model.types.IntervalType;
import tr.com.jalgo.model.types.StrategyResultType;

public class Strategy3 extends Strategy {

	public Strategy3() {
		super("Strategy3");
		// TODO Auto-generated constructor stub
	}

	@Override
	public StrategyResultType test(Candle ohlc, IntervalType interval) {
		this.log( this.getName()+ " test started" );
		StrategyResultType result = StrategyResultType.UNDEFINED;
		Random rand = new Random();
		int int_random = rand.nextInt(5);
		if (int_random == 3)
			result = StrategyResultType.BUY;
		else if (int_random == 0)
			result = StrategyResultType.SELL;
		else if (int_random == 5)
			result = StrategyResultType.STOP_LOSS;
		this.log( this.getName()+ " test finished" );
		return result;
	}

}
