FROM openjdk:17-jdk-alpine
VOLUME /main-app
ADD target/organizr-1.0.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]