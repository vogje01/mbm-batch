FROM openjdk:14-jdk-alpine
ARG JAR_FILE

# Create app directory
WORKDIR /app/batch/batch-listener

ADD ${JAR_FILE} batch-listener.jar

ENTRYPOINT ["java","-Dlogging.config=classpath:batch-listener-log.yml","-Dspring.config.location=classpath:batch-listener.yml", "-Djasypt.encryptor.password=1Qaz0oKm", "-jar", "batch-listener.jar"]
