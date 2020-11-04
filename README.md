# calendar-api
Simplified Calendar API


The application uses:
Postgres, Spring Boot, Spring Data, Swagger.
And it can be run with Docker and docker-compose.

There were included some unit tests and integration tests.

## Build app and run application with docker-compose:
Run:
```
docker-compose up --build
```
Running the app with docker it will listen at port: 18080. And the url will be:
http://localhost:18080/calendar/api/swagger-ui/#/

(The three endpoints can be called with swagger).

## Build app and run it in local environment:
Build:
```
mvn clean package
```


In order to run the application in local machine, we will need to pass some env variables to set database connection configs:
```
mvn spring-boot:run -Dspring-boot.run.arguments="--DATABASE_PLATFORM=postgres --DATABASE_URL=postgresql://localhost:5432/calendar --DATABASE_USERNAME=calendaruser --DATABASE_PASSWORD=calendarpass"
```
In this example, the env variables are passed with this values:
```
DATABASE_PLATFORM=postgres
DATABASE_URL=postgresql://localhost:5432/calendar DATABASE_USERNAME=calendaruser
DATABASE_PASSWORD=calendarpass
```

Running the app in the local machine it will listen at port: 8080. And the url will be:
http://localhost:18080/calendar/api/swagger-ui/#/

(The three endpoints can be called with swagger).



swagger ui url:
http://localhost:8080/calendar/api/swagger-ui/


## Simple event creation:
### Example of a simple event creation:
POST
http://localhost:8080/calendar/api/events

```
{
  "name": "An event X",
  "startDateTime": "2020-12-25T16:11:55.262Z",
  "duration": "PT3H30M"
}
```
Duration format is: ISO8601 


## Recurrent event creation:

##### recurrence.endType can be: 'AFTER_OCCURRENCES', 'ON_A_DATE' or 'NEVER'
If recurrence.endType is 'ON_A_DATE' we must pass the field recurrence.endDate with the format 'yyyy-mm-dd'.

If recurrence.endType is 'AFTER_OCCURRENCES' we must pass the field: recurrence.occurrences indicating the number of occurrences.


##### recurrence.recurrenceType can be: 'DAILY', 'WEEELKY' or 'MONTHLY'
If recurrence.recurrenceType is 'WEEKLY' we must pass set the days of the week using the fields:
```
 recurrence.mondayRecurrence
 recurrence.tuesdayRecurrence
 recurrence.wednesdayRecurrence
 recurrence.thursdayRecurrence
 recurrence.fridayRecurrence
 recurrence.saturdayRecurrence
 recurrence.sundayRecurrence
```

### Example of a DAILY recurrent event creation that ends after 5 occurrences:
POST
http://localhost:8080/calendar/api/events

```
 {
    "eventDefinitionId": 7,
    "name": "A daily event at 18:30",
    "startDateTime": "2020-12-26T18:30:00Z",
    "duration": "PT1H",
    "recurrence": {
      "endType": "AFTER_OCCURRENCES",
      "occurrences": 5,
      "recurrenceType": "DAILY"
    }
  }
```

### Example of a WEEKLY recurrent event that has place on monday and tuesday. And it ends on a specific endDate:
POST
http://localhost:8080/calendar/api/events

```
 {
    "name": "event that ends on 2021-03-14",
    "startDateTime": "2020-12-26T18:30:00Z",
    "duration": "PT1H",
    "recurrence": {
      "endType": "ON_A_DATE",
      "endDate": "2021-03-14",
      "recurrenceType": "WEEKLY",
      "mondayRecurrence": true,
      "tuesdayRecurrence": true,
      "wednesdayRecurrence": false,
      "thursdayRecurrence": false,
      "fridayRecurrence": false,
      "saturdayRecurrence": false,
      "sundayRecurrence": false
    }
  }
  ```