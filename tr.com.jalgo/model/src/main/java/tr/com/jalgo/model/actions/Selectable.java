package tr.com.jalgo.model.actions;

import tr.com.jalgo.model.BaseModel;
import java.util.List;

public interface Selectable<T extends BaseModel> {
	
	public List<T> getAll();
	
	public List<T> findAll(T param);

	public T find(T param);
	
	public T getById(long id);
}
