FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY target/user-service.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]