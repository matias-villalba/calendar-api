package calendar.calendarapi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.util.Date;
import static calendar.calendarapi.config.AppConfiguration.DATE_FORMAT;


public class DateTimeWrapper {

	private OffsetDateTime dateTime;

	public DateTimeWrapper(String dateString) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
			Date date = formatter.parse(dateString);
	
			long millis = date.getTime();
			Instant instant = Instant.ofEpochMilli(millis);
			this.dateTime = instant.atZone(ZoneId.systemDefault()).toOffsetDateTime();
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		
	}
	public DateTimeWrapper(OffsetDateTime dateTime) {
		this.dateTime = dateTime;
	}
	
	public DateTimeWrapper(OffsetDateTime date, OffsetTime time) {	
		this.dateTime = OffsetDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), time.getHour(), time.getMinute(), time.getSecond(), time.getNano(), time.getOffset() );
	}
	
	public DateTimeWrapper(LocalDate date, OffsetTime time) {
		this.dateTime = OffsetDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), time.getHour(), time.getMinute(), time.getSecond(), time.getNano(), time.getOffset() );
	}
	
	
	public OffsetTime getTime() {
		return  OffsetTime.of(this.dateTime.getHour(), this.dateTime.getMinute(), this.dateTime.getSecond(), this.dateTime.getNano(), ZoneId.systemDefault().getRules().getOffset(this.dateTime.toInstant()) );		

	}
	
	public LocalDate getDate() {
		return LocalDate.of(this.dateTime.getYear(), this.dateTime.getMonth(), this.dateTime.getDayOfMonth());
	}
	
	public OffsetDateTime getDateTime() {
		return this.dateTime;
	}

	public boolean isAfter(DateTimeWrapper anotherEventDate) {
		return this.dateTime.isAfter(anotherEventDate.getDateTime());
	}

	public boolean isBefore(DateTimeWrapper anotherEventDate) {
		return this.dateTime.isBefore(anotherEventDate.getDateTime());
	}

	public boolean isBefore(OffsetDateTime endDate) {
		return this.dateTime.isBefore(endDate);
	}
	public DateTimeWrapper toLastTimeOfTheDay() {
		if(this.dateTime.getHour() == 0 && 
			this.dateTime.getMinute() == 0 && 
			this.dateTime.getSecond() == 0 && 
			this.dateTime.getNano() == 0) {
			return new DateTimeWrapper(this.dateTime.plusDays(1).minusNanos(1));			
		}

		return this;
	}
	
}
