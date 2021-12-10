package tr.com.jalgo.service;

import tr.com.jalgo.model.Exchange;
import tr.com.jalgo.model.actions.Insertable;
import tr.com.jalgo.model.actions.Selectable;
import tr.com.jalgo.model.actions.Updateable;

public interface ExchangeService extends Service , Insertable<Exchange>,Updateable<Exchange>,Selectable<Exchange> {

}
