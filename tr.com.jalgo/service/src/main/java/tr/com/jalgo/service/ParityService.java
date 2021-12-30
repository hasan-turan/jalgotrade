package tr.com.jalgo.service;

import tr.com.jalgo.model.Parity;
import tr.com.jalgo.model.actions.Insertable;
import tr.com.jalgo.model.actions.Selectable;
import tr.com.jalgo.model.actions.Updateable;

public interface ParityService extends Service , Insertable<Parity>,Updateable<Parity>,Selectable<Parity> {

}
