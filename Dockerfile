FROM openjdk:12
COPY ./target/to-doList-0.0.1-SNAPSHOT.jar /usr/src/todo-list
WORKDIR /usr/src/todo-list
EXPOSE 8080
CMD ["java", "-jar", "to-doList-0.0.1-SNAPSHOT.jar"]
