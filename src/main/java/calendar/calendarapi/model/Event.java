package calendar.calendarapi.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import calendar.calendarapi.config.DurationConverter;

@Entity
@Table(name = "events")
public class Event {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Long id;
	
	@Column(name = "start_date ")	
	private LocalDate startDate;
	
	@Column(name = "start_time")	
	private OffsetTime startTime;
	
	@Convert(converter = DurationConverter.class)
	private Duration duration;
	
	@Column(name = "name")
	private String name;

	@Embedded
	private Recurrence recurrence;
	
	public Event() {}
	
	public Event(String name, LocalDate startDate, OffsetTime startTime, Duration duration) {
		this.name = name;
		this.startDate = startDate;
		this.startTime = startTime;
		this.duration = duration;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public OffsetTime getStartTime() {
		return startTime;
	}

	public void setStartTime(OffsetTime startTime) {
		this.startTime = startTime;
	}
	
	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Recurrence getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(Recurrence recurrence) {
		this.recurrence = recurrence;
	}
	
}


