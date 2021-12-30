package tr.com.jalgo.repository;

import tr.com.jalgo.model.DataSource;
import tr.com.jalgo.model.actions.Insertable;
import tr.com.jalgo.model.actions.Selectable;
import tr.com.jalgo.model.actions.Updateable;

public interface DataSourceRepository extends Repository,Insertable<DataSource>,Updateable<DataSource>,Selectable<DataSource> {

}
