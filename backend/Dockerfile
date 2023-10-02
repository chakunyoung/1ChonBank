# Use Zulu OpenJDK 11 as the base image
FROM azul/zulu-openjdk:11

# Install wget
RUN apt-get update && apt-get install -y wget

# Install dockerize
ENV DOCKERIZE_VERSION v0.6.1
RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz

# Working directory in the container
WORKDIR /app

# Copy the JAR file to the image
COPY ./build/libs/*.jar /app/app.jar

# Set the start command
ENTRYPOINT ["dockerize", "-wait", "tcp://mysql:3306", "-wait", "tcp://redis:6379", "-wait", "tcp://rabbitmq:5672", "-timeout", "60s", "java", "-jar", "/app/app.jar"]
