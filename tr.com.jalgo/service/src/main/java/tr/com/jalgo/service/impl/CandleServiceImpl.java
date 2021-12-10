package tr.com.jalgo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.jalgo.model.Candle;
import tr.com.jalgo.repository.CandleRepository;
import tr.com.jalgo.service.CandleService;

@Service
public class CandleServiceImpl implements CandleService {

	@Autowired
	@Qualifier("CandleRepositoryJdbcImpl")
	CandleRepository candleRepository;

	@Override
	public long Add(Candle param) {
		return candleRepository.Add(param);
	}

	@Override
	public List<Candle> findAll(Candle param) {
		return candleRepository.findAll(param);
	}

	@Override
	public Candle find(Candle param) {
		return candleRepository.find(param);
	}

	@Override
	public Candle getById(long id) {
		return candleRepository.getById(id);
	}

	@Override
	public void update(Candle param) {
		candleRepository.update(param);
	}

}
