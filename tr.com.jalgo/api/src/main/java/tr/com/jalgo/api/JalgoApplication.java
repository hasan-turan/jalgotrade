package tr.com.jalgo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import tr.com.jalgo.binance.BinanceExchange;
import tr.com.jalgo.binance.BinanceProperties;
import tr.com.jalgo.model.Exchange;
import tr.com.jalgo.model.Parity;
import tr.com.jalgo.model.types.EnvironmentType;
import tr.com.jalgo.service.ExchangeService;
import tr.com.jalgo.service.ParityService;

@SpringBootApplication
@ComponentScan(basePackages = { "tr.com.jalgo.*.**" })
public class JalgoApplication extends SpringBootServletInitializer  implements CommandLineRunner {

	@Autowired
	ExchangeService exchangeService;
	
	@Autowired
	ParityService parityService;

	List<Exchange> exchanges;
	List<Parity> parities;
	
	public static void main(String[] args) {
		
//		exchanges= exchangeService.getAll();
//		parities= parityService.getAll();
//		
//		
//		Exchange binance=exchanges.stream().filter(e->e.getName().equals("Binance")).findFirst().orElse(null);
//		Parity btcUsd= parities.stream().filter(a->a.getBaseAsset().getSymbol().equals("BTC") && a.getCounterAsset().getSymbol().equals("USDT")).findFirst().orElse(null);
//		List<ExchangeTask> exchangeTasks = new ArrayList<ExchangeTask>();
//		
		SpringApplication.run(JalgoApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		BinanceExchange binance = new BinanceExchange(BinanceProperties.LIVE_API_KEY, BinanceProperties.LIVE_SECRET_KEY,
				EnvironmentType.LIVE);
		binance.start(EnvironmentType.LIVE);
		
	}
 

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(JalgoApplication.class);
//	}
//
// 

}
