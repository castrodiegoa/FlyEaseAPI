FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/flyease-api-rest-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_api_flyease.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "app_api_flyease.jar"]