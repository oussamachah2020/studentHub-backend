# Use Maven to build the project
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Set the working directory
WORKDIR /app

# Copy only the necessary project files to cache dependencies
COPY pom.xml .
COPY src ./src

# Run Maven to package the application
RUN mvn clean package -DskipTests

# Use a base image with Java
FROM openjdk:17

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file from the build stage
COPY --from=build /app/target/studentHub-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
