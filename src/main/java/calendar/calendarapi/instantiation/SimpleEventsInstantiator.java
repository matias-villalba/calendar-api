package calendar.calendarapi.instantiation;


import java.util.Arrays;
import java.util.List;

import calendar.calendarapi.DateTimeWrapper;
import calendar.calendarapi.dto.EventDto;
import calendar.calendarapi.model.Event;

public class SimpleEventsInstantiator extends ChainInstantiator {

	public SimpleEventsInstantiator(ChainInstantiator nextInstantiator) {
		super(nextInstantiator);
	}

	@Override
	protected boolean canInstantiate(Event event) {
		return event != null && (event.getRecurrence() == null || event.getRecurrence().getRecurrenceType() == null);
	}

	@Override
	protected List<EventDto> instantiate(Event event, DateTimeWrapper from, DateTimeWrapper to) {
		var eventDto = new EventDto();
		eventDto.setEventDefinitionId(event.getId());
		eventDto.setName(event.getName());		
		var startDateTime = new DateTimeWrapper(event.getStartDate(), event.getStartTime());
		eventDto.setStartDateTime(startDateTime.getDateTime());
		eventDto.setDuration(event.getDuration());
		return Arrays.asList(eventDto);
	}

}
