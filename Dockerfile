# Этап сборки
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Сначала копируем pom.xml и загружаем зависимости
COPY pom.xml .
RUN mvn dependency:go-offline

# Копируем исходники и собираем fat-jar
COPY src ./src
RUN mvn clean package -DskipTests

# Этап запуска
FROM eclipse-temurin:21-jre
WORKDIR /app

# Копируем собранный fat-jar
COPY --from=build /app/target/auth-service-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
