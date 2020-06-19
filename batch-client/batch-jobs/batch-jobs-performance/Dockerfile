FROM openjdk:14-jdk-alpine

# Arguments
ARG JAR_FILE

# Create app directory
WORKDIR /app/batch/jobs/performance-consolidation

# Environment
ENV TZ=Europe/Berlin

# Copy JAR file
ADD ${JAR_FILE} performance-consolidation.jar

# Entrypoint
ENTRYPOINT ["java","-jar","performance-consolidation.jar"]
