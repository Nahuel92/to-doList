# Execute stage
FROM openjdk:16-jdk-alpine AS app-image

ENV SPRING_PROFILES_ACTIVE docker
ENTRYPOINT ["java", "-jar", "app.jar"]

COPY /target/*.jar app.jar

# Unset SUID and GUID permissions to get a hardened docker image
RUN for i in `find / -perm +6000 -type f`; do chmod a-s $i; done
