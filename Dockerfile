# Execute stage
FROM openjdk:13-jdk-alpine AS app-image

ENV SPRING_PROFILES_ACTIVE docker

# Get jar file produces by Maven dockerfile goal
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

# Unset SUID and GUID permissions to get a hardened image
RUN for i in `find / -perm +6000 -type f`; do chmod a-s $i; done

ENTRYPOINT ["java", "-jar", "app.jar"]
