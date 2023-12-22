FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} reservationservice.jar

ENTRYPOINT ["java", "-jar", "/reservationservice.jar"]

EXPOSE 8588