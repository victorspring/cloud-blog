FROM openjdk:18-jdk-oracle

COPY target/*.jar app.jar

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "/app.jar"]