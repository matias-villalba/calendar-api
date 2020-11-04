CREATE TABLE events(
 id BIGSERIAL PRIMARY KEY,
 name VARCHAR (255) NOT NULL,
 start_date date NOT NULL,
 start_time time with time zone NOT NULL,
 duration BIGINT NOT NULL, 
 
 end_type VARCHAR (64) NULL,
 end_date timestamp with time zone NULL,
 occurrences INTEGER NULL,

 recurrence_type VARCHAR (64) NULL,
 monday_recurrence BOOLEAN NULL,
 tuesday_recurrence BOOLEAN NULL,
 wednesday_recurrence BOOLEAN NULL,
 thursday_recurrence BOOLEAN NULL,
 friday_recurrence BOOLEAN NULL,
 saturday_recurrence BOOLEAN NULL,
 sunday_recurrence BOOLEAN NULL
);



CREATE INDEX calendar_start_date_and_start_time ON events(start_date, start_time);