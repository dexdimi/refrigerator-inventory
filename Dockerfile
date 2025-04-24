FROM openjdk:23-jdk-slim

WORKDIR /app

ARG JAR_FILE=build/libs/fridge-service-1.0.0.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]