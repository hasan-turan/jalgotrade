package tr.com.jalgo.model.actions;

import java.util.List;

import tr.com.jalgo.model.BaseModel;

public interface BatchInsertable<T extends BaseModel> {
	public long[] batchInsert(List<T> params)   ;
}
 