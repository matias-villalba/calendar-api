package calendar.calendarapi.instantiation;

import org.junit.jupiter.api.Test;

import calendar.calendarapi.DateTimeWrapper;
import calendar.calendarapi.dto.EventDto;
import calendar.calendarapi.model.Event;
import calendar.calendarapi.model.Recurrence;
import calendar.calendarapi.model.RecurrenceEndType;
import calendar.calendarapi.model.RecurrenceType;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

public class RecurrentEventsInstantiatorTest {

	
	@Test
	public void evaluateIfEventCanBeInstantiateShouldReturnTrue() {
		var eventInstantiator = new RecurrentEventsInstantiator(null);
		
		var event = new Event();
		event.setId(Long.valueOf(2));
		event.setName("aName");
		event.setStartDate(LocalDate.now());
		event.setDuration(Duration.parse("PT2H"));
		
		var recurrence = new Recurrence();
		recurrence.setRecurrenceType(RecurrenceType.DAILY);
		recurrence.setEndType(RecurrenceEndType.AFTER_OCCURRENCES);
		recurrence.setOccurrences(4);
		
		event.setRecurrence(recurrence);
		assertThat(eventInstantiator.canInstantiate(event)).isTrue();		
	}
	
	@Test
	public void evaluateIfEventCanBeInstantiateShouldReturnFalse() {
		var eventInstantiator = new RecurrentEventsInstantiator(null);
		
		var event = new Event();
		event.setId(Long.valueOf(2));
		event.setName("aName");
		event.setStartDate(LocalDate.now());
		event.setDuration(Duration.parse("PT2H"));
		
		assertThat(eventInstantiator.canInstantiate(event)).isFalse();		
	}
	
	@Test
	public void instantiateValidEventShouldReturnFourEventDtoInstances() {
		var eventInstantiator = new RecurrentEventsInstantiator(null);

		var now = OffsetDateTime.now();
		
		var event = new Event();
		event.setId(Long.valueOf(2));
		event.setName("aName");
		event.setStartDate(now.plusDays(8).toLocalDate());
		event.setStartTime(OffsetTime.of(14, 22, 0, 0, ZoneOffset.UTC ) );
		event.setDuration(Duration.parse("PT2H"));
		
		var recurrence = new Recurrence();
		recurrence.setRecurrenceType(RecurrenceType.DAILY);
		recurrence.setEndType(RecurrenceEndType.AFTER_OCCURRENCES);
		recurrence.setOccurrences(4);
		
		event.setRecurrence(recurrence);
		
		var from = new DateTimeWrapper(now);
		var to = new DateTimeWrapper(now.plusMonths(1));
		
		var eventDtos = eventInstantiator.instantiate(event, from, to);
		assertThat(eventDtos).isNotNull();		
		
		assertThat(eventDtos.size()).isEqualTo(4);

		assertEventDto(eventDtos.get(0), event, event.getStartDate(), event.getStartTime());
		assertEventDto(eventDtos.get(1), event, event.getStartDate().plusDays(1), event.getStartTime());
		assertEventDto(eventDtos.get(2), event, event.getStartDate().plusDays(2), event.getStartTime());
		assertEventDto(eventDtos.get(3), event, event.getStartDate().plusDays(3), event.getStartTime());
	}

	private void assertEventDto(EventDto eDto, Event expectedEvent, LocalDate expectedStartDate, OffsetTime expectedStartTime) {
		assertThat(eDto).isNotNull();
		assertThat(eDto.getName()).isEqualTo(expectedEvent.getName());
		assertThat(eDto.getEventDefinitionId()).isEqualTo(expectedEvent.getId());
		
		assertThat(eDto.getStartDateTime().getYear()).isEqualTo(expectedStartDate.getYear());
		assertThat(eDto.getStartDateTime().getMonth()).isEqualTo(expectedStartDate.getMonth());
		assertThat(eDto.getStartDateTime().getDayOfMonth()).isEqualTo(expectedStartDate.getDayOfMonth());
		
		assertThat(eDto.getStartDateTime().getHour()).isEqualTo(expectedStartTime.getHour());
		assertThat(eDto.getStartDateTime().getMinute()).isEqualTo(expectedStartTime.getMinute());
		assertThat(eDto.getStartDateTime().getSecond()).isEqualTo(expectedStartTime.getSecond());
		assertThat(eDto.getStartDateTime().getNano()).isEqualTo(expectedStartTime.getNano());

	}
}
