package tr.com.jalgo.repository;

import tr.com.jalgo.model.ExchangeAccount;
import tr.com.jalgo.model.actions.Insertable;
import tr.com.jalgo.model.actions.Selectable;
import tr.com.jalgo.model.actions.Updateable;

public interface ExchangeAccountRepository extends Repository,Insertable<ExchangeAccount>,Updateable<ExchangeAccount>,Selectable<ExchangeAccount> {
 
}
