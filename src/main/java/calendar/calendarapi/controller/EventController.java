package calendar.calendarapi.controller;



import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import calendar.calendarapi.DateTimeWrapper;
import calendar.calendarapi.EventFactory;
import calendar.calendarapi.EventRepository;
import calendar.calendarapi.dto.EventDto;
import calendar.calendarapi.instantiation.EventsInstantiator;
import calendar.calendarapi.model.Event;
import calendar.calendarapi.validator.DateRangeValidator;
import calendar.calendarapi.validator.EventDtoValidator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Validated
@ApiOperation(value = "Events endpoints")
public class EventController {

	@Autowired
	private EventRepository eventRepository;
	
    @Autowired
    private EventDtoValidator eventDtoValidator;
    
    @Autowired
    private EventFactory eventFactory;
    
    @Autowired
    private EventsInstantiator eventsInstantiator;
    
    @Autowired
    private DateRangeValidator dateRangeValidator;

    @InitBinder("eventDto")
    public void initEventBinder(WebDataBinder binder) {
    	binder.addValidators(eventDtoValidator);
    }
    
	
	@GetMapping("/events")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<EventDto> getEvents(
            @ApiParam(name="from", value="Date in format: yyyy-mm-dd.", example = "2020-11-02")
			@RequestParam(name = "from") String from,
            @ApiParam(name="to", value="Date in format: yyyy-mm-dd.", example = "2020-12-22")
			@RequestParam(name = "to") String to) {
		
		dateRangeValidator.validate(from, to);
		var fromDateTime = new DateTimeWrapper(from);
		var toDateTime = new DateTimeWrapper(to).toLastTimeOfTheDay();
		List<Event> events = eventRepository.findEventsInRange(fromDateTime.getDate(), fromDateTime.getTime(), toDateTime.getDate(), toDateTime.getTime());

		List<EventDto>  eventsDtos = events.parallelStream()
			.map(ev -> eventsInstantiator.getInstancesOf(ev, fromDateTime, toDateTime))
			.flatMap(Collection::stream)
			.collect(Collectors.toList());
		
	
		return eventsDtos;
	}

	@DeleteMapping("/events/{eventDefinitionId}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiOperation(value = "Delete event by the eventDefinitionId",notes = "This method deletes all events with the passed eventDefinitionId")
	public void deleteEvent(@PathVariable Long eventDefinitionId) {
		if(!eventRepository.existsById(eventDefinitionId)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is not an event with eventDefinitionId: "+eventDefinitionId);
		}
		eventRepository.deleteById(eventDefinitionId);
	}

	
	
	@PostMapping("/events")
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@ApiOperation(value = "Create event",notes = "This method creates a new event")
	public EventDto createEvent(@RequestBody @Valid EventDto eventDto) {
		
		var event = eventFactory.createEvent(eventDto);		
		var savedEvent = eventRepository.save(event);
		eventDto.setEventDefinitionId(savedEvent.getId());
		return eventDto;
	}
	
	
}
