package tr.com.jalgo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.jalgo.model.Interval;
import tr.com.jalgo.repository.IntervalRepository;
import tr.com.jalgo.service.IntervalService;

@Service
public class IntervalServiceImpl implements IntervalService {

	@Autowired
	@Qualifier("IntervalRepositoryJdbcImpl")
	IntervalRepository intervalRepository;

	@Override
	public long Add(Interval param) {
		return intervalRepository.Add(param);
	}

	@Override
	public List<Interval> findAll(Interval param) {
		return intervalRepository.findAll(param);
	}

	@Override
	public Interval find(Interval param) {
		return intervalRepository.find(param);
	}

	@Override
	public Interval getById(long id) {
		return intervalRepository.getById(id);
	}

	@Override
	public void update(Interval param) {
		intervalRepository.update(param);
	}

}
