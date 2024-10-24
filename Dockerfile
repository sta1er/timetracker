FROM openjdk:17-jdk-slim-buster
WORKDIR /app

COPY target/timetracker-0.0.1-SNAPSHOT.jar service.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "service.jar"]