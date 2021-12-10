package tr.com.jalgo.model.actions;

import tr.com.jalgo.model.BaseModel;

public interface Insertable<T extends BaseModel> {
	public long Add(T param);
}
