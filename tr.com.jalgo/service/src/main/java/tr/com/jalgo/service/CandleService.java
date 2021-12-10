package tr.com.jalgo.service;

import tr.com.jalgo.model.Candle;
import tr.com.jalgo.model.actions.Insertable;
import tr.com.jalgo.model.actions.Selectable;
import tr.com.jalgo.model.actions.Updateable;

public interface CandleService extends Service , Insertable<Candle>,Updateable<Candle>,Selectable<Candle> {

}
