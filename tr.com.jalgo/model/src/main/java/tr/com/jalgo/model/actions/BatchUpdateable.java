package tr.com.jalgo.model.actions;

import tr.com.jalgo.model.BaseModel;

public interface BatchUpdateable<T extends BaseModel> {
	public void update(T param);
}
