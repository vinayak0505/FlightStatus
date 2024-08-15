#I Use an official Maven image to build the application
FROM maven:3.8.4-openjdk-17 AS build
# Set the working directory
WORKDIR /app
# Copy the pom.xml file and the source code
COPY pom.xml
COPY env.properties
COPY src ./src
# Package the application
RUN mvn clean package -DskipTests
# Use an official OpenJDK runtime as a parent image
FROM amd64/openjdk:17
# Set the working directory in the container
WORKDIR /app
# Copy the packaged jar file from the build stage
COPY --from=build /app/target/*.jar app.jar 
COPY --from=build /app/env.properties env.properties
# Make port 8080 available to the world outside this container
EXPOSE 8080
# Run the jar file
ENTRYPOINT ["java","-jar", "app. jar"]