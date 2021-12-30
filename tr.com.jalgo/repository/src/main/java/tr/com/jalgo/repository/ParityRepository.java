package tr.com.jalgo.repository;

import tr.com.jalgo.model.Parity;
import tr.com.jalgo.model.actions.Insertable;
import tr.com.jalgo.model.actions.Selectable;
import tr.com.jalgo.model.actions.Updateable;

public interface ParityRepository extends Repository,Insertable<Parity>,Updateable<Parity>,Selectable<Parity> {

}
