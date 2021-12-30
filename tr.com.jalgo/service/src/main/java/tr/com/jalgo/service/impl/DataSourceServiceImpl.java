package tr.com.jalgo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.jalgo.model.DataSource;
import tr.com.jalgo.repository.DataSourceRepository;
import tr.com.jalgo.service.DataSourceService;

@Service
public class DataSourceServiceImpl implements DataSourceService {

	@Autowired
	@Qualifier("DataSourceRepositoryJdbcImpl")
	DataSourceRepository dataSourceRepository;

	@Override
	public long insert(DataSource param) {
		return dataSourceRepository.insert(param);
	}

	@Override
	public List<DataSource> findAll(DataSource param) {
		return dataSourceRepository.findAll(param);
	}

	@Override
	public DataSource find(DataSource param) {
		return dataSourceRepository.find(param);
	}

	@Override
	public DataSource getById(long id) {
		return dataSourceRepository.getById(id);
	}

	@Override
	public void update(DataSource param) {
		dataSourceRepository.update(param);
	}

	@Override
	public List<DataSource> getAll() {
		return dataSourceRepository.getAll();
	}

}
