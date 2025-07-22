# Use a base image with JDK 21
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Maven wrapper and pom.xml to leverage Docker cache
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy the rest of the application source code
COPY src ./src
COPY uploads ./uploads

# Package the application
RUN ./mvnw package -DskipTests

# Expose the port the application runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/backend-0.0.1-SNAPSHOT.jar"]
