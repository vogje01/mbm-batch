FROM openjdk:14-jdk-alpine
ARG JAR_FILE
ENV HOST_NAME batch-agent-01
ENV NODE_NAME batch-agent-01

# Create app directory
WORKDIR /app/batch/batch-agent

RUN mkdir ./jobs

ADD ${JAR_FILE} batch-agent.jar

ENTRYPOINT ["java","-Dmbm.agent.docker.hostname=${HOST_NAME}","-Dmbm.agent.hostName=${HOST_NAME}","-Dmbm.agent.nodeName=${NODE_NAME}","-Dlogging.config=classpath:batch-agent-log.yml","-Dspring.config.location=classpath:batch-agent.yml", "-Djasypt.encryptor.password=1Qaz0oKm", "-jar", "batch-agent.jar"]
