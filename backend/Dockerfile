# Use Zulu OpenJDK 11 as the base image
FROM azul/zulu-openjdk:11

# Working directory in the container
WORKDIR /app

# Copy the JAR file to the image
COPY ./build/libs/*.jar /app/app.jar

# Set the start command
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Note: EXPOSE is omitted since the port information is in application.yml
