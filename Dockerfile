# Use Maven to build the application
FROM maven:3.8.4-openjdk-11 AS builder

# Set working directory
WORKDIR /app


# Copy the entire project
COPY . /app

# Build the application
RUN mvn package -DskipTests

# Use OpenJDK 11 as base image
FROM openjdk:11

# Set working directory
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/urlshortener-api/target/urlshortener-api-0.0.1.jar .

# Expose port 8080
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "urlshortener-api-0.0.1.jar"]
