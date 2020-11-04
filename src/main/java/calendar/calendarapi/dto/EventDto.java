package calendar.calendarapi.dto;

import java.time.Duration;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;


public class EventDto {

	@ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY)
	private Long eventDefinitionId;

	@ApiModelProperty(required = true, example = "An event name")
	@JsonProperty(required = true)
	private String name;
	
	@ApiModelProperty(
			  value = "start date and timf of the event with ISO8601 format",
			  name = "startDate",
			  dataType = "String",
			  example = "2020-12-02T16:11:55.262+00:00",
			  required = true)
	@JsonFormat(shape = Shape.STRING)
	@JsonProperty(required = true)
	private OffsetDateTime startDateTime;
	
		
	@ApiModelProperty(
			  value = "The event duration in ISO_8601. Format: PThHmM. For instance 'PT3H30M'",
			  name = "duration",
			  dataType = "String",
			  example = "PT3H30M",
			  required = true)
	
	@JsonFormat(shape = Shape.STRING)
	@JsonProperty(required = true)
	private Duration duration;	
	
	@ApiModelProperty(required = false)
	@JsonProperty(required = false)
	private RecurrenceDto recurrence;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getEventDefinitionId() {
		return eventDefinitionId;
	}
	public void setEventDefinitionId(Long eventDefinitionId) {
		this.eventDefinitionId = eventDefinitionId;
	}
	
	public Duration getDuration() {
		return duration;
	}
	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	public RecurrenceDto getRecurrence() {
		return recurrence;
	}
	public void setRecurrence(RecurrenceDto recurrence) {
		this.recurrence = recurrence;
	}
	public OffsetDateTime getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(OffsetDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	
}
