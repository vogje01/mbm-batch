FROM openjdk:12-jdk-alpine
ARG JAR_FILE

# Create app directory
WORKDIR /app/fis-batch/fis-batch-manager

ADD ${JAR_FILE} fis-batch-manager.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","fis-batch-manager.jar"]
