FROM openjdk:12-jdk-alpine

# Arguments
ARG JAR_FILE

# Create app directory
WORKDIR /app/fis-batch/jobs/fis-performance-consolidation

# Environment
ENV TZ=Europe/Berlin

# Copy JAR file
ADD ${JAR_FILE} fis-performance-consolidation.jar

# Entrypoint
ENTRYPOINT ["java","-jar","fis-performance-consolidation.jar"]
