package tr.com.jalgo.model.actions;

import tr.com.jalgo.model.BaseModel;

public interface Deletable<T extends BaseModel> {
	public void delete(T param);
	
	public void deleteById(long id);
}
