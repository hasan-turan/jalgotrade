package tr.com.jalgo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.jalgo.model.Indicator;
import tr.com.jalgo.repository.IndicatorRepository;
import tr.com.jalgo.service.IndicatorService;

@Service
public class IndicatorServiceImpl implements IndicatorService {

	@Autowired
	@Qualifier("IndicatorRepositoryJdbcImpl")
	IndicatorRepository indicatorRepository;

	@Override
	public long insert(Indicator param) {
		return indicatorRepository.insert(param);
	}

	@Override
	public List<Indicator> findAll(Indicator param) {
		return indicatorRepository.findAll(param);
	}

	@Override
	public Indicator find(Indicator param) {
		return indicatorRepository.find(param);
	}

	@Override
	public Indicator getById(long id) {
		return indicatorRepository.getById(id);
	}

	@Override
	public void update(Indicator param) {
		indicatorRepository.update(param);
	}

	@Override
	public List<Indicator> getAll() {
		return indicatorRepository.getAll();
	}

}
