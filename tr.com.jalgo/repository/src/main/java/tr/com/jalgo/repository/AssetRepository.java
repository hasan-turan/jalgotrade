package tr.com.jalgo.repository;

import tr.com.jalgo.model.Asset;
import tr.com.jalgo.model.actions.Insertable;
import tr.com.jalgo.model.actions.Selectable;
import tr.com.jalgo.model.actions.Updateable;

public interface AssetRepository extends Repository,Insertable<Asset>,Updateable<Asset>,Selectable<Asset> {

}
