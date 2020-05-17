FROM openjdk:12-jdk-alpine

# Arguments
ARG JAR_FILE

# Create app directory
WORKDIR /app/fis-batch/jobs/fis-housekeeping-data

# Environment
ENV TZ=Europe/Berlin

# Copy JAR file
ADD ${JAR_FILE} fis-housekeeping-data.jar

# Entrypoint
ENTRYPOINT ["java","-jar","fis-housekeeping-data.jar"]
