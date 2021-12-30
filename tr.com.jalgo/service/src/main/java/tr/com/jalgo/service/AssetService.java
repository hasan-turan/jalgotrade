package tr.com.jalgo.service;

import tr.com.jalgo.model.Asset;
import tr.com.jalgo.model.actions.Insertable;
import tr.com.jalgo.model.actions.Selectable;
import tr.com.jalgo.model.actions.Updateable;

public interface AssetService extends Service , Insertable<Asset>,Updateable<Asset>,Selectable<Asset> {

}
