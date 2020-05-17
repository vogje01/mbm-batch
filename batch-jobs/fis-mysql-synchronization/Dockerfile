FROM openjdk:12-jdk-alpine

# Arguments
ARG JAR_FILE

# Create app directory
WORKDIR /app/fis-batch/jobs/fis-mysql-synchronization

# Environment
ENV TZ=Europe/Berlin

# Copy JAR file
ADD ${JAR_FILE} fis-mysql-synchronization.jar

# Entrypoint
ENTRYPOINT ["java","-jar","fis-mysql-synchronization.jar"]
