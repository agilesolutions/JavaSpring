FROM openjdk:21-jdk-slim
VOLUME /tmp
ARG JAR_FILE=build/libs/application.jar
COPY ${JAR_FILE} app.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "/app.jar"]