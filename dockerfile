FROM openjdk:22-jdk-slim
WORKDIR /app
COPY target/todo-0.0.1-SNAPSHOT.jar /app/todo.jar
ENTRYPOINT ["java", "-jar", "/app/todo.jar"]
EXPOSE 9210