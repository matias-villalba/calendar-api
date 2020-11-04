package calendar.calendarapi.instantiation;


import java.util.LinkedList;
import java.util.List;

import calendar.calendarapi.DateTimeWrapper;
import calendar.calendarapi.dto.EventDto;
import calendar.calendarapi.dto.RecurrenceDto;
import calendar.calendarapi.model.Event;
import calendar.calendarapi.model.Recurrence;
import calendar.calendarapi.model.RecurrenceEndType;
import calendar.calendarapi.model.RecurrenceType;

public class RecurrentEventsInstantiator extends ChainInstantiator{

	public RecurrentEventsInstantiator(ChainInstantiator nextInstantiator) {
		super(nextInstantiator);
	}

	@Override
	protected boolean canInstantiate(Event event) {
		return event.getRecurrence() != null &&
				event.getRecurrence().getRecurrenceType() != null;
	}

	@Override
	protected List<EventDto> instantiate(Event event, DateTimeWrapper from, DateTimeWrapper to) {

		List<EventDto> events = new LinkedList<EventDto>();

		var startDateTime = new DateTimeWrapper(event.getStartDate(), event.getStartTime());
		var currentEventDate =  from.isAfter(startDateTime)? new DateTimeWrapper(from.getDateTime(), event.getStartTime()) : startDateTime;
		int occurrenceNumber = 1;
		while(!isEndConditionMet(event.getRecurrence(), occurrenceNumber, currentEventDate) && 
			  isBetweenDates(from, to, currentEventDate)) {
		
			var eventDto = new EventDto();
			eventDto.setEventDefinitionId(event.getId());
			eventDto.setName(event.getName());		
			eventDto.setStartDateTime(currentEventDate.getDateTime());
			eventDto.setDuration(event.getDuration());
			
			eventDto = this.addEventRecurrence(eventDto, event.getRecurrence());
			
			events.add(eventDto);
			occurrenceNumber++;
			currentEventDate = getNextEventDate(currentEventDate , event.getRecurrence().getRecurrenceType());
		}
		
		return events;
	}

	private EventDto addEventRecurrence(EventDto eventDto, Recurrence recurrence) {

		var recurrenceDto = new RecurrenceDto();
		eventDto.setRecurrence(recurrenceDto);		
		if(recurrence.getEndDate() != null) {
			recurrenceDto.setEndDate(recurrence.getEndDate().toLocalDate());			
		}		
		recurrenceDto.setEndType(recurrence.getEndType().name());
		recurrenceDto.setRecurrenceType(recurrence.getRecurrenceType().name());
		recurrenceDto.setOccurrences(recurrence.getOccurrences());
		
		recurrenceDto.setMondayRecurrence(isTrue(recurrence.getMondayRecurrence()));
		recurrenceDto.setTuesdayRecurrence(isTrue(recurrence.getTuesdayRecurrence()));
		recurrenceDto.setWednesdayRecurrence(isTrue(recurrence.getWednesdayRecurrence()));
		recurrenceDto.setThursdayRecurrence(isTrue(recurrence.getThursdayRecurrence()));
		recurrenceDto.setFridayRecurrence(isTrue(recurrence.getFridayRecurrence()));
		recurrenceDto.setSaturdayRecurrence(isTrue(recurrence.getSaturdayRecurrence()));
		recurrenceDto.setSundayRecurrence(isTrue(recurrence.getSundayRecurrence()));
		
		return eventDto;
	}
	private boolean isTrue(Boolean value) {
		return value != null && value;
	}

	private boolean isEndConditionMet(Recurrence recurrence, int occurrenceNumber, DateTimeWrapper currentEventDate) {

		if(RecurrenceEndType.AFTER_OCCURRENCES.equals(recurrence.getEndType()) &&
		   occurrenceNumber > recurrence.getOccurrences()) {
			return true;
		}

		if(RecurrenceEndType.ON_A_DATE.equals(recurrence.getEndType()) &&
			!recurrence.getEndDate().isAfter(currentEventDate.getDateTime()) ) {
			return true;
		}
		if(RecurrenceEndType.NEVER.equals(recurrence.getEndType())) {
			return false;
		}
		
		return false;
	}

	private boolean isBetweenDates(DateTimeWrapper from, DateTimeWrapper to, DateTimeWrapper currentEventDate) {
		return  !from.isAfter(currentEventDate) && !to.isBefore(currentEventDate);
	}

	private DateTimeWrapper getNextEventDate(DateTimeWrapper startDateTime, RecurrenceType recurrenceType) {
		
		if(RecurrenceType.DAILY.equals(recurrenceType)) {
			return new DateTimeWrapper (startDateTime.getDate().plusDays(1), startDateTime.getTime());

		}
		
		if(RecurrenceType.WEEKLY.equals(recurrenceType)) {
			return new DateTimeWrapper (startDateTime.getDate().plusWeeks(1), startDateTime.getTime());
		}
		
		if(RecurrenceType.MONTHLY.equals(recurrenceType)) {
			return new DateTimeWrapper (startDateTime.getDate().plusMonths(1), startDateTime.getTime());
		}
		throw new RuntimeException("unknown recurrenceType: "+ recurrenceType);

	}

}
