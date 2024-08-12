# Spring Boot Todo Application

This is a simple Todo application built with Spring Boot. The application allows you to create, update, delete, and toggle the completion status of tasks.

## Prerequisites

Before you can build and run the application, ensure you have the following installed:

- Java JDK 22
- Maven
- Docker
- Docker Compose

## Building and Running the Application

### Option 1: Running the Application Manually

You can run the application manually by following these steps:

1. **Build the Application**

   Use Maven to build the application and create a JAR file:

   ```bash
   mvn clean install

2. **Run the Application**

   After building, you can run the application with the following command:
   ```bash
   java -jar target/todo-0.0.1-SNAPSHOT.jar

The application will start on port 9210 by default. You can access it at http://localhost:9210 and http://localhost:9210/swagger-ui/


**Option 2: Running the Application with Docker Compose**
Alternatively, you can run the application in a Docker container using Docker Compose.

Build the Application

1. First, build the application using Maven:
   ```bash
   mvn clean install

2. **Build the Docker Image**
   ```bash
   docker build -t todo-app .

3. **Run the Application**
   
   Finally, use Docker Compose to start the application:
   ```bash
   docker-compose up -d

This will start the application in detached mode. The application will be accessible at http://localhost:9210.
