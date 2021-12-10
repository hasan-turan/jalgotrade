package tr.com.jalgo.repository;

import tr.com.jalgo.model.Symbol;
import tr.com.jalgo.model.actions.Insertable;
import tr.com.jalgo.model.actions.Selectable;
import tr.com.jalgo.model.actions.Updateable;

public interface SymbolRepository extends Repository,Insertable<Symbol>,Updateable<Symbol>,Selectable<Symbol> {

}
