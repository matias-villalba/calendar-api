package calendar.calendarapi;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import calendar.calendarapi.controller.EventController;
import calendar.calendarapi.dto.EventDto;

@SpringBootTest
class CalendarApiIntegrationTest {

 
	private static final String TO = "2020-11-20";
	private static final String FROM = "2020-02-22";
	@Autowired
	private EventController eventController;
	
 
    @Test
    public void getEventsShouldReturnAnEmptyList() {
    	var events = eventController.getEvents(FROM, TO);
    	
    	assertThat(events).isNotNull();
    	assertThat(events).isEmpty();
    }
    
    @Test
    public void createEventsShouldReturnTheEventInstances() {
    	var eventName = "first event name";
    	
    	var startDateTime = Instant.parse("2020-11-02T18:30:00Z")
				.atZone(ZoneId.systemDefault()).toOffsetDateTime();

    	var duration = Duration.parse("PT2H30M");
    	var eventDto = new EventDto();
    	eventDto.setName(eventName);
    	eventDto.setStartDateTime(startDateTime);
    	eventDto.setDuration(duration);
    	
    	eventController.createEvent(eventDto);
    	
    	var eventsGettingResponse = eventController.getEvents(FROM, TO);
    	
    	assertThat(eventsGettingResponse).isNotNull();
    	assertThat(eventsGettingResponse).hasOnlyOneElementSatisfying(ev -> assertEqual( ev, eventName, startDateTime, duration));
    }
    
    @Test
    public void getEventsInstancesAfterDeleteShouldReturnEmptyList() {
    	var eventName = "first event name";
    	
    	var startDateTime = Instant.parse("2020-11-02T18:30:00Z")
				.atZone(ZoneId.systemDefault()).toOffsetDateTime();

    	var duration = Duration.parse("PT2H30M");
    	var eventDto = new EventDto();
    	eventDto.setName(eventName);
    	eventDto.setStartDateTime(startDateTime);
    	eventDto.setDuration(duration);
    	var eventsCreationResponse = eventController.createEvent(eventDto);
    	
    	assertThat(eventsCreationResponse).isNotNull();
    	assertThat(eventsCreationResponse.getEventDefinitionId()).isNotNull();    	
    	Long eventId = eventsCreationResponse.getEventDefinitionId();	
    	
    	var eventsGettingResponse = eventController.getEvents(FROM, TO);
      	assertThat(eventsGettingResponse).isNotNull();
    	assertThat(eventsGettingResponse).hasOnlyOneElementSatisfying(ev -> assertThat(ev.getEventDefinitionId()).isEqualTo(eventId));
    	
    	eventController.deleteEvent(eventId);    	
    	var events = eventController.getEvents(FROM, TO);
    	assertThat(events).isNotNull();
    	assertThat(events).isEmpty();
    	
    }
    
    private static void assertEqual(EventDto ev, String expectedEventName, OffsetDateTime expectedStartDateTime, Duration exptectedDuration){
		assertThat(ev).isNotNull();
		assertThat(ev.getName()).isEqualTo(expectedEventName);
		assertThat(ev.getDuration()).isNotNull();
		assertThat(ev.getDuration().toNanos()).isEqualTo(exptectedDuration.toNanos());
		assertThat(ev.getEventDefinitionId()).isNotNull();
		assertThat(ev.getStartDateTime()).isNotNull();
		assertThat(ev.getStartDateTime().toEpochSecond()).isEqualTo(expectedStartDateTime.toEpochSecond());		
    }
    
}