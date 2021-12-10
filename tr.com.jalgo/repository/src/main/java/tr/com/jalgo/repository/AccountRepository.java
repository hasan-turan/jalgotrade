package tr.com.jalgo.repository;

import tr.com.jalgo.model.Account;
import tr.com.jalgo.model.actions.Insertable;
import tr.com.jalgo.model.actions.Selectable;
import tr.com.jalgo.model.actions.Updateable;

public interface AccountRepository extends Repository,Insertable<Account>,Updateable<Account>,Selectable<Account> {

}
