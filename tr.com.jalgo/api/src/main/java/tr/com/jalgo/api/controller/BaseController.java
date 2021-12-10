package tr.com.jalgo.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.com.jalgo.model.exceptions.ExchangeException;

@RestController
@RequestMapping
public class BaseController {
	@GetMapping(value = "/")
	public String index() throws ExchangeException {
		return "Jalgo application is runoning!";
	}
}
