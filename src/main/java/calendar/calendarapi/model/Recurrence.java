package calendar.calendarapi.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Recurrence {

	public Recurrence() {}
	
	 public Recurrence(RecurrenceEndType endType, RecurrenceType recurrenceType) {
		this.endType = endType;
		this.recurrenceType = recurrenceType;
	}

	@Column(name = "end_type")
	 @Enumerated(EnumType.STRING)
	 private RecurrenceEndType endType;
	 
	 @Column(name = "end_date")
	 private OffsetDateTime endDate;
	 
	 @Column(name = "occurrences")
	 private Integer occurrences;

	 @Enumerated(EnumType.STRING)
	 @Column(name = "recurrence_type")
	 private RecurrenceType recurrenceType;
	 
	 @Column(name = "monday_recurrence")
	 private Boolean mondayRecurrence;
	 
	 @Column(name = "tuesday_recurrence")
	 private Boolean tuesdayRecurrence;
	 
	 @Column(name = "wednesday_recurrence")
	 private Boolean wednesdayRecurrence;
	 
	 @Column(name = "thursday_recurrence")
	 private Boolean thursdayRecurrence;
	 
	 @Column(name = "friday_recurrence")
	 private Boolean fridayRecurrence;
	 
	 @Column(name = "saturday_recurrence")
	 private Boolean saturdayRecurrence;
	 
	 @Column(name = "sunday_recurrence")
	 private Boolean sundayRecurrence;

	public RecurrenceEndType getEndType() {
		return endType;
	}

	public void setEndType(RecurrenceEndType endType) {
		this.endType = endType;
	}

	public OffsetDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(OffsetDateTime endDate) {
		this.endDate = endDate;
	}

	public Integer getOccurrences() {
		return occurrences;
	}

	public void setOccurrences(Integer occurrences) {
		this.occurrences = occurrences;
	}

	public RecurrenceType getRecurrenceType() {
		return recurrenceType;
	}

	public void setRecurrenceType(RecurrenceType recurrenceType) {
		this.recurrenceType = recurrenceType;
	}

	public Boolean getMondayRecurrence() {
		return mondayRecurrence;
	}

	public void setMondayRecurrence(Boolean mondayRecurrence) {
		this.mondayRecurrence = mondayRecurrence;
	}

	public Boolean getTuesdayRecurrence() {
		return tuesdayRecurrence;
	}

	public void setTuesdayRecurrence(Boolean tuesdayRecurrence) {
		this.tuesdayRecurrence = tuesdayRecurrence;
	}

	public Boolean getWednesdayRecurrence() {
		return wednesdayRecurrence;
	}

	public void setWednesdayRecurrence(Boolean wednesdayRecurrence) {
		this.wednesdayRecurrence = wednesdayRecurrence;
	}

	public Boolean getThursdayRecurrence() {
		return thursdayRecurrence;
	}

	public void setThursdayRecurrence(Boolean thursdayRecurrence) {
		this.thursdayRecurrence = thursdayRecurrence;
	}

	public Boolean getFridayRecurrence() {
		return fridayRecurrence;
	}

	public void setFridayRecurrence(Boolean fridayRecurrence) {
		this.fridayRecurrence = fridayRecurrence;
	}

	public Boolean getSaturdayRecurrence() {
		return saturdayRecurrence;
	}

	public void setSaturdayRecurrence(Boolean saturdayRecurrence) {
		this.saturdayRecurrence = saturdayRecurrence;
	}

	public Boolean getSundayRecurrence() {
		return sundayRecurrence;
	}

	public void setSundayRecurrence(Boolean sundayRecurrence) {
		this.sundayRecurrence = sundayRecurrence;
	}
	 
	 
}
