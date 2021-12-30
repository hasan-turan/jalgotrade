package tr.com.jalgo.repository;

import tr.com.jalgo.model.Candle;
import tr.com.jalgo.model.actions.BatchInsertable;
import tr.com.jalgo.model.actions.Insertable;
import tr.com.jalgo.model.actions.Selectable;
import tr.com.jalgo.model.actions.Updateable;

public interface CandleRepository extends Repository, Insertable<Candle>,BatchInsertable<Candle> ,Updateable<Candle>,Selectable<Candle> {

}
