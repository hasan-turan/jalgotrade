package tr.com.jalgo.service;

import tr.com.jalgo.model.DataSource;
import tr.com.jalgo.model.actions.Insertable;
import tr.com.jalgo.model.actions.Selectable;
import tr.com.jalgo.model.actions.Updateable;

public interface DataSourceService extends Service , Insertable<DataSource>,Updateable<DataSource>,Selectable<DataSource> {

}
