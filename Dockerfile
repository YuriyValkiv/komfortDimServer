# Use an OpenJDK base image
FROM openjdk:11

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory on the host into the container
COPY target/komfort.jar komfort-0.0.1.jar

# Expose the port your application listens on
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "komfort-0.0.1.jar"]
