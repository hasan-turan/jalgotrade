package tr.com.jalgo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.jalgo.model.Symbol;
import tr.com.jalgo.repository.SymbolRepository;
import tr.com.jalgo.service.SymbolService;

@Service
public class SymbolServiceImpl implements SymbolService {

	@Autowired
	@Qualifier("SymbolRepositoryJdbcImpl")
	SymbolRepository symbolRepository;

	@Override
	public long Add(Symbol param) {
		return symbolRepository.Add(param);
	}

	@Override
	public List<Symbol> findAll(Symbol param) {
		return symbolRepository.findAll(param);
	}

	@Override
	public Symbol find(Symbol param) {
		return symbolRepository.find(param);
	}

	@Override
	public Symbol getById(long id) {
		return symbolRepository.getById(id);
	}

	@Override
	public void update(Symbol param) {
		symbolRepository.update(param);
	}

}
