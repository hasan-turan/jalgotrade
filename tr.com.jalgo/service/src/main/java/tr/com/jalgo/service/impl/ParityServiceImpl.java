package tr.com.jalgo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.jalgo.model.Parity;
import tr.com.jalgo.repository.ParityRepository;
import tr.com.jalgo.service.ParityService;

@Service
public class ParityServiceImpl implements ParityService {

	@Autowired
	@Qualifier("ParityRepositoryJdbcImpl")
	ParityRepository parityRepository;

	@Override
	public long insert(Parity param) {
		return parityRepository.insert(param);
	}

	@Override
	public List<Parity> findAll(Parity param) {
		return parityRepository.findAll(param);
	}

	@Override
	public Parity find(Parity param) {
		return parityRepository.find(param);
	}

	@Override
	public Parity getById(long id) {
		return parityRepository.getById(id);
	}

	@Override
	public void update(Parity param) {
		parityRepository.update(param);
	}

	@Override
	public List<Parity> getAll() {
		return parityRepository.getAll();
	}

}
