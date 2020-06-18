FROM openjdk:14-jdk-alpine
ARG JAR_FILE

# Create app directory
WORKDIR /app/batch/batch-scheduler
RUN mkdir ./jobs ./dropins

ADD ${JAR_FILE} batch-scheduler.jar

ENTRYPOINT ["java","-Dlogging.config=classpath:batch-scheduler-log.yml","-Dspring.config.location=classpath:batch-scheduler.yml", "-Djasypt.encryptor.password=1Qaz0oKm", "-jar", "batch-scheduler.jar"]
