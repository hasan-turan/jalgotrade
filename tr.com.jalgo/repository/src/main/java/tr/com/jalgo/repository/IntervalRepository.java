package tr.com.jalgo.repository;

import tr.com.jalgo.model.Interval;
import tr.com.jalgo.model.actions.Insertable;
import tr.com.jalgo.model.actions.Selectable;
import tr.com.jalgo.model.actions.Updateable;

public interface IntervalRepository extends Repository,Insertable<Interval>,Updateable<Interval>,Selectable<Interval> {

}
