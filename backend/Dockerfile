FROM maven:3.8.6-eclipse-temurin-17-alpine AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project file to the container
COPY pom.xml .

# Download the project dependencies
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline -B

# Copy the application source code to the container
COPY src ./src

# Build the application
RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests

FROM builder

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Specify the command to run the Spring Boot application
CMD ["java", "-jar", "app.jar"]