package tr.com.jalgo.model.actions;

import tr.com.jalgo.model.BaseModel;

public interface Deletable<T extends BaseModel> {
	public void Delete(T param);
	
	public void DeleteById(long id);
}
