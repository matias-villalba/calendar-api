CREATE USER calendaruser WITH ENCRYPTED PASSWORD 'calendarpass';
GRANT ALL PRIVILEGES ON database calendar TO calendaruser;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO calendaruser;
GRANT USAGE, SELECT ON SEQUENCE events_id_seq TO calendaruser;