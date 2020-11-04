package calendar.calendarapi.controller;

import java.time.OffsetDateTime;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import calendar.calendarapi.dto.ErrorResponse;

@Order(Ordered.HIGHEST_PRECEDENCE)
@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler 
{
	
    @ExceptionHandler(ResponseStatusException.class)
    public final ResponseEntity<Object> handleAllExceptions(ResponseStatusException ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(OffsetDateTime.now(), ex.getStatus().value(), ex.getStatus().name(), ex.getMessage());
        return new ResponseEntity(errorResponse, ex.getStatus());
    }
 
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        StringBuilder details = new StringBuilder();
        details.append(ex.getLocalizedMessage());
        ex.printStackTrace();
        ErrorResponse errorResponse = new ErrorResponse(OffsetDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name(), details.toString());
        return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
 
 
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder details = new StringBuilder();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
        	
            details.append(error.getDefaultMessage())
            .append("   ");
        }
        ErrorResponse errorResponse = new ErrorResponse(OffsetDateTime.now(), status.value(), status.name(), details.toString());
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
