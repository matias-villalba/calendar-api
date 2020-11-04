package calendar.calendarapi;

import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZoneId;

import org.springframework.stereotype.Component;

import calendar.calendarapi.dto.EventDto;
import calendar.calendarapi.model.Event;
import calendar.calendarapi.model.RecurrenceEndType;
import calendar.calendarapi.model.RecurrenceType;
import calendar.calendarapi.model.Recurrence;

@Component
public class EventFactory {

	public Event createEvent(EventDto eventDto) {
				
		var dateTime = new DateTimeWrapper(eventDto.getStartDateTime());
		LocalDate startDate = dateTime.getDate();	
		OffsetTime startTime = dateTime.getTime();
		
		var event =  new Event(eventDto.getName(), startDate, startTime, eventDto.getDuration());
		
		if(eventDto.getRecurrence() != null) {
			
			var recurrenceEndType = RecurrenceEndType.valueOf(eventDto.getRecurrence().getEndType());
			var recurrenceType = RecurrenceType.valueOf(eventDto.getRecurrence().getRecurrenceType());
			var recurrence = new Recurrence(recurrenceEndType, recurrenceType);
			event.setRecurrence(recurrence);

			if(RecurrenceEndType.AFTER_OCCURRENCES.equals(recurrenceEndType)) {
				recurrence.setOccurrences(eventDto.getRecurrence().getOccurrences());
			}
			
			if(RecurrenceEndType.ON_A_DATE.equals(recurrenceEndType)) {
				recurrence.setEndDate( eventDto.getRecurrence().getEndDate().atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime());
			}
			
			if(RecurrenceType.WEEKLY.equals(recurrenceType)) {
				recurrence.setMondayRecurrence(isTrue(eventDto.getRecurrence().getMondayRecurrence()));
				recurrence.setTuesdayRecurrence(isTrue(eventDto.getRecurrence().getTuesdayRecurrence()));
				recurrence.setWednesdayRecurrence(isTrue(eventDto.getRecurrence().getWednesdayRecurrence()));
				recurrence.setThursdayRecurrence(isTrue(eventDto.getRecurrence().getThursdayRecurrence()));
				recurrence.setFridayRecurrence(isTrue(eventDto.getRecurrence().getFridayRecurrence()));
				recurrence.setSaturdayRecurrence(isTrue(eventDto.getRecurrence().getSaturdayRecurrence()));
				recurrence.setSundayRecurrence(isTrue(eventDto.getRecurrence().getSundayRecurrence()));
			}
		}
		
		return event;
	}
	
	private Boolean isTrue(Boolean bool) {
		return bool != null && bool;
	}
}
