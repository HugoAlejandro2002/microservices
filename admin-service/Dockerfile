FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} adminservice.jar

ENTRYPOINT ["java", "-jar", "/adminservice.jar"]

EXPOSE 8586
