package tr.com.jalgo.api.controller.binance;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import tr.com.jalgo.binance.BinanceExchange;
import tr.com.jalgo.binance.BinanceProperties;
import tr.com.jalgo.common.utils.ReflectionUtils;
import tr.com.jalgo.model.ApiResponse;
import tr.com.jalgo.model.Parity;
import tr.com.jalgo.model.exceptions.ExchangeException;
import tr.com.jalgo.model.strategies.Strategy;
import tr.com.jalgo.model.types.IntervalType;;

//https://www.baeldung.com/rest-vs-websockets
//https://spring.io/guides/gs/messaging-stomp-websocket/
///https://mkyong.com/webservices/jax-rs/restfull-java-client-with-java-net-url/
@RestController
@RequestMapping(value = "/binance")
@CrossOrigin //@CrossOrigin(origins = "http://localhost:4200", allowedHeaders="*")
public class BinanceController {
	BinanceExchange binance;
 

	public BinanceController() {
		
		binance = new BinanceExchange(BinanceProperties.LIVE_API_KEY, BinanceProperties.LIVE_SECRET_KEY, false);
	}

	@GetMapping(value = "/help")
	public ApiResponse help() throws ExchangeException {
		StringBuilder help = new StringBuilder();
		Arrays.stream(this.getClass().getMethods()).forEach(method -> {
			System.out.println(method.getName());
			GetMapping getMapping = (GetMapping) ReflectionUtils.getMethodAnnotation(this.getClass(), method,
					GetMapping.class);

			if (getMapping != null) {
				help.append(String.join(",", getMapping.value()));
			}
		});
		return new ApiResponse(HttpStatus.SC_OK, help.toString());
	}

	@GetMapping(value = "/ping")
	public ApiResponse index() throws ExchangeException {
		return binance.ping();
	}

	@GetMapping(value = "/time")
	public ApiResponse time() throws ExchangeException {
		return binance.getTime();
	}

	@GetMapping(value = { "/klines" })
	public ApiResponse klines(@RequestBody ObjectNode json)
			throws ExchangeException, JsonProcessingException, IllegalArgumentException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		Parity parity = mapper.treeToValue(json.get("parity"), Parity.class);
		IntervalType interval = mapper.treeToValue(json.get("interval"), IntervalType.class);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
		Date startTime = dateFormat.parse(dateFormat.format(dateFormat.parse(json.get("startTime").textValue())));
		Date endTime = dateFormat.parse(json.get("endTime").textValue());

		return binance.getKlines(parity, interval, startTime, endTime, 1000);
	}

	@GetMapping(value = "/trade")
	public ApiResponse trade(@RequestBody ObjectNode json)
			throws ExchangeException, JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Parity parity = mapper.treeToValue(json.get("parity"), Parity.class);
		IntervalType interval = mapper.treeToValue(json.get("interval"), IntervalType.class);
		Strategy[] strategies = mapper.treeToValue(json.get("strategies"), Strategy[].class);
		ApiResponse apiResponse = new ApiResponse();

		binance.trade(parity, interval, Arrays.asList(strategies));

		return apiResponse;
	}

	 

	@GetMapping(value = "/ticker")
	public ApiResponse ticker(@RequestBody Parity currency) throws IOException, ParseException {
		ApiResponse response = new ApiResponse(HttpStatus.SC_ACCEPTED);

		return response;
	}

}
