FROM openjdk:14-jdk-alpine

# Arguments
ARG JAR_FILE

# Create app directory
WORKDIR /app/batch/jobs/housekeeping-batch

# Environment
ENV TZ=Europe/Berlin

# Copy JAR file
ADD ${JAR_FILE} housekeeping-batch.jar

# Entrypoint
ENTRYPOINT ["java","-jar","housekeeping-batch.jar"]
