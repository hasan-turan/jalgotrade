package tr.com.jalgo.service;

import tr.com.jalgo.model.Symbol;
import tr.com.jalgo.model.actions.Insertable;
import tr.com.jalgo.model.actions.Selectable;
import tr.com.jalgo.model.actions.Updateable;

public interface SymbolService extends Service , Insertable<Symbol>,Updateable<Symbol>,Selectable<Symbol> {

}
