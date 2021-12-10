package tr.com.jalgo.repository;

import tr.com.jalgo.model.User;
import tr.com.jalgo.model.actions.Insertable;
import tr.com.jalgo.model.actions.Selectable;
import tr.com.jalgo.model.actions.Updateable;

public interface UserRepository extends Repository,Insertable<User>,Updateable<User>,Selectable<User> {

}
