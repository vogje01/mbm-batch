FROM openjdk:14-jdk-alpine
ARG JAR_FILE

# Create app directory
WORKDIR /app/batch/batch-manager
RUN mkdir ./jobs

ADD ${JAR_FILE} batch-manager.jar

EXPOSE 8090
ENTRYPOINT ["java","-Dlogging.config=classpath:batch-manager-log.yml","-Dspring.config.location=classpath:batch-manager.yml",\
            "-Djasypt.encryptor.password=1Qaz0oKm", "-jar", "batch-manager.jar"]
