package calendar.calendarapi.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import io.swagger.annotations.ApiModelProperty;

public class RecurrenceDto {

	@ApiModelProperty(
			  value = "How the recurrent event ends. Posible values: 'NEVER', 'ON_A_DATE', 'AFTER_OCCURRENCES'",
			  name = "endType",
			  dataType = "String",
			  example = "ON_A_DATE",
			  required = true)
	@JsonProperty(required = true)
	private String endType;
	
	@ApiModelProperty(
			  value = "When the recurrent event ends (Only if endType: 'ON_A_DATE'). Format: yyyy/MM/dd",
			  name = "endDate",
			  dataType = "String",
			  example = "2021-10-26",
			  required = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;
	
	
	@ApiModelProperty(
			  value = "After how many occurrences the recurrent event ends (Only if endType: 'AFTER_OCCURRENCES').",
			  name = "occurrences",
			  dataType = "Integer",
			  example = "6",
			  required = false)
	private Integer occurrences;
	
	@ApiModelProperty(
			  value = "Kind of event recurrence: 'DAILY', 'WEEKLY', 'MONTHLY'",
			  name = "recurrenceType",
			  dataType = "String",
			  example = "WEEKLY",
			  required = true)
	private String recurrenceType;
	
	@ApiModelProperty(
			  value = "The event takes place on every monday (Only if recurrenceType: 'WEEKLY').",
			  example = "true",
			  required = false)
	private Boolean mondayRecurrence;
	
	@ApiModelProperty(
			  value = "The event takes place on every tuesday (Only if recurrenceType: 'WEEKLY').",
			  example = "true",
			  required = false)
	private Boolean tuesdayRecurrence;
	
	@ApiModelProperty(
			  value = "The event takes place on every wednesday (Only if recurrenceType: 'WEEKLY').",
			  example = "true",
			  required = false)
	private Boolean wednesdayRecurrence;
	
	@ApiModelProperty(
			  value = "The event takes place on every thursday (Only if recurrenceType: 'WEEKLY').",
			  example = "true",
			  required = false)
	private Boolean thursdayRecurrence;
	
	@ApiModelProperty(
			  value = "The event takes place on every friday (Only if recurrenceType: 'WEEKLY').",
			  example = "true",
			  required = false)
	private Boolean fridayRecurrence;
	
	@ApiModelProperty(
			  value = "The event takes place on every saturday (Only if recurrenceType: 'WEEKLY').",
			  example = "true",
			  required = false)
	private Boolean saturdayRecurrence;
	
	@ApiModelProperty(
			  value = "The event takes place on every sunday (Only if recurrenceType: 'WEEKLY').",
			  example = "true",
			  required = false)
	private Boolean sundayRecurrence;
	 
	public String getEndType() {
		return endType;
	}
	public void setEndType(String endType) {
		this.endType = endType;
	}
	public Integer getOccurrences() {
		return occurrences;
	}
	public void setOccurrences(Integer occurrences) {
		this.occurrences = occurrences;
	}
	public String getRecurrenceType() {
		return recurrenceType;
	}
	public void setRecurrenceType(String recurrenceType) {
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
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
}
