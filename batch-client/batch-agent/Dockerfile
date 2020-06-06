FROM openjdk:12-jdk-alpine
ARG JAR_FILE

# Create app directory
WORKDIR /app/fis-batch/fis-batch-agent

ADD ${JAR_FILE} fis-batch-agent.jar
ENTRYPOINT ["java","-jar", "fis-batch-agent.jar"]
