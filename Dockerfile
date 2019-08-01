FROM openjdk:12-jdk-alpine

ARG JAR_FILE
COPY ${JAR_FILE} to-doList-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "to-doList-0.0.1-SNAPSHOT.jar"]
