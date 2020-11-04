FROM maven:3.6.3-jdk-11 AS BUILD

COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package -DskipTests

FROM openjdk:11.0.9-jdk-buster
WORKDIR /app/
COPY --from=BUILD /build/target/calendar-api-1.0.0.jar /app/

ENTRYPOINT ["java", "-jar", "calendar-api-1.0.0.jar"]