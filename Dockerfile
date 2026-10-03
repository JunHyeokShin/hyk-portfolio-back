FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

ARG JAR_FILE=build/libs/hyk-portfolio-back.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]
