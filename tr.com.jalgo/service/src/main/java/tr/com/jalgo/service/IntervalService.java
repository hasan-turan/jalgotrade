package tr.com.jalgo.service;

import tr.com.jalgo.model.Interval;
import tr.com.jalgo.model.actions.Insertable;
import tr.com.jalgo.model.actions.Selectable;
import tr.com.jalgo.model.actions.Updateable;

public interface IntervalService extends Service , Insertable<Interval>,Updateable<Interval>,Selectable<Interval> {

}
