package calendar.calendarapi.validator;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.server.ResponseStatusException;

import static calendar.calendarapi.config.AppConfiguration.DATE_FORMAT;

@Component
public class DateRangeValidator {

	public void validate(@Valid String fromAsString, String toAsString) {

		var df = new SimpleDateFormat(DATE_FORMAT);
		try {
			var from = df.parse(fromAsString);
			var to = df.parse(toAsString);
			
			if(from.getTime() > to.getTime()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "'from' query param value must be before 'to' value");
			}
			
		} catch (ParseException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format. It must be: "+DATE_FORMAT);
		}
		
		
	}
	
}
