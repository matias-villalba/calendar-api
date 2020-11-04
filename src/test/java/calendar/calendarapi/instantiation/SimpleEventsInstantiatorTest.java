package calendar.calendarapi.instantiation;

import org.junit.jupiter.api.Test;

import calendar.calendarapi.DateTimeWrapper;
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

public class SimpleEventsInstantiatorTest {

	
	@Test
	public void evaluateIfEventCanBeInstantiateShouldReturnTrue() {
		var eventInstantiator = new SimpleEventsInstantiator(null);
		
		var event = new Event();
		event.setId(Long.valueOf(2));
		event.setName("aName");
		event.setStartDate(LocalDate.now());
		event.setDuration(Duration.parse("PT2H"));
		
		assertThat(eventInstantiator.canInstantiate(event)).isTrue();		
	}
	
	@Test
	public void evaluateIfEventCanBeInstantiateShouldReturnFalse() {
		var eventInstantiator = new SimpleEventsInstantiator(null);
		
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
		assertThat(eventInstantiator.canInstantiate(event)).isFalse();		
	}
	
	@Test
	public void instantiateValidEventShouldReturnAnEventDtoInstance() {
		var eventInstantiator = new SimpleEventsInstantiator(null);

		var now = OffsetDateTime.now();
		
		var event = new Event();
		event.setId(Long.valueOf(2));
		event.setName("aName");
		event.setStartDate(now.plusDays(8).toLocalDate());
		event.setStartTime(OffsetTime.of(14, 22, 0, 0, ZoneOffset.UTC ) );
		event.setDuration(Duration.parse("PT2H"));
		

		var from = new DateTimeWrapper(now);
		var to = new DateTimeWrapper(now.plusMonths(1));
		
		var eventDto = eventInstantiator.instantiate(event, from, to);
		assertThat(eventDto).isNotNull();		
		
		assertThat(eventDto).hasOnlyOneElementSatisfying(eDto -> {
			assertThat(eDto).isNotNull();
			assertThat(eDto.getName()).isEqualTo(event.getName());
			assertThat(eDto.getEventDefinitionId()).isEqualTo(event.getId());
			
			assertThat(eDto.getStartDateTime().getYear()).isEqualTo(event.getStartDate().getYear());
			assertThat(eDto.getStartDateTime().getMonth()).isEqualTo(event.getStartDate().getMonth());
			assertThat(eDto.getStartDateTime().getDayOfMonth()).isEqualTo(event.getStartDate().getDayOfMonth());
			
			assertThat(eDto.getStartDateTime().getHour()).isEqualTo(event.getStartTime().getHour());
			assertThat(eDto.getStartDateTime().getMinute()).isEqualTo(event.getStartTime().getMinute());
			assertThat(eDto.getStartDateTime().getSecond()).isEqualTo(event.getStartTime().getSecond());
			assertThat(eDto.getStartDateTime().getNano()).isEqualTo(event.getStartTime().getNano());

			assertThat(eDto.getStartDateTime().getOffset().getTotalSeconds()).isEqualTo(event.getStartTime().getOffset().getTotalSeconds());

		});	
	}
}
