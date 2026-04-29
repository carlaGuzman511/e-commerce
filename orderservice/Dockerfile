FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=dev
ENV SERVER_PORT=8084

EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]
