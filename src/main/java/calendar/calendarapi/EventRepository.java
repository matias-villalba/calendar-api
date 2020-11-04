package calendar.calendarapi;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import calendar.calendarapi.model.Event;

public interface EventRepository extends CrudRepository<Event, Long>{

	@Query(value = "from Event as e where "
			+ 	"  (e.startDate < :toDate or (e.startDate = :toDate and e.startTime <= :toTime)) "
			+ 	"  and ("
			+ 	"     ( e.startDate > :fromDate or (e.startDate = :fromDate and e.startTime >= :fromTime))"
			+ 	"      or ("
			+ 	"	       ( e.startDate < :fromDate or (e.startDate = :fromDate and e.startTime < :fromTime) )"
			+ 	"             and "
			+ 	"          ( e.recurrence.recurrenceType is not null and (e.recurrence.endType in('NEVER', 'AFTER_OCCURRENCES') "
			+ 	"													or "
			+ 	"											   (e.recurrence.endDate is not null and e.recurrence.endDate > :fromDate )"
			+ "												   ) "
			+ "				) "
			+ 	"         )"
			+ 	"  )")
	List<Event> findEventsInRange(@Param(value = "fromDate") LocalDate fromDate, @Param(value = "fromTime") OffsetTime fromTime, @Param(value = "toDate") LocalDate toDate, @Param(value = "toTime") OffsetTime toTime);
}
