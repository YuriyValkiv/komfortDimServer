FROM maven:3.9.4-eclipse-temurin-11-alpine
COPY . .
RUN mvn clean package -DskipTests

# Use an OpenJDK base image
FROM openjdk:11

# Set the working directory inside the container
#WORKDIR /app

#VOLUME /tmp

# Copy the JAR file from the target directory on the host into the container
COPY --from=build /target/komfort-0.0.1.jar komfort.jar

ENTRYPOINT ["java", "-jar", "/komfort.jar"]

# Expose the port your application listens on
EXPOSE 8080

# Define the command to run your application
#CMD ["java", "-jar", "komfort-0.0.1.jar"]
