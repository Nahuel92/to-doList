# Build stage
FROM maven:3.6.2-jdk-13 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn -e -B verify

# Execute stage
FROM openjdk:13-jdk-alpine
COPY --from=builder /app/target/to-doList-0.0.1-SNAPSHOT.jar to-doList.jar
CMD ["java", "-jar", "to-doList.jar"]