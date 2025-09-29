# Multi-stage build for Customer Microservice
FROM maven:3.8-eclipse-temurin-11 AS build

WORKDIR /app
COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline

COPY src ./src

RUN mvn -q -e -DskipTests package

FROM eclipse-temurin:11-jre

WORKDIR /app

COPY --from=build /app/target/customer_ms-*.jar app.jar

ENV JAVA_OPTS=""

EXPOSE 8081

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]