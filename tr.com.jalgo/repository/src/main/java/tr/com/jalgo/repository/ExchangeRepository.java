package tr.com.jalgo.repository;

import tr.com.jalgo.model.Exchange;
import tr.com.jalgo.model.actions.Insertable;
import tr.com.jalgo.model.actions.Selectable;
import tr.com.jalgo.model.actions.Updateable;

public interface ExchangeRepository extends Repository,Insertable<Exchange>,Updateable<Exchange>,Selectable<Exchange> {

}
