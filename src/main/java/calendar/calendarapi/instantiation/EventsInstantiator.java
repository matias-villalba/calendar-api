package calendar.calendarapi.instantiation;

import java.util.List;

import calendar.calendarapi.DateTimeWrapper;
import calendar.calendarapi.dto.EventDto;
import calendar.calendarapi.model.Event;

public interface EventsInstantiator {
	
	List<EventDto> getInstancesOf(Event event, DateTimeWrapper from, DateTimeWrapper to);

}
