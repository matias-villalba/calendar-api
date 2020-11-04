package calendar.calendarapi.instantiation;

import java.util.ArrayList;
import java.util.List;

import calendar.calendarapi.DateTimeWrapper;
import calendar.calendarapi.dto.EventDto;
import calendar.calendarapi.model.Event;

public abstract class ChainInstantiator implements EventsInstantiator{
	
	private ChainInstantiator nextInstantiator;
	
	public ChainInstantiator(ChainInstantiator nextInstantiator) {
		this.nextInstantiator = nextInstantiator;
	}

	@Override
	public List<EventDto> getInstancesOf(Event event, DateTimeWrapper from, DateTimeWrapper to) {
		
		if(this.canInstantiate(event) ) {			
			return this.instantiate(event,  from,  to);
		}
		if(this.nextInstantiator != null) {
			return this.nextInstantiator.instantiate(event,  from,  to);
		}
	
		return new ArrayList<EventDto>();
	}

	protected abstract boolean canInstantiate(Event event);
	protected abstract List<EventDto> instantiate(Event event, DateTimeWrapper from, DateTimeWrapper to);
	
}
