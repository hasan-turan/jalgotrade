package tr.com.jalgo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.jalgo.model.Asset;
import tr.com.jalgo.repository.AssetRepository;
import tr.com.jalgo.service.AssetService;

@Service
public class AssetServiceImpl implements AssetService {

	@Autowired
	@Qualifier("AssetRepositoryJdbcImpl")
	AssetRepository assetRepository;

	@Override
	public long insert(Asset param) {
		return assetRepository.insert(param);
	}

	@Override
	public List<Asset> findAll(Asset param) {
		return assetRepository.findAll(param);
	}

	@Override
	public Asset find(Asset param) {
		return assetRepository.find(param);
	}

	@Override
	public Asset getById(long id) {
		return assetRepository.getById(id);
	}

	@Override
	public void update(Asset param) {
		assetRepository.update(param);
	}

	@Override
	public List<Asset> getAll() {
		return assetRepository.getAll();
	}

}
