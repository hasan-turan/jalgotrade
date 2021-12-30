package tr.com.jalgo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.jalgo.model.Exchange;
import tr.com.jalgo.repository.ExchangeRepository;
import tr.com.jalgo.service.ExchangeService;

@Service
public class ExchangeServiceImpl implements ExchangeService {

	@Autowired
	@Qualifier("ExchangeRepositoryJdbcImpl")
	ExchangeRepository exchangeRepository;

	@Override
	public long insert(Exchange param) {
		return exchangeRepository.insert(param);
	}

	@Override
	public List<Exchange> findAll(Exchange param) {
		return exchangeRepository.findAll(param);
	}

	@Override
	public Exchange find(Exchange param) {
		return exchangeRepository.find(param);
	}

	@Override
	public Exchange getById(long id) {
		return exchangeRepository.getById(id);
	}

	@Override
	public void update(Exchange param) {
		exchangeRepository.update(param);
	}

	@Override
	public List<Exchange> getAll() {
		return exchangeRepository.getAll();
	}

}
