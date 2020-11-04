package calendar.calendarapi.validator;

import java.time.OffsetDateTime;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import calendar.calendarapi.dto.EventDto;
import calendar.calendarapi.dto.RecurrenceDto;

@Component
public class EventDtoValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
	    return EventDto.class.isAssignableFrom(clazz);

	}

	@Override
	public void validate(Object target, Errors errors) {

		if (errors.getErrorCount() == 0) {
	    	EventDto event = (EventDto) target;
	    	if (event.getName() == null) {
	    		errors.reject("100",
	            "Event name can't be null");
	    		return;
	        }
	    	if (event.getStartDateTime() == null) {
	    		errors.reject("101",
	            "Event startDateTime can't be null");
	    		return;
	        }

	    	if (event.getDuration() == null) {
	    		errors.reject("102",
	            "Event duration can't be null");
	    		return;
	        }

	        OffsetDateTime endDateTime = event.getStartDateTime().plus(event.getDuration());
	              
	        if (endDateTime.getDayOfMonth() != event.getStartDateTime().getDayOfMonth() ||
	    	    endDateTime.getMonthValue()!= event.getStartDateTime().getMonthValue() ||
	    	    endDateTime.getYear() != event.getStartDateTime().getYear()) {

	          errors.reject("103",
	            "Events are not allowed to span to the next day");
	        }
	        
	    	if (event.getRecurrence() != null && !validateRecurrence(event.getRecurrence(), errors)) {
	    		return;
	        }
		
	    }
	}
	
	private boolean validateRecurrence(RecurrenceDto recurrence, Errors errors){
    	if (!Objects.equals("DAILY", recurrence.getRecurrenceType()) &&
    		!Objects.equals("WEEKLY", recurrence.getRecurrenceType()) &&
    		!Objects.equals("MONTHLY", recurrence.getRecurrenceType())
    		) {
    		errors.reject("104",
            "If there is a recurrence defined, recurrenceType must be one of: DAILY, WEELKY, MONTHLY");
    		return false;
        }
    	
    	if (!Objects.equals("NEVER", recurrence.getEndType()) &&
        	!Objects.equals("ON_A_DATE", recurrence.getEndType()) &&
        	!Objects.equals("AFTER_OCCURRENCES", recurrence.getEndType())
        	) {
        	errors.reject("105",
            "If there is a recurrence defined, endType must be one of: 'NEVER', 'ON_A_DATE', 'AFTER_OCCURRENCES'");
        	return false;
        }
    	
      	if (Objects.equals("ON_A_DATE", recurrence.getEndType())
      		&& recurrence.getEndDate() == null) {
            	errors.reject("106",
                "If there is a recurrence defined which ends ON_A_DATE, endDate must be passed");
            return false;
        }
      	
      	if (Objects.equals("AFTER_OCCURRENCES", recurrence.getEndType())
      		&& (recurrence.getOccurrences() == null || recurrence.getOccurrences() <= 0)) {
        	errors.reject("107",
            "If there is a recurrence defined which ends AFTER_OCCURRENCES, occurrences must be passed");
        	return false;
      	}	
    			
		return true;
	}
}
