FROM openjdk:8-jre-alpine
COPY target/dashboard-service-1.0.0.jar dashboard-service-1.0.0.jar
ENTRYPOINT ["java","-jar","/dashboard-service-1.0.0.jar"]