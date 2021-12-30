package tr.com.jalgo.repository;

import tr.com.jalgo.model.Indicator;
import tr.com.jalgo.model.actions.Insertable;
import tr.com.jalgo.model.actions.Selectable;
import tr.com.jalgo.model.actions.Updateable;

public interface IndicatorRepository extends Repository,Insertable<Indicator>,Updateable<Indicator>,Selectable<Indicator> {

}
