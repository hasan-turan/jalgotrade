package tr.com.jalgo.api.controller.binance;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.com.jalgo.binance.BinanceExchange;
import tr.com.jalgo.model.enums.EnumStatus;
import tr.com.jalgo.model.exchange.ApiResponse;
import tr.com.jalgo.model.exchange.CurrencyPair;
import tr.com.jalgo.model.exchange.IExchange;
import tr.com.jalgo.model.exchange.KlineParameter;

//https://www.baeldung.com/rest-vs-websockets
//https://spring.io/guides/gs/messaging-stomp-websocket/
///https://mkyong.com/webservices/jax-rs/restfull-java-client-with-java-net-url/
@RestController()
@RequestMapping(value = "/binance")
public class BinanceController {
	IExchange exchange;
	String API_KEY = "IAvfaD2REd60VUO5Z1NOA7DSioJC8w3mJfuEq9k8okF3NwHnpdbd49jWarpU2fH2";
	String SECRET_KEY = "IQ4Kd4kreGRfiQN108mr1fxsG2YjM4zSAmnNtessS0qwRarG3Ad84MoN1qFweD3j";

	public BinanceController() {
		exchange = new BinanceExchange(API_KEY, SECRET_KEY, true);
	}

	@GetMapping(value = { "/", "" }, produces = "application/json")
	public ApiResponse index() throws IOException {
		return exchange.ping();
	}

	@GetMapping(value = "/ticker", produces = "application/json")
	public ApiResponse ticker(@RequestBody CurrencyPair pair) throws IOException, ParseException {
		ApiResponse response = new ApiResponse();

		response.setStatus(EnumStatus.OK);

		return response;
	}

	@GetMapping(value = "/klines", produces = "application/json")
	public ApiResponse klines(@RequestBody KlineParameter param) throws IOException, ParseException {
		ApiResponse response = new ApiResponse();

		return response;
	}

}
