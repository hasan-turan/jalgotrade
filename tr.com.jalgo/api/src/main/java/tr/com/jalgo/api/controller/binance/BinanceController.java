package tr.com.jalgo.api.controller.binance;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import tr.com.jalgo.binance.BinanceExchange;
import tr.com.jalgo.model.Symbol;
import tr.com.jalgo.model.exceptions.ExchangeException;
import tr.com.jalgo.model.exchange.ApiResponse;
import tr.com.jalgo.model.strategies.Strategy;
import tr.com.jalgo.model.types.IntervalType;
import tr.com.jalgo.model.types.StatusType;;

//https://www.baeldung.com/rest-vs-websockets
//https://spring.io/guides/gs/messaging-stomp-websocket/
///https://mkyong.com/webservices/jax-rs/restfull-java-client-with-java-net-url/
@RestController
@RequestMapping(value = "/binance")
public class BinanceController {
	BinanceExchange binance;
	String TEST_API_KEY = "IAvfaD2REd60VUO5Z1NOA7DSioJC8w3mJfuEq9k8okF3NwHnpdbd49jWarpU2fH2";
	String TEST_SECRET_KEY = "IQ4Kd4kreGRfiQN108mr1fxsG2YjM4zSAmnNtessS0qwRarG3Ad84MoN1qFweD3j";

	String LIVE_API_KEY = "WBilz8ikRmkgOws8OBq9B6oo3bOJRe6MFVtUZJ2y3XA93iIPC7W2si5WrlFwpCoa";
	String LIVE_SECRET_KEY = "BNo3pAdzmPCyB910S8vnuCZjyj7XD6l7IuaBM6rI01zqoWnxRZtDy8QhWHLodgEs";

	public BinanceController() {
		binance = new BinanceExchange(LIVE_API_KEY, LIVE_SECRET_KEY, true);
	}

	@GetMapping(value = { "/", "", "/ping" })
	public ApiResponse index() throws ExchangeException {
		return binance.ping();
	}

	@GetMapping(value = "/time")
	public ApiResponse time() throws ExchangeException {
		return binance.time();
	}

 
	@GetMapping(value = "/trade")
	public ApiResponse trade(@RequestBody ObjectNode json  ) throws ExchangeException, JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Symbol currency = mapper.treeToValue(json.get("currency"), Symbol.class);
		IntervalType interval = mapper.treeToValue(json.get("interval"), IntervalType.class);
		Strategy[] strategies = mapper.treeToValue(json.get("strategies"), Strategy[].class);
		ApiResponse apiResponse = new ApiResponse();

		binance.trade(currency, interval, Arrays.asList(strategies));

		return apiResponse;
	}

	@GetMapping(value = "/ticker")
	public ApiResponse ticker(@RequestBody Symbol currency) throws IOException, ParseException {
		ApiResponse response = new ApiResponse(StatusType.OK);

		return response;
	}

}
