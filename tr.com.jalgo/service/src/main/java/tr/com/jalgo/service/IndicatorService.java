package tr.com.jalgo.service;

import tr.com.jalgo.model.Indicator;
import tr.com.jalgo.model.actions.Insertable;
import tr.com.jalgo.model.actions.Selectable;
import tr.com.jalgo.model.actions.Updateable;

public interface IndicatorService extends Service , Insertable<Indicator>,Updateable<Indicator>,Selectable<Indicator> {

}
